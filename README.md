# Friends
* This is a website developed by Dropwizard and REACT for English learning through Friends bilingual captions

## How to use

* Users need to log in to use the app and get last position

* Users can choose season and episode to go

* Provided Prev and Next caption button

* Provided Hide and Show caption button

## Back-End

* Implemented with Dropwizard
  https://www.dropwizard.io/en/latest/getting-started.html
  
* Used Json to deal with http request/response data

* Used mysql to persist data

* Java Coding style: https://google.github.io/styleguide/javaguide.html

* How to run:

  Open as a Java project and run the src/main/MyApplication with two arguments: server and friends.yml

  Or install maven and run "mvn clean package" in the root folder where pom.xml resides to get jar file in target folder, then put jar file and friends.yml and dictionary file in the same folder and run "java -jar target/friends-1.0-SNAPSHOT.jar server friends.yml"

## Front-end

* Implemented with REACT

* How to run:
  Run "npm install" and "npm start" under frontend folder

