package ps1623.nalla_ruchi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Carl on 15/09/16.
 */

public class ViewCook extends BaseActivity implements View.OnClickListener {

    private EditText editTextCook_ID;
    private EditText editTextRole;
    private EditText editTextCook_First_Name;
    private EditText editTextCook_Surname;
    private EditText editTextCook_Mobile;
    private EditText editTextCook_Email;
    private EditText editTextCook_Address;
    private EditText editTextCook_Suburb;
    private EditText editTextCook_State;
    private EditText editTextCook_DOR;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String cookid;
    private String role;
    private String cookFirstName;
    private String cookSurname;
    private String cookMobile;
    private String cookEmail;
    private String cookAddress;
    private String cookSuburb;
    private String cookState;
    private String cookDOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cook);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        cookid = extras.getString(Config.COOK_ID);
        role = extras.getString(Config.COOK_ROLE);
        cookFirstName = extras.getString(Config.COOK_FIRSTNAME);
        cookSurname = extras.getString(Config.COOK_SURNAME);
        cookMobile = extras.getString(Config.COOK_MOBILE);
        cookEmail = extras.getString(Config.COOK_EMAIL);
        cookAddress = extras.getString(Config.COOK_ADDRESS);
        cookSuburb = extras.getString(Config.COOK_SUBURB);
        cookState = extras.getString(Config.COOK_STATE);
        cookDOR = extras.getString(Config.COOK_DOR);

        editTextCook_ID = (EditText) findViewById(R.id.editTextCook_ID);
        editTextRole = (EditText) findViewById(R.id.editTextRole);
        editTextCook_First_Name = (EditText) findViewById(R.id.editTextCook_First_Name);
        editTextCook_Surname = (EditText) findViewById(R.id.editTextCook_Surname);
        editTextCook_Mobile = (EditText) findViewById(R.id.editTextCook_Mobile);
        editTextCook_Email = (EditText) findViewById(R.id.editTextCook_Email);
        editTextCook_Address = (EditText) findViewById(R.id.editTextCook_Address);
        editTextCook_Suburb = (EditText) findViewById(R.id.editTextCook_Suburb);
        editTextCook_State = (EditText) findViewById(R.id.editTextCook_State);
        editTextCook_DOR = (EditText) findViewById(R.id.editTextCook_DOR);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextCook_ID.setText(cookid);
        editTextRole.setText(role);
        editTextCook_First_Name.setText(cookFirstName);
        editTextCook_Surname.setText(cookSurname);
        editTextCook_Mobile.setText(cookMobile);
        editTextCook_Email.setText(cookEmail);
        editTextCook_Address.setText(cookAddress);
        editTextCook_Suburb.setText(cookSuburb);
        editTextCook_State.setText(cookState);
        editTextCook_DOR.setText(cookDOR);

        editTextCook_DOR.setKeyListener(null);

        getCook();
    }

    private void getCook(){
        class GetCook extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewCook.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(Config.URL_GET_COOK,cookid);
                return s;
            }
        }
        GetCook ge = new GetCook();
        ge.execute();
    }

    private void showCook(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String cookid = c.getString(Config.TAG_COOK_ID);
            String role = c.getString(Config.TAG_COOK_ROLE);
            String cookFirstName = c.getString(Config.TAG_COOK_FIRSTNAME);
            String cookSurname = c.getString(Config.TAG_COOK_SURNAME);
            String cookMobile = c.getString(Config.TAG_COOK_MOBILE);
            String cookEmail = c.getString(Config.TAG_COOK_EMAIL);
            String cookAddress = c.getString(Config.TAG_COOK_ADDRESS);
            String cookSuburb = c.getString(Config.TAG_COOK_SUBURB);
            String cookState = c.getString(Config.TAG_COOK_STATE);
            String cookDOR = c.getString(Config.TAG_COOK_DOR);

            editTextCook_ID.setText(cookid);
            editTextRole.setText(role);
            editTextCook_First_Name.setText(cookFirstName);
            editTextCook_Surname.setText(cookSurname);
            editTextCook_Mobile.setText(cookMobile);
            editTextCook_Email.setText(cookEmail);
            editTextCook_Address.setText(cookAddress);
            editTextCook_Suburb.setText(cookSuburb);
            editTextCook_State.setText(cookState);
            editTextCook_DOR.setText(cookDOR);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateCook(){
        final String Cook_ID = editTextCook_ID.getText().toString().trim();
        final String Role = editTextRole.getText().toString().trim();
        final String Cook_First_Name = editTextCook_First_Name.getText().toString().trim();
        final String Cook_Surname = editTextCook_Surname.getText().toString().trim();
        final String Cook_Mobile = editTextCook_Mobile.getText().toString().trim();
        final String Cook_Email = editTextCook_Email.getText().toString().trim();
        final String Cook_Address = editTextCook_Address.getText().toString().trim();
        final String Cook_Suburb = editTextCook_Suburb.getText().toString().trim();
        final String Cook_State = editTextCook_State.getText().toString().trim();
        final String Cook_DOR = editTextCook_DOR.getText().toString().trim();

        class UpdateCook extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewCook.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewCook.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_COOK_ID,Cook_ID);
                hashMap.put(Config.KEY_COOK_ROLE,Role);
                hashMap.put(Config.KEY_COOK_FIRSTNAME,Cook_First_Name);
                hashMap.put(Config.KEY_COOK_SURNAME,Cook_Surname);
                hashMap.put(Config.KEY_COOK_MOBILE,Cook_Mobile);
                hashMap.put(Config.KEY_COOK_EMAIL,Cook_Email);
                hashMap.put(Config.KEY_COOK_ADDRESS,Cook_Address);
                hashMap.put(Config.KEY_COOK_SUBURB,Cook_Suburb);
                hashMap.put(Config.KEY_COOK_STATE,Cook_State);
                hashMap.put(Config.KEY_COOK_DOR,Cook_DOR);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_COOK,hashMap);

                return s;
            }
        }

        UpdateCook ue = new UpdateCook();
        ue.execute();
    }

    private void deleteCook(){
        class DeleteCook extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewCook.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewCook.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_COOK, cookid);
                return s;
            }
        }

        DeleteCook de = new DeleteCook();
        de.execute();
    }

    private void confirmDeleteCook(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this cook?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteCook();
                        startActivity(new Intent(ViewCook.this,ViewAllCooks.class));
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
            startActivity(new Intent(ViewCook.this,ViewAllCooks.class));
        }

        if(v == buttonDelete){
            confirmDeleteCook();
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

        return super.onOptionsItemSelected(item);
    }

}
