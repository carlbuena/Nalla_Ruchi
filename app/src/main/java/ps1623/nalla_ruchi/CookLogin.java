package ps1623.nalla_ruchi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CookLogin extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_SIGNUP = 0;

    private EditText editTextUserName;
    private EditText editTextPassword;

    public static final String USER_NAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    private static final String URL_COOK_LOGIN ="http://pe-ps1623.scem.westernsydney.edu.au/api/login/cooklogin.php";


    @InjectView(R.id.cook_input_email) EditText emailText;
    @InjectView(R.id.cook_input_password) EditText passwordText;
    @InjectView(R.id.cook_btn_login) Button loginButton;
    @InjectView(R.id.link_cook_register) TextView cookRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_login);
        editTextUserName = (EditText) findViewById(R.id.cook_input_email);
        editTextPassword = (EditText) findViewById(R.id.cook_input_password);

        ButterKnife.inject(this);
        cookRegisterLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Cook Register activity
                Intent intent = new Intent(getApplicationContext(), RegisterCook.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        loginButton.setOnClickListener(this);
    }

    public void invokeLogin(){
        String email = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        login(email,password);
    }
    public void login(final String email, final String password) {
        class UserLoginClass extends AsyncTask<String, Void, String>{
            ProgressDialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(CookLogin.this, "Please wait", null, true, true);
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("email", params[0]);
                data.put("password", params[1]);

                RequestHandler ruc = new RequestHandler();

                String result = ruc.sendPostRequest(URL_COOK_LOGIN, data);

                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(CookLogin.this, CookHome.class);
                    intent.putExtra(USER_NAME, email);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(email,password);

    }

    public void onClick(View v)
    {
        if(v == loginButton)
        {
            if(!validate())
            {
                onLoginFailed();
            }
            else
            {
                invokeLogin();
            }
        }
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
        }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please enter a valid email address.");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty()) {
            passwordText.setError("Please enter your password.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}