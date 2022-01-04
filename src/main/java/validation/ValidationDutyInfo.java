package validation;

import exception.InValidUserInfoException;

public class ValidationDutyInfo {
    public static boolean isValidInfo(String input) {
        String check = "";
        String[] split = input.split(",");
        if (!isValidCharacter(split[0])) {
            check += "name of service";
        } else if (!isValidCharacter(split[1])) {
            check += " Description";
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


}