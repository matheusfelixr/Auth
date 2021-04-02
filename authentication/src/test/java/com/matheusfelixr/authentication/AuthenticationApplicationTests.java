package com.matheusfelixr.authentication;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@SpringBootTest
class AuthenticationApplicationTests {

	@Test
	void contextLoads()  {
		try {
			String ntpServer = "a.st1.ntp.br";//servidor de horario brasileiro

			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByName(ntpServer);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getReturnTime();
			Date time = new Date(returnTime);
			System.out.println("Hora para " + ntpServer + ": " + time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
