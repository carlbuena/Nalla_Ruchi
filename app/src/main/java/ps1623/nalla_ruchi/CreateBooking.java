package ps1623.nalla_ruchi;

import android.os.Build;
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
import android.widget.Toast;

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
public class CreateBooking extends AppCompatActivity implements Spinner.OnItemSelectedListener/*, View.OnClickListener*/ {

    //Declaring an Spinner
    private Spinner cookOrderSpinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> cooks;

    //JSON Array
    private JSONArray result;

    //TextViews to display details
    private TextView textViewFirstName;
    private TextView textViewSurname;
    private TextView textViewAddress;

    private Button buttonBook;

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

        //Initializing Spinner
        cookOrderSpinner = (Spinner) findViewById(R.id.cookOrderSpinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class itself we are passing this to setOnItemSelectedListener
        cookOrderSpinner.setOnItemSelectedListener(this);

        //Initializing TextViews
        textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
        textViewSurname = (TextView) findViewById(R.id.textViewSurname);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);

        //This method will fetch the data from the URL
        getData();

        //buttonBook.setOnClickListener(this);
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
        cookOrderSpinner.setAdapter(new ArrayAdapter<String>(CreateBooking.this, android.R.layout.simple_spinner_dropdown_item, cooks));
    }

    private String getCookID(int position){
        String cook_id="";
        try {
            JSONObject json = result.getJSONObject(position);
            cook_id = json.getString(Config.TAG_COOK_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cook_id;
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
        String surname="";
        try {
            JSONObject json = result.getJSONObject(position);
            surname = json.getString(Config.TAG_COOK_SURNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return surname;
    }

    //Getting cook address
    private String getCookAddress(int position){
        String address="";
        try {
            JSONObject json = result.getJSONObject(position);
            address = json.getString(Config.TAG_COOK_ADDRESS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return address;
    }

    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        textViewFirstName.setText(getCookName(position));
        textViewSurname.setText(getCookSurname(position));
        textViewAddress.setText(getCookAddress(position));

        String name = (String) textViewFirstName.getText();
        String surname = (String) textViewSurname.getText();
        String address = (String) textViewAddress.getText();

        String sp1= String.valueOf(cookOrderSpinner.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
            cooks.add(name);
            cooks.add(surname);
            cooks.add(address);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cooks);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();

    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewFirstName.setText("");
        textViewSurname.setText("");
        textViewAddress.setText("");
    }

    /*private void makeBooking(){

        final String Cook_Name = editTextFood_Name.getText().toString().trim();
        final String Booking_Time = editTextPrice.getText().toString().trim();
        final String Booking_Date = editTextDescription.getText().toString().trim();
        final String Customer_ID = editTextEthnicity.getText().toString().trim();

        class AddFood extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InsertFood.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(InsertFood.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_FOOD_NAME,Food_Name);
                params.put(Config.KEY_FOOD_PRICE,Price);
                params.put(Config.KEY_FOOD_DES,Description);
                params.put(Config.KEY_FOOD_ETH,Ethnicity);
                params.put(Config.KEY_FOOD_TYPE,Type);
                params.put(Config.KEY_FOOD_DISH,Dish);
                params.put(Config.KEY_FOOD_MENU_ID,Menu_ID);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_FOOD, params);
                return res;
            }
        }

        AddFood af = new AddFood();
        af.execute();
    }

    @Override
    public void setOnTimeChangedListener(TimePicker.OnTimeChangedListener onTimeChangedListener)
    {

    }*/

    //Database format
    public String databaseDate() {
        StringBuilder dataDate = new StringBuilder();
        month = datePicker.getMonth() + 1;
        dataDate.append(datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth());
        return dataDate.toString();
    }

    public String dataTime() {
        if(Build.VERSION.SDK_INT<=22)
        {
            String dataTime = ("Time: " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
            return dataTime;
        }
        else
        {
            String dataTime = ("Time: " + timePicker.getHour() + ":" + timePicker.getMinute());
            return dataTime;
        }
    }

    //Ui format
    public String currentDate() {
        StringBuilder mcurrentDate = new StringBuilder();
        month = datePicker.getMonth() + 1;
        mcurrentDate.append("This is your selected date: " + datePicker.getDayOfMonth() + "/" + month);
        return mcurrentDate.toString();
    }

    public String currentTime() {
        if(Build.VERSION.SDK_INT<=22) {
            String mcurrentTime = ("This is your selected Time: " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
            return mcurrentTime;
        }
        else
        {
            String mcurrentTime = ("This is your selected Time: " + timePicker.getHour() + ":" + timePicker.getMinute());
            return mcurrentTime;
        }
    }

    /*@Override
    public void onClick(View v) {
        if(v == buttonBook){
            makeBooking();
            startActivity(new Intent(this,CustomerHome.class));
        }
    }*/
}


