package com.matheusfelixr.authentication.util;

import com.matheusfelixr.authentication.controller.SecurityController;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Date;

public class DateNtp {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);


    public static Date getDate(){
        try {
            String ntpServer = "a.st1.ntp.br";//servidor de horario brasileiro

            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(ntpServer);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getReturnTime();
            Date time = new Date(returnTime);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Erro inesperado ao encontrar data NTP.br ");
            return null;
        }
    }
}
