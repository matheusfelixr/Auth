package com.matheusfelixr.authentication.service;

import com.matheusfelixr.authentication.model.domain.HistoryAuthentication;
import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import com.matheusfelixr.authentication.repository.HistoryAuthenticationRepository;
import com.matheusfelixr.authentication.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.Optional;

@Service
public class HistoryAuthenticationService {

    @Autowired
    private HistoryAuthenticationRepository historyAuthenticationRepository;

    public HistoryAuthentication generateHistorySucess(UserAuthentication userAuthentication, HttpServletRequest httpServletRequest ){
        HistoryAuthentication historyAuthentication = new HistoryAuthentication();
        historyAuthentication.setUserAuthentication(userAuthentication);
        historyAuthentication.setDate(new Date());
        historyAuthentication.setObservation("Login realizado com sucesso");
        if(httpServletRequest != null){
            historyAuthentication.setIp(httpServletRequest.getRemoteAddr());
        }
        return this.save(historyAuthentication);
    }

    public HistoryAuthentication generateHistoryFail(String userName, HttpServletRequest httpServletRequest, String observation ){
        HistoryAuthentication historyAuthentication = new HistoryAuthentication();
        historyAuthentication.setDate(new Date());
        historyAuthentication.setObservation(observation);
        if(httpServletRequest != null){
            historyAuthentication.setIp(httpServletRequest.getRemoteAddr());
        }
        return this.save(historyAuthentication);
    }


    private HistoryAuthentication save(HistoryAuthentication historyAuthentication){
        return historyAuthenticationRepository.save(historyAuthentication);
    }

}
