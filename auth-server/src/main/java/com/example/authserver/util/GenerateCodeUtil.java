package com.example.authserver.util;

import java.security.SecureRandom;

public final class GenerateCodeUtil {
    private GenerateCodeUtil(){}

    public static String generateCode(){
        String code;
        try{
            SecureRandom random=
                    SecureRandom.getInstanceStrong();
            int c=random.nextInt(9000)+1000;
            code=String.valueOf(c);
        }catch (Exception e){
            throw new RuntimeException("Problem with generating the random code.");
        }
        return code;
    }
}
