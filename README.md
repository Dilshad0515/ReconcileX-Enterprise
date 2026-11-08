
# ReconcileX Enterprise

An enterprise-oriented financial transaction reconciliation and exception management platform built using Java, Spring Boot and PostgreSQL.

## Overview

ReconcileX Enterprise is designed to help organizations reconcile financial transactions across multiple systems, including internal order management systems, payment gateways and bank settlement systems.

The platform aims to identify transaction discrepancies, detect duplicate records, track missing transactions and provide a structured workflow for investigating reconciliation exceptions.

The project is being developed as a modular backend-first application, with a React frontend planned for a later phase.

## Problem Statement

Financial transactions are often recorded across multiple independent systems. Differences in transaction amounts, missing records, duplicate references and settlement delays can make manual reconciliation time-consuming and error-prone.

ReconcileX aims to automate this process through structured data ingestion, transaction matching and exception management.

## Core Objectives

- Import financial transaction data from multiple sources.
- Validate and normalize incoming transaction records.
- Match transactions across internal, gateway and bank systems.
- Identify missing, duplicate and mismatched transactions.
- Track reconciliation batches and processing results.
- Provide explanations for identified discrepancies.
- Support investigation and resolution of exceptions.
- Maintain an auditable history of reconciliation activities.

## Technology Stack

### Backend
- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Maven

### Database
- PostgreSQL 18
- SQL
- JSONB

### Development Tools
- Visual Studio Code
- Git
- GitHub
- pgAdmin
- Postman (planned for API testing)

### Planned Technologies
- React
- Redis
- Docker
- JUnit 5
- Mockito
- Swagger / OpenAPI

## Project Structure

```text
ReconcileX/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/reconcilex/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── exception/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   └── resources/
│   │   └── test/
│   ├── .env.example
│   └── pom.xml
│
├── database/
│   ├── 01_create_database.sql
│   ├── 02_create_tables.sql
│   ├── 03_insert_sample_data.sql
│   └── 04_verification_queries.sql
│
├── docs/
│
├── .gitignore
└── README.md
```

## Database Design

The initial database consists of three core tables:

| Table | Purpose |
|---|---|
| data_sources | Stores registered transaction sources |
| import_batches | Tracks imported files and processing batches |
| source_transactions | Stores individual financial transactions |

The initial development dataset contains synthetic transactions from three sources:

- Internal Order System
- Payment Gateway
- Bank Settlement System

## Current Implementation Status

### Completed

- Initial Spring Boot backend setup.
- PostgreSQL database configuration.
- Database schema creation.
- JPA entity mappings.
- Repository layer.
- Transaction DTO.
- Transaction service.
- Transaction retrieval REST API.
- Successful database connectivity and schema validation.
- Synthetic sample data insertion.
- Initial GitHub repository setup.

### In Progress

- Backend API improvements.
- Request validation and exception handling.
- Pagination, sorting and filtering.
- File ingestion and validation.
- Reconciliation engine.
- Exception management.

### Planned

- Advanced transaction matching.
- Idempotent processing.
- Background processing.
- Audit trail.
- Automated testing.
- React frontend.
- Docker-based deployment.

## Running the Backend

### Prerequisites

- Java 21 or compatible Java runtime.
- Maven (or the included Maven wrapper).
- PostgreSQL.
- Git.

### 1. Clone the repository

```bash
git clone https://github.com/Dilshad0515/ReconcileX-Enterprise.git
```

### 2. Create the database

Create a PostgreSQL database named:

```text
reconcilex_db
```

Execute the SQL scripts inside the database folder in the correct order.

### 3. Configure environment variables

Create a local `.env` file inside the backend directory using `.env.example` as a reference.

Add your local PostgreSQL credentials.

Never commit your actual `.env` file.

### 4. Start the backend

Navigate to the backend directory:

```bash
cd backend
```

Run:

```bash
.\mvnw.cmd spring-boot:run
```

The backend starts at:

```text
http://localhost:8080
```

### 5. Test the transaction API

```http
GET http://localhost:8080/api/transactions
```

## Development Approach

ReconcileX is being developed incrementally using a modular architecture.

Each major feature is implemented, tested and documented before moving to the next development milestone.

## Disclaimer

This project uses synthetic financial transaction data for development and demonstration purposes. It is not connected to real banking or payment systems.

---

**ReconcileX Enterprise**  
Building reliable financial reconciliation through backend engineering.
