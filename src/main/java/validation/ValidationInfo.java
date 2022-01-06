package validation;

import exception.InValidUserInfoException;

public class ValidationInfo {

    public static boolean isValidInfo(String input) {
        String check = "";
        String[] split = input.split(",");
        if (!isValidCharacter(split[0])) {
            check += " firstName";
        } else if (!isValidCharacter(split[1])) {
            check += " lastName";

        } else if (!isValidEmail(split[2])) {
            check += " email";

        } else if (!isValidPassword(split[3])) {
            check += " password";
        } else if (!isValidPhoneNumber(split[4])) {
            check += " phoneNumber";

        } else if (!isValidNumeric(split[5])) {
            check += " credit";
        }

        if (check.length() > 1)
            throw new InValidUserInfoException("---- " + check + " is not valid you should enter alphabet----");
        else return true;
    }

    public static boolean isValidCharacter(String input) {
        if (input.matches("^[a-zA-Z]+$"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter alphabet----");
    }

    public static boolean isValidNumeric(String input) {
        if (input.matches("[0-9]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter numeric----");
    }

    public static boolean isValidLogin(String input) {
        if (input.matches("[1-3]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter number 1-3 ----");
    }

    public static boolean isValidEmail(String input) {
        if (input.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"))
            return true;
        throw new InValidUserInfoException("---- email that entered is not valid ----");
    }


    public static boolean isValidPassword(String input) {
        if (input.matches("^[a-zA-Z0-9]{8}+$"))
            return true;
        throw new InValidUserInfoException("---- password that entered is not valid ----");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("^(\\+98|0)?9\\d{9}$"))
            return true;
        throw new InValidUserInfoException("---- phoneNumber  that entered is not valid ----");
    }

}
