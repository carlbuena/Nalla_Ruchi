package ps1623.nalla_ruchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
/**
 * Created by Carl on 19/09/16.
 */
public class CookHome extends BaseActivity implements View.OnClickListener {

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
            //startActivity(new Intent(this, CreateOrder.class));
        }
        if (v == buttonAddFood) {
            startActivity(new Intent(this, InsertFood.class));
        }
        if (v == buttonViewOrders) {
            //startActivity(new Intent(this, CustomerLogin.class));
        }
        if (v == buttonViewBookings) {
            //startActivity(new Intent(this, CustomerLogin.class));
        }
        if (v == buttonViewProfile) {
            //startActivity(new Intent(this, CustomerLogin.class));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        navigationView.getMenu().findItem(R.id.customer_home).setVisible(false);
        navigationView.getMenu().findItem(R.id.make_booking).setVisible(false);
        navigationView.getMenu().findItem(R.id.view_cooks).setVisible(false);
        navigationView.getMenu().findItem(R.id.view_food).setVisible(false);
        return true;
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


