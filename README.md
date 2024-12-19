## About the Project
This project was developed as part of my CIS coursework at Holland College PEI in 2024. We worked in a group of three to complete this project, with each member playing a specific role. I served as the **Lead Developer**, where I was tasked with implementing the Japanese Restaurant system based on the idea presented by our **Business Client**, Nguyen Nguyen. Additionally, I contributed as a **Quality Assurance (QA)** tester for another teammate's project.

This project was an excellent demonstration of teamwork and collaboration. It also introduced us to **source control and project management tools**, such as **Bitbucket**, **Jira**, and **Sourcetree**, which we used extensively to manage our codebase, track tasks, and maintain version control throughout the development process.

Some of the code was adapted from BJ MacLean's examples.

---
# Advanecd Java Project: Japanese Restaurant #
---

## Development Team ##
Business Client:  Nguyen Nguyen
<br/>
Lead Developer:  Sherri Ashton
<br/>
Quality Control:   Vy Phan
<br/>
<br/>
The project: This project Japanese Restaurant belongs to Nguyen Nguyen.
<br/>

---

## Description ##
**A Culinary Journey: Our All - You - Can - Eat -  Japanese Restaurant**
<br/><br/>
Our Japanese restaurant offers an authentic dining experience that will transport you to the Land of the Rising Sun. With a commitment to using the freshest ingredients and traditional cooking techniques, we strive to create dishes that are both delicious and visually stunning.
<br/><br/>
Our menu features a wide variety of Japanese specialties, from classic sushi and sashimi to hearty ramen and savory tempura. For those seeking a more substantial meal, our ramen dishes are packed with flavorful broth, tender noodles, and a variety of toppings.
<br/><br/>
**A Seamless Reservation Experience**
<br/><br/>
To make your dining experience even more enjoyable, we offer a convenient online reservation system. Simply visit our website and select your desired date, time, and number of guests. Once you've submitted your reservation, you'll receive a confirmation email, ensuring that your booking is secure.
<br/><br/>
**Exclusive Offers and Discounts**
<br/><br/>
To make your reservation even more rewarding, we offer a variety of coupons and discounts that can be applied to your online booking. Whether you're looking for a discount on your total bill or a complimentary appetizer, our coupons provide a great way to save money and enhance your dining experience.
<br/><br/>
We understand that unexpected changes can sometimes occur, so we offer a flexible cancellation policy. If you need to cancel your reservation, simply contact us directly and we'll do our best to accommodate your request.
<br/><br/>
By choosing to make your reservation online, you're not only ensuring a hassle-free dining experience but also taking advantage of exclusive offers and discounts. We look forward to welcoming you to our restaurant and providing you with an unforgettable culinary journey.</br>

---

## Color ##
 --main-color-grey: #9c9a9a;<br>
    --main-color-primary: #d1a36e;<br>
    --main-color-white: #f9f4ef;<br>
    --main-color-text: #fa8072;<br>
    --main-color-bright: #ffffff;<br>
    --main-color-container: #e4ddd6;<br>
    
---
## Required Fields ##
This will be a list of fields and their datatype (class design format). There are expected to be a minimum of six fields.<br>
id: int - Unique id Assigns ID for every reservation <br/>
name: String - Name of the person who books <br/>
numberOfSeniors: int - Number of seniors that will attend <br/>
numberOfChildren: int - Number of children that will attend <br/>
numberOfAdults: int - Number of adults that will attend <br/>
couponDiscount -double - 30% off the bill</br>
entryCostPerCustomer - int - $25 for each customer<br/>
date: LocalDateTime - The date and time of the reservation <br/>
email: String Email - address of the person who books </br>
bill: double - Final bill according to how many customers attend and if they have discounts  (see calculation) </br>
childrenDiscount -double - 20% off the bill<br/>
seniorDiscount - double - 15% off the bill
 
---
## Calculation ##

Entry Cost per Customer: Every customer has a base entry cost of $25. This means, for each customer attending, the total bill starts by multiplying the number of customers by this base cost. <br/>

Discounts for Children and Seniors: If there are children or seniors attending, they receive special discounts on their portion of the bill.<br/>

Children receive a 20% discount. <br/>

Seniors receive a 15% discount. <br/>

Regular Adult Customers: Any adults attending without a discount will simply pay the full entry cost of $25.<br/>

Applying a Coupon Discount: If a coupon is applied, it will reduce the total bill by 30%. The final bill is calculated by subtracting 30% from the total. <br/>


---
## Report Details ##
The report allows users to retrieve reservations based on a specified time period.<br/>

DAO Class:
<br/>Complete the DAO class to fetch reservation data from the database using JDBC.
User Input View:
Develop an input form for users to enter a start and end date for the report.<br/>
<br/>
Controller Class:
<br/>Created a controller to manage user inputs, process report logic, and display results.
Results View:
<br/>Designed a view to show the report results along with the input criteria.<br/>
<br/>
File Output:
<br/>Implemented functionality to save report results to a file in the c:\cis2232\ directory, with a naming convention that includes the report type and current date (e.g., ReservationsForPeriodYYYYMMDDHHMM.txt).

---
## Sprints and Assignment 4 ##

### Sprint 1
<br/> - Initial version of the project created and checked into Bitbucket.
<br/> - Project name updated, and context path set.
<br/> - README created with project information, formatted neatly.
<br/> - Repository access granted to the learning manager.
<br/> - Team members added as collaborators.
<br/> - Color scheme implemented and demonstrated in the project.

### Sprint 2
<br/> - Report functionality implemented, as specified in the README.
<br/> - JDBC used for fetching input and output result sets.
<br/> - Report content written to a file in the specified format.

### Sprint 3
<br/> - Added list page functionality to the web application.
<br/> - Calculations completed when add/edit is submitted, showing detailed results to the user.
<br/> - Implemented functionality for adding, updating, and deleting entries in the web application.
<br/> - Enabled Spring validation for adding new entries.

### Sprint 4
<br/> - REST services implemented to provide JSON data.
<br/> - Clients set up to call the REST services.
<br/> - Standard response codes implemented, including error handling for invalid inputs.
<br/> - Services provided for fetching all records, selecting one record, and performing create, update, and delete operations.
<br/> - SOAP service implemented to fetch a single entity by ID, along with a client to call the service.

### Sprint 5
<br/> - Added search functionality to allow searching by non-primary keys on the list page. In this case by a name.
<br/> - Business validation rules implemented - validates date is not in the past for making reservations, having at least a senior or adult book and no children can be booked without an adult or senior.
<br/> - Unit tests created for calculation methods and input validation.
<br/> - Demo provided showcasing full application functionality.
<br/> - Repository, documentation, and project code reviewed for quality assurance.

### Assignment 4: External API Access
<br/> - Integrated an external API to fetch additional information.
<br/> - Java client implemented to call the API.
<br/> - Web application modified to display results from the API call.

---


