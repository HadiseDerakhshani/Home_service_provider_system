package validation;

import exception.InValidUserInfoException;

public class ValidationUpdateExpert {

    public static boolean isValidInfo(String input, String value) {
        String check = "";
        switch (input) {
            case "1":
                if (!isValidCharacter(value))
                    check += " firstName";
                break;
            case "2":
                if (!isValidCharacter(value))
                    check += " lastName";
                break;
            case "3":
                if (!isValidEmail(value))
                    check += " email";
                break;
            case "4":
                if (!isValidPassword(value))
                    check += " password";
                break;
            case "5":
                if (!isValidPhoneNumber(value))
                    check += " phoneNumber";
                break;
            case "6":
                if (!isValidNumeric(value))
                    check += " credit";
                break;
            case " 7":
                if (!isValidScore(value))
                    check += " Score";
                break;
        }
        if (check.length() > 1)
            throw new InValidUserInfoException("---- " + check + " is not valid ----");
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

    public static boolean isValidScore(String input) {
        if (input.matches("[1-10]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter numeric----");
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

    public static boolean isValidByte(int image) {
        if (image <= 300000)
            return true;
        throw new InValidUserInfoException("---- the byte of picture is more than 300 Kilobyte----");
    }

    public static boolean isValidSelectUpdate(String input) {
        if (input.matches("[1-9]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter numeric----");
    }
}
