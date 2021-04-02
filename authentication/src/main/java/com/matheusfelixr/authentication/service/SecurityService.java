package com.matheusfelixr.authentication.service;

import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import com.matheusfelixr.authentication.model.dto.MessageDTO;
import com.matheusfelixr.authentication.model.dto.security.AuthenticateRequestDTO;
import com.matheusfelixr.authentication.model.dto.security.AuthenticateResponseDTO;
import com.matheusfelixr.authentication.model.dto.security.CreateUserRequestDTO;
import com.matheusfelixr.authentication.model.dto.security.NewPasswordRequestDTO;
import com.matheusfelixr.authentication.security.JwtTokenUtil;
import com.matheusfelixr.authentication.util.EmailHelper;
import com.matheusfelixr.authentication.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.Optional;

@Component
public class SecurityService implements UserDetailsService {
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoryAuthenticationService historyAuthenticationService;

    @Autowired
    private HistoryResetPasswordService historyResetPasswordService;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	    	
    	Optional<UserAuthentication> userAuthentication = this.userAuthenticationService.findByUserName(username);
    	
    	if(!userAuthentication.isPresent()) {
            throw new UsernameNotFoundException(username + " nao encontrado");
        }
    	
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                userAuthentication.get().getUserName(),
                userAuthentication.get().getPassword(),
                AuthorityUtils.createAuthorityList(userAuthentication.get().getRoles())
        );
		return userDetails;
    }


    public AuthenticateResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO, HttpServletRequest httpServletRequest ) throws Exception {
        try {
            //valida autenticacao
            this.validateAuthenticate(authenticateRequestDTO);
            //Autentica o usuario
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequestDTO.getUsername(), authenticateRequestDTO.getPassword()));
            //Busca o userDetails para geracao do token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticateRequestDTO.getUsername());
            //Gera o token
            String token = jwtTokenUtil.generateToken(userDetails);
            //Busca os dados do usuario
            UserAuthentication userAuthentication = this.userAuthenticationService.findByUserName(authenticateRequestDTO.getUsername()).get();
            //Gera historico
            historyAuthenticationService.generateHistorySuccess(userAuthentication, httpServletRequest );
            return new AuthenticateResponseDTO(userAuthentication.getUserName(), token,userAuthentication.getChangePassword(), userAuthentication.getIsAdmin());
        } catch (DisabledException e) {
            historyAuthenticationService.generateHistoryFail(authenticateRequestDTO, httpServletRequest, "Usuário desabilitado: " + authenticateRequestDTO.getUsername());
            throw new ValidationException("Usuário desabilitado");
        } catch (BadCredentialsException e) {
            historyAuthenticationService.generateHistoryFail(authenticateRequestDTO, httpServletRequest, "Senha invalida para o usuário: " + authenticateRequestDTO.getUsername());
            throw new ValidationException("Verifique se digitou corretamente usuário e senha.");
        }
    }

    private void validateAuthenticate(AuthenticateRequestDTO authenticateRequestDTO) throws ValidationException {
        if(authenticateRequestDTO.getUsername() == null || authenticateRequestDTO.getUsername().length() == 0 ){
            throw new ValidationException("Usuário não pode ser vazio");
        }
        if(authenticateRequestDTO.getPassword() == null || authenticateRequestDTO.getUsername().length() == 0 ){
            throw new ValidationException("Senha não pode ser vazio");
        }
        authenticateRequestDTO.setUsername(authenticateRequestDTO.getUsername().trim());
    }

    public MessageDTO resetPassword(String userName, HttpServletRequest httpServletRequest) throws Exception {
        String password = Password.generatePasswordInt(5);
        UserAuthentication userAuthentication = userAuthenticationService.modifyPassword(userName, password , true);
        emailService.resetPassword(userAuthentication, password);
        historyResetPasswordService.generateHistory(userAuthentication, httpServletRequest);
        return new MessageDTO ("Foi enviado uma nova senha para o E-mail: "+EmailHelper.maskEmail(userAuthentication.getEmail()));
    }


    public MessageDTO createUser(CreateUserRequestDTO createUserRequestDTO) throws Exception {
        // Pego senha
        String password = this.getPassword(createUserRequestDTO);

        //Cria objeto a ser salvo
        UserAuthentication ret = this.getUserAuthentication(createUserRequestDTO, password);

        //chama metodo para criar usario
        this.userAuthenticationService.create(ret);

        return new MessageDTO ("Usuário cadastrado com sucesso! Foi enviada a senha para o E-mail: " + EmailHelper.maskEmail(ret.getEmail()));
    }

    private UserAuthentication getUserAuthentication(CreateUserRequestDTO createUserRequestDTO, String password) {
        UserAuthentication ret = new UserAuthentication();
        ret.setUserName(createUserRequestDTO.getUsername().trim());
        ret.setPassword(password);
        ret.setEmail(createUserRequestDTO.getEmail());
        ret.setChangePassword(true);
        ret.setIsAdmin(createUserRequestDTO.getIsAdmin());
        return ret;
    }

    private String getPassword(CreateUserRequestDTO createUserRequestDTO) {
        String password ="";
        if(createUserRequestDTO.getPassword() == null || createUserRequestDTO.getPassword().equals("") ){
            password = Password.generatePasswordInt(5);
        }else{
            password = createUserRequestDTO.getPassword();
        }
        return password;
    }

    public AuthenticateResponseDTO newPassword(NewPasswordRequestDTO newPasswordRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
        UserAuthentication userAuthentication = userAuthenticationService.modifyPassword(newPasswordRequestDTO.getUserName(), newPasswordRequestDTO.getPassword() , false);
        emailService.newPassword(userAuthentication);
        return this.authenticate(new AuthenticateRequestDTO(newPasswordRequestDTO.getUserName(), newPasswordRequestDTO.getPassword()), httpServletRequest);
    }

    public UserAuthentication getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();

        Optional<UserAuthentication> ret = userAuthenticationService.findByUserName(principal.getUsername());
        if(!ret.isPresent()){
            throw new ValidationException("Erro ao encontrar usuario. Contate o desenvolvedor.");
        }

        return ret.get();
    }
}