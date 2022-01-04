package validation;

import exception.InValidUserInfoException;

public class ValidationFilterCustomer {
    public static String isValidInfo(String input) {
        String check, checkNull, name = null, family = null, email = null;

        String[] split = input.split(",");
        if (isValidLength(split.length)) {
            checkNull = isValidCharacter(split[0]);
            if (checkNull.equals("true")) {
                name = (split[0]);
            }
            checkNull = isValidCharacter(split[1]);
            if (checkNull.equals("true")) {
                family = split[1];
            }
            checkNull = isValidEmail(split[2]);
            if (checkNull.equals("true")) {
                email = split[2];
            }
            check = name + "," + family + "," + email;
            if (check.length() > 1)
                return check;

            throw new InValidUserInfoException("---- is not valid ----");
        }
        throw new InValidUserInfoException("----entered more than invalid you shod 1 or 2 or 3 case for filter ----");
    }

    public static boolean isValidLength(int input) {
        if (input >= 1 && input < 4)
            return true;
        throw new InValidUserInfoException("----entered more than invalid you shod 1 or 2 or 3 case for filter ----");
    }

    public static String isValidCharacter(String input) {
        if (input.matches("^[a-zA-Z]+$"))
            return "true";
        else if (input.equals("0"))
            return "false";
        throw new InValidUserInfoException("----entered is not valid you should enter alphabet----");
    }

    public static String isValidEmail(String input) {
        if (input.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"))
            return "true";
        else if (input.equals("0"))
            return "false";

        throw new InValidUserInfoException("---- email that entered is not valid ----");
    }

    public static boolean isValid(String input) {
        if (input.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"))
            return true;

        throw new InValidUserInfoException("---- email that entered is not valid ----");
    }
}
