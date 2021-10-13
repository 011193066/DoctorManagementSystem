package main.java.objects;

import java.util.Locale;

public enum Gender {
    MALE, FEMALE;

    public static Gender getGender(String gender) {
        if (gender.equals("Male")) {
            return MALE;
        } else if (gender.equals("Female")) {
            return FEMALE;
        }
        throw new RuntimeException("Invalid Gender Input");
    }
}
