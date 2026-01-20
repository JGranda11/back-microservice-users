package com.pragma.challenge.msvc_users.application.util;

public class ApplicationConstants {

    private ApplicationConstants(){
        throw new IllegalStateException("Utility class");
    }

    // Regex
    public static final String EMAIL_ADDRESS_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    //criterio de aceptacion
    public static final String PHONE_NUMBER_REGEX = "^(\\+\\d{2})?\\d{10}$";
    public static final String IDENTITY_DOCUMENT_REGEX = "^\\d{6,16}";


}
