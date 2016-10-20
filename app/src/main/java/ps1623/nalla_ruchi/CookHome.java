package ps1623.nalla_ruchi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Carl on 19/09/16.
 */
public class CookHome extends BaseActivity implements View.OnClickListener {

    private Button buttonAddFood;
    //private Button buttonViewOrders;
    private Button buttonViewBookings;
    private Button buttonViewProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_home);

        buttonAddFood = (Button) findViewById(R.id.addFood);
        //buttonViewOrders = (Button) findViewById(R.id.viewOrders);
        buttonViewBookings = (Button) findViewById(R.id.viewBookings);
        buttonViewProfile = (Button) findViewById(R.id.viewProfile);


        //Setting listeners to button
        buttonAddFood.setOnClickListener(this);
        //buttonViewOrders.setOnClickListener(this);
        buttonViewBookings.setOnClickListener(this);
        buttonViewProfile.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == buttonViewBookings) {
            startActivity(new Intent(this, CookBookings.class));
        }
        if (v == buttonAddFood) {
            startActivity(new Intent(this, InsertFood.class));
        }
        /*if (v == buttonViewOrders) {
            //startActivity(new Intent(this, CustomerLogin.class));
        }*/
        if (v == buttonViewProfile) {
            startActivity(new Intent(this, CookProfile.class));
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
            case R.id.cook_home: return true;
        }

        return super.onOptionsItemSelected(item);
    }



}


