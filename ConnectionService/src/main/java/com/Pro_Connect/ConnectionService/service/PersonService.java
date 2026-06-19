package com.Pro_Connect.ConnectionService.service;

import com.Pro_Connect.ConnectionService.entity.Person;
import com.Pro_Connect.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    public void createPerson(Long userId,String name){
        Person person = Person.builder().userId(userId).name(name).build();
        personRepository.save(person);
    }

}
