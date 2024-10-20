DROP DATABASE IF EXISTS cis2232_japanese_restaurant;
CREATE DATABASE cis2232_japanese_restaurant;
use cis2232_japanese_restaurant;

--------------------------------------------------------------------------------
-- Note the table below to hold data associated with your project.  Expect one
-- table with 7-9 fields.
--------------------------------------------------------------------------------

CREATE TABLE SkillsAssessmentSquashTechnical
(
    id                int(5),
    assessmentDate    varchar(10) NOT NULL COMMENT 'yyyy-MM-dd',
    createdDateTime   varchar(20) NOT NULL COMMENT 'yyyy-MM-dd hh:mm:ss',
    athleteName       varchar(50) NOT NULL COMMENT 'Athletes name',
    assessorName      varchar(50) NOT NULL COMMENT 'Athletes name',
    forehandDrives    int(5) COMMENT 'Number of forehand drives',
    backhandDrives    int(5) COMMENT 'Number of backhand drives',
    forehandVolleyMax int(5) COMMENT 'Max number of forehand volleys',
    forehandVolleySum int(5) COMMENT 'Sum of forehand volleys',
    backhandVolleyMax int(5) COMMENT 'Max number of backhand volleys',
    backhandVolleySum int(5) COMMENT 'Sum of backhand volleys',
    technicalScore    int(5) COMMENT 'Score calculated at submission'
) COMMENT 'This table holds technical skills assessment details';

ALTER TABLE SkillsAssessmentSquashTechnical
    ADD PRIMARY KEY (id);
ALTER TABLE SkillsAssessmentSquashTechnical
    MODIFY id int(4) NOT NULL AUTO_INCREMENT COMMENT 'This is the primary key',
    AUTO_INCREMENT = 1;

-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-22',	'2022-08-20 11:35:15','Maria Smith','BJ MacLean',		11,	5,	14,	78,	6,	59,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-11',	'2022-08-11 11:35:15','Rhonda Jones','BJ MacLean',		5,	7,	4,	36,	5,	38,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-07', '2022-08-07 11:35:15','Chad Collins','BJ MacLean',		8,	8,	4,	37,	5,	42,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-07', '2022-08-07 11:35:15','Rhonda Jones','BJ MacLean',		12,	8,	9,	53,	4,	42,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-07', '2022-08-05 11:35:15','Chad Collins','BJ MacLean',		8,	10,	7,	52,	3,	26,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-08',	'2022-08-08 11:35:15','Rhonda Jones','BJ MacLean',		10,	8,	8,	61,	6,	57,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-10',	'2022-08-10 11:35:15','Chad Collins','BJ MacLean',		17,	14,	8,	70,	13,	84,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-08',	'2022-08-08 11:35:15','Maria Smith','BJ MacLean',		17,	18,	12,	77,	8,	63,0);
-- insert into SkillsAssessmentSquashTechnical values(0, '2022-08-22',	'2022-08-20 11:35:15','Chad Collins','BJ MacLean',		14,	11,	10,	86,	16,	87,0);

INSERT INTO skillsassessmentsquashtechnical (id, assessmentDate, createdDateTime, athleteName, assessorName,
                                             forehandDrives, backhandDrives, forehandVolleyMax, forehandVolleySum,
                                             backhandVolleyMax, backhandVolleySum, technicalScore)
VALUES (1, '2022-08-22', '2023-11-07 01:38:48', 'Maria Smith', 'BJ MacLean', 11, 5, 14, 78, 6, 59, 1085),
       (2, '2022-08-11', '2023-11-07 01:38:52', 'Rhonda Jones', 'BJ MacLean', 5, 7, 4, 36, 5, 38, 622),
       (3, '2022-08-07', '2023-11-07 01:38:56', 'Chad Collins', 'BJ MacLean', 8, 8, 4, 37, 5, 42, 707),
       (4, '2022-08-07', '2023-11-07 01:38:59', 'Rhonda Jones', 'BJ MacLean', 12, 8, 9, 53, 4, 42, 879),
       (5, '2022-08-07', '2023-11-07 01:39:01', 'Chad Collins', 'BJ MacLean', 8, 10, 7, 52, 3, 26, 740),
       (6, '2022-08-08', '2023-11-07 01:39:04', 'Rhonda Jones', 'BJ MacLean', 10, 8, 8, 61, 6, 57, 972),
       (7, '2022-08-10', '2023-11-07 01:39:07', 'Chad Collins', 'BJ MacLean', 17, 14, 8, 70, 13, 84, 1403),
       (8, '2022-08-08', '2023-11-07 01:39:10', 'Maria Smith', 'BJ MacLean', 17, 18, 12, 77, 8, 63, 1385),
       (9, '2022-08-22', '2023-11-07 01:38:44', 'Chad Collins', 'BJ MacLean', 14, 11, 10, 86, 16, 87, 1448);


--------------------------------------------------------------------------------
-- NOTE for tables below.  These are standard tables that have the same structure for all
-- projects.  The code type tables can hold static lists that can be use in the program.
-- Doing this can allow you to reuse code from the sample project more easily.  Note
-- that I am loading the skill types into a code type below.
-- Code type 1 is always user types that may exist in your projects.
--------------------------------------------------------------------------------

CREATE TABLE CodeType
(
    codeTypeId         int(3) COMMENT 'This is the primary key for code types',
    englishDescription varchar(100) NOT NULL COMMENT 'English description',
    frenchDescription  varchar(100) DEFAULT NULL COMMENT 'French description',
    createdDateTime    datetime     DEFAULT NULL,
    createdUserId      varchar(20)  DEFAULT NULL,
    updatedDateTime    datetime     DEFAULT NULL,
    updatedUserId      varchar(20)  DEFAULT NULL
) COMMENT 'This tables holds the code types that are available for the application';

INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId,
                      updatedDateTime, updatedUserId)
VALUES (1, 'User Types', 'User Types FR', sysdate(), '', CURRENT_TIMESTAMP, '');
INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId,
                      updatedDateTime, updatedUserId)
VALUES (2, 'Skill Types Technical', 'Skill Types Technical FR', sysdate(), '', CURRENT_TIMESTAMP, '');
INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId,
                      updatedDateTime, updatedUserId)
VALUES (3, 'Skill Types Technical Points', 'Skill Types Technical Points FR', sysdate(), '', CURRENT_TIMESTAMP, '');

CREATE TABLE CodeValue
(
    codeTypeId              int(3)       NOT NULL COMMENT 'see code_type table',
    codeValueSequence       int(3)       NOT NULL,
    englishDescription      varchar(100) NOT NULL COMMENT 'English description',
    englishDescriptionShort varchar(20)  NOT NULL COMMENT 'English abbreviation for description',
    frenchDescription       varchar(100) DEFAULT NULL COMMENT 'French description',
    frenchDescriptionShort  varchar(20)  DEFAULT NULL COMMENT 'French abbreviation for description',
    sortOrder               int(3)       DEFAULT NULL COMMENT 'Sort order if applicable',
    createdDateTime         datetime     DEFAULT NULL,
    createdUserId           varchar(20)  DEFAULT NULL,
    updatedDateTime         datetime     DEFAULT NULL,
    updatedUserId           varchar(20)  DEFAULT NULL
) COMMENT ='This will hold code values for the application.';

INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (1, 1, 'General', 'General', 'GeneralFR', 'GeneralFR', '2015-10-25 18:44:37', 'admin', '2015-10-25 18:44:37',
        'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (1, 2, 'Admin', 'Admin', 'Admin', 'Admin', '2015-10-25 18:44:37', 'admin', '2015-10-25 18:44:37', 'admin');

INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (2, 3, 'Number Forehand Drives', 'NFD', 'Number Forehand DrivesFR', 'NFD', '2023-10-25 18:44:37', 'admin',
        '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (2, 4, 'Number Backhand Drives', 'NBD', 'Number Backhand DrivesFR', 'NBD', '2023-10-25 18:44:37', 'admin',
        '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (2, 5, 'Forehand Volley Max', 'FVM', 'Forehand Volley MaxFR', 'FVM', '2023-10-25 18:44:37', 'admin',
        '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (2, 6, 'Forehand Volley Sum', 'FVS', 'Forehand Volley SumFR', 'FVS', '2023-10-25 18:44:37', 'admin',
        '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (2, 7, 'Backhand Volley Max', 'BVM', 'Backhand Volley MaxFR', 'BVM', '2023-10-25 18:44:37', 'admin',
        '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (2, 8, 'Backhand Volley Sum', 'BVS', 'Backhand Volley SumFR', 'BVS', '2023-10-25 18:44:37', 'admin',
        '2023-10-25 18:44:37', 'admin');

INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (3, 9, '15', '15', '15', '15', '2023-10-25 18:44:37', 'admin', '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (3, 10, '15', '15', '15', '15', '2023-10-25 18:44:37', 'admin', '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (3, 11, '8', '8', '8', '8', '2023-10-25 18:44:37', 'admin', '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (3, 12, '5', '5', '5', '5', '2023-10-25 18:44:37', 'admin', '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (3, 13, '8', '8', '8', '8', '2023-10-25 18:44:37', 'admin', '2023-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription,
                       frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId)
VALUES (3, 14, '5', '5', '5', '5', '2023-10-25 18:44:37', 'admin', '2023-10-25 18:44:37', 'admin');


CREATE TABLE UserAccess
(
    userAccessId         int(3)       NOT NULL,
    username             varchar(100) NOT NULL COMMENT 'Unique user name for app',
    password             varchar(128) NOT NULL,
    name                 varchar(128),
    userAccessStatusCode int(3)       NOT NULL DEFAULT '1' COMMENT 'Code type #2',
    userTypeCode         int(3)       NOT NULL DEFAULT '1' COMMENT 'Code type #1',
    createdDateTime      datetime              DEFAULT NULL COMMENT 'When user was created.'
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
/*
Table Name: reservations

Description:
This table stores information about reservations made for a specific event or service. It keeps track of the number of
seniors, children, and adults attending, along with discounts applied and the final bill calculation.

Fields:
- ID (INT, AUTO_INCREMENT, PRIMARY KEY): Unique identifier for each reservation.
- Name (VARCHAR(100), NOT NULL): Name of the person making the reservation.
- Number_Of_Seniors (INT, NOT NULL): Number of seniors attending the event.
- Number_Of_Children (INT, NOT NULL): Number of children attending the event.
- Number_Of_Adults (INT, NOT NULL): Number of adults attending the event.
- Number_Of_Customers (INT, STORED GENERATED): Automatically calculated as the sum of seniors, children, and adults.
- Coupon_Discount (DOUBLE, DEFAULT 0.30): Discount applied to the total bill (30% off by default).
- Date_Time (DATETIME, NOT NULL): The date and time of the reservation.
- Entry_Cost_Per_Customer (INT, DEFAULT 25): The entry cost per customer, defaulted to $25 per customer.
- Children_Discount (DOUBLE, DEFAULT 0.20): Discount applied to children’s tickets (20% off by default).
- Senior_Discount (DOUBLE, DEFAULT 0.15): Discount applied to seniors’ tickets (15% off by default).
- Final_Bill (DOUBLE, STORED GENERATED): Automatically calculated final bill based on the number of customers and discounts.

Business Logic:
- The Number_Of_Customers is generated as the sum of seniors, children, and adults.
- Final_Bill is calculated based on the number of customers, the entry cost per customer, and the applicable discounts for children, seniors, and any coupon discounts.

*/
CREATE TABLE reservation
(
    id                   int auto_increment primary key,
    name                 varchar(50) not null,
    dateTime             datetime    not null,
    email                varchar(30) not null,
    numberOfAdults       int         not null,
    numberOfSeniors      int         not null,
    numberOfChildren     int         not null,
    numberOfCustomers    int generated always as (numberOfSeniors + numberOfChildren + numberOfAdults) stored,
    entryCostPerCustomer int    default 25,
    couponDiscount       double default 0.30,
    childrenDiscount     double default 0.20,
    seniorDiscount       double default 0.15,
    finalBill            double generated always as (
        (numberOfCustomers * entryCostPerCustomer)
            - (numberOfChildren * entryCostPerCustomer * childrenDiscount)
            - (numberOfSeniors * entryCostPerCustomer * seniorDiscount)
            - (numberOfCustomers * entryCostPerCustomer * couponDiscount)
        ) stored
);

INSERT INTO cis2232_japanese_restaurant.Reservation (name, email, numberOfAdults, numberOfSeniors, numberOfChildren,
                                                     dateTime, couponDiscount)
VALUES ('John Doe', 'john.doe@example.com', 2, 1, 1, '2024-10-20 18:30:00', 0.10);
INSERT INTO cis2232_japanese_restaurant.Reservation (name, email, numberOfAdults, numberOfSeniors, numberOfChildren,
                                                     dateTime, couponDiscount)
VALUES ('Alice Johnson', 'alice.johnson@example.com', 3, 2, 2, '2024-11-01 19:00:00', 0.00);
INSERT INTO cis2232_japanese_restaurant.Reservation (name, email, numberOfAdults, numberOfSeniors, numberOfChildren,
                                                     dateTime, couponDiscount)
VALUES ('Bob Smith', 'bob.smith@example.com', 4, 1, 3, '2024-11-10 18:00:00', 0.50);
INSERT INTO cis2232_japanese_restaurant.Reservation (name, email, numberOfAdults, numberOfSeniors, numberOfChildren,
                                                     dateTime, couponDiscount)
VALUES ('Charlie Lee', 'charlie.lee@example.com', 5, 0, 0, '2024-11-15 20:00:00', 0.20);
INSERT INTO cis2232_japanese_restaurant.Reservation (name, email, numberOfAdults, numberOfSeniors, numberOfChildren,
                                                     dateTime, couponDiscount)
VALUES ('David Green', 'david.green@example.com', 50, 10, 5, '2024-12-01 12:30:00', 0.10);
INSERT INTO cis2232_japanese_restaurant.Reservation (name, email, numberOfAdults, numberOfSeniors, numberOfChildren,
                                                     dateTime, couponDiscount)
VALUES ('Eve White', 'eve.white@example.com', 3, 0, 0, '2024-12-05 13:00:00', 0.00);


