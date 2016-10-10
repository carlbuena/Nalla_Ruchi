package ps1623.nalla_ruchi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Carl on 19/09/16.
 */
public class CookHome extends AppCompatActivity implements View.OnClickListener {

    private Button buttonViewMenu;
    private Button buttonAddFood;
    private Button buttonViewOrders;
    private Button buttonViewBookings;
    private Button buttonViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_home);

        buttonViewMenu = (Button) findViewById(R.id.viewMenu);
        buttonAddFood = (Button) findViewById(R.id.addFood);
        buttonViewOrders = (Button) findViewById(R.id.viewOrders);
        buttonViewBookings = (Button) findViewById(R.id.viewBookings);
        buttonViewProfile = (Button) findViewById(R.id.viewProfile);


        //Setting listeners to button
        buttonViewMenu.setOnClickListener(this);
        buttonAddFood.setOnClickListener(this);
        buttonViewOrders.setOnClickListener(this);
        buttonViewBookings.setOnClickListener(this);
        buttonViewProfile.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == buttonViewMenu) {
            //startActivity(new Intent(this, CreateBooking.class));
        }
        if (v == buttonAddFood) {
            startActivity(new Intent(this, InsertFood.class));
        }
        if (v == buttonViewOrders){
            //startActivity(new Intent(this, CustomerLogin.class));
        }
        if (v == buttonViewBookings){
            //startActivity(new Intent(this, CustomerLogin.class));
        }
        if (v == buttonViewProfile){
            //startActivity(new Intent(this, CustomerLogin.class));
        }

    }
}

