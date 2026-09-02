# Customer Transactions - Toucan Payments Engineering Challenge

## 1. Problem Understanding

This project implements a small transaction-processing service for managing customer transactions.

The service supports four required operations:

1. Create a transaction
2. Get a transaction by Transaction ID
3. Update the status of a transaction
4. Get all transactions belonging to a Customer ID

The application is implemented inside the provided Spring Boot starter project using Java 17, Spring Web, Spring Data JPA, H2, Maven, and JUnit.

The goal of the implementation is to keep the solution simple, readable, and focused on the required business operations.

---

## 2. Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 embedded database
- Maven
- JUnit 5
- Spring Boot Test
- Jakarta Bean Validation

---

## 3. Transaction Model

Each transaction contains the following fields:

| Field | Description |
|---|---|
| Transaction ID | Unique identifier for the transaction |
| Customer ID | Identifier of the customer who owns the transaction |
| Amount | Monetary amount of the transaction |
| Currency | Three-character currency code |
| Transaction Type | Type of transaction |
| Transaction Status | Current processing status |

---

## 4. Validation Rules

The following validation rules are implemented.

### Transaction ID

- Transaction ID is required.
- It must not be blank.
- It is used as the primary key.
- Duplicate Transaction IDs are rejected.

### Customer ID

- Customer ID is required.
- It must not be blank.

### Amount

- Amount is required.
- Amount must be at least `0.01`.

### Currency

- Currency is required.
- Currency must contain exactly three characters.

### Transaction Type

Transaction type is required and is represented using an enum.

The currently supported transaction types are:

- `PAYMENT`
- `REFUND`
- `TRANSFER`

### Transaction Status

Transaction status is required.

A newly created transaction must have:

```text
PENDING