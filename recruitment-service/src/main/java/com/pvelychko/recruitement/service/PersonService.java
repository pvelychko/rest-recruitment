package com.pvelychko.recruitement.service;

import com.pvelychko.recruitement.domain.Person;
import com.pvelychko.recruitement.storage.PeopleCatalog;

import java.util.Set;
import java.util.stream.Collectors;

public class PersonService {
    private Set<Person> people = PeopleCatalog.getPeople();
    private Long counter = 1L;

    public Person getPerson(Long id) {
        for (Person exists: people) {
            if (exists.getId().equals(id)) {
                return exists;
            }
        }

        return null;
    }

    public Set<Person> getPeople(String gender) {
        if(null == gender) {
            return people;
        }
        return people.stream()
                .filter(p -> gender.equals(p.getGender()))
                .collect(Collectors.toSet());
    }

    public Long createPerson(Person person) {
        for (Person exists: people) {
            if (exists.equals(person)) {
                return exists.getId();
            }
        }
        person.setId(counter);
        people.add(person);

        return counter++;
    }

    public void updatePerson(Long id, Person person) {
        for (Person exists: people) {
            if (exists.getId().equals(id)) {
                people.remove(exists);
                person.setId(exists.getId());
                people.add(person);
                return;
            }
        }
    }

    public void deletePerson(Long id) {
        people.remove(id);
    }
}
