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

    //Keys that will be used to send the request to php scripts
    public static final String KEY_FOOD_ID = "Food_Id";
    public static final String KEY_FOOD_NAME = "Food_Name";
    public static final String KEY_FOOD_PRICE = "Price";
    public static final String KEY_FOOD_DES = "Description";
    public static final String KEY_FOOD_ETH = "Ethnicity";
    public static final String KEY_FOOD_TYPE = "Type";
    public static final String KEY_FOOD_DISH = "Dish_Type";
    public static final String KEY_MENU_ID = "Menu_ID";

    public static final String KEY_CUSTOMER_ID = "Customer_Id";
    public static final String KEY_CUSTOMER_FIRSTNAME = "FirstName";
    public static final String KEY_CUSTOMER_SURNAME = "Surname";
    public static final String KEY_CUSTOMER_MOBILE = "Mobile";
    public static final String KEY_CUSTOMER_EMAIL = "Email";
    public static final String KEY_CUSTOMER_PASSWORD = "Password";
    public static final String KEY_CUSTOMER_ADDRESS = "Address";
    public static final String KEY_CUSTOMER_SUBURB = "Suburb";
    public static final String KEY_CUSTOMER_STATE = "State";
    public static final String KEY_CUSTOMER_DOR = "DateOfRegistration";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_ID = "Food_Id";
    public static final String TAG_NAME = "Food_Name";
    public static final String TAG_PRICE = "Price";
    public static final String TAG_DES = "Description";
    public static final String TAG_ETH = "Ethnicity";
    public static final String TAG_TYPE = "Type";
    public static final String TAG_DISH = "Dish_Type";
    public static final String TAG_MENU_ID = "Menu_ID";

    public static final String TAG_CUSTOMER_ID = "Customer_Id";
    public static final String TAG_FIRSTNAME = "FirstName";
    public static final String TAG_SURNAME = "Surname";
    public static final String TAG_MOBILE = "Mobile";
    public static final String TAG_EMAIL = "Email";
    public static final String TAG_PASSWORD = "Password";
    public static final String TAG_ADDRESS = "Address";
    public static final String TAG_SUBURB = "Suburb";
    public static final String TAG_STATE = "State";
    public static final String TAG_DOR = "DateOfRegistration";

    //food id to pass with intent
    public static final String FOOD_ID = "Food_Id";
}