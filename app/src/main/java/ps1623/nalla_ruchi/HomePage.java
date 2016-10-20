package ps1623.nalla_ruchi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sarahmurphy on 20/09/16.
 */
public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private Button buttonCook;
    private Button buttonCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonCook = (Button) findViewById(R.id.cookHome);
        buttonCustomer = (Button) findViewById(R.id.customerHome);


        //Setting listeners to button
        buttonCook.setOnClickListener(this);
        buttonCustomer.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == buttonCook) {
            startActivity(new Intent(this, CookLogin.class));
        }
        if (v == buttonCustomer) {
            startActivity(new Intent(this, CustomerLogin.class));
        }

    }

    @Override
    public void onBackPressed()
    {
      moveTaskToBack(true);
    }
}
