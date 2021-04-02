package com.matheusfelixr.authentication.service;

import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import com.matheusfelixr.authentication.model.dto.config.EmailFormatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void resetPassword(UserAuthentication userAuthentication, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        EmailFormatDTO emailFormatDTO =   new EmailFormatDTO("noreply <matheusfelixr@hotmail.com>",
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

    public void newUser(UserAuthentication userAuthentication, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        EmailFormatDTO emailFormatDTO =   new EmailFormatDTO("noreply <matheusfelixr@hotmail.com>",
                Arrays.asList(userAuthentication.getUserName()+"<"+userAuthentication.getEmail()+">")
                , "Cadastro de novo usuário",
                "Você acaba de ser cadastrado no sistema, seu usuário é: "+ userAuthentication.getUserName() +" e a sua senha é: " + password);

        simpleMailMessage.setFrom(emailFormatDTO.getSender());
        simpleMailMessage.setTo(emailFormatDTO.getRecipients()
                .toArray(new String[emailFormatDTO.getRecipients().size()]));
        simpleMailMessage.setSubject(emailFormatDTO.getSubject());
        simpleMailMessage.setText(emailFormatDTO.getBody());

        javaMailSender.send(simpleMailMessage);
    }

    public void newPassword(UserAuthentication userAuthentication) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        EmailFormatDTO emailFormatDTO =   new EmailFormatDTO("noreply <matheusfelixr@hotmail.com>",
                Arrays.asList(userAuthentication.getUserName()+"<"+userAuthentication.getEmail()+">")
                , "Alteração de senha.",
                "Sua senha acaba de ser alterada.");

        simpleMailMessage.setFrom(emailFormatDTO.getSender());
        simpleMailMessage.setTo(emailFormatDTO.getRecipients()
                .toArray(new String[emailFormatDTO.getRecipients().size()]));
        simpleMailMessage.setSubject(emailFormatDTO.getSubject());
        simpleMailMessage.setText(emailFormatDTO.getBody());

        javaMailSender.send(simpleMailMessage);
    }


}