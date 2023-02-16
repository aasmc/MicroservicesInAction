# Microservices In Action

Educational project based on the book Spring Microservices in Action
2-nd edition. 

***The more distributed a system is, the more places it can fail.***

```bash
# To build the code examples as a docker image, open a command-line 
# window and execute the following command:
$ mvn clean package dockerfile:build

# Now we are going to use docker-compose to start the actual image.  To start the docker image, stay in the root directory containing source code and  Run the following command: 
$ docker-compose -f docker/docker-compose.yml up
```