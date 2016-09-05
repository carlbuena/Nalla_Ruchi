package ps1623.nalla_ruchi;

import android.app.AlertDialog;
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

public class RegisterCook extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextFirst_Name;
    private EditText editTextSurname;
    private EditText editTextMobile;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextAddress;
    private EditText editTextSuburb;
    private EditText editTextState;

    private EditText editTextConfirm_Password;

    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_cook);

        //Initializing views
        editTextFirst_Name = (EditText) findViewById(R.id.editTextFirst_Name);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextState = (EditText) findViewById(R.id.editTextState);
        editTextSuburb = (EditText) findViewById(R.id.editTextSuburb);

        editTextConfirm_Password = (EditText) findViewById(R.id.editTextConfirm_Password);

        buttonRegister = (Button) findViewById(R.id.buttonRegister_Cook);

        //Setting listeners to button
        buttonRegister.setOnClickListener(this);

    }

    public boolean noNull (String First_Name, String Surname, String Mobile, String Email, String Address, String State, String Suburb)
    {
        boolean check = false;
        if(First_Name.equals("") || Surname.equals("") || Mobile.equals("") || Email.equals("") || Address.equals("") || State.equals("") || Suburb.equals(""))
        {
            new AlertDialog.Builder(this).setMessage("You must fill in all the fields.").show();
        }
        else
        {
            check = true;
        }
        return check;
    }


    public boolean checkPassword (String Password, String Confirm)
    {
        boolean pstatus = false;
        if(Confirm.equals("") || Password.equals(""))
        {
            new AlertDialog.Builder(this).setMessage("You must fill in all the fields.").show();
        }
        else
        {
            if(Password.equals(Confirm))
            {
                pstatus = true;
            }
        }
        return pstatus;
    }


    //Adding cook
    private void addCook(){

        final String First_Name = editTextFirst_Name.getText().toString().trim();
        final String Surname = editTextSurname.getText().toString().trim();
        final String Mobile = editTextMobile.getText().toString().trim();
        final String Email = editTextEmail.getText().toString().trim();
        final String Password = editTextPassword.getText().toString().trim();
        final String Address = editTextAddress.getText().toString().trim();
        final String State = editTextState.getText().toString().trim();
        final String Suburb = editTextSuburb.getText().toString().trim();

        final String Confirm = editTextConfirm_Password.getText().toString().trim();


        class AddCook extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterCook.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RegisterCook.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_COOK_FIRSTNAME,First_Name);
                params.put(Config.KEY_COOK_SURNAME,Surname);
                params.put(Config.KEY_COOK_MOBILE,Mobile);
                params.put(Config.KEY_COOK_EMAIL,Email);
                params.put(Config.KEY_COOK_PASSWORD,Password);
                params.put(Config.KEY_COOK_ADDRESS,Address);
                params.put(Config.KEY_COOK_STATE,State);
                params.put(Config.KEY_COOK_SUBURB,Suburb);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_COOK, params);
                return res;
            }
        }

        if(noNull(First_Name, Surname, Mobile, Email, Address, State, Suburb))
        {
            if(checkPassword(Password, Confirm))
            {
                AddCook ac = new AddCook();
                ac.execute();
            }
            else
            {
                new AlertDialog.Builder(this).setMessage("Your password must match.").show();
            }
        }

    }

    @Override
    public void onClick(View v) {

        if(v == buttonRegister){
            addCook();
        }

    }


}