package ps1623.nalla_ruchi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterCustomer extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextFirst_Name;
    private EditText editTextSurname;
    private EditText editTextMobile;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextAddress;
    private EditText editTextSuburb;
    private EditText editTextState;

    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_customer);

        //Initializing views
        editTextFirst_Name = (EditText) findViewById(R.id.editTextFirst_Name);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextState = (EditText) findViewById(R.id.editTextState);
        editTextSuburb = (EditText) findViewById(R.id.editTextSuburb);

        buttonRegister = (Button) findViewById(R.id.buttonRegister_Customer);

        //Setting listeners to button
        buttonRegister.setOnClickListener(this);
    }


    //Adding an customer
    private void addCustomer(){

        final String First_Name = editTextFirst_Name.getText().toString().trim();
        final String Surname = editTextSurname.getText().toString().trim();
        final String Mobile = editTextMobile.getText().toString().trim();
        final String Email = editTextEmail.getText().toString().trim();
        final String Password = editTextPassword.getText().toString().trim();
        final String Address = editTextAddress.getText().toString().trim();
        final String State = editTextState.getText().toString().trim();
        final String Suburb = editTextSuburb.getText().toString().trim();

        class AddCustomer extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterCustomer.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RegisterCustomer.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_CUSTOMER_FIRSTNAME,First_Name);
                params.put(Config.KEY_CUSTOMER_SURNAME,Surname);
                params.put(Config.KEY_CUSTOMER_MOBILE,Mobile);
                params.put(Config.KEY_CUSTOMER_EMAIL,Email);
                params.put(Config.KEY_CUSTOMER_PASSWORD,Password);
                params.put(Config.KEY_CUSTOMER_ADDRESS,Address);
                params.put(Config.KEY_CUSTOMER_STATE,State);
                params.put(Config.KEY_CUSTOMER_SUBURB,Suburb);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_CUSTOMER, params);
                return res;
            }
        }

        AddCustomer ae = new AddCustomer();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            addCustomer();
        }
    }
}