package ps1623.nalla_ruchi;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextFood_Id;
    private EditText editTextFood_Name;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextEthnicity;
    private EditText editTextType;
    private EditText editTextDish;
    private EditText editTextMenu_ID;

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing views
        editTextFood_Id = (EditText) findViewById(R.id.editTextFood_Id);
        editTextFood_Name = (EditText) findViewById(R.id.editTextFood_Name);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextEthnicity = (EditText) findViewById(R.id.editTextEthnicity);
        editTextType = (EditText) findViewById(R.id.editTextType);
        editTextDish = (EditText) findViewById(R.id.editTextDish);
        editTextMenu_ID = (EditText) findViewById(R.id.editTextMenu_ID);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }


    //Adding an food
    private void addFood(){

        final String Food_ID = editTextFood_Id.getText().toString().trim();
        final String Food_Name = editTextFood_Name.getText().toString().trim();
        final String Price = editTextPrice.getText().toString().trim();
        final String Description = editTextDescription.getText().toString().trim();
        final String Ethnicity = editTextEthnicity.getText().toString().trim();
        final String Type = editTextType.getText().toString().trim();
        final String Dish = editTextDish.getText().toString().trim();
        final String Menu_ID = editTextMenu_ID.getText().toString().trim();

        class AddFood extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_FOOD_ID,Food_ID);
                params.put(Config.KEY_FOOD_NAME,Food_Name);
                params.put(Config.KEY_FOOD_PRICE,Price);
                params.put(Config.KEY_FOOD_DES,Description);
                params.put(Config.KEY_FOOD_ETH,Ethnicity);
                params.put(Config.KEY_FOOD_TYPE,Type);
                params.put(Config.KEY_FOOD_DISH,Type);
                params.put(Config.KEY_MENU_ID,Menu_ID);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_FOOD, params);
                return res;
            }
        }

        AddFood ae = new AddFood();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addFood();
        }

        if(v == buttonView){
            startActivity(new Intent(this,ViewAllFood.class));
        }
    }
}