package com.pvelychko.recruitement.storage;

import com.pvelychko.recruitement.domain.Person;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class PeopleCatalog {
    private static Set<Person> people = new HashSet();

    public static Set<Person> getPeople() {
        return people;
    }
}
