package com.example.ecobeeloginapp.data;

/***
 * Utils class to get the hardcoded data
 */
public class DataUtils {

    private static String userNameToValidate = "user1";
    private static String passwordToValidate = "12345";

    public static String getPasswordToValidate() {
        return passwordToValidate;
    }

    public static String getUserNameToValidate() {
        return userNameToValidate;
    }

}
