### Client Application v2 

This is the template for Spring Boot OAuth2.0 Client Application (CA)

* Integration with custom [Authorization Server](https://github.com/isurunuwanthilaka/oauth-AS-v2)

* Used `H2 database` for testing - all `sql` schemas included

* JWT Generation for secondary system

* This is a hybrid with `OAuth2.0` and `JWT`. `OAuth2` provides authentication for primary application and `JWT` provides authorization information for the secondary system  

Related [Blog](https://isurunuwanthilaka.github.io/engineering/2020/10/25/oauth2-jwt-hybrid-architecture)

## Docker Build

* Install dependencies for the first time

`mvn install`

* Package the project

`mvn clean package`

* Build docker image

`docker build -t oauth-ca ./`

* Run docker container

`docker run -d -p 8082:8082 --name oauth-ca oauth-ca`
