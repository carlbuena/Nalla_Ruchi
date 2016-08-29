package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://pe-ps1623.scem.westernsydney.edu.au/addFood.php?id=";
    public static final String URL_GET_ALL = "http://pe-ps1623.scem.westernsydney.edu.au/getAllFood.php";
    public static final String URL_GET_FOOD = "http://pe-ps1623.scem.westernsydney.edu.au/getFood.php?id=";
    public static final String URL_UPDATE_FOOD = "http://pe-ps1623.scem.westernsydney.edu.au/updateFood.php";
    public static final String URL_DELETE_FOOD = "http://pe-ps1623.scem.westernsydney.edu.au/deleteFood.php?id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_FOOD_ID = "Food_Id";
    public static final String KEY_FOOD_NAME = "Food_Name";
    public static final String KEY_FOOD_PRICE = "Price";
    public static final String KEY_FOOD_DES = "Description";
    public static final String KEY_FOOD_ETH = "Ethnicity";
    public static final String KEY_FOOD_TYPE = "Type";
    public static final String KEY_MENU_ID = "Menu_ID";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_ID = "Food_Id";
    public static final String TAG_NAME = "Food_Name";
    public static final String TAG_PRICE = "Price";
    public static final String TAG_DES = "Description";
    public static final String TAG_ETH = "Ethnicity";
    public static final String TAG_FOOD_TYPE = "Type";
    public static final String TAG_MENU_ID = "Menu_ID";

    //employee id to pass with intent
    public static final String FOOD_ID = "Food_Id";
}