CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       rut BIGINT NOT NULL UNIQUE,
                       dv CHAR(1) NOT NULL CHECK (dv ~ '^[0-9Kk]$'),
                       birth_date DATE NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       active BOOLEAN DEFAULT TRUE
);
