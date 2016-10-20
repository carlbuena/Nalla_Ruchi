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
public class CustomerHome extends BaseActivity implements View.OnClickListener {

    private Button buttonOrder;
    private Button buttonViewCooks;
    private Button buttonBooking;
    private Button buttonSearch;
    private Button buttonviewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);


        buttonOrder = (Button) findViewById(R.id.Order);
        buttonViewCooks = (Button) findViewById(R.id.Cooks);
        buttonBooking = (Button) findViewById(R.id.Booking);
        buttonSearch = (Button) findViewById(R.id.Search);
        buttonviewProfile = (Button) findViewById(R.id.myProfile);


        //Setting listeners to button
        buttonOrder.setOnClickListener(this);
        buttonViewCooks.setOnClickListener(this);
        buttonBooking.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonviewProfile.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == buttonOrder) {
            startActivity(new Intent(this, CreateOrder.class));
        }
        if (v == buttonViewCooks) {
            startActivity(new Intent(this, ViewAllCooks.class));
        }
        if (v == buttonBooking) {
            startActivity(new Intent(this, Booking.class));
        }
        if (v == buttonSearch){
            startActivity(new Intent(this, Search.class));
        }
        if (v == buttonviewProfile){
            startActivity(new Intent(this, CustomerProfile.class));
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
        // Handle action bar item clicks here.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.customer_home: return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

