package com.matheusfelixr.authentication.util;

import java.util.Random;

public class Password {

    public static String generatePasswordInt(int numberCharacters ){
        Random random = new Random();
        String ret = "";

        for(ret.length() ; ret.length() < numberCharacters; ){
            ret = ret + random.nextInt(1000);
        }
        return ret.substring(0 , numberCharacters );
    }
}
