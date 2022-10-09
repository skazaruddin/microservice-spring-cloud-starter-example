# Microservice example start that show cases basic working of MS environment.This example
doesnt have production configurations like running eureka server in replication mode
and caching information using a custom cache manager that is highly available.
Configuration of HTTPS and MTLS between microservices during communication.

The APIGW only does routing of traffic to microservices as per the routing information 
configured in APIGW. Moreover, it doesn't hold and authentication/authorization mechanism.

## cURL

```
To get all the orders placed by profile 2
curl --location --request GET 'http://127.0.0.1:9000/public/v1/query/profile/2/orders'
```

## Using Docker Plugin
Add the following plugin inorder to build and deploy docker image to the docker
running at your required machine.Note: We suggest to use this plugin only during development
in local machine.
```
<plugin> 
             <groupId>com.spotify</groupId> 
             <artifactId>docker-maven-plugin</artifactId> 
             <version>1.2.2</version> 
             <configuration> 
                 <baseImage>openjdk:8-jre-alpine</baseImage> 
                 <imageName>${docker.image.prefix}/${project.artifactId}</imageName> 
                 <exposes>8761</exposes> 
                 <entryPoint>["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/${project.build.finalName}.jar"]</entryPoint> 
                 <resources> 
                     <resource> 
                     <targetPath>/</targetPath> 
                     <directory>${project.build.directory}</directory> 
                     <include>${project.build.finalName}.jar</include> 
                     </resource> 
                 </resources> 
             </configuration> 
</plugin> 

```
