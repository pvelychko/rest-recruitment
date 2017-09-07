package com.pvelychko.recruitment.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pvelychko.recruitment.server.model.PersonResponce;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pvelychko.recruitement.domain.Person;
import com.pvelychko.recruitement.service.PersonService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController("personRestService")
@RequestMapping(value = "person")
public class PersonRestService {
    private Gson gson = new Gson();
    private PersonService personService;

    private final Logger log = LoggerFactory.getLogger(PersonRestService.class);

    public PersonRestService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getPerson(@PathVariable Long id) throws IOException {
        Person person = personService.getPerson(id);
        if(null == person) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PersonResponce personResponce = new PersonResponce(person);
        Map<String, PersonResponce> fullnameToPerson = new HashMap<>();
        fullnameToPerson.put(personResponce.getGivenName() + " " + personResponce.getFamilyName(), personResponce);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fullnameToPerson);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getEveryone(@RequestParam(value = "gender", required = false) String gender) {
        Set<Person> people = personService.getPeople(gender);

        Map<String, Object> fullnameToPerson = new HashMap<>();
        people.forEach(p -> {
            PersonResponce personResponce = new PersonResponce(p);
            fullnameToPerson.put(personResponce.getGivenName() + " " + personResponce.getFamilyName(), personResponce);
        });
        String json = gson.toJson(fullnameToPerson);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createPerson(@RequestBody String request) {
        Long personId = null;
        try {
            personId = personService.createPerson(convertRDF(request));
        } catch (IllegalArgumentException e) {
            // Ignored
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Created/retrieved person with id " + personId);

        return ResponseEntity.created(URI.create("http://localhost/person/" + personId)).build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deletePerson(@PathVariable Long id) {
        Person person = personService.getPerson(id);
        if(null == person) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        personService.deletePerson(id);

        log.info("Removed person: " + person);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updatePerson(@PathVariable Long id, @RequestBody String request) {
        try {
            personService.updatePerson(id, convertRDF(request));
        } catch (IllegalArgumentException e) {
            // Ignored
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Updated person: " + id);

        return ResponseEntity.accepted().build();
    }

    private Person convertRDF(String request) {
        Model model = ModelFactory.createDefaultModel();
        model.read(new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8)), null, "RDF/XML");

        if (null == model || !model.listStatements().hasNext()) {
            throw new IllegalArgumentException("Unable to process the provided RDF file");
        }

        StmtIterator iter = model.listStatements();
        Person person = new Person();
        while (iter.hasNext()){
            Statement stmt = iter.nextStatement();

            final String property = stmt.getPredicate().getLocalName();
            final String value = stmt.getObject().toString();

            switch (property) {
                case "homepage":
                    person.setHomepage(value);
                    break;
                case "mbox":
                    String[] emails = value.split(":");
                    person.getMbox().add(emails[1]);
                    break;
                case "family_name":
                    person.setFamilyName(value);
                    break;
                case "givenname":
                    person.setGivenName(value);
                    break;
                case "gender":
                    person.setGender(value.toLowerCase());
                    break;
                case "name":
                    person.setName(value);
                    break;
            }
        }

        return person;
    }
}
