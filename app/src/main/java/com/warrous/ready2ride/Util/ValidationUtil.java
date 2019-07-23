package com.warrous.ready2ride.Util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidationUtil {

    private static final String WARROUS_DOMAIN = "gmail.com";

    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }


    public static boolean isEmptyAll(String... values) {

        for (String value : values) {
            if (!isEmpty(value)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Validate whether given string is valid email or not.
     *
     * @param target
     * @return true , if valid els false.
     */
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidMobile(String phone) {

        String phoneNo=phone.trim();

        if(phoneNo.length()<10 || phoneNo.length()>10){
            return false;

        }
        return true;

    }
    public static boolean isValidPassword(String password){
        return password.length()==4;
    }

    public static boolean isValidPinCode(String password){
        return password.length()==6;
    }

    public static boolean isMobileNo(String phone) {
        try {
            Double.parseDouble(phone);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }

    public static boolean isValidMobile(String contactNum, String emergencyNum) {
        if (contactNum.equals(emergencyNum)) {
            return true;
        }
        return false;


    }
    public static boolean isValidName(String name){

        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(name);
        boolean bs = ms.matches();
        if (bs == false) {
            return false;
        }

        return true;
    }



    public static boolean isValidDomain(String email) {
        String domain = getEmailDomain(email);
        if (domain.equalsIgnoreCase(WARROUS_DOMAIN)) {
            return true;
        }
        return false;
    }

    private static String getEmailDomain(String email) {
        return email.substring(email.indexOf('@') + 1);
    }


    public static String getFormateText(String value){
        return value + "<font color='#800080'><sup>*</sup></font>";
    }

}
