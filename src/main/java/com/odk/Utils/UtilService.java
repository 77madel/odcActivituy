package com.odk.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilService {

    public static boolean isValidEmail(String email) {
        final String EMAIL_PATTERN =
                "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
