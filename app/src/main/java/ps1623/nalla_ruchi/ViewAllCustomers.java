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

public class ViewAllCustomers extends BaseActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_customers);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showCustomer(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_CUSTOMER_ID);
                String firstName = jo.getString(Config.TAG_FIRSTNAME);
                String surname = jo.getString(Config.TAG_SURNAME);
                String mobile = jo.getString(Config.TAG_MOBILE);
                String email = jo.getString(Config.TAG_EMAIL);
                String address = jo.getString(Config.TAG_ADDRESS);
                String suburb = jo.getString(Config.TAG_SUBURB);
                String state = jo.getString(Config.TAG_STATE);
                String dor = jo.getString(Config.TAG_DOR);

                HashMap<String,String> customer = new HashMap<>();
                customer.put(Config.TAG_CUSTOMER_ID,id);
                customer.put(Config.TAG_FIRSTNAME,firstName);
                customer.put(Config.TAG_SURNAME,surname);
                customer.put(Config.TAG_MOBILE,mobile);
                customer.put(Config.TAG_EMAIL,email);
                customer.put(Config.TAG_ADDRESS,address);
                customer.put(Config.TAG_SUBURB,suburb);
                customer.put(Config.TAG_STATE,state);
                customer.put(Config.TAG_DOR,dor);
                list.add(customer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewAllCustomers.this, list, R.layout.list_customer,
                new String[]{Config.TAG_CUSTOMER_ID,Config.TAG_FIRSTNAME,Config.TAG_SURNAME,Config.TAG_MOBILE,Config.TAG_EMAIL,Config.TAG_ADDRESS,Config.TAG_SUBURB,Config.TAG_STATE,Config.TAG_DOR},
                new int[]{R.id.Customer_ID, R.id.FirstName, R.id.Surname, R.id.Mobile, R.id.Email, R.id.Address, R.id.Suburb, R.id.State, R.id.DateOfRegistration});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAllCustomers.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showCustomer();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_CUSTOMERS);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewCustomer.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);

        //Reading the Preferences File
        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String Uname = userDetails.getString("username", "");
        String Utype = userDetails.getString("usertype", "");

        String customer_id = map.get(Config.TAG_CUSTOMER_ID).toString();
        String firstName = map.get(Config.TAG_FIRSTNAME).toString();
        String surname = map.get(Config.TAG_SURNAME).toString();
        String mobile = map.get(Config.TAG_MOBILE).toString();
        String email = map.get(Config.TAG_EMAIL).toString();
        String address = map.get(Config.TAG_ADDRESS).toString();
        String suburb = map.get(Config.TAG_SUBURB).toString();
        String state = map.get(Config.TAG_STATE).toString();
        String dor =  map.get(Config.TAG_DOR).toString();

        Bundle extras = new Bundle();

        extras.putString(Config.TAG_CUSTOMER_ID,customer_id);
        extras.putString(Config.TAG_FIRSTNAME,firstName);
        extras.putString(Config.TAG_SURNAME,surname);
        extras.putString(Config.TAG_MOBILE,mobile);
        extras.putString(Config.TAG_EMAIL,email);
        extras.putString(Config.TAG_ADDRESS,address);
        extras.putString(Config.TAG_SUBURB,suburb);
        extras.putString(Config.TAG_STATE,state);
        extras.putString(Config.TAG_DOR,dor);

        intent.putExtras(extras);

        if(Utype.equalsIgnoreCase("customer")) {
            startActivity(intent);
        }
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
            case R.id.view_customers: return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
