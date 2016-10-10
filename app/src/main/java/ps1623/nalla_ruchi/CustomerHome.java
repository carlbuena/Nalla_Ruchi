package ps1623.nalla_ruchi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Carl on 19/09/16.
 */
public class CustomerHome extends AppCompatActivity implements View.OnClickListener {

    private Button buttonOrder;
    private Button buttonViewCooks;
    private Button buttonSearch;
    private Button buttonviewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);

        buttonOrder = (Button) findViewById(R.id.Order);
        buttonViewCooks = (Button) findViewById(R.id.Booking);
        buttonSearch = (Button) findViewById(R.id.Search);
        buttonviewProfile = (Button) findViewById(R.id.myProfile);


        //Setting listeners to button
        buttonOrder.setOnClickListener(this);
        buttonViewCooks.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonviewProfile.setOnClickListener(this);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v) {
        if (v == buttonOrder) {
            startActivity(new Intent(this, CreateBooking.class));
        }
        if (v == buttonViewCooks) {
            //startActivity(new Intent(this, CustomerLogin.class));
        }
        if (v == buttonSearch){
            //startActivity(new Intent(this, CustomerLogin.class));
        }
        if (v == buttonviewProfile){
            //startActivity(new Intent(this, CustomerLogin.class));
        }

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}

