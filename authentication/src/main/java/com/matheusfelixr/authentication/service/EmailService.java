package com.matheusfelixr.authentication.service;

import com.matheusfelixr.authentication.model.DTO.config.EmailFormatDTO;
import com.matheusfelixr.authentication.model.DTO.security.AuthenticateRequestDTO;
import com.matheusfelixr.authentication.model.DTO.security.AuthenticateResponseDTO;
import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import com.matheusfelixr.authentication.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void resetPassword(UserAuthentication userAuthentication, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        EmailFormatDTO emailFormatDTO =   new EmailFormatDTO("Sistem <matheusfelixr@hotmail.com>",
                Arrays.asList(userAuthentication.getUserName()+"<"+userAuthentication.getEmail()+">")
                , "Reset de senha",
                "Sua nova senha e: " + password);

        simpleMailMessage.setFrom(emailFormatDTO.getSender());
        simpleMailMessage.setTo(emailFormatDTO.getRecipients()
                .toArray(new String[emailFormatDTO.getRecipients().size()]));
        simpleMailMessage.setSubject(emailFormatDTO.getSubject());
        simpleMailMessage.setText(emailFormatDTO.getBody());

        javaMailSender.send(simpleMailMessage);
    }


}