package com.pvelychko.recruitement.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private Long id;
    private String name;
    private String gender;
    private String givenName;
    private String familyName;
    private List<String> mbox = new ArrayList<>();
    private String homepage;

    public Person() {
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<String> getMbox() {
        return mbox;
    }

    public void setMbox(List<String> mbox) {
        this.mbox = mbox;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person other = (Person) o;
        return Objects.equals(name, other.getName()) &&
                Objects.equals(gender, other.getGender()) &&
                Objects.equals(givenName, other.getGivenName()) &&
                Objects.equals(familyName, other.getFamilyName()) &&
                Objects.equals(mbox, other.getMbox()) &&
                Objects.equals(homepage, other.getHomepage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, givenName, familyName, mbox, homepage);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("gender", gender)
                .add("givenName", givenName)
                .add("familyName", familyName)
                .add("mbox", mbox)
                .add("homepage", homepage)
                .toString();
    }
}

