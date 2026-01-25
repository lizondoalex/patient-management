# Member Management System - Microservices Architecture

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green?style=flat&logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?style=flat&logo=docker)
![gRPC](https://img.shields.io/badge/Communication-gRPC-red?style=flat&logo=google)

##  Overview

This project is a comprehensive **Microservices Architecture** designed to manage member records and billing systems for a Gym environment.

It demonstrates a production-ready approach by integrating modern technologies like **gRPC** and **kafka** for high-performance inter-service communication 

##  Architecture

The system is composed of several independent services:

* **Api Service** Handles routing requests from clients to different service
* **Member Service:** Handles Member data (CRUD), validation, and business logic. Exposes a **REST API**.
* **Billing Service:** Manages payments and invoicing. Communicates via **gRPC** for low-latency performance.
* **Database:** Independent **PostgreSQL** instances for each service (Database per Service pattern).

---

## Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3+
* **Communication:** * REST (External API)
    * gRPC & Protobuf (Internal Service-to-Service)
* **Database:** PostgreSQL
* **Containerization:** Docker 
* **Documentation:** OpenAPI (Swagger)

---

## Getting Started

You can run the entire infrastructure using Docker Compose.

### Prerequisites

* **Docker** (running)
* **Java 17** & **Maven** (if running locally without Docker)

### Instructions using docker:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/lizondoalex/GymManager-SpringBoot.git
    cd GymManager-SpringBoot
    ```
    
2.  **Navigate to run-scripts folder:**
    ```bash
    cd run-scripts
    ```

3.  **Make scripts executable (First time only):**
    ```bash
    ./run-all.sh
    ```
    


##  API Endpoints 

### Member Service (REST)
Some examples are:

| Method | Endpoint             | Description                                  |
|:-------|:---------------------|:---------------------------------------------|
| `POST` | `/api/members`       | Register a new member                        |
| `GET`  | `/api/members`       | Retrieve member details                      |
| `GET`  | `/api-docs/members/` | Get swagger documentation for member-service |
| `GET`  | `/api-docs/auth/`    | Get swagger documentation for auth-service   |

There are some examples for possible requests under the folder api-requests