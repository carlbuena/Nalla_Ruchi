package ps1623.nalla_ruchi;

import android.app.Dialog;
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

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    private EditText editTextUserName;
    private EditText editTextPassword;

    public static final String USER_NAME = "USERNAME";
    String email;
    String password;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_cook_register) TextView _cookRegisterLink;
    @InjectView(R.id.link_customer_register) TextView _customerRegisterLink;
    @InjectView(R.id.link_continue_guest) TextView _continueGuestLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUserName = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);

        ButterKnife.inject(this);
        _cookRegisterLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Cook Register activity
                Intent intent = new Intent(getApplicationContext(), RegisterCook.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        _customerRegisterLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the Customer Register activity
                Intent intent = new Intent(getApplicationContext(), RegisterCustomer.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        _continueGuestLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Continue to app as guest
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }
    public void invokeLogin(View view){
        email = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();

        login(email,password);
    }
    public void login(final String email, final String password) {
        class LoginAsync extends AsyncTask<String, String, String>{
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
            }
            @Override
            protected String doInBackground(String... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMAIL,email);
                params.put(Config.KEY_PASSWORD,password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_LOGIN, params);
                return res;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(USER_NAME, email);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        if (!validate()) {
            onLoginFailed();
            return;
        }
        else 
        {
            la.execute(email, password);
        }

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
        }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Please enter a valid email address.");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Please enter your password.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}