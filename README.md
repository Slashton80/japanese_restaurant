# Project: Japanese Restaurant #
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
main-color-light: #9c9a9a;<br/>
main-color-primary: #d1a36e;<br/>
main-color-dark: #f9f4ef;<br/>
main-color-text: #ff6347;<br/>
main-color-bright: #ffffff;<br/>
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
Currently, this is the project as it stands. However, modifications to documents, layout, functionality, and colors may be made in the future based on the client's preferences.
