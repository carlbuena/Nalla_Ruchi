package ps1623.nalla_ruchi;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);

        buttonOrder = (Button) findViewById(R.id.Order);
        buttonViewCooks = (Button) findViewById(R.id.Booking);
        buttonSearch = (Button) findViewById(R.id.Search);


        //Setting listeners to button
        buttonOrder.setOnClickListener(this);
        buttonViewCooks.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == buttonOrder) {
            //startActivity(new Intent(this, CookLogin.class));
        }
        if (v == buttonViewCooks) {
            //startActivity(new Intent(this, CustomerLogin.class));
        }
        if (v == buttonSearch){
            //startActivity(new Intent(this, CustomerLogin.class));
        }

    }
}

