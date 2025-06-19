# Volunteering management platform

---

## Project description

The **Volunteering management platform** is a comprehensive application designed for the efficient management of volunteer activities. The platform provides support to both organizers, enabling them to easily create, manage, and track activities, and volunteers, offering them an intuitive interface for Browse, signing up, and monitoring their participation in volunteer actions.

Through the application, users can create profiles, browse available activities, sign up for participation, and track their volunteer engagement. Organizers have the ability to monitor volunteer attendance, automatically generate volunteering certificates, and gain insights into engagement statistics. A notification system ensures timely alerts about new events and schedule changes, while search and filtering functions facilitate easy discovery of relevant activities. Additional functionalities include team creation, social integration, and internal communication channels (chat/forum), fostering community and interaction among volunteers.

---

## Team

* Osmanković Ilhana
* Kršlak Anesa
* Mioković Danijel

---

## Getting started

To successfully run the project, follow the steps below. Make sure to launch the services in the specified order to avoid dependency issues.

1.  **Config server**
    ```bash
    cd config-server
    mvn spring-boot:run
    ```

2.  **API gateway**
    ```bash
    cd api-gateway
    mvn spring-boot:run
    ```

3.  **System events service**
    ```bash
    cd system-events-service
    mvn spring-boot:run
    ```

4.  **Eureka server**
    ```bash
    cd eureka-server
    mvn spring-boot:run
    ```

5.  **User service**
    ```bash
    cd user-service
    mvn spring-boot:run
    ```

6.  **Participation service**
    ```bash
    cd participation-service
    mvn spring-boot:run
    ```

7.  **Activity management service**
    ```bash
    cd activity-management-service
    mvn spring-boot:run
    ```

8.  **Feedback service**
    ```bash
    cd feedback-service
    mvn spring-boot:run
    ```

9.  **Notification communication service**
    ```bash
    cd notification-communication-service
    mvn spring-boot:run
    ```

10. **Frontend**
    ```bash
    cd frontend
    npm start
    ```

---

## Security solution (Overview)

The platform's security is implemented through a robust system based on the **JWT (JSON Web Token) standard**, utilizing an **API gateway** as the central point for authentication.

* The **API gateway** is the primary entry point for authenticating all external requests. After the client sends credentials, the **User service** validates them and generates a **JWT Access Token** (short-lived) and a **Refresh token** (long-lived). All subsequent requests will include the Access Token.
* We use **JSON Web Token (JWT)** due to its `stateless` nature, which enables scalability and efficiency in a distributed system. JWT contains necessary `claims` (assertions) about the user (e.g., ID, roles) and is digitally signed to ensure integrity.
* **Roles and permissions** are stored in the User Service and included in the JWT payload. For authorization, a **hybrid approach** is used:
    * **Centralized authentication** at the API Gateway.
    * **Decentralized authorization** at the microservice level, where each service validates the token and applies granular authorization rules (e.g., using `@PreAuthorize` annotations in Spring Security).
* **Inter-microservice authorization** is crucial and is achieved by passing the user's JWT (for "on behalf of" user requests) or by using **Service-to-Service Tokens** for internal processes. Individual microservices are **never** directly exposed publicly.
* **Token invalidation** is handled by a combination of short-lived **Access tokens**, long-lived **Refresh tokens** (which are `stateful` and can be revoked), and a centralized **Blacklist** for immediate invalidation of compromised or logged-out Access Tokens.
* **Mobile device access** is supported, utilizing the same RESTful API and JWT for secure authentication and authorization. It's recommended to use **OAuth 2.0 with PKCE** for additional security on mobile platforms.

---

## Application demo

Watch a video demonstration of the implemented functionalities and technical capabilities of the application:

[Demo Video](https://drive.google.com/drive/u/0/folders/1q53smDa_ThoNA1ieK_NSj_qN6Lgu_Ee5)
