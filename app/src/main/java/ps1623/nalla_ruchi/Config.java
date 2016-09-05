package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD_FOOD ="http://pe-ps1623.scem.westernsydney.edu.au/api/food/addFood.php";
    public static final String URL_GET_ALL = "http://pe-ps1623.scem.westernsydney.edu.au/api/food/getAllFood.php";
    public static final String URL_GET_FOOD = "http://pe-ps1623.scem.westernsydney.edu.au/api/food/getFood.php";
    public static final String URL_UPDATE_FOOD = "http://pe-ps1623.scem.westernsydney.edu.au/api/food/updateFood.php";
    public static final String URL_DELETE_FOOD = "http://pe-ps1623.scem.westernsydney.edu.au/api/food/deleteFood.php";

    public static final String URL_ADD_CUSTOMER = "http://pe-ps1623.scem.westernsydney.edu.au/api/customer/addCustomer.php";
    public static final String URL_GET_ALL_CUSTOMERS = "http://pe-ps1623.scem.westernsydney.edu.au/api/customer/getAllCustomers.php";
    public static final String URL_GET_CUSTOMER = "http://pe-ps1623.scem.westernsydney.edu.au/api/customer/getCustomer.php";
    public static final String URL_UPDATE_CUSTOMER = "http://pe-ps1623.scem.westernsydney.edu.au/api/customer/updateCustomer.php";
    public static final String URL_DELETE_CUSTOMER = "http://pe-ps1623.scem.westernsydney.edu.au/api/customer/deleteCustomer.php";

    public static final String URL_ADD_COOK="http://pe-ps1623.scem.westernsydney.edu.au/api/cook/addCook.php";
    public static final String URL_GET_ALL_COOKS = "http://pe-ps1623.scem.westernsydney.edu.au/api/cook/getAllCooks.php";
    public static final String URL_GET_COOK = "http://pe-ps1623.scem.westernsydney.edu.au/api/cook/getCook.php";
    public static final String URL_UPDATE_COOK = "http://pe-ps1623.scem.westernsydney.edu.au/api/cook/updateCooks.php";
    public static final String URL_DELETE_COOK = "http://pe-ps1623.scem.westernsydney.edu.au/api/cook/deleteCooks.php";

    public static final String URL_ADD_BOOKING = "http://pe-ps1623.scem.westernsydney.edu.au/api/booking/addBooking.php";
    public static final String URL_GET_ALL_BOOKINGS = "http://pe-ps1623.scem.westernsydney.edu.au/api/booking/getAllBooking.php";
    public static final String URL_GET_BOOKING = "http://pe-ps1623.scem.westernsydney.edu.au/api/booking/getBooking.php";
    public static final String URL_UPDATE_BOOKING = "http://pe-ps1623.scem.westernsydney.edu.au/api/booking/updateBooking.php";
    public static final String URL_DELETE_BOOKING = "http://pe-ps1623.scem.westernsydney.edu.au/api/booking/deleteBooking.php";

    public static final String URL_ADD_ORDER ="http://pe-ps1623.scem.westernsydney.edu.au/api/order/addOrder.php";
    public static final String URL_GET_ALL_ORDERS = "http://pe-ps1623.scem.westernsydney.edu.au/api/order/getAllOrders.php";
    public static final String URL_GET_ORDER = "http://pe-ps1623.scem.westernsydney.edu.au/api/order/getOrder.php";
    public static final String URL_UPDATE_ORDER = "http://pe-ps1623.scem.westernsydney.edu.au/api/order/updateOrder.php";
    public static final String URL_DELETE_ORDER = "http://pe-ps1623.scem.westernsydney.edu.au/api/order/deleteOrder.php";

    public static final String URL_ADD_PAYMENT ="http://pe-ps1623.scem.westernsydney.edu.au/api/payment/addPayment.php";
    public static final String URL_GET_ALL_PAYMENTS = "http://pe-ps1623.scem.westernsydney.edu.au/api/payment/getAllPayments.php";
    public static final String URL_GET_PAYMENT = "http://pe-ps1623.scem.westernsydney.edu.au/api/payment/getPayment.php";
    public static final String URL_UPDATE_PAYMENT = "http://pe-ps1623.scem.westernsydney.edu.au/api/payment/updatePayment.php";
    public static final String URL_DELETE_PAYMENT = "http://pe-ps1623.scem.westernsydney.edu.au/api/payment/deletePayment.php";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_FOOD_ID = "Food_Id";
    public static final String KEY_FOOD_NAME = "Food_Name";
    public static final String KEY_FOOD_PRICE = "Price";
    public static final String KEY_FOOD_DES = "Description";
    public static final String KEY_FOOD_ETH = "Ethnicity";
    public static final String KEY_FOOD_TYPE = "Type";
    public static final String KEY_FOOD_DISH = "Dish_Type";
    public static final String KEY_FOOD_MENU_ID = "Menu_ID";

    public static final String KEY_CUSTOMER_ID = "Customer_ID";
    public static final String KEY_CUSTOMER_FIRSTNAME = "FirstName";
    public static final String KEY_CUSTOMER_SURNAME = "Surname";
    public static final String KEY_CUSTOMER_MOBILE = "Mobile";
    public static final String KEY_CUSTOMER_EMAIL = "Email";
    public static final String KEY_CUSTOMER_PASSWORD = "Password";
    public static final String KEY_CUSTOMER_ADDRESS = "Address";
    public static final String KEY_CUSTOMER_SUBURB = "Suburb";
    public static final String KEY_CUSTOMER_STATE = "State";
    public static final String KEY_CUSTOMER_DOR = "DateOfRegistration";

    public static final String KEY_COOK_ID = "Cook_Id";
    public static final String KEY_COOK_FIRSTNAME = "FirstName";
    public static final String KEY_COOK_SURNAME = "Surname";
    public static final String KEY_COOK_MOBILE = "Mobile";
    public static final String KEY_COOK_EMAIL = "Email";
    public static final String KEY_COOK_PASSWORD = "Password";
    public static final String KEY_COOK_ADDRESS = "Address";
    public static final String KEY_COOK_SUBURB = "Suburb";
    public static final String KEY_COOK_STATE = "State";
    public static final String KEY_COOK_DOR = "DateOfRegistration";

    public static final String KEY_BOOKING_ID = "Booking_ID";
    public static final String KEY_BOOKING_PAYMENT_ID = "Payment_ID";
    public static final String KEY_BOOKING_CUSTOMER_ID = "Customer_ID";
    public static final String KEY_BOOKING_DATE = "BookingDate";
    public static final String KEY_BOOKING_COOK_ID = "Cook_ID";
    public static final String KEY_BOOKING_PRICE = "bookingPrice";
    public static final String KEY_BOOKING_STATUS = "bookingStatus";

    public static final String KEY_ORDER_ID = "Order_ID";
    public static final String KEY_ORDER_PAYMENT_ID = "Payment_ID";
    public static final String KEY_ORDER_CUSTOMER_ID = "Customer_ID";
    public static final String KEY_ORDER_FOOD_ID= "Food_ID";
    public static final String KEY_ORDER_COOK_ID = "Cook_ID";
    public static final String KEY_ORDER_PRICE = "orderPrice";
    public static final String KEY_ORDER_DATE = "orderDate";
    public static final String KEY_ORDER_STATUS = "orderStatus";
    public static final String KEY_ORDER_MADE = "orderMadeDate";
    public static final String KEY_ORDER_DUE = "orderDueDate";

    public static final String KEY_PAYMENT_ID = "Payment_ID";
    public static final String KEY_PAYMENT_TYPE = "paymentType";
    public static final String KEY_PAYMENT_AMOUNT = "Amount";
    public static final String KEY_PAYMENT_DATE = "Date";
    public static final String KEY_PAYMENT_ORDER_ID = "Order_ID";
    public static final String KEY_PAYMENT_BOOKING_ID = "Booking_ID";
    public static final String KEY_PAYMENT_CUSTOMER_ID = "Customer_ID";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_ID = "Food_Id";
    public static final String TAG_NAME = "Food_Name";
    public static final String TAG_PRICE = "Price";
    public static final String TAG_DES = "Description";
    public static final String TAG_ETH = "Ethnicity";
    public static final String TAG_TYPE = "Type";
    public static final String TAG_DISH = "Dish_Type";
    public static final String TAG_FOOD_MENU_ID = "Menu_ID";

    public static final String TAG_CUSTOMER_ID = "Customer_ID";
    public static final String TAG_FIRSTNAME = "FirstName";
    public static final String TAG_SURNAME = "Surname";
    public static final String TAG_MOBILE = "Mobile";
    public static final String TAG_EMAIL = "Email";
    public static final String TAG_PASSWORD = "Password";
    public static final String TAG_ADDRESS = "Address";
    public static final String TAG_SUBURB = "Suburb";
    public static final String TAG_STATE = "State";
    public static final String TAG_DOR = "DateOfRegistration";

    public static final String TAG_COOK_ID = "Cook_Id";
    public static final String TAG_COOK_FIRSTNAME = "FirstName";
    public static final String TAG_COOK_SURNAME = "Surname";
    public static final String TAG_COOK_MOBILE = "Mobile";
    public static final String TAG_COOK_EMAIL = "Email";
    public static final String TAG_COOK_PASSWORD = "Password";
    public static final String TAG_COOK_ADDRESS = "Address";
    public static final String TAG_COOK_SUBURB = "Suburb";
    public static final String TAG_COOK_STATE = "State";
    public static final String TAG_COOK_DOR = "DateOfRegistration";

    public static final String TAG_BOOKING_ID = "Booking_ID";
    public static final String TAG_BOOKING_CUSTOMER_ID = "Customer_ID";
    public static final String TAG_BOOKINGDATE = "BookingDate";
    public static final String TAG_BOOKING_COOK_ID = "Cook_ID";
    public static final String TAG_BOOKINGPRICE = "bookingPrice";
    public static final String TAG_BOOKINGSTATUS = "bookingStatus";

    public static final String TAG_ORDER_ID = "Order_ID";
    public static final String TAG_ORDER_CUSTOMER_ID= "Customer_ID";
    public static final String TAG_ORDER_FOOD_ID = "Food_ID";
    public static final String TAG_ORDER_COOK_ID = "Cook_ID";
    public static final String TAG_ORDER_PRICE = "orderPrice";
    public static final String TAG_ORDER_DATE = "orderDate";
    public static final String TAG_ORDER_STATUS = "orderStatus";
    public static final String TAG_ORDER_MADE = "orderMadeDate";
    public static final String TAG_ORDER_DUE = "orderDueDate";

    public static final String TAG_PAYMENT_ID = "Payment_ID";
    public static final String TAG_PAYMENT_TYPE = "paymentType";
    public static final String TAG_PAYMENT_AMOUNT = "Amount";
    public static final String TAG_PAYMENT_DATE = "Date";
    public static final String TAG_PAYMENT_ORDER_ID = "Order_ID";
    public static final String TAG_PAYMENT_BOOKING_ID = "Booking_ID";
    public static final String TAG_PAYMENT_CUSTOMER_ID = "Customer_ID";

    //food id to pass with intent
    public static final String FOOD_ID = "Food_ID";
    public static final String CUSTOMER_ID = "Customer_ID";
    public static final String COOK_ID = "Cook_Id";
    public static final String BOOKING_ID = "Booking_ID";
    public static final String ORDER_ID = "Order_ID";
    public static final String PAYMENT_ID = "Payment_ID";
}