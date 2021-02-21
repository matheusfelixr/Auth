package com.matheusfelixr.authentication.service;

import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import com.matheusfelixr.authentication.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.xml.bind.ValidationException;
import java.util.Optional;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAuthentication create(UserAuthentication userAuthentication) throws Exception {

        validateNewUser(userAuthentication);

        userAuthentication.setPassword(this.passwordEncoder.encode(userAuthentication.getPassword()));
        return userAuthenticationRepository.save(userAuthentication);
    }

    public Optional<UserAuthentication> findByUserName(String userName){
        return Optional.ofNullable(userAuthenticationRepository.findByUserName(userName));
    }

    public Optional<UserAuthentication> findByEmail(String Email){
        return Optional.ofNullable(userAuthenticationRepository.findByEmail(Email));
    }


    private void validateNewUser(UserAuthentication userAuthentication) throws Exception {
        Optional<UserAuthentication> userResName = this.findByUserName(userAuthentication.getUserName());
        if(userResName.isPresent()) {
            throw new ValidationException("Usuário já existente");
        }

        Optional<UserAuthentication> userResEmail = this.findByEmail(userAuthentication.getEmail());
        if(userResEmail.isPresent()) {
            throw new ValidationException("E-mail já cadastrado para outro usuário");
        }
    }
}
