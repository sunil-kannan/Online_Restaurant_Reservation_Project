package com.example.project1.validation;

import java.util.regex.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
    private static final String Phone_number = "[6-9][0-9]{9}";
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    
        if (value == null) {
            return true; // Let other validators handle null values
        }

        return Pattern.matches(Phone_number, value);
  
    }

}