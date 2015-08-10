[![Travis build status](https://travis-ci.org/zalando/catwatch.svg)](https://travis-ci.org/zalando/catwatch)
[![Coveralls coverage status](https://img.shields.io/coveralls/zalando/catwatch.svg)](https://coveralls.io/r/zalando/catwatch)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Analytics](https://ga-beacon.appspot.com/UA-65266986-1/zalando/catwatch)](https://github.com/zalando/catwatch)


# CatWatch

CatWatch provides a web application that fetches regularly statistics for your GitHub accounts from GitHub.
The web application processes and saves the data in a database and then makes the data available via a REST-API.
The provided data reveal the popularity of your projects, your most active contributors etc.

In comparison to [CoderStats](http://coderstats.net/) the statistics can be aggregated over a list of GitHub accounts.

## Prerequisites

* maven
* java 8
* postresql

## Getting started

First run postgresql and create the database and a role via unix shell
    
    postgres -D /usr/local/var/postgres
    createdb catwatch
    createuser cat1

Build and run the web application either by Gradle or Maven. 

Gradle:

    cd catwatch-backend
    
    # build
    ./gradlew build
    
    # run
    java -jar build/libs/catwatch-backend-0.0.1-SNAPSHOT.jar -Dorganization.list=<listOfGitHubAccounts>


Maven:

    cd catwatch-backend

    # build (Note: currently postresql with database and role is needed! TODO: Tests need H2 database running)
    mvn package
    
    # run
    mvn spring-boot:run -Dorganization.list=<listOfGitHubAccounts>
    
    # run with postgresql and auto create the database
    mvn spring-boot:run -Dspring.profiles.active=postgresql -Dspring.jpa.hibernate.ddl-auto=create
    
    # run with H2 in memory database and auto create the database
    mvn spring-boot:run -Dspring.profiles.active=hbm2ddl -Dspring.jpa.hibernate.ddl-auto=create


The web application is available under http://localhost:8080

It provides the [CatWatch REST-API](https://zalando.github.io/catwatch/).

By default the web application uses an H2 in-memory database.
The file application-postgresql.properties demonstrates how a PostgreSQL database can be configured. %
