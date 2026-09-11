# Payment Gateway - Domain Core

This repository contains the core business logic (Domain Layer) for an enterprise payment processing engine. It is designed using pure Java (JDK 21) without framework dependencies, adhering strictly to Clean Architecture principles.

## Architectural Decisions
*   **Polymorphism over Inheritance:** Payment methods (`GarantiPos`, `CryptoWallet`) implement the `PaymentMethod` interface rather than extending a fragile base class, ensuring the system is loosely coupled and easily extensible.
*   **Encapsulation:** Domain entities (e.g., `Order`) heavily encapsulate their state. State mutations (like marking an order as paid) are restricted to controlled business methods.
*   **Guard Clauses:** Adopted early-return patterns to fail fast on invalid states, maintaining flat and readable execution paths.

## Tech Stack
*   Java 21
*   Maven (Build Management)

## Roadmap
This core domain is actively evolving. Upcoming phases:
- [ ] Implement automated Unit Tests (JUnit 5).
- [ ] Integrate Spring Boot framework.
- [ ] Implement data persistence (PostgreSQL).