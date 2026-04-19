package com.Pro_Connect.userService.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Bcrypt {

    public static String hash(String s){
        return BCrypt.hashpw(s, BCrypt.gensalt());
    }
    public static boolean check(String passwordText, String s){
        return BCrypt.checkpw(passwordText, s);
    }
}
