package com.shopsphere.swing.dao;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

final class PasswordHash {
    private PasswordHash(){ }
    static boolean verify(char[] password,String stored){
        try{String[] p=stored.split("\\$",-1);if(p.length!=4||!p[0].equals("pbkdf2-sha256"))return false;
            PBEKeySpec spec=new PBEKeySpec(password,Base64.getDecoder().decode(p[2]),Integer.parseInt(p[1]),256);
            byte[] actual=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();spec.clearPassword();
            return MessageDigest.isEqual(actual,Base64.getDecoder().decode(p[3]));
        }catch(Exception ex){return false;}
    }
}
