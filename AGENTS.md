# AGENTS.md — Engineering Agent Contract

This file defines the repository-level operating contract for any AI coding agent (such as Google Antigravity, OpenAI Codex, or other tools) working on **Core Payment Engine**.

> **Tool Independence & Durable State:** Do not assume conversation history persists across sessions or tools. Treat Git history, repository source files, documentation, and explicit handoff reports as the durable source of truth.

---

## A. Project Context

- **Repository:** `core-payment-engine` (`com.deneme:payment-engine:1.0-SNAPSHOT` in `pom.xml`)
- **Purpose & Direction:** A learning project following a 15-chapter Java backend engineering roadmap, evolving toward an internationally understandable, **English-language**, **USD-only** demo payment engine. Real payment-provider integrations and multi-currency support are explicitly out of scope unless separately authorized. Do not implement roadmap chapters autonomously; implement only explicitly approved tasks.
- **Source of Truth Note:** `README.md` still describes an earlier pure-Java phase and is outdated. Treat `pom.xml`, `src/`, `src/main/resources/db/migration/`, `docs/database/erd.md`, and Git history as authoritative.

### Verified Tech Stack (`pom.xml` & `application.yml`)
- **Language & Build:** Java 21, Maven (`maven-compiler-plugin` `3.11.0`); no Maven Wrapper (`mvnw`) currently exists.
- **Framework:** Spring Boot `3.3.5` (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`, `spring-boot-starter-security`, `spring-boot-starter-test`).
- **Database & Migrations:** PostgreSQL (`org.postgresql:postgresql`), Flyway (`flyway-core`, `flyway-database-postgresql`), Hibernate `ddl-auto: validate`.
- **Security & Libraries:** `jjwt` `0.12.3` (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`), `mapstruct` `1.5.5.Final` (with `mapstruct-processor`), `springdoc-openapi-starter-webmvc-ui` `2.6.0`, `spring-security-test`.
- **Runtime Configuration:** Externalized via environment variables in `src/main/resources/application.yml` (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`).

### High-Level Architecture & Package Layout
- **Entry Point:** `src/main/java/com/deneme/payment/Main.java`
- **Packages (`src/main/java/com/deneme/payment/`):**
  - `config/` — Security filter chain (`SecurityConfig`), auth beans (`SecurityBeansConfig`), OpenAPI metadata (`OpenApiConfig`)
  - `controller/` — REST controllers (`AuthController`, `OrderController`)
  - `dto/` — Request/response records (`AuthResponse`, `CreateOrderRequest`, `LoginRequest`, `OrderResponse`, `RegisterRequest`, `RevenueResponse`)
  - `entity/` — JPA domain entities and enums (`Order`, `OrderStatus`, `Product`, `User`, `Role`, `Wallet`)
  - `exception/` — Global RFC 7807 `ProblemDetail` advice (`GlobalExceptionHandler`) and domain exceptions (`InsufficientBalanceException`, `NoRefundPendingException`)
  - `mapper/` — MapStruct mappers (`OrderMapper`)
  - `repository/` — Spring Data JPA repositories (`OrderRepository`, `UserRepository`, `WalletRepository`)
  - `security/` — Stateless JWT filter (`JwtAuthenticationFilter`)
  - `service/` & `service/strategy/` — Application services and payment method strategies
- **Migrations & Docs:** `src/main/resources/db/migration/` (`V1__create_base_schema.sql`, `V2__index_products_by_order_id.sql`, `V3__enforce_product_price_constraint.sql`) and `docs/database/erd.md`.

### Current Implementation Boundaries (Implemented vs. Partial/Missing)
- **Implemented:** Stateless JWT authentication (`/api/auth/register`, `/api/auth/login`), Order CRUD/pagination/revenue endpoints (`/api/orders`), domain invariants on `Order`/`Product`/`Wallet`, and Flyway schema `V1`–`V3`.
- **Partially Implemented / Not Wired to API:**
  - `PaymentService` is a `@Service` with `@Transactional` wallet withdrawal and crash-simulation testing logic (`simulateCrash`), but has no REST endpoint.
  - `PaymentQueueManager`, `RefundService`, `ReceiptGenerator`, and `service/strategy/` (`PaymentMethod`, `GarantiPos`, `CryptoWallet`) are standalone in-memory POJOs/stubs not wired into Spring DI, persistence, or controllers.
  - `OrderService.processNewOrder` ignores `amount` and `currency` from `CreateOrderRequest`; `Role.ADMIN` is defined on `User` without enforced route/method authorization.
- **Missing Infrastructure:** Payment/wallet/refund REST APIs, user ownership links on orders/wallets, transaction ledger/audit timestamps, Maven Wrapper (`mvnw`), and CI workflows (`.github/workflows/`).

### Target Direction vs. Current State
- **Language:** Source code, documentation, API contracts, and developer-facing messages should use English (existing legacy Turkish strings/comments remain until updated under approved tasks).
- **Currency:** `USD` is the target and only supported currency for the completed project; multi-currency support is out of scope. Currently `OrderService.getDailyRevenue()` still hardcodes `"TRY"`, and `orders` does not persist currency.
- **Monetary Precision:** The target monetary representation is `BigDecimal` with PostgreSQL `NUMERIC(19,2)`. Currently `V1__create_base_schema.sql` and JPA entities (`Product`, `Wallet`) use `NUMERIC(19,4)`; transitioning to `NUMERIC(19,2)` requires a separately approved Flyway migration and tests.

---

## B. Architecture and Code Quality

- **Respect Boundaries:** Preserve existing layered architectural boundaries (`controller -> service -> repository -> entity`, with `dto` and `mapper` separation) unless an approved task explicitly changes them.
- **Language Standard:** Write all new or updated source code, documentation, API contracts, and developer-facing messages in English.
- **Injection & Composition:** Prefer constructor injection and clean, maintainable composition over field injection or inheritance hierarchies.
- **Simplicity:** Avoid unnecessary abstractions, extra libraries, speculative generalizations, and overengineering.
- **Schema Management:** Manage all database schema changes exclusively through new versioned Flyway migrations (`src/main/resources/db/migration/V{N}__description.sql`). Keep `spring.jpa.hibernate.ddl-auto` set to `validate`. Changing monetary column precision from the current `NUMERIC(19,4)` to the target `NUMERIC(19,2)` requires a separately approved Flyway migration and automated tests before any precision change is applied.
- **Migration Immutability:** Never rewrite, reorder, or edit already-applied Flyway migrations.
- **Dependencies:** Never introduce major dependency or framework upgrades without explicit human approval.
- **Behavioral Stability:** Preserve existing API contracts and domain behavior unless an approved requirement explicitly changes them.

---

## C. Agent Autonomy and Development Workflow

Coding agents must **never** independently decide to implement unrelated features, unsolicited refactors, or roadmap chapters. Analysis and recommendations do **not** constitute authorization to modify code.

### Mandatory Task Workflow
For every new development task:
1. **Inspect:** Read relevant code, tests, docs, and current Git status (`git status`, `git rev-parse HEAD`).
2. **Define:** Confirm task scope, constraints, and measurable acceptance criteria.
3. **Plan:** Present a concise implementation plan covering files to touch, DB/security impact, and verification strategy.
4. **Gate:** Wait for explicit human approval before making edits.
5. **Execute Autonomously:** Once approved, work autonomously within the approved scope without asking permission for routine implementation details.
6. **Verify:** Add or update tests, run relevant verification commands, and resolve issues directly within the approved scope.
7. **Isolate Out-of-Scope Findings:** Report bugs, vulnerabilities, or cleanup ideas discovered outside the task scope separately without implementing them.

### Git Rules
- Always create and work on a dedicated feature branch for approved development work.
- Make small, focused, meaningful commits.
- Preserve existing uncommitted changes; never silently overwrite or discard them.
- Never force-push, hard-reset, discard work, or rewrite Git history without explicit approval.
- Never push to, merge into, or commit directly on `main` without explicit approval.
- Present all changes, diffs, and verification output for human review before merging.

---

## D. Security and Financial Integrity

- **Secrets Hygiene:** Never expose, hardcode, log, or commit credentials, JWT secrets, API keys, passwords, or `.env` values.
- **Server-Side Authority:** Never trust client-supplied monetary amounts, balances, statuses, or resource ownership identifiers without server-side validation and authorization checks.
- **Monetary Precision & Currency:** Use `BigDecimal` exclusively for all monetary values and calculations; avoid `double`/`float`, silent rounding, precision loss, or inconsistent financial states. The target monetary standard is `BigDecimal` backed by PostgreSQL `NUMERIC(19,2)` with `USD` as the only supported currency. Note that the current database schema and entities still use `NUMERIC(19,4)`—do not change monetary precision without a separately approved Flyway migration and tests.
- **Transactional Integrity:** Preserve `@Transactional` atomicity and clean rollback behavior across multi-step state transitions and balance mutations.
- **Payment Safety Controls:** Explicitly evaluate authorization, ownership, concurrency/locking, idempotency, state-transition validity, and rollback handling whenever modifying payment, order, or wallet flows.
- **Out-of-Scope Financial Features:** Keep real banking/payment-provider integrations (PSPs, live gateways) and multi-currency support out of scope unless explicitly authorized.
- **Vulnerability Handling:** If serious existing security issues are observed while working on another task, report them separately rather than expanding the approved task scope without permission.

---

## E. Testing and Verification

- **Verification Requirement:** Every behavior-changing task must include appropriate automated verification.
- **Coverage Expectations:** Add or update unit, web-slice, or integration tests covering normal, boundary, and failure cases relevant to the change.
- **Real PostgreSQL for DB Behavior:** Prefer real PostgreSQL integration tests for database-specific queries, Flyway migrations, locking, and constraints. Never substitute H2 in-memory databases for PostgreSQL verification.
- **Current Tooling Reality (Do Not Assume Missing Infra):**
  - Standard Maven (`mvn test`, `mvn verify`) is the build/test mechanism; neither Maven Wrapper (`mvnw`) nor GitHub Actions CI workflows exist in this repository yet.
  - Pure JUnit 5 unit tests (`entity/*Test`, `PaymentQueueManagerTest`, `ReceiptGeneratorTest`, `RefundServiceTest`) have no Spring/DB dependencies; `OrderControllerTest` uses `@WebMvcTest` with mocked `OrderService`, `JwtService`, and `UserDetailsService`; DB-backed tests (`OrderRepositoryTest` with `@DataJpaTest` / `Replace.NONE`, `PaymentServiceTest` with `@SpringBootTest`, and `NPlusOneTest` with `@SpringBootTest` diagnostic output only) depend on a PostgreSQL configuration (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET` for full context).
- **Truthful Reporting:** Run all available and relevant verification commands, and report exact commands, exit status, and observed test counts/failures.
- **Unverified Policy:** If required tools, runtime services, or PostgreSQL are unavailable and tests cannot execute, explicitly mark verification as **`UNVERIFIED`** and state the exact reason. Never fabricate test outputs or claim completion while acceptance criteria remain unmet.

---

## F. Task Completion and Handoff

For every implementation task, conclude with a concise completion and handoff report containing:

- **Task objective**
- **Scope of implemented changes**
- **Modified files** (created, updated, deleted)
- **Architectural decisions**
- **Verification commands and results** (exact outputs, counts, or `UNVERIFIED` reason)
- **Acceptance criteria status** (itemized pass/fail/unverified)
- **Security and database impact** (auth, input validation, concurrency, Flyway migrations)
- **Known limitations and remaining issues** (including any out-of-scope findings)
- **Git branch and commit SHA**
- **Approximate execution time and retries** (when measurable)
- **Token or usage metrics** (only when actually available; omit or mark unavailable otherwise)

For **security-critical, transaction-related, or architecturally significant changes**, explicitly recommend an independent review by another AI model or a qualified human engineer before merging.

The human engineer retains final responsibility for approval and merging.
