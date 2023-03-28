<br>
<p align="center">
  <a href="" rel="noopener">
 <img src="https://user-images.githubusercontent.com/31359489/228345878-7616f0e0-a93c-40d5-9003-0ba66c70d8a6.gif" alt="Project logo"></a>
</p>

<h3 align="center">Shopping List with Cloud Vision</h3>



---

<p align="center"> API for managing groceries shopping with integration of Google Cloud Vision.
    <br> 
</p>

## 📝 Table of Contents

- [About](#about)
- [Getting Started](#getting_started)
- [Deployment](#deployment)
- [Usage](#usage)
- [Built Using](#built_using)
- [Authors](#authors)
- [What i learned](#learned)

<br>

---

## 🧐 About <a name = "about"></a>

<p>The objective of this project was to improve and consolidate my knowledge about the technologies used, mainly the Google Vision Cloud API.

For now this its just a concept of what can be done using the Vision Cloud.

I utilized the Vision API to extract text from an image of an supermarket shelf label, to obtain information such as the name of the product and its price.
</p>

---

## 🏁 Getting Started <a name = "getting_started"></a>


### Prerequisites

<br>

- Google cloud service with Application Default Credentials set (in my example i've used an JSON file with the service key and set the GOOGLE_APPLICATION_CREDENTIALS environment variable)
- JDK 19
- Spring Boot 3

<br>

### Installing
<br>

Configuring Application Default Credentials

```
https://cloud.google.com/docs/authentication/provide-credentials-adc#on-prem
```
<br>

---

## 🎈 Usage <a name="usage"></a>

<br>

### Base URL: 
### /api/product

| Method | Parameters | Description |
|---|---|---|
| `GET` | id (integer) or name (string) | Return information of a record passing it's ID as a paramether. |
| `POST` | | Creates a new record. |
| `PUT` | | Updates a record. |
| `DELETE /{id}` | | Deletes a record passing it's ID as a paramether. |

<br>

### Base URL: 
### /api/carts

| Method | Description |
|---|---|
| `GET /{id}` | Return information of a record passing it's ID as a paramether. |
| `POST` | Creates a new record. |
| `POST /product` | Add a product to a cart. |
| `PUT` | Updates a record. |
| `DELETE /{id}` | Deletes a record passing it's ID as a paramether. |

<br>

### Base URL: 
### /api/users

| Method | Description |
|---|---|
| `GET` | Return information of all records. |
| `GET /{id}` | Return information of a record passing it's ID as a paramether. |
| `GET /email/{email}` | Return information of a record passing it's email as a paramether. |
| `POST /create` | Creates a new record. |
| `POST /login` | Authenticates a user returning a token. |
| `PUT /{id}` | Updates a record given it's ID as a paramether. |
| `DELETE /{id}` | Deletes a record passing it's ID as a paramether. |

<br>

### Base URL: 
### /api/extractText

| Method | Parameters | Description |
|---|---|---|
| `GET` | imageUrl (string) | Get the text of an image |

<br>

---

## 🚀 Deployment <a name = "deployment"></a>

```
./mvnw spring-boot:run
```
---

## ⛏️ Built Using <a name = "built_using"></a>

- [JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html) - Language
- [H2](https://www.h2database.com/html/main.html) - Database
- [Spring Boot](https://spring.io/) - Framework

---

## ✍️ Authors <a name = "authors"></a>

- [@GustavoAntunesRocha](https://github.com/GustavoAntunesRocha)

---


## 🎉 What i learned <a name = "learned"></a>

- A lot about Google Cloud Vision
  - Setting up Google Cloud
  - Cloud Vision Template
  - Extracting text from images
  - Image labels
- Resource Loader interface
- Regex
- Lambda expressions
- Improved my knowledge of authentication and Spring Security
  - Better understanding of the filters
  - JWT Claims
  - User Details Service
  - Authentication Manager
