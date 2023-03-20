package com.example.FirstSecurityApp.util;

import com.example.FirstSecurityApp.models.Person;
import com.example.FirstSecurityApp.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final RegistrationService registrationService;

    @Autowired
    public PersonValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;
        if (registrationService.validUsername(person).isEmpty())
            return;
        errors.rejectValue("username", "", "Пользователь с таким именем уже существует");


    }
}

