# Spring Security 5 Authorization Server

Build a simple spring security 5 authentication server with support for the most important flows of the OAuth 2 authentication framework.

## Build Authorization Server Image

```sh
./gradlew bootBuildImage --imageName=richardsobreiro/spring-security-5-auth-server
```

## Deploy Authorization Server using the image 

```sh
cd infra\auth-server
```

```sh
 aws cloudformation validate-template --template-body file://auth-server.yml
```

```sh
aws cloudformation create-stack --stack-name stack-auth-server --template-body file://auth-server.yml --capabilities CAPABILITY_IAM
```

## Clean up

```sh
aws cloudformation delete-stack --stack-name stack-auth-server

```

```sh
```

```sh
```