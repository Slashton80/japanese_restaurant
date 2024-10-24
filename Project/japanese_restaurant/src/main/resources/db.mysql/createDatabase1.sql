DROP DATABASE IF EXISTS cis2232_japanese_restaurant;
CREATE DATABASE cis2232_japanese_restaurant;
use cis2232_japanese_restaurant;

--------------------------------------------------------------------------------
-- Note the table below to hold data associated with your project.  Expect one
-- table with 7-9 fields.
--------------------------------------------------------------------------------


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

DROP TABLE IF EXISTS reservation;

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

INSERT INTO reservation (id, name, dateTime, email, numberOfAdults, numberOfSeniors, numberOfChildren, couponDiscount)
VALUES
    (1, 'John Doe', '2024-10-20 18:30:00', 'john.doe@example.com', 2, 1, 1, 0.10),
    (2, 'Alice Johnson', '2024-11-01 19:00:00', 'alice.johnson@example.com', 3, 2, 2, 0.00),
    (3, 'Bob Smith', '2024-11-10 18:00:00', 'bob.smith@example.com', 4, 1, 3, 0.50),
    (4, 'Charlie Lee', '2024-11-15 20:00:00', 'charlie.lee@example.com', 5, 0, 0, 0.20),
    (5, 'David Green', '2024-12-01 12:30:00', 'david.green@example.com', 50, 10, 5, 0.10),
    (6, 'Frank Miller', '2024-09-15 14:00:00', 'frank.miller@example.com', 1, 0, 0, 0.05),
    (7, 'Grace Hopper', '2024-10-05 16:30:00', 'grace.hopper@example.com', 3, 1, 2, 0.15),
    (8, 'Henry Ford', '2024-10-22 19:45:00', 'henry.ford@example.com', 2, 0, 1, 0.00),
    (9, 'Isabella Garcia', '2024-11-08 11:15:00', 'isabella.garcia@example.com', 4, 2, 3, 0.20),
    (10, 'Jack Black', '2024-11-12 17:00:00', 'jack.black@example.com', 5, 1, 0, 0.10),
    (11, 'Karen Johnson', '2024-11-20 20:30:00', 'karen.johnson@example.com', 2, 2, 2, 0.05),
    (12, 'Liam Nguyen', '2024-12-03 13:00:00', 'liam.nguyen@example.com', 3, 0, 1, 0.00),
    (13, 'Mia Brown', '2024-12-10 09:30:00', 'mia.brown@example.com', 1, 0, 0, 0.20),
    (14, 'Noah Davis', '2024-12-18 18:45:00', 'noah.davis@example.com', 4, 3, 2, 0.10),
    (15, 'Olivia Wilson', '2025-01-05 14:00:00', 'olivia.wilson@example.com', 2, 1, 1, 0.15);




