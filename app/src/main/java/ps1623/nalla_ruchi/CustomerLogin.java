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

/**
 * Created by Carl on 19/09/16.
 */
public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_SIGNUP = 0;

    private EditText editTextUserName;
    private EditText editTextPassword;

    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    private static final String URL_CUSTOMER_LOGIN ="http://pe-ps1623.scem.westernsydney.edu.au/api/login/customerlogin.php";

    @InjectView(R.id.customer_input_email) EditText _emailText;
    @InjectView(R.id.customer_input_password) EditText _passwordText;
    @InjectView(R.id.customer_btn_login) Button _loginButton;
    @InjectView(R.id.link_customer_register) TextView _customerRegisterLink;
    @InjectView(R.id.link_continue_guest) TextView _continueGuestLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_login);
        editTextUserName = (EditText) findViewById(R.id.customer_input_email);
        editTextPassword = (EditText) findViewById(R.id.customer_input_password);

        ButterKnife.inject(this);

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

        _loginButton.setOnClickListener(this);
    }
    public void invokeLogin(){
        String email = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        login(email,password);
    }
    public void login(final String email, final String password) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CustomerLogin.this, "Please wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(CustomerLogin.this, MainActivity.class);
                    intent.putExtra(USER_NAME, email);
                    startActivity(intent);
                } else {
                    Toast.makeText(CustomerLogin.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("email", params[0]);
                data.put("password", params[1]);

                RequestHandler ruc = new RequestHandler();

                String result = ruc.sendPostRequest(URL_CUSTOMER_LOGIN, data);

                return result;
            }
        }
        UserLoginClass la = new UserLoginClass();
        la.execute(email,password);
    }

    @Override
    public void onClick(View v)
    {
        if(v == _loginButton)
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

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}
