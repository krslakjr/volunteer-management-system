# Volunteering Management Platform

---

## Opis projekta

**Volunteering Management Platform** je sveobuhvatna aplikacija dizajnirana za efikasno upravljanje volonterskim aktivnostima. Platforma pruža podršku kako organizatorima, omogućavajući im jednostavno kreiranje, upravljanje i praćenje aktivnosti, tako i volonterima, nudeći im intuitivan interfejs za pregledavanje, prijavljivanje i praćenje učešća u volonterskim akcijama.

Kroz aplikaciju, korisnici mogu kreirati profile, pregledavati dostupne aktivnosti, prijavljivati se za učešće, te pratiti svoj volonterski angažman. Organizatori imaju mogućnost praćenja prisustva volontera, automatskog generisanja potvrda o volontiranju, te uvida u statistike angažmana. Sistem notifikacija osigurava pravovremene obavijesti o novim događajima i promjenama, dok funkcije pretrage i filtriranja olakšavaju pronalazak relevantnih aktivnosti. Dodatne funkcionalnosti uključuju kreiranje timova, društvenu integraciju i interne komunikacijske kanale (chat/forum), podstičući zajedništvo i interakciju unutar volonterske zajednice.

---

## Tim

* Osmanković Ilhana
* Kršlak Anesa
* Mioković Danijel

---

## Pokretanje projekta

Da biste uspješno pokrenuli projekat, slijedite korake navedene u nastavku. Obavezno pokrećite servise redom, kako je navedeno, kako biste izbjegli probleme sa zavisnostima.

1.  **Config Server**
    ```bash
    cd config-server
    mvn spring-boot:run
    ```

2.  **API Gateway**
    ```bash
    cd api-gateway
    mvn spring-boot:run
    ```

3.  **System Events Service**
    ```bash
    cd system-events-service
    mvn spring-boot:run
    ```

4.  **Eureka Server**
    ```bash
    cd eureka-server
    mvn spring-boot:run
    ```

5.  **User Service**
    ```bash
    cd user-service
    cd user-service
    mvn spring-boot:run
    ```

6.  **Participation Service**
    ```bash
    cd participation-service
    cd participation-service
    mvn spring-boot:run
    ```

7.  **Activity Management Service**
    ```bash
    cd activity-management-service
    cd activity-management-service
    mvn spring-boot:run
    ```

8.  **Feedback Service**
    ```bash
    cd feedback-service
    cd feedback-service
    mvn spring-boot:run
    ```

9.  **Notification Communication Service**
    ```bash
    cd notification-communication-service
    cd notification-communicatoin-service
    mvn spring-boot:run
    ```

10. **Frontend**
    ```bash
    cd frontend
    npm start
    ```

---

## Sigurnosno rješenje (Pregled)

Sigurnost platforme implementirana je kroz robustan sistem baziran na **JWT (JSON Web Token) standardu**, koristeći **API Gateway** kao centralnu tačku za autentifikaciju.

* **API Gateway** je primarna tačka za autentifikaciju svih eksternih zahtjeva. Nakon što klijent pošalje kredencijale, **User Service** ih validira i generiše **JWT Access Token** (kratkotrajan) i **Refresh Token** (dugotrajan). Svi budući zahtjevi sadrže Access Token.
* Koristimo **JSON Web Token (JWT)** zbog njegove `stateless` prirode, što omogućava skalabilnost i efikasnost u distribuiranom sistemu. JWT sadrži potrebne `claims` (tvrdnje) o korisniku (npr. ID, uloge) i digitalno je potpisan za osiguranje integriteta.
* **Role i permisije** su pohranjene u User Service-u i uključene u JWT payload. Za autorizaciju se koristi **hibridni pristup**:
    * **Centralizovana autentifikacija** na API Gatewayu.
    * **Decentralizovana autorizacija** na nivou mikroservisa, gdje svaki servis validira token i primjenjuje granularna pravila autorizacije (npr. koristeći `@PreAuthorize` anotacije u Spring Security-u).
* **Autorizacija između mikroservisa** je ključna i realizuje se proslijeđivanjem korisničkog JWT-a (za zahtjeve "on behalf of" korisnika) ili korištenjem **Service-to-Service Tokena** za interne procese. Pojedinačni mikroservisi **nikada** nisu direktno izloženi javno.
* **Invalidacija tokena** je riješena kombinacijom kratkotrajnih **Access Tokena**, dugotrajnih **Refresh Tokena** (koji su `stateful` i mogu se opozvati), te centralizovane **Blackliste** (crne liste) za trenutnu invalidaciju kompromitovanih ili odjavljenih Access Tokena.
* **Pristup sa mobilnih uređaja** je podržan, koristeći isti RESTful API i JWT za sigurnu autentifikaciju i autorizaciju. Preporučuje se korištenje **OAuth 2.0 sa PKCE** za dodatnu sigurnost na mobilnim platformama.

## Demo aplikacije

Pogledajte video demonstraciju implementiranih funkcionalnosti i tehničkih mogućnosti aplikacije:

[Demo Video](https://drive.google.com/drive/u/0/folders/1q53smDa_ThoNA1ieK_NSj_qN6Lgu_Ee5)
