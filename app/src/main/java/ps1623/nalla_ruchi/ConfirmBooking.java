package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ConfirmBooking extends BaseActivity implements View.OnClickListener {

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
        date = extras.getString(Config.CLASS_DATE);
        time = extras.getString(Config.CLASS_TIME);
        price = extras.getString(Config.CLASS_PRICE);

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

        final String Cook_Name = editTextCook_Name.getText().toString().trim();
        final String Address = editTextAddress.getText().toString().trim();
        final String Suburb = editTextSuburb.getText().toString().trim();
        final String Date = editTextDate.getText().toString().trim();
        final String Time = editTextTime.getText().toString().trim();
        final String Price = editTextPrice.getText().toString().trim();
        final String Status = "Pending";

        class AddBooking extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ConfirmBooking.this,"Adding...","Wait...",false,false);
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
                hashMap.put(Config.KEY_BOOKING_DATE,Date);
                hashMap.put(Config.KEY_BOOKING_TIME,Time);
                hashMap.put(Config.KEY_BOOKING_PRICE,Price);
                hashMap.put(Config.KEY_BOOKING_PRICE,Price);
                hashMap.put(Config.KEY_BOOKING_STATUS,Status);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_ADD_BOOKING,hashMap);

                return s;
            }
        }

        AddBooking ab = new AddBooking();
        ab.execute();
    }

    public void alertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmBooking.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Booking...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want book this class?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Cash on Hand", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                addBooking();
                startActivity(new Intent(ConfirmBooking.this, cookGalleryMain.class));
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNeutralButton("PayPal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Cancel booking
                startActivity(new Intent(ConfirmBooking.this, PayPal.class));
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Cancel booking
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonConfirm){
            alertDialog();
        }

        if(v == buttonCancel){
            startActivity(new Intent(ConfirmBooking.this,Booking.class));
        }
    }

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

        return super.onOptionsItemSelected(item);
    }
}