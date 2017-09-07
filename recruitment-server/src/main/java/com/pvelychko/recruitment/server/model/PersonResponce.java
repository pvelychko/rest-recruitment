package com.pvelychko.recruitment.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pvelychko.recruitement.domain.Person;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonResponce extends Person {
    private NameModel nameModel;

    public PersonResponce(Person person) {
        this.nameModel = new NameModel(person.getGivenName(), person.getFamilyName());
        this.setGivenName(person.getGivenName());
        this.setFamilyName(person.getFamilyName());
        this.setGender(person.getGender());
        this.setMbox(person.getMbox());
        this.setHomepage(person.getHomepage());
    }

    @Override
    @JsonIgnore
    public Long getId() {
        return super.getId();
    }

    @JsonProperty("name")
    public NameModel getNameModel() {
        return nameModel;
    }

    @Override
    @JsonIgnore
    public String getGivenName() {
        return super.getGivenName();
    }

    @Override
    @JsonIgnore
    public String getFamilyName() {
        return super.getFamilyName();
    }

    @Override
    @JsonProperty("email")
    public List<String> getMbox() {
        return super.getMbox();
    }

    public void setNameModel(NameModel nameModel) {
        this.nameModel = nameModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonResponce)) return false;
        PersonResponce other = (PersonResponce) o;
        return Objects.equals(this.getNameModel(), other.getNameModel()) &&
                Objects.equals(this.getName(), other.getName()) &&
                Objects.equals(this.getGivenName(), other.getGivenName()) &&
                Objects.equals(this.getFamilyName(), other.getFamilyName()) &&
                Objects.equals(this.getGender(), other.getGender()) &&
                Objects.equals(this.getMbox(), other.getMbox()) &&
                Objects.equals(this.getHomepage(), other.getHomepage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getNameModel(), this.getName(), this.getGivenName(), this.getFamilyName(), this.getGender(), this.getMbox(), this.getHomepage());
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("nameModel", this.getNameModel())
                .add("name", this.getName())
                .add("givenName", this.getGivenName())
                .add("familyName", this.getFamilyName())
                .add("gender", this.getGender())
                .add("mbox", this.getMbox())
                .add("homepage", this.getHomepage())
                .toString();
    }

    public class NameModel implements Serializable {

        private String given;
        private String family;

        private NameModel() {
            // Default constructor
        }

        public NameModel(String given, String family) {
            this.given = given;
            this.family = family;
        }

        public String getGiven() {
            return given;
        }

        public String getFamily() {
            return family;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NameModel)) return false;
            NameModel other = (NameModel) o;
            return Objects.equals(given, other.getGiven()) &&
                    Objects.equals(family, other.getFamily());
        }

        @Override
        public int hashCode() {
            return Objects.hash(given, family);
        }

        @Override
        public String toString() {
            return com.google.common.base.MoreObjects.toStringHelper(this)
                    .add("given", given)
                    .add("family", family)
                    .toString();
        }
    }
}
