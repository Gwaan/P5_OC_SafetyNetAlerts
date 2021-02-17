DROP TABLE IF EXISTS persons;

CREATE TABLE persons
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(250) NOT NULL,
    last_name  VARCHAR(250) NOT NULL,
    address    VARCHAR(250) NOT NULL,
    city       VARCHAR(250) NOT NULL,
    zip        VARCHAR(5),
    phone      VARCHAR(30),
    email      VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS firestations;

CREATE TABLE firestations
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    station INT,
    address VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS medicalrecords;

CREATE TABLE medicalrecords
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(250) NOT NULL,
    last_name   VARCHAR(250) NOT NULL,
    birth_date  DATE         NOT NULL,
    medications VARCHAR(250),
    allergies   VARCHAR(250)
);
