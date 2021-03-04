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

INSERT INTO persons
VALUES (1, 'John', 'Doe', '123 test st', 'Test City', 12345, '0607080910', 'john.doe@test.fr');
INSERT INTO persons
VALUES (2, 'Jane', 'Doe', '456 test st', 'Test City', 12345, '0607080910', 'john.doe@test.fr');
INSERT INTO persons
VALUES (3, 'Test', 'Test', '789 test st', 'Test City', 12345, '0607080910', 'john.doe@test.fr');

DROP TABLE IF EXISTS firestations;

CREATE TABLE firestations
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    station INT,
    address VARCHAR(250) NOT NULL
);

INSERT INTO firestations
VALUES (1, 1, '123 test st');
INSERT INTO firestations
VALUES (2, 2, '456 test st');
INSERT INTO firestations
VALUES (3, 3, '789 test st');

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

INSERT INTO medicalrecords
VALUES (1, 'John', 'Doe', '28/08/2020', ('test', 'test'), ('test', 'test'));
INSERT INTO medicalrecords
VALUES (2, 'Jane', 'Doe', '28/08/2015', ('test', 'test'), ('test', 'test'));
INSERT INTO medicalrecords
VALUES (3, 'Test', 'Test', '28/08/1991', ('test', 'test'), ('test', 'test'));
