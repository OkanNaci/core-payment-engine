# Core Payment Engine - Database ERD

```mermaid
erDiagram

    ORDERS ||--o{ PRODUCTS : contains

    ORDERS {
        varchar id PK
        varchar status
    }

    PRODUCTS {
        varchar id PK
        varchar name
        numeric price
        varchar order_id FK
    }

    USERS {
        varchar id PK
        varchar email UK
        varchar password
        varchar role
    }

    WALLETS {
        varchar id PK
        numeric balance
    }