DROP DATABASE IF EXISTS cis2232_bus_pass;
CREATE DATABASE cis2232_bus_pass;
use cis2232_bus_pass;

CREATE TABLE CodeType (codeTypeId int(3) COMMENT 'This is the primary key for code types',
  englishDescription varchar(100) NOT NULL COMMENT 'English description',
  frenchDescription varchar(100) DEFAULT NULL COMMENT 'French description',
  createdDateTime datetime DEFAULT NULL,
  createdUserId varchar(20) DEFAULT NULL,
  updatedDateTime datetime DEFAULT NULL,
  updatedUserId varchar(20) DEFAULT NULL
) COMMENT 'This tables holds the code types that are available for the application';

INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
(1, 'User Types', 'User Types FR', sysdate(), '', CURRENT_TIMESTAMP, '');
INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 'Pass Types', 'Pass Types FR', sysdate(), '', CURRENT_TIMESTAMP, '');

CREATE TABLE CodeValue (
  codeTypeId int(3) NOT NULL COMMENT 'see code_type table',
  codeValueSequence int(3) NOT NULL,
  englishDescription varchar(100) NOT NULL COMMENT 'English description',
  englishDescriptionShort varchar(20) NOT NULL COMMENT 'English abbreviation for description',
  frenchDescription varchar(100) DEFAULT NULL COMMENT 'French description',
  frenchDescriptionShort varchar(20) DEFAULT NULL COMMENT 'French abbreviation for description',
  sortOrder int(3) DEFAULT NULL COMMENT 'Sort order if applicable',
  createdDateTime datetime DEFAULT NULL,
  createdUserId varchar(20) DEFAULT NULL,
  updatedDateTime datetime DEFAULT NULL,
  updatedUserId varchar(20) DEFAULT NULL
) COMMENT='This will hold code values for the application.';

INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
(1, 1, 'General', 'General', 'GeneralFR', 'GeneralFR', '2015-10-25 18:44:37', 'admin', '2015-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
(1, 2, 'Admin', 'Admin', 'Admin', 'Admin', '2015-10-25 18:44:37', 'admin', '2015-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 3, 'Regular', 'Regular', 'RegularFR', 'RegularFR', '2024-09-13 18:44:37', 'admin', '2024-09-13 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 4, 'K12', 'K12', 'K12FR', 'K12FR', '2024-09-13 18:44:37', 'admin', '2024-09-13 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 5, 'Student', 'Student', 'StudentFR', 'StudentFR', '2024-09-13 18:44:37', 'admin', '2024-09-13 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 6, 'Senior', 'Senior', 'SeniorFR', 'SeniorFR', '2024-09-13 18:44:37', 'admin', '2024-09-13 18:44:37', 'admin');

CREATE TABLE UserAccess (
  userAccessId int(3) NOT NULL,
  username varchar(100) NOT NULL COMMENT 'Unique user name for app',
  password varchar(128) NOT NULL,
  name varchar(128),
  userAccessStatusCode int(3) NOT NULL DEFAULT '1' COMMENT 'Code type #2',
  userTypeCode int(3) NOT NULL DEFAULT '1' COMMENT 'Code type #1',
  createdDateTime datetime DEFAULT NULL COMMENT 'When user was created.'
);

ALTER TABLE CodeType
  ADD PRIMARY KEY (codeTypeId);

ALTER TABLE CodeValue
  ADD PRIMARY KEY (codeValueSequence);
--  ADD KEY codeTypeId (codeTypeId);

ALTER TABLE UserAccess
  ADD PRIMARY KEY (userAccessId),
  ADD KEY userTypeCode (userTypeCode);

ALTER TABLE CodeType
  MODIFY codeTypeId int(3) NOT NULL COMMENT 'This is the primary key for code types';

ALTER TABLE CodeValue
  MODIFY codeValueSequence int(3) NOT NULL;

ALTER TABLE UserAccess
  MODIFY userAccessId int(3) NOT NULL AUTO_INCREMENT;

CREATE TABLE BusPass(
id int(5),
name varchar(20) NOT NULL COMMENT 'Customer name',
address varchar(50) NOT NULL  COMMENT 'Address',
city varchar(20) NOT NULL COMMENT 'City',
preferredRoute varchar(10) NOT NULL COMMENT 'Preferred bus route',
passType int(3) NOT NULL COMMENT 'Code Type 2',
validForRuralRoute boolean NOT NULL  COMMENT 'Rural route option true/false',
lengthOfPass int(4) NOT NULL default 0 COMMENT 'Number of days for the pass',
cost decimal(6,2) NOT NULL  COMMENT 'Cost of pass'
) COMMENT 'This table holds bus pass data';

ALTER TABLE BusPass
  ADD PRIMARY KEY (id);
ALTER TABLE BusPass
  MODIFY id int(4) NOT NULL AUTO_INCREMENT COMMENT 'This is the primary key', AUTO_INCREMENT=1;

# insert into BusPass values();
