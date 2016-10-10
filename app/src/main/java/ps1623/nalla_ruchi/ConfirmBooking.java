package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ConfirmBooking extends AppCompatActivity implements View.OnClickListener {

    private TextView editTextCook_Name;
    private TextView editTextAddress;
    private TextView editTextSuburb;
    private TextView editTextDate;
    private TextView editTextTime;
    private TextView editTextPrice;

    private Button buttonConfirm;
    private Button buttonCancel;

    private String cookname;
    private String address;
    private String suburb;
    private String date;
    private String time;
    private String price;
    private String cookid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        cookid = extras.getString(Config.CLASS_COOK_ID);
        cookname = extras.getString(Config.CLASS_COOK_NAME);
        address = extras.getString(Config.COOK_ADDRESS);
        suburb = extras.getString(Config.COOK_SUBURB);
        date = extras.getString(Config.BOOKING_DATE);
        time = extras.getString(Config.BOOKING_TIME);
        price = extras.getString(Config.BOOKING_PRICE);

        editTextCook_Name = (TextView) findViewById(R.id.editTextCook_Name);
        editTextAddress = (TextView) findViewById(R.id.editTextCook_Address);
        editTextSuburb = (TextView) findViewById(R.id.editTextsuburb);
        editTextDate = (TextView) findViewById(R.id.editTextDate);
        editTextTime = (TextView) findViewById(R.id.editTextTime);
        editTextPrice = (TextView) findViewById(R.id.editTextPrice);

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        buttonConfirm.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        editTextCook_Name.setText(cookname);
        editTextAddress.setText(address);
        editTextSuburb.setText(suburb);
        editTextDate.setText(date);
        editTextTime.setText(time);
        editTextPrice.setText(price);

        getBooking();
    }



    private void getBooking(){
        class GetBooking extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ConfirmBooking.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showBooking(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_BOOKING, cookname);
                return s;
            }
        }
        GetBooking gb = new GetBooking();
        gb.execute();
    }

    private void showBooking(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String classid = c.getString(Config.TAG_CLASS_ID);
            String cookid = c.getString(Config.TAG_CLASS_COOK_ID);
            String date = c.getString(Config.TAG_CLASS_DATE);
            String time = c.getString(Config.TAG_CLASS_TIME);
            String price = c.getString(Config.TAG_CLASS_PRICE);
            String max = c.getString(Config.TAG_CLASS_MAX);
            String total = c.getString(Config.TAG_CLASS_TOTAL);
            String cookname = c.getString(Config.TAG_CLASS_COOK_NAME);
            String address = c.getString(Config.TAG_COOK_ADDRESS);
            String suburb = c.getString(Config.TAG_COOK_SUBURB);

            editTextCook_Name.setText(cookname);
            editTextAddress.setText(address);
            editTextSuburb.setText(suburb);
            editTextDate.setText(date);
            editTextTime.setText(time);
            editTextPrice.setText(price);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void addBooking(){
        /*
        final String Cook_Name = editTextCook_Name.getText().toString().trim();
        final String Address = editTextAddress.getText().toString().trim();
        final String Suburb = editTextSuburb.getText().toString().trim();
        final String Date = editTextDate.getText().toString().trim();
        final String Time = editTextTime.getText().toString().trim();
        final String Price = editTextPrice.getText().toString().trim();*/

        class AddBooking extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ConfirmBooking.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ConfirmBooking.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                //hashMap.put(Config.KEY_BOOKING_CUSTOMER_ID,Customer_ID);
                hashMap.put(Config.KEY_BOOKING_COOK_ID,cookid);
                hashMap.put(Config.KEY_BOOKING_DATE,date);
                hashMap.put(Config.KEY_BOOKING_TIME,time);
                hashMap.put(Config.KEY_BOOKING_PRICE,price);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_ADD_BOOKING,hashMap);

                return s;
            }
        }

        AddBooking ab = new AddBooking();
        ab.execute();
    }
/*
    private void deleteFood(){
        class DeleteFood extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ConfirmBooking.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ConfirmBooking.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                //String s = rh.sendGetRequestParam(Config.URL_DELETE_FOOD, foodid);
                //return s;
            }
        }

        DeleteFood de = new DeleteFood();
        de.execute();
    }

    private void confirmDeleteFood(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this food?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteFood();
                        startActivity(new Intent(ConfirmBooking.this,ViewAllFood.class));
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
    }*/

    @Override
    public void onClick(View v) {
        if(v == buttonConfirm){
            addBooking();
            startActivity(new Intent(ConfirmBooking.this, CustomerHome.class));
        }

        if(v == buttonCancel){
            startActivity(new Intent(ConfirmBooking.this,Booking.class));
        }
    }
}