package ps1623.nalla_ruchi;

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

public class CookProfile extends BaseActivity implements View.OnClickListener {

    private ListView listView;

    private String JSON_STRING;

    private Button buttonEdit;

    private TextView editTextCook_ID;
    private TextView editTextRole;
    private TextView editTextCook_First_Name;
    private TextView editTextCook_Surname;
    private TextView editTextCook_Mobile;
    private TextView editTextCook_Email;
    private TextView editTextCook_Address;
    private TextView editTextCook_Suburb;
    private TextView editTextCook_State;
    private TextView editTextCook_DOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_profile);

        editTextCook_ID = (TextView) findViewById(R.id.editC_ID);
        editTextRole = (TextView) findViewById(R.id.editRole);
        editTextCook_First_Name = (TextView) findViewById(R.id.FName);
        editTextCook_Surname = (TextView) findViewById(R.id.sname);
        editTextCook_Mobile = (TextView) findViewById(R.id.mob);
        editTextCook_Email = (TextView) findViewById(R.id.email);
        editTextCook_Address = (TextView) findViewById(R.id.address);
        editTextCook_Suburb = (TextView) findViewById(R.id.suburb);
        editTextCook_State = (TextView) findViewById(R.id.state);
        editTextCook_DOR = (TextView) findViewById(R.id.dor);

        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(this);

        getJSON();
    }

    private void showCustomer(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
            String Uname = userDetails.getString("username", "");
            String Utype = userDetails.getString("usertype", "");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_COOK_ID);
                String role = jo.getString(Config.TAG_COOK_ROLE);
                String firstName = jo.getString(Config.TAG_COOK_FIRSTNAME);
                String surname = jo.getString(Config.TAG_COOK_SURNAME);
                String mobile = jo.getString(Config.TAG_COOK_MOBILE);
                String email = jo.getString(Config.TAG_COOK_EMAIL);
                String address = jo.getString(Config.TAG_COOK_ADDRESS);
                String suburb = jo.getString(Config.TAG_COOK_SUBURB);
                String state = jo.getString(Config.TAG_COOK_STATE);
                String dor = jo.getString(Config.TAG_COOK_DOR);

                if(email.equalsIgnoreCase(Uname)){
                    editTextCook_ID.setText(id);
                    editTextRole.setText(role);
                    editTextCook_First_Name.setText(firstName);
                    editTextCook_Surname.setText(surname);
                    editTextCook_Mobile.setText(mobile);
                    editTextCook_Email.setText(email);
                    editTextCook_Address.setText(address);
                    editTextCook_Suburb.setText(suburb);
                    editTextCook_State.setText(state);
                    editTextCook_DOR.setText(dor);
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
                loading = ProgressDialog.show(CookProfile.this,"Fetching Data","Wait...",false,false);
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
                String s = rh.sendGetRequest(Config.URL_GET_ALL_COOKS);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onClick(View v){
        if(v == buttonEdit) {
            Intent intent = new Intent(CookProfile.this, editCookProfile.class);

            String cook_id = editTextCook_ID.getText().toString();
            String role = editTextRole.getText().toString();
            String firstName = editTextCook_First_Name.getText().toString();
            String surname = editTextCook_Surname.getText().toString();
            String mobile = editTextCook_Mobile.getText().toString();
            String email = editTextCook_Email.getText().toString();
            String address = editTextCook_Address.getText().toString();
            String suburb = editTextCook_Suburb.getText().toString();
            String state = editTextCook_State.getText().toString();
            String dor = editTextCook_DOR.getText().toString();

            Bundle extras = new Bundle();

            extras.putString(Config.TAG_COOK_ID, cook_id);
            extras.putString(Config.TAG_COOK_ROLE, role);
            extras.putString(Config.TAG_COOK_FIRSTNAME, firstName);
            extras.putString(Config.TAG_COOK_SURNAME, surname);
            extras.putString(Config.TAG_COOK_MOBILE, mobile);
            extras.putString(Config.TAG_COOK_EMAIL, email);
            extras.putString(Config.TAG_COOK_ADDRESS, address);
            extras.putString(Config.TAG_COOK_SUBURB, suburb);
            extras.putString(Config.TAG_COOK_STATE, state);
            extras.putString(Config.TAG_COOK_DOR, dor);

            intent.putExtras(extras);

            SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
            String Uname = userDetails.getString("username", "");
            String Utype = userDetails.getString("usertype", "");

            if(Uname.equalsIgnoreCase(email) && Utype.equalsIgnoreCase("cook")) {
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
            case R.id.cook_profile: return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
