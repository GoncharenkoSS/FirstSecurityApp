package com.example.FirstSecurityApp.services;

import com.example.FirstSecurityApp.models.Person;
import com.example.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Поиск пользователя для валидации
    public Optional<Person> validUsername(Person person) {
        return peopleRepository.findByUsername(person.getUsername());
    }

    //Метод регистрации нового пользователя
    @Transactional
    public void register(Person person) {
        //Кодирование пароля
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
