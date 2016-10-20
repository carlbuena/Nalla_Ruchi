package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
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

public class Booking extends BaseActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_list);
        listView = (ListView) findViewById(R.id.listViewBooking);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showTimes(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String classid = jo.getString(Config.TAG_CLASS_ID);
                String cookid = jo.getString(Config.TAG_CLASS_COOK_ID);
                String cookname = jo.getString(Config.TAG_CLASS_COOK_NAME);
                String address = jo.getString(Config.TAG_COOK_ADDRESS);
                String suburb = jo.getString(Config.TAG_COOK_SUBURB);
                String date = jo.getString(Config.TAG_CLASS_DATE);
                String time = jo.getString(Config.TAG_CLASS_TIME);
                String price = jo.getString(Config.TAG_CLASS_PRICE);
                String max = jo.getString(Config.TAG_CLASS_MAX);
                String total = jo.getString(Config.TAG_CLASS_TOTAL);

                HashMap<String,String> booking = new HashMap<>();
                booking.put(Config.TAG_CLASS_ID, classid);
                booking.put(Config.TAG_CLASS_COOK_ID,cookid);
                booking.put(Config.TAG_CLASS_COOK_NAME,cookname);
                booking.put(Config.TAG_COOK_ADDRESS,address);
                booking.put(Config.TAG_COOK_SUBURB, suburb);
                booking.put(Config.TAG_CLASS_DATE,date);
                booking.put(Config.TAG_CLASS_TIME,time);
                booking.put(Config.TAG_CLASS_PRICE,price);
                booking.put(Config.TAG_CLASS_MAX,max);
                booking.put(Config.TAG_CLASS_TOTAL,total);
                list.add(booking);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Booking.this, list, R.layout.booking_item,
                new String[]{Config.TAG_CLASS_ID, Config.TAG_CLASS_COOK_ID, Config.TAG_CLASS_COOK_NAME, Config.TAG_COOK_ADDRESS, Config.TAG_COOK_SUBURB, Config.TAG_CLASS_DATE, Config.TAG_CLASS_TIME, Config.TAG_CLASS_PRICE, Config.TAG_CLASS_MAX, Config.TAG_CLASS_TOTAL},
                new int[]{R.id.Class_ID, R.id.Cook_ID, R.id.cookName, R.id.Address, R.id.Suburb, R.id.Date, R.id.Time, R.id.Price, R.id.Max, R.id.Total});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Booking.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showTimes();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_CLASSES);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ConfirmBooking.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);

        String classid = map.get(Config.TAG_CLASS_ID).toString();
        String cookid = map.get(Config.TAG_CLASS_COOK_ID).toString();
        String cookname = map.get(Config.TAG_CLASS_COOK_NAME).toString();
        String classaddress = map.get(Config.TAG_COOK_ADDRESS).toString();
        String classsuburb = map.get(Config.TAG_COOK_SUBURB).toString();
        String classdate = map.get(Config.TAG_CLASS_DATE).toString();
        String classtime = map.get(Config.TAG_CLASS_TIME).toString();
        String classprice = map.get(Config.TAG_CLASS_PRICE).toString();
        String max = map.get(Config.TAG_CLASS_MAX).toString();
        String total = map.get(Config.TAG_CLASS_TOTAL).toString();

        Bundle extras = new Bundle();

        extras.putString(Config.CLASS_ID,classid);
        extras.putString(Config.CLASS_COOK_ID,cookid);
        extras.putString(Config.CLASS_COOK_NAME,cookname);
        extras.putString(Config.COOK_ADDRESS,classaddress);
        extras.putString(Config.COOK_SUBURB,classsuburb);
        extras.putString(Config.CLASS_DATE,classdate);
        extras.putString(Config.CLASS_TIME,classtime);
        extras.putString(Config.CLASS_PRICE,classprice);
        extras.putString(Config.CLASS_MAX,max);
        extras.putString(Config.CLASS_TOTAL,total);

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
            case R.id.make_booking: return true;
        }

        return super.onOptionsItemSelected(item);
    }
}