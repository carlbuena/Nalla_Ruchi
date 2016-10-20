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

import static ps1623.nalla_ruchi.R.id.buttonDelete;

/**
 * Created by Carl on 15/09/16.
 */

public class editCookBookings extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextBooking_ID;
    private EditText editTextCook_ID;
    private EditText editTextCustomer_ID;
    private EditText editTextFirst_Name;
    private EditText editTextSurname;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextMade;
    private EditText editTextPrice;
    private EditText editTextStatus;

    private Button buttonUpdate;
    private Button buttonComplete;

    private String cookid;
    private String bookingid;
    private String customerid;
    private String firstname;
    private String surname;
    private String date;
    private String time;
    private String made;
    private String price;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_cook_bookings);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        cookid = extras.getString(Config.COOK_ID);
        bookingid = extras.getString(Config.COOK_ROLE);
        customerid = extras.getString(Config.COOK_FIRSTNAME);
        firstname = extras.getString(Config.COOK_SURNAME);
        surname = extras.getString(Config.COOK_MOBILE);
        date = extras.getString(Config.COOK_EMAIL);
        time = extras.getString(Config.COOK_ADDRESS);
        made = extras.getString(Config.COOK_SUBURB);
        price = extras.getString(Config.COOK_STATE);
        status = extras.getString(Config.COOK_DOR);

        editTextCook_ID = (EditText) findViewById(R.id.editTextCook_ID);
        editTextBooking_ID = (EditText) findViewById(R.id.editTextBooking_ID);
        editTextCustomer_ID = (EditText) findViewById(R.id.editTextCustomer_ID);
        editTextFirst_Name = (EditText) findViewById(R.id.editTextCustomer_First_Name);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextDate = (EditText) findViewById(R.id.editTextBooking_Date);
        editTextTime = (EditText) findViewById(R.id.editTextBooking_Time);
        editTextMade = (EditText) findViewById(R.id.editTextBooking_Made);
        editTextPrice = (EditText) findViewById(R.id.editTextBooking_Price);
        editTextStatus = (EditText) findViewById(R.id.editTextBooking_Status);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonComplete = (Button) findViewById(buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonComplete.setOnClickListener(this);

        editTextCook_ID.setText(cookid);
        editTextBooking_ID.setText(bookingid);
        editTextCustomer_ID.setText(customerid);
        editTextFirst_Name.setText(firstname);
        editTextSurname.setText(surname);
        editTextDate.setText(date);
        editTextTime.setText(time);
        editTextPrice.setText(made);
        editTextMade.setText(price);
        editTextStatus.setText(status);

        getBooking();
    }

    private void getBooking(){
        class GetBooking extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(editCookBookings.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showCook(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_BOOKING_PROFILE,bookingid);
                return s;
            }
        }
        GetBooking ge = new GetBooking();
        ge.execute();
    }

    private void showCook(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String cookid = c.getString(Config.TAG_BOOKING_COOK_ID);
            String bookingid = c.getString(Config.TAG_BOOKING_ID);
            String customerid = c.getString(Config.TAG_BOOKING_CUSTOMER_ID);
            String firstname = c.getString(Config.TAG_BOOKING_CUSTOMER_FIRSTNAME);
            String surname = c.getString(Config.TAG_BOOKING_CUSTOMER_FIRSTNAME);
            String date = c.getString(Config.TAG_BOOKINGDATE);
            String time = c.getString(Config.TAG_BOOKINGTIME);
            String made = c.getString(Config.TAG_BOOKINGMADE);
            String price = c.getString(Config.TAG_BOOKINGPRICE);
            String status = c.getString(Config.TAG_BOOKINGSTATUS);

            editTextCook_ID.setText(cookid);
            editTextBooking_ID.setText(bookingid);
            editTextCustomer_ID.setText(customerid);
            editTextFirst_Name.setText(firstname);
            editTextSurname.setText(surname);
            editTextDate.setText(date);
            editTextTime.setText(time);
            editTextMade.setText(made);
            editTextPrice.setText(price);
            editTextStatus.setText(status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateCook(){
        final String Cook_ID = editTextCook_ID.getText().toString().trim();
        final String Booking_ID = editTextBooking_ID.getText().toString().trim();
        final String Customer_ID = editTextCustomer_ID.getText().toString().trim();
        final String First_Name = editTextFirst_Name.getText().toString().trim();
        final String Surname = editTextSurname.getText().toString().trim();
        final String Date = editTextDate.getText().toString().trim();
        final String Time = editTextTime.getText().toString().trim();
        final String Made = editTextMade.getText().toString().trim();
        final String Price = editTextPrice.getText().toString().trim();
        final String Status = editTextStatus.getText().toString().trim();

        class UpdateCook extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(editCookBookings.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(editCookBookings.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_BOOKING_COOK_ID,Cook_ID);
                hashMap.put(Config.KEY_BOOKING_ID,Booking_ID);
                hashMap.put(Config.KEY_BOOKING_CUSTOMER_ID,Customer_ID);
                hashMap.put(Config.KEY_BOOKING_CUSTOMER_FIRSTNAME,First_Name);
                hashMap.put(Config.KEY_BOOKING_CUSTOMER_SURNAME,Surname);
                hashMap.put(Config.KEY_BOOKING_DATE,Date);
                hashMap.put(Config.KEY_BOOKING_TIME,Time);
                hashMap.put(Config.KEY_BOOKING_MADE,Made);
                hashMap.put(Config.KEY_BOOKING_PRICE,Price);
                hashMap.put(Config.KEY_BOOKING_STATUS,Status);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_COOK,hashMap);

                return s;
            }
        }

        UpdateCook ue = new UpdateCook();
        ue.execute();
    }

    private void complete(){
        class DeleteCook extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(editCookBookings.this, "Completing...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(editCookBookings.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_COMPLETE_BOOKING, bookingid);
                return s;
            }
        }

        DeleteCook de = new DeleteCook();
        de.execute();
    }

    private void confirmComplete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to mark this booking as complete?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        complete();
                        startActivity(new Intent(editCookBookings.this,CookBookings.class));
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
            updateCook();
            startActivity(new Intent(editCookBookings.this,CookBookings.class));
        }

        if(v == buttonComplete){
            confirmComplete();
        }
    }
}
