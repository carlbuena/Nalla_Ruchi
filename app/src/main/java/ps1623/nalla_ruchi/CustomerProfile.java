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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerProfile extends BaseActivity implements View.OnClickListener {

    private ListView listView;

    private String JSON_STRING;

    private Button buttonEdit;

    private TextView editTextCustomer_ID;
    private TextView editTextCustomer_First_Name;
    private TextView editTextCustomer_Surname;
    private TextView editTextCustomer_Mobile;
    private TextView editTextCustomer_Email;
    private TextView editTextCustomer_Address;
    private TextView editTextCustomer_Suburb;
    private TextView editTextCustomer_State;
    private TextView editTextCustomer_DOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile_list);

        editTextCustomer_ID = (TextView) findViewById(R.id.Customer_ID);
        editTextCustomer_First_Name = (TextView) findViewById(R.id.FirstName);
        editTextCustomer_Surname = (TextView) findViewById(R.id.Surname);
        editTextCustomer_Mobile = (TextView) findViewById(R.id.Mobile);
        editTextCustomer_Email = (TextView) findViewById(R.id.Email);
        editTextCustomer_Address = (TextView) findViewById(R.id.Address);
        editTextCustomer_Suburb = (TextView) findViewById(R.id.Suburb);
        editTextCustomer_State = (TextView) findViewById(R.id.State);
        editTextCustomer_DOR = (TextView) findViewById(R.id.DateOfRegistration);

        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(this);

        getJSON();
    }

    private void showCustomer() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
            String Uname = userDetails.getString("username", "");
            String Utype = userDetails.getString("usertype", "");

            for (int i = 0; i < result.length(); i++) {
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

                if (email.equalsIgnoreCase(Uname)) {
                    editTextCustomer_ID.setText(id);
                    editTextCustomer_First_Name.setText(firstName);
                    editTextCustomer_Surname.setText(surname);
                    editTextCustomer_Mobile.setText(mobile);
                    editTextCustomer_Email.setText(email);
                    editTextCustomer_Address.setText(address);
                    editTextCustomer_Suburb.setText(suburb);
                    editTextCustomer_State.setText(state);
                    editTextCustomer_DOR.setText(dor);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CustomerProfile.this,"Fetching Data","Wait...",false,false);
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
    public void onClick(View v){
        if(v == buttonEdit) {
            Intent intent = new Intent(CustomerProfile.this, editCookProfile.class);

            String customer_id = editTextCustomer_ID.getText().toString();
            String firstName = editTextCustomer_First_Name.getText().toString();
            String surname = editTextCustomer_Surname.getText().toString();
            String mobile = editTextCustomer_Mobile.getText().toString();
            String email = editTextCustomer_Email.getText().toString();
            String address = editTextCustomer_Address.getText().toString();
            String suburb = editTextCustomer_Suburb.getText().toString();
            String state = editTextCustomer_State.getText().toString();
            String dor = editTextCustomer_DOR.getText().toString();

            Bundle extras = new Bundle();

            extras.putString(Config.TAG_CUSTOMER_ID, customer_id);
            extras.putString(Config.TAG_FIRSTNAME, firstName);
            extras.putString(Config.TAG_SURNAME, surname);
            extras.putString(Config.TAG_MOBILE, mobile);
            extras.putString(Config.TAG_EMAIL, email);
            extras.putString(Config.TAG_ADDRESS, address);
            extras.putString(Config.TAG_SUBURB, suburb);
            extras.putString(Config.TAG_STATE, state);
            extras.putString(Config.TAG_DOR, dor);

            intent.putExtras(extras);

            SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
            String Uname = userDetails.getString("username", "");
            String Utype = userDetails.getString("usertype", "");

            if(Uname.equalsIgnoreCase(email) && Utype.equalsIgnoreCase("customer")) {
                startActivity(intent);
            }
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
            case R.id.customer_profile: return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
