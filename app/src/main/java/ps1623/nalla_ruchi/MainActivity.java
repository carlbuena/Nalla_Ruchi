package ps1623.nalla_ruchi;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Carl on 6/09/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{


        private Button buttonRegisterCustomer;
        private Button buttonMaps;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_activity);

            buttonRegisterCustomer = (Button) findViewById(R.id.buttonRegisterCustomer);
            buttonMaps = (Button) findViewById(R.id.buttonMaps);

            //Setting listeners to button
            buttonRegisterCustomer.setOnClickListener(this);
            buttonMaps.setOnClickListener(this);
        }

    public void onClick(View v) {
        if(v == buttonRegisterCustomer)
        {
            startActivity(new Intent(this,RegisterCustomer.class));
        }
        if(v == buttonMaps) {
            startActivity(new Intent(this,MapsActivity.class));
        }
    }

}
