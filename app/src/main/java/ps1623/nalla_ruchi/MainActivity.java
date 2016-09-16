package ps1623.nalla_ruchi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Carl on 6/09/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button buttonRegisterCustomer;
    private Button buttonRegisterCook;
    private Button buttonSignIn;
    private Button buttonViewAllFood;
    private Button buttonViewAllCooks;
    private Button buttonViewAllCustomers;
    private Button buttonAddFood;
    private Button buttonMaps;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_activity);

            buttonRegisterCustomer = (Button) findViewById(R.id.buttonRegisterCustomer);
            buttonRegisterCook = (Button) findViewById(R.id.buttonRegisterCook);
            buttonViewAllCooks = (Button) findViewById(R.id.buttonViewAllCooks);
            buttonViewAllCustomers = (Button) findViewById(R.id.buttonViewAllCustomers);
            buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
            buttonViewAllFood = (Button) findViewById(R.id.buttonViewAllFood);
            buttonAddFood = (Button) findViewById(R.id.buttonAddFood);
            buttonMaps = (Button) findViewById(R.id.buttonMaps);

            //Setting listeners to button
            buttonRegisterCustomer.setOnClickListener(this);
            buttonRegisterCook.setOnClickListener(this);
            buttonViewAllCooks.setOnClickListener(this);
            buttonViewAllCustomers.setOnClickListener(this);
            buttonSignIn.setOnClickListener(this);
            buttonViewAllFood.setOnClickListener(this);
            buttonAddFood.setOnClickListener(this);
            buttonMaps.setOnClickListener(this);
        }

    public void onClick(View v) {
        if(v == buttonRegisterCustomer) {
            startActivity(new Intent(this,RegisterCustomer.class));
        }
        if(v == buttonRegisterCook) {
            startActivity(new Intent(this,RegisterCook.class));
        }
        if(v == buttonViewAllCooks) {
            startActivity(new Intent(this,ViewAllCooks.class));
        }
        if(v == buttonViewAllCustomers) {
            startActivity(new Intent(this,ViewAllCustomers.class));
        }
        if(v == buttonSignIn) {
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(v == buttonViewAllFood) {
            startActivity(new Intent(this,ViewAllFood.class));
        }
        if(v == buttonAddFood) {
            startActivity(new Intent(this,InsertFood.class));
        }
        if(v == buttonMaps) {
            startActivity(new Intent(this,MapsActivity.class));
        }
    }

}
