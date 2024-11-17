DROP DATABASE IF EXISTS cis2232_japanese_restaurant;
CREATE DATABASE cis2232_japanese_restaurant;
USE cis2232_japanese_restaurant;

-- Disable foreign key checks to prevent constraint errors during table drops
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS codeValue;
DROP TABLE IF EXISTS codeType;
DROP TABLE IF EXISTS userAccess;
DROP TABLE IF EXISTS reservation;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create CodeType table
CREATE TABLE codeType
(
    codeTypeId         INT(3) PRIMARY KEY COMMENT 'Primary key for code types',
    englishDescription VARCHAR(100) NOT NULL COMMENT 'English description',
    frenchDescription  VARCHAR(100) DEFAULT NULL COMMENT 'French description',
    createdDateTime    DATETIME     DEFAULT NULL,
    createdUserId      VARCHAR(20)  DEFAULT NULL,
    updatedDateTime    DATETIME     DEFAULT NULL,
    updatedUserId      VARCHAR(20)  DEFAULT NULL
) COMMENT 'Holds code types for restaurant packages and offerings';

-- Insert values into CodeType table
INSERT INTO codeType (codeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId,
                      updatedDateTime, updatedUserId)
VALUES (1, 'User Types', 'User Types FR', sysdate(), '', CURRENT_TIMESTAMP, '');

-- Create CodeValue table
CREATE TABLE codeValue
(
    codeValueId             INT(3) AUTO_INCREMENT PRIMARY KEY,
    codeTypeId              INT(3)       NOT NULL COMMENT 'see codeType table',
    codeValueSequence       INT(3)       NOT NULL,
    englishDescription      VARCHAR(100) NOT NULL COMMENT 'English description',
    englishDescriptionShort VARCHAR(20)  NOT NULL COMMENT 'English abbreviation for description',
    frenchDescription       VARCHAR(100) DEFAULT NULL COMMENT 'French description',
    frenchDescriptionShort  VARCHAR(20)  DEFAULT NULL COMMENT 'French abbreviation for description',
    sortOrder               INT(3)       DEFAULT NULL COMMENT 'Sort order if applicable',
    createdDateTime         DATETIME     DEFAULT NULL,
    createdUserId           VARCHAR(20)  DEFAULT NULL,
    updatedDateTime         DATETIME     DEFAULT NULL,
    updatedUserId           VARCHAR(20)  DEFAULT NULL,
    FOREIGN KEY (codeTypeId) REFERENCES codeType (codeTypeId)
) COMMENT ='This will hold code values for the application.';

-- Insert values into CodeValue table to match the `reservationTypeId` values used in reservation table
INSERT INTO codeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, createdDateTime,
                       createdUserId, updatedDateTime, updatedUserId)
VALUES (1, 1, 'Standard', 'STD', '2024-01-01 00:00:00', 'admin', '2024-01-01 00:00:00', 'admin'),
       (1, 2, 'Deluxe', 'DLX', '2024-01-01 00:00:00', 'admin', '2024-01-01 00:00:00', 'admin'),
       (1, 3, 'Family Package', 'FAM', '2024-01-01 00:00:00', 'admin', '2024-01-01 00:00:00', 'admin');

-- Create UserAccess table
CREATE TABLE UserAccess
(
    userAccessId         INT(3)       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username             VARCHAR(100) NOT NULL COMMENT 'Unique user name for app',
    password             VARCHAR(128) NOT NULL,
    name                 VARCHAR(128),
    userAccessStatusCode INT(3)       NOT NULL DEFAULT '1' COMMENT 'Code type #2',
    userTypeCode         INT(3)       NOT NULL DEFAULT '1' COMMENT 'Code type #1',
    createdDateTime      DATETIME              DEFAULT NULL COMMENT 'When user was created.'
);

-- Create Reservation table
CREATE TABLE reservation
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(50)  NOT NULL COMMENT 'Customer name',
    email               VARCHAR(100) NOT NULL COMMENT 'Customer email',
    reservationDateTime DATETIME     NOT NULL COMMENT 'Date and time of reservation',
    numberOfAdults      INT(3)        DEFAULT 0 COMMENT 'Number of adults',
    numberOfSeniors     INT(3)        DEFAULT 0 COMMENT 'Number of seniors',
    numberOfChildren    INT(3)        DEFAULT 0 COMMENT 'Number of children',
    couponDiscount      DECIMAL(4, 2) DEFAULT 0.0 COMMENT 'Discount applied as a percentage (0.0 to 1.0)',
    totalCost           DECIMAL(10, 2) COMMENT 'Calculated total cost',
    reservationTypeId   INT(3),
    FOREIGN KEY (reservationTypeId) REFERENCES codeValue (codeValueId)
);

-- Insert reservation values
INSERT INTO reservation (name, email, reservationDateTime, reservationTypeId, numberOfAdults, numberOfSeniors,
                         numberOfChildren, couponDiscount, totalCost)
VALUES ('John Doe', 'john.doe@example.com', '2024-08-15 18:30', 1, 2, 1, 0, 0.1, 63.75),
       ('Laura Adams', 'laura.a@example.com', '2024-09-21 18:00', 2, 3, 1, 0, 0.10, 85.50),
       ('Ethan Murphy', 'ethan.murphy@example.com', '2024-10-22 19:00', 3, 2, 0, 1, 0.20, 74.00),
       ('Zoe Bell', 'zoe.bell@example.com', '2024-10-23 17:45', 1, 2, 2, 2, 0.15, 120.75),
       ('Lucas Walker', 'lucas.walker@example.com', '2024-11-24 20:30', 2, 1, 0, 0, 0.00, 30.00),
       ('Grace Hughes', 'grace.hughes@example.com', '2024-11-25 18:15', 3, 3, 0, 0, 0.05, 90.25),
       ('Emma Wilson', 'emma.wilson@example.com', '2024-11-26 19:00', 1, 0, 1, 3, 0.25, 65.00),
       ('Oliver Hall', 'oliver.hall@example.com', '2024-12-27 18:45', 3, 4, 1, 0, 0.10, 105.00),
       ('Charlotte Brooks', 'charlotte.brooks@example.com', '2024-12-28 18:30', 1, 2, 1, 1, 0.00, 88.50),
       ('Benjamin Green', 'benjamin.green@example.com', '2025-11-29 19:15', 2, 1, 1, 1, 0.10, 56.75);

-- Add additional reservations as needed
