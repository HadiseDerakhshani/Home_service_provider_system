package validation;

import exception.InValidUserInfoException;

public class ValidationUtils {
   /* public static boolean isValidMenu(String input) {
        if (input.matches("[1-2]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter number 1 or 2----");
    }*/


    public static boolean isValidInfo(String input) {
        String check = "";
        String[] split = input.split(",");
        if (isValidCharacter(split[0])) {
            check = "firstName";
            return true;
        } else if (isValidCharacter(split[1])) {
            check = "lastName";
            return true;
        } else if (isValidEmail(split[2])) {
            check = "email";
            return true;
        } else if (isValidPassword(split[3])) {
            check = "password";
            return true;
        } else if (isValidPhoneNumber(split[4])) {
            check = "phoneNumber";
            return true;
        } else if (isValidNumeric(split[5])) {
            check = "credit";
            return true;
        }
        throw new InValidUserInfoException("---- " + check + " is not valid you should enter alphabet----");
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

    /* public static boolean isValidSelectSearch(String input) {
         if (input.matches("[1-3]+"))
             return true;
         throw new InValidUserInfoException("----entered is not valid you should enter number 1-3 ----");
     }*/
    public static boolean isValidEmail(String input) {
        if (input.matches("^[A-Z0-9+_.-]+@[A-Z0-9.-]+$"))
            return true;
        throw new InValidUserInfoException("---- email that entered is not valid ----");
    }

    /* public static boolean isValidSelectFilter(String input) {
         if (input.matches("[1-8]+"))
             return true;
         throw new InValidUserInfoException("----entered is not valid you should enter number 1-8 ----");
     }*/
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

   /* public  static boolean isValidSplitFilter(String input){
        if(input.matches("[1-8]+"))
            return  true;
        throw  new InValidUserInfoException("----entered is not valid you should enter number 1-8 ----");
    }*/
}
