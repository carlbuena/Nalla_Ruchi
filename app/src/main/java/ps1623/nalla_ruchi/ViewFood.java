package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
        import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewFood extends BaseActivity implements View.OnClickListener {

    private EditText editTextFood_ID;
    private EditText editTextFood_Name;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextEthnicity;
    private EditText editTextType;
    private EditText editTextDish;
    private EditText editTextMenu_ID;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String foodid;
    private String foodname;
    private String foodprice;
    private String fooddes;
    private String foodeth;
    private String foodtype;
    private String fooddish;
    private String foodmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_food);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        foodid = extras.getString(Config.FOOD_ID);
        foodname = extras.getString(Config.FOOD_NAME);
        foodprice = extras.getString(Config.FOOD_PRICE);
        fooddes = extras.getString(Config.FOOD_DES);
        foodeth = extras.getString(Config.FOOD_ETH);
        foodtype = extras.getString(Config.FOOD_TYPE);
        fooddish = extras.getString(Config.FOOD_DISH);
        foodmenu = extras.getString(Config.FOOD_MENU_ID);

        editTextFood_ID = (EditText) findViewById(R.id.editTextFood_ID);
        editTextFood_Name = (EditText) findViewById(R.id.editTextFood_Name);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextEthnicity = (EditText) findViewById(R.id.editTextEthnicity);
        editTextType = (EditText) findViewById(R.id.editTextType);
        editTextDish = (EditText) findViewById(R.id.editTextDish);
        editTextMenu_ID = (EditText) findViewById(R.id.editTextMenu_ID);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextFood_ID.setText(foodid);
        editTextFood_Name.setText(foodname);
        editTextPrice.setText(foodprice);
        editTextDescription.setText(fooddes);
        editTextEthnicity.setText(foodeth);
        editTextType.setText(foodtype);
        editTextDish.setText(fooddish);
        editTextMenu_ID.setText(foodmenu);

        getFood();
    }

    private void getFood(){
        class GetFood extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewFood.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showFood(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_FOOD,foodid);
                return s;
            }
        }
        GetFood ge = new GetFood();
        ge.execute();
    }

    private void showFood(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String id = c.getString(Config.TAG_ID);
            String name = c.getString(Config.TAG_NAME);
            String price = c.getString(Config.TAG_PRICE);
            String des = c.getString(Config.TAG_DES);
            String eth = c.getString(Config.TAG_ETH);
            String type = c.getString(Config.TAG_TYPE);
            String dish = c.getString(Config.TAG_DISH);
            String menu_id = c.getString(Config.TAG_FOOD_MENU_ID);

            editTextFood_ID.setText(id);
            editTextFood_Name.setText(name);
            editTextPrice.setText(price);
            editTextDescription.setText(des);
            editTextEthnicity.setText(eth);
            editTextType.setText(type);
            editTextDish.setText(dish);
            editTextMenu_ID.setText(menu_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateFood(){
        final String Food_ID = editTextFood_ID.getText().toString().trim();
        final String Food_Name = editTextFood_Name.getText().toString().trim();
        final String Price = editTextPrice.getText().toString().trim();
        final String Description = editTextDescription.getText().toString().trim();
        final String Ethnicity = editTextEthnicity.getText().toString().trim();
        final String Type = editTextType.getText().toString().trim();
        final String Dish = editTextDish.getText().toString().trim();
        final String Menu_ID = editTextMenu_ID.getText().toString().trim();

        class UpdateFood extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewFood.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewFood.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_FOOD_ID,Food_ID);
                hashMap.put(Config.KEY_FOOD_NAME,Food_Name);
                hashMap.put(Config.KEY_FOOD_PRICE,Price);
                hashMap.put(Config.KEY_FOOD_DES,Description);
                hashMap.put(Config.KEY_FOOD_ETH,Ethnicity);
                hashMap.put(Config.KEY_FOOD_TYPE,Type);
                hashMap.put(Config.KEY_FOOD_DISH,Dish);
                hashMap.put(Config.KEY_FOOD_MENU_ID,Menu_ID);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_FOOD,hashMap);

                return s;
            }
        }

        UpdateFood ue = new UpdateFood();
        ue.execute();
    }

    private void deleteFood(){
        class DeleteFood extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewFood.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewFood.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_FOOD, foodid);
                return s;
            }
        }

        DeleteFood de = new DeleteFood();
        de.execute();
    }

    private void confirmDeleteFood(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this food?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteFood();
                        startActivity(new Intent(ViewFood.this,ViewAllFood.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateFood();
            startActivity(new Intent(ViewFood.this,ViewAllFood.class));
        }

        if(v == buttonDelete){
            confirmDeleteFood();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        navigationView.getMenu().findItem(R.id.cook_home).setVisible(false);
        navigationView.getMenu().findItem(R.id.cook_profile).setVisible(false);
        navigationView.getMenu().findItem(R.id.view_customers).setVisible(false);
        navigationView.getMenu().findItem(R.id.add_food).setVisible(false);
        navigationView.getMenu().findItem(R.id.view_bookings).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}