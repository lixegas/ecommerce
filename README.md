# Ecommerce Project
This GitHub repo contains the source code for an e-commerce service distributed on a microservice architecture.
#### Built with 
[![My Skills](https://skillicons.dev/icons?i=java,spring,kafka,mysql,mongodb,redis&theme=light)](https://skillicons.dev)

# General Architecture
![General Architecture(off)](https://github.com/user-attachments/assets/10dd0181-6339-454f-af21-1a518068072e)

In the general architecture, all microservices communicate via a Kafka queue and HTTP requests (implemented via Feign clients). 

Every microservice is placed behind an API gateway implemented with Spring Cloud Gateway. Furthermore, every system communicates with its own MySQL, MongoDB, and Redis instances. 

There are multiple flows inside the architecture, but the most important ones will be covered below.

## Auth Flow
The authentication flows are composed mainly of two actions: register and login.

### Registration flow
![UserRegistration](https://github.com/user-attachments/assets/6446c3f9-6e3b-44dc-85fb-78a5ac2e62de)

In the registration flow, the user registers directly on the keycloak instance. There, the user will insert important details (such as email, address, cellphone number, and so on). 

These fields will be saved on the keycloak instances. Other information, such as preferences and addresses, will be saved instead inside the user service's database. These details will be known as the user profile.

After the user registration is successful, the newly created user's ID is sent to the Kafka queue thanks to a custom SPI. The user service will then read the message and create a user profile with the corresponding ID.

### Login flow
![Login flow](https://github.com/user-attachments/assets/43946f5e-8a1e-4a65-821b-ea60d86c5662)

Similarly to the registration flow, the user logins directly to the keycloak instance. If the login is successful, the user receives both an access token (needed for requests) and a refresh token, needed to get another access token when it expires.

Requests on the backend services need to be authenticated by providing the access token inside the authorization header. The backend services are configured with the Spring Oauth2 server library, which eases the JWT validation by filtering the request and asking for validity directly from the keycloak instance.

Important information, such as user IDs, roles, and other claims, is then used to authorize the request.

## File upload flow
![FileUpload](https://github.com/user-attachments/assets/4b372ba0-bde0-4506-9201-a769012ec683)

To manage product images, a media service handles file uploads.

When a user with the right permissions uploads a file, it is then forwarded to S3. A MySQL database saves the file metadata (slug, uploader, size, etc.).

The slug can then be used to provide media for things such as product descriptions.
## Product flow
The product flow is interesting because the products are one of the heaviest objects in the whole project. This is because the product contains both small metadata (such as prices, slugs, etc.) and other big data, such as descriptions, characteristics, etc.

### Creation Flow
![Product creation](https://github.com/user-attachments/assets/8c114934-d672-4ec2-b6c8-e94d60e7723a)

When the user creates a product, the smaller metadata is saved inside the MySQL database. The larger data is instead saved inside the Mongo database.

### Get flow
![GetProduct](https://github.com/user-attachments/assets/646ad0ba-997e-4c83-be3f-5c257df1fb29)

The products are one of the most accessed resources. They are also very resource-expensive since they are one of the heaviest objects. To prevent wasting resources, a layer of cache is given to the product service by implementing a Redis instance that will cache frequently used responses.

## Order flow
![OrderFlow](https://github.com/user-attachments/assets/722cefd1-0fda-41d6-9382-edca3a5eaa75)

The order flow is surely something essential. Developing this specific flow is more complicated when using a microservice architecture, but it is surely not impossible.

Before creating the order, two essential checks must be done:
- The first check is done by asking the warehouse service if the product quantities are present.
- The second check is done by sending the payment details to the payment service.

Finally, after the two checks are satisfied, a request to the shipment service is sent, and a tracking ID is given to the order.

## Installation Guide
### Requisites
- Java SDKs 17 and 22
- Docker
### Installation
Clone the Github repo in a directory of your liking.
```
git clone https://github.com/lixegas/ecommerce.git
```

Before booting the modules, you have to create and configure the application-dev.yaml files for every module. An example structure of the yaml is contained inside the application-dev.example.yaml for every application.

To configure keycloak and add the event listener, you have to compile the user-registration-event-listener and upload the file inside your keycloak event listeners directory. If you are using Docker, the folder is opt/keycloak/providers.

## License
This project is licensed under the MIT license.
