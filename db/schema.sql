CREATE TABLE types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE rules
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE accidents
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    text    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    type_id INTEGER NOT NULL REFERENCES types (id)
);

CREATE TABLE accident_rules
(
    accident_id INTEGER NOT NULL REFERENCES accident (id) ON DELETE CASCADE,
    rules_id    INTEGER NOT NULL REFERENCES rules (id)
);