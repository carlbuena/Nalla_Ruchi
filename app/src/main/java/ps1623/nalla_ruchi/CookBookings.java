package ps1623.nalla_ruchi;

/**
 * Created by Carl on 15/09/16.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CookBookings extends BaseActivity implements ListView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_bookings_list);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showBooking(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String cook_id = jo.getString(Config.TAG_BOOKING_COOK_ID);
                String customer_id = jo.getString(Config.TAG_BOOKING_CUSTOMER_ID);
                String first_name = jo.getString(Config.TAG_FIRSTNAME);
                String surname = jo.getString(Config.TAG_SURNAME);
                String booking_id = jo.getString(Config.TAG_BOOKING_ID);
                String date = jo.getString(Config.TAG_BOOKINGDATE);
                String time = jo.getString(Config.TAG_BOOKINGTIME);
                String price = jo.getString(Config.TAG_BOOKINGPRICE);
                String made = jo.getString(Config.TAG_BOOKINGMADE);
                String status = jo.getString(Config.TAG_BOOKINGSTATUS);

                HashMap<String,String> booking = new HashMap<>();
                booking.put(Config.TAG_BOOKING_COOK_ID,cook_id);
                booking.put(Config.TAG_BOOKING_CUSTOMER_ID,customer_id);
                booking.put(Config.TAG_FIRSTNAME,first_name);
                booking.put(Config.TAG_SURNAME,surname);
                booking.put(Config.TAG_BOOKING_ID,booking_id);
                booking.put(Config.TAG_BOOKINGDATE,date);
                booking.put(Config.TAG_BOOKINGTIME,time);
                booking.put(Config.TAG_BOOKINGPRICE,price);
                booking.put(Config.TAG_BOOKINGMADE,made);
                booking.put(Config.TAG_BOOKINGSTATUS,status);

                list.add(booking);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                CookBookings.this, list, R.layout.cook_bookings,
                new String[]{Config.TAG_BOOKING_COOK_ID,Config.TAG_BOOKING_CUSTOMER_ID,Config.TAG_BOOKING_ID,Config.TAG_BOOKINGDATE,Config.TAG_BOOKINGTIME,Config.TAG_BOOKINGPRICE,Config.TAG_BOOKINGMADE,Config.TAG_BOOKINGSTATUS, Config.TAG_FIRSTNAME,Config.TAG_SURNAME},
                new int[]{R.id.Cook_ID,R.id.Customer_ID,R.id.Booking_ID, R.id.BookingDate, R.id.BookingTime, R.id.BookingDateMade, R.id.BookingPrice, R.id.BookingStatus, R.id.CustomerFirstName, R.id.CustomerLastName});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CookBookings.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showBooking();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_BOOKING_PROFILE);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, editCookBookings.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);

        String cook_id = map.get(Config.TAG_BOOKING_COOK_ID).toString();
        String booking_id = map.get(Config.TAG_BOOKING_ID).toString();
        String customer_id = map.get(Config.TAG_BOOKING_CUSTOMER_ID).toString();
        String firstName = map.get(Config.TAG_FIRSTNAME).toString();
        String surname = map.get(Config.TAG_SURNAME).toString();
        String date = map.get(Config.TAG_BOOKINGDATE).toString();
        String time = map.get(Config.TAG_BOOKINGTIME).toString();
        String price = map.get(Config.TAG_BOOKINGPRICE).toString();
        String made = map.get(Config.TAG_BOOKINGMADE).toString();
        String status = map.get(Config.TAG_BOOKINGSTATUS).toString();

        Bundle extras = new Bundle();

        extras.putString(Config.TAG_BOOKING_COOK_ID,cook_id);
        extras.putString(Config.TAG_BOOKING_ID,booking_id);
        extras.putString(Config.TAG_FIRSTNAME,firstName);
        extras.putString(Config.TAG_SURNAME,surname);
        extras.putString(Config.TAG_BOOKING_CUSTOMER_ID,customer_id);
        extras.putString(Config.TAG_BOOKINGDATE,date);
        extras.putString(Config.TAG_BOOKINGTIME,time);
        extras.putString(Config.TAG_BOOKINGPRICE,price);
        extras.putString(Config.TAG_BOOKINGMADE,made);
        extras.putString(Config.TAG_BOOKINGSTATUS,status);

        intent.putExtras(extras);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        //Reading the Preferences File
        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String Uname = userDetails.getString("username", "");
        String Utype = userDetails.getString("usertype", "");

        TextView user_email = (TextView) findViewById(R.id.user_email);
        user_email.setText(Uname);

        if(Utype.equalsIgnoreCase("customer")) {
            navigationView.getMenu().findItem(R.id.cook_home).setVisible(false);
            navigationView.getMenu().findItem(R.id.cook_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.view_customers).setVisible(false);
            navigationView.getMenu().findItem(R.id.add_food).setVisible(false);
            navigationView.getMenu().findItem(R.id.cook_bookings).setVisible(false);
            return true;
        }
        else {
            navigationView.getMenu().findItem(R.id.customer_home).setVisible(false);
            navigationView.getMenu().findItem(R.id.customer_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.make_booking).setVisible(false);
            navigationView.getMenu().findItem(R.id.view_bookings).setVisible(false);
            navigationView.getMenu().findItem(R.id.view_cooks).setVisible(false);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.cook_bookings: return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
