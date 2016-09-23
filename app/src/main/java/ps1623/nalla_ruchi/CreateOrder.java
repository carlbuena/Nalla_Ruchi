package ps1623.nalla_ruchi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Prazad Silva on 16/9/2016.
 */
public class CreateOrder extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    //Declaring an Spinner
    private Spinner cookOrderSpinner;
    private Spinner FoodSpinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> cooks;
    private ArrayList<String> food;

    //JSON Array
    private JSONArray result;

    //TextViews to display details
    private TextView textViewFirstName;
    private TextView textViewSurname;
    private TextView textViewAddress;

    private TextView textViewPrice;
    private TextView textViewDescription;
    private TextView textViewEthnicity;
    private TextView textViewType;
    private TextView textViewDishType;

    DatePicker datePicker;
    TextView displayDate;
    Button selectDate;
    int month;

    TimePicker timePicker;
    TextView displayTime;
    Button changeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_order);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        selectDate = (Button) findViewById(R.id.select_date_button);
        displayDate = (TextView) findViewById(R.id.display_date);
        displayDate.setText("Display Date");

        datePicker.setMinDate(System.currentTimeMillis()-1000);

        displayDate.setText(currentDate());
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDate.setText(currentDate());
            }
        });

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        displayTime = (TextView) findViewById(R.id.display_time);
        changeTime = (Button) findViewById(R.id.bchange_time);

        timePicker.setIs24HourView(false);
        displayTime.setText(currentTime());
        changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTime.setText(currentTime());
            }
        });

        //Initializing the ArrayList
        cooks = new ArrayList<String>();
        food = new ArrayList<String>();

        //Initializing Spinner
        cookOrderSpinner = (Spinner) findViewById(R.id.cookOrderSpinner);
        FoodSpinner = (Spinner) findViewById(R.id.FoodSpinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class itself we are passing this to setOnItemSelectedListener
        cookOrderSpinner.setOnItemSelectedListener(this);
        FoodSpinner.setOnItemSelectedListener(this);

        //Initializing TextViews
        textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
        textViewSurname = (TextView) findViewById(R.id.textViewSurname);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);

        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewEthnicity = (TextView) findViewById(R.id.textViewEthnicity);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewDishType = (TextView) findViewById(R.id.textViewDishType);

        //This method will fetch the data from the URL
        getData();
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY2);

                            //Calling method getStudents to get the students from the JSON Array
                            getCooks(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getCooks(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the cook to array list
                cooks.add(json.getString(Config.TAG_COOK_FIRSTNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        cookOrderSpinner.setAdapter(new ArrayAdapter<String>(CreateOrder.this, android.R.layout.simple_spinner_dropdown_item, cooks));
    }

    private String getCookID(int position){
        String course="";
        try {
            JSONObject json = result.getJSONObject(position);
            course = json.getString(Config.TAG_COOK_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }

    //Method to get cook name of a particular position
    private String getCookName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString(Config.TAG_COOK_FIRSTNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //Getting cook last name
    private String getCookSurname(int position){
        String course="";
        try {
            JSONObject json = result.getJSONObject(position);
            course = json.getString(Config.TAG_COOK_SURNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }

    //Getting cook address
    private String getCookAddress(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_COOK_ADDRESS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }

    //Get food details
    private String getFoodID(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    private String getFoodPrice(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_PRICE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    private String getFoodDescription(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_DES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    private String getFoodEthnicity(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_ETH);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    private String getFoodType(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    private String getFoodDishType(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString(Config.TAG_DISH);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }


    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        textViewFirstName.setText(getCookID(position));
        textViewFirstName.setText(getCookName(position));
        textViewSurname.setText(getCookSurname(position));
        textViewAddress.setText(getCookAddress(position));

        textViewPrice.setText(getFoodPrice(position));
        textViewDescription.setText(getFoodDescription(position));
        textViewEthnicity.setText(getFoodEthnicity(position));
        textViewType.setText(getFoodType(position));
        textViewDishType.setText(getFoodDishType(position));
    }


    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewFirstName.setText("");
        textViewSurname.setText("");
        textViewAddress.setText("");

        textViewPrice.setText("");
        textViewDescription.setText("");
        textViewEthnicity.setText("");
        textViewType.setText("");
        textViewDishType.setText("");
    }

    //Database format
    public String databaseDate() {
        StringBuilder dataDate = new StringBuilder();
        month = datePicker.getMonth() + 1;
        dataDate.append(datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth());
        return dataDate.toString();
    }

    public String dataTime() {
        String dataTime = ("Time: " + timePicker.getHour() + ":" + timePicker.getMinute());
        return dataTime;
    }

    //Ui format
    public String currentDate() {
        StringBuilder mcurrentDate = new StringBuilder();
        month = datePicker.getMonth() + 1;
        mcurrentDate.append("This is your selected date: " + datePicker.getDayOfMonth() + "/" + month);
        return mcurrentDate.toString();
    }

    public String currentTime() {
        String mcurrentTime = ("This is your selected Time: " + timePicker.getHour() + ":" + timePicker.getMinute());
        return mcurrentTime;
    }
}


