package com.matheusfelixr.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

@SpringBootTest
class AuthenticationApplicationTests {

	@Test
	void contextLoads() throws FileNotFoundException {

		Scanner in = new Scanner(new FileReader("D:\\teste.txt"));

		while (in.hasNextLine()) {
			String line = in.nextLine();
			System.out.println(line);


			int index = 0;
			int nextPipe = 0;
			String lineFor = line;
			for(index = 0 ; index < 1;){

				nextPipe= lineFor.indexOf("|");
				if(nextPipe == -1){
					System.out.println(lineFor.substring(0, lineFor.length()) );
					index = 1;
				}else{
					System.out.println(lineFor.substring(0, nextPipe) );
					lineFor = lineFor.substring(nextPipe + 1, lineFor.length());
				}


			}
		}
	}

}
