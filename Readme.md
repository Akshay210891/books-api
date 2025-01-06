# Book Management Application RESTAPI Using Spring Boot and PostgreSQL


## Prerequisite to run the application.
- Java 23
- PostgreSQL
- Postman

## Run Application

The application can be run via Intellij or the command line using the below command from the repository root folder:

~~~java
java -jar /target/booksapi-0.0.1-SNAPSHOT.jar 
~~~

## To use the Book Management API's JWT Token is required.
You can use the below endpoints to register a user and generate an authentication token which has to be used for Book Management Apis.

## Register USER:

~~~java
POST
/register //register a new user

--data-raw '{
    "email": "{email}",
    "username": "{username}",
    "password": "{password}"
}'
~~~

## Get JWT bearer token
~~~java
/auth //generate auth token

--data-raw '{
    "email": "{email}",
    "username": "{username}",
    "password": "{password}"
}'
~~~

### This application is built with all the CRUD capabilities for Book Management.

## BookManagement API's
~~~java
/v1/api/books //add book in catalog
--data '{
    "title": "{title}",
    "author": "{author}",
    "publishedDate": "{dd-mm-yyyy}",
    "genre": "{genre}"    
}'
~~~

### GET

~~~java
/v1/api/books/{Id} //get book by id
/v1/api/books // get all books
/v1/api/books?pageNo=0&pageSize=5 //get all books with pagenation parameters
/v1/api/books/author/{author} //find book with author name
/v1/api/books/search/{keyword} //find book by keyword which might be presnet in author or title not case sensitive
/v1/api/books/genre/{genre} //find book by genre needs to be exact match
/v1/api/books/date/{dd-mm-yyyy} //find book which has published date greater than the provided date
/v1/api/books/title/{title} // find book by the title which contain the provided string it is not case sensitive
~~~

### PUT

~~~java
/v1/api/books/{Id} //update a specific book by Id
--data '{
    "title": "{title}",
    "author": "{author}",
    "publishedDate": "{dd-mm-yyyy}",
    "genre": "{genre}"    
}'
~~~


### DELETE

~~~java
/v1/api/books/{Id} // delete a book by Id
~~~


## Note: Postman collection can be found in the repository for all the APIs.