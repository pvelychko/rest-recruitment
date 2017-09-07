# Rest recruitment service

An HTTP server that stores and provides information about people. The data does not need to be stored persistently. 

XML is used for all input. JSON used for all output.


## API specification

1. Create a person:
```
POST http://127.0.0.1:8080/person
```
In the response, the address of the created person will be set as the value for the location header

2. Update person:
```
PUT http://127.0.0.1:8080/person/<id>
```
New data will overwrite old data

3. Get the given person:
```
GET http://127.0.0.1:8080/person/<id>
```
Response with a JSON that corresponds to the format in the **recruitment-verification** module
   
4: List all persons or only those given by a gender parameter:
```
GET  http://127.0.0.1:8080/person?[gender=<male|female>]
```
Response with a JSON with all persons.
If the parameter `gender` is sent, then only show persons of the specified gender

## How to run

1. Run `mvn jetty:run` from **recruitment-server** directory, this will start a local web-server
3. Browse to http://localhost:8080/, you should see a static example of a JSON for a person

## How to test

In order execute tests run the following commands:
1. From **recruitment-server**, run: `[mvn jetty:run]`
2. From **recruitment-verification** run: `[mvn test]`
