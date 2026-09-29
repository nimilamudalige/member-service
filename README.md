# PulseFit — Member Service

## Project Description

Owns gym member profiles for PulseFit: create/read/update/delete a member,
plus a profile-photo upload endpoint that stores the image in a Google
Cloud Storage bucket and saves the resulting public URL on the member
record. Backed by Cloud SQL for MySQL. Called directly by `booking-service`
(via Eureka service discovery) to confirm a member exists before a booking
is created.

## Technology Stack

- Java 25
- Spring Boot 4.0.8 (Spring Web MVC)
- Spring Data JPA + MySQL (Google Cloud SQL)
- Spring Cloud Eureka Client + Config Client
- Google Cloud Storage client (`google-cloud-storage`)
- PM2 (process management on the deployed VM)

## API

| Method | Path                       | Description                    |
|--------|----------------------------|---------------------------------|
| POST   | `/api/members`             | Create a member                 |
| GET    | `/api/members`              | List all members                |
| GET    | `/api/members/{id}`         | Get one member                  |
| PUT    | `/api/members/{id}`         | Update a member                 |
| DELETE | `/api/members/{id}`         | Delete a member                 |
| POST   | `/api/members/{id}/photo`   | Upload a profile photo (multipart `file`) — stores it in Cloud Storage |

Example create request body:

```json
{
  "fullName": "Nadeesha Perera",
  "email": "nadeesha@example.com",
  "phone": "0771234567",
  "membershipPlan": "PREMIUM",
  "joinDate": "2026-01-15"
}
```

## Setup / Getting Started

### Prerequisites

- Java 25 JDK, Maven 3.9+
- A MySQL instance reachable locally (or via Cloud SQL Auth Proxy) — database
  `pulsefit_member_db` is created automatically on first run
  (`createDatabaseIfNotExist=true`)
- A GCS bucket if you want to test photo upload locally, with
  `GOOGLE_APPLICATION_CREDENTIALS` pointing at a service-account key, or run
  on a GCE VM where Application Default Credentials are automatic

### Run locally

```bash
mvn clean package
java -jar target/member-service.jar
```

## Student Information

- **Student Name:** Pasan Nimila
- **Student Number:** 2301692034
- **Slack Handle:** pasan_nimila
- **GCP Project ID:** pulsefit-capstone
