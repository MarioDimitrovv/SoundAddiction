package com.soundaddiction.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Checker {

    //Fields
    private static final int MIN_USERNAME_LENGTH = 2;

    //Methods
    public static final boolean isNotNullOrEmpty(String str) {
        return (str != null) && !str.trim().isEmpty();
    }

    public static final boolean isValidName(String name) {
        //Check if name is a validString
        if(!isNotNullOrEmpty(name)) {
            return false;
        }

        //Check for a minimum length of MIN_USERNAME_LENGTH symbols
        if(name.length() < MIN_USERNAME_LENGTH) {
            return false;
        }

        //Check if each symbol is a lowercase letter
        if(!Character.isUpperCase(name.charAt(0))){
            return false;
        }
        for(int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if(!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isValidEmail(String email){
        //Check if email is a valid string
        if(!isNotNullOrEmpty(email)) {
            return false;
        }

        //Check if email matches the pattern
        Pattern ptr = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return ptr.matcher(email).matches();
    }

    public static final boolean isValidPassword(String str){
        //Regex source - https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
        Pattern ptr = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
        return ptr.matcher(str).matches();
    }

    public static final boolean isValidPhoneNumber(String str){
        return str.length() == 10 && str.charAt(0) == '0' && str.matches("[0-9]+");
    }

    public static final boolean isValidDate(LocalDate date){
        Period period = Period.between(date, LocalDate.now());
        return (period.getYears() < 1) && (period.getMonths() < 1);
    }


}
