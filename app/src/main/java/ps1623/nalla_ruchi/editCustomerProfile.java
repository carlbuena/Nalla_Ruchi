package ps1623.nalla_ruchi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Carl on 15/09/16.
 */

public class editCustomerProfile extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCustomer_ID;
    private EditText editTextCustomer_First_Name;
    private EditText editTextCustomer_Surname;
    private EditText editTextCustomer_Mobile;
    private EditText editTextCustomer_Email;
    private EditText editTextCustomer_Address;
    private EditText editTextCustomer_Suburb;
    private EditText editTextCustomer_State;
    private EditText editTextCustomer_DOR;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String customerid;
    private String customerFirstName;
    private  String customerSurname;
    private String customerMobile;
    private  String customerEmail;
    private String customerAddress;
    private  String customerSuburb;
    private  String customerState;
    private  String customerDOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_customer_profile);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        customerid = extras.getString(Config.CUSTOMER_ID);
        customerFirstName = extras.getString(Config.CUSTOMER_FIRSTNAME);
        customerSurname = extras.getString(Config.CUSTOMER_SURNAME);
        customerMobile = extras.getString(Config.CUSTOMER_MOBILE);
        customerEmail = extras.getString(Config.CUSTOMER_EMAIL);
        customerAddress = extras.getString(Config.CUSTOMER_ADDRESS);
        customerSuburb = extras.getString(Config.CUSTOMER_SUBURB);
        customerState = extras.getString(Config.CUSTOMER_STATE);
        customerDOR = extras.getString(Config.CUSTOMER_DOR);

        editTextCustomer_ID = (EditText) findViewById(R.id.editTextCustomer_ID);
        editTextCustomer_First_Name = (EditText) findViewById(R.id.editTextCustomer_First_Name);
        editTextCustomer_Surname = (EditText) findViewById(R.id.editTextCustomer_Surname);
        editTextCustomer_Mobile = (EditText) findViewById(R.id.editTextCustomer_Mobile);
        editTextCustomer_Email = (EditText) findViewById(R.id.editTextCustomer_Email);
        editTextCustomer_Address = (EditText) findViewById(R.id.editTextCustomer_Address);
        editTextCustomer_Suburb = (EditText) findViewById(R.id.editTextCustomer_Suburb);
        editTextCustomer_State = (EditText) findViewById(R.id.editTextCustomer_State);
        editTextCustomer_DOR = (EditText) findViewById(R.id.editTextCustomer_DOR);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextCustomer_ID.setText(customerid);
        editTextCustomer_First_Name.setText(customerFirstName);
        editTextCustomer_Surname.setText(customerSurname);
        editTextCustomer_Mobile.setText(customerMobile);
        editTextCustomer_Email.setText(customerEmail);
        editTextCustomer_Address.setText(customerAddress);
        editTextCustomer_Suburb.setText(customerSuburb);
        editTextCustomer_State.setText(customerState);
        editTextCustomer_DOR.setText(customerDOR);

        editTextCustomer_DOR.setKeyListener(null);

        getCustomer();
    }

    private void getCustomer(){
        class GetCustomer extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(editCustomerProfile.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showCustomer(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_CUSTOMER_PROFILE,customerid);
                return s;
            }
        }
        GetCustomer ge = new GetCustomer();
        ge.execute();
    }

    private void showCustomer(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String customerid = c.getString(Config.TAG_CUSTOMER_ID);
            String customerFirstName = c.getString(Config.TAG_FIRSTNAME);
            String customerSurname = c.getString(Config.TAG_SURNAME);
            String customerMobile = c.getString(Config.TAG_MOBILE);
            String customerEmail = c.getString(Config.TAG_EMAIL);
            String customerAddress = c.getString(Config.TAG_ADDRESS);
            String customerSuburb = c.getString(Config.TAG_SUBURB);
            String customerState = c.getString(Config.TAG_STATE);
            String customerDOR = c.getString(Config.TAG_DOR);

            editTextCustomer_ID.setText(customerid);
            editTextCustomer_First_Name.setText(customerFirstName);
            editTextCustomer_Surname.setText(customerSurname);
            editTextCustomer_Mobile.setText(customerMobile);
            editTextCustomer_Email.setText(customerEmail);
            editTextCustomer_Address.setText(customerAddress);
            editTextCustomer_Suburb.setText(customerSuburb);
            editTextCustomer_State.setText(customerState);
            editTextCustomer_DOR.setText(customerDOR);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateCustomer(){
        final String Customer_ID = editTextCustomer_ID.getText().toString().trim();
        final String Customer_First_Name = editTextCustomer_First_Name.getText().toString().trim();
        final String Customer_Surname = editTextCustomer_Surname.getText().toString().trim();
        final String Customer_Mobile = editTextCustomer_Mobile.getText().toString().trim();
        final String Customer_Email = editTextCustomer_Email.getText().toString().trim();
        final String Customer_Address = editTextCustomer_Address.getText().toString().trim();
        final String Customer_Suburb = editTextCustomer_Suburb.getText().toString().trim();
        final String Customer_State = editTextCustomer_State.getText().toString().trim();
        final String Customer_DOR = editTextCustomer_DOR.getText().toString().trim();

        class UpdateCustomer extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(editCustomerProfile.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(editCustomerProfile.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_CUSTOMER_ID,Customer_ID);
                hashMap.put(Config.KEY_CUSTOMER_FIRSTNAME,Customer_First_Name);
                hashMap.put(Config.KEY_CUSTOMER_SURNAME,Customer_Surname);
                hashMap.put(Config.KEY_CUSTOMER_MOBILE,Customer_Mobile);
                hashMap.put(Config.KEY_CUSTOMER_EMAIL,Customer_Email);
                hashMap.put(Config.KEY_CUSTOMER_ADDRESS,Customer_Address);
                hashMap.put(Config.KEY_CUSTOMER_SUBURB,Customer_Suburb);
                hashMap.put(Config.KEY_CUSTOMER_STATE,Customer_State);
                hashMap.put(Config.KEY_CUSTOMER_DOR,Customer_DOR);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_CUSTOMER,hashMap);

                return s;
            }
        }

        UpdateCustomer uc = new UpdateCustomer();
        uc.execute();
    }

    private void deleteCustomer(){
        class DeleteCustomer extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(editCustomerProfile.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(editCustomerProfile.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_CUSTOMER, customerid);
                return s;
            }
        }

        DeleteCustomer dc = new DeleteCustomer();
        dc.execute();
    }

    private void confirmDeleteCustomer(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this customer?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteCustomer();
                        startActivity(new Intent(editCustomerProfile.this,ViewAllCustomers.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateCustomer();
            startActivity(new Intent(editCustomerProfile.this,CustomerProfile.class));
        }

        if(v == buttonDelete){
            confirmDeleteCustomer();
        }
    }
}
