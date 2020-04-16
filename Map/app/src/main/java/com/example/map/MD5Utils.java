package com.example.map;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String encode(String str){
        StringBuffer buffer = new StringBuffer();

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            for(byte b : digest){
                int x = b & 0xff;
                String s = Integer.toHexString(x);
                if(s.length() == 1){
                    s = "0" + s;
                }
                buffer.append(s);

            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
