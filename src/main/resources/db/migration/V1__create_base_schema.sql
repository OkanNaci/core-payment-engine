CREATE TABLE orders (
                        id VARCHAR(255) PRIMARY KEY,
                        status VARCHAR(255) NOT NULL,

                        CONSTRAINT orders_status_check
                            CHECK (status IN (
                                              'PENDING',
                                              'COMPLETED',
                                              'REFUNDED',
                                              'FAILED',
                                              'CANCELED'
                                ))
);


CREATE TABLE products (
                          id VARCHAR(255) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price NUMERIC(19,4) NOT NULL,
                          order_id VARCHAR(255),

                          CONSTRAINT fk_products_order
                              FOREIGN KEY (order_id)
                                  REFERENCES orders(id)
);


CREATE TABLE users (
                       id VARCHAR(255) PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,

                       CONSTRAINT uk_users_email
                           UNIQUE (email),

                       CONSTRAINT users_role_check
                           CHECK (role IN ('USER', 'ADMIN'))
);


CREATE TABLE wallets (
                         id VARCHAR(255) PRIMARY KEY,
                         balance NUMERIC(19,4) NOT NULL,

                         CONSTRAINT wallets_balance_check
                             CHECK (balance >= 0)
);