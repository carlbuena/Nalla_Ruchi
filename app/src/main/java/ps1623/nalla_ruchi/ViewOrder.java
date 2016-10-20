package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewOrder extends BaseActivity implements View.OnClickListener {

    private EditText editTextOrder_ID;
    private EditText editTextCustomer_ID;
    private EditText editTextFood_ID;
    private EditText editTextCook_ID;
    private EditText editTextQuantity;
    private EditText editTextDue;
    private EditText editTextPrice;
    private EditText editTextComment;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String orderid;
    private String customerid;
    private String foodid;
    private String cookid;
    private String quantity;
    private String due;
    private String price;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        orderid = extras.getString(Config.ORDER_ID);
        customerid = extras.getString(Config.ORDER_CUSTOMER_ID);
        foodid = extras.getString(Config.ORDER_FOOD_ID);
        cookid = extras.getString(Config.ORDER_COOK_ID);
        quantity = extras.getString(Config.ORDER_QUANTITY);
        due = extras.getString(Config.ORDER_DUE);
        price = extras.getString(Config.ORDER_PRICE);
        comment = extras.getString(Config.ORDER_COMMENT);

        editTextOrder_ID = (EditText) findViewById(R.id.editTextOrder_ID);
        editTextCustomer_ID = (EditText) findViewById(R.id.editTextCustomer_ID);
        editTextFood_ID = (EditText) findViewById(R.id.editTextFood_ID);
        editTextCook_ID = (EditText) findViewById(R.id.editTextCook_ID);
        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        editTextDue = (EditText) findViewById(R.id.editTextDue);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextComment = (EditText) findViewById(R.id.editTextComment);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextOrder_ID.setText(orderid);
        editTextCustomer_ID.setText(customerid);
        editTextFood_ID.setText(foodid);
        editTextCook_ID.setText(cookid);
        editTextQuantity.setText(quantity);
        editTextDue.setText(due);
        editTextPrice.setText(price);
        editTextComment.setText(comment);

        getOrder();
    }

    private void getOrder(){
        class GetOrder extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewOrder.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showOrder(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_ALL_ORDERS,orderid);
                return s;
            }
        }
        GetOrder go = new GetOrder();
        go.execute();
    }

    private void showOrder(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String orderid = c.getString(Config.TAG_ORDER_ID);
            String customerid = c.getString(Config.TAG_ORDER_CUSTOMER_ID);
            String foodid = c.getString(Config.TAG_ORDER_FOOD_ID);
            String cookid = c.getString(Config.TAG_ORDER_COOK_ID);
            String quantity = c.getString(Config.TAG_ORDER_QUANTITY);
            String due = c.getString(Config.TAG_ORDER_DUE);
            String price = c.getString(Config.TAG_ORDER_PRICE);
            String comment = c.getString(Config.TAG_ORDER_COMMENT);

            editTextOrder_ID.setText(orderid);
            editTextCustomer_ID.setText(customerid);
            editTextFood_ID.setText(foodid);
            editTextCook_ID.setText(cookid);
            editTextQuantity.setText(quantity);
            editTextDue.setText(due);
            editTextPrice.setText(price);
            editTextComment.setText(comment);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateFood(){
        final String Order_ID = editTextOrder_ID.getText().toString().trim();
        final String Customer_ID = editTextCustomer_ID.getText().toString().trim();
        final String Food_ID = editTextFood_ID.getText().toString().trim();
        final String Cook_ID = editTextCook_ID.getText().toString().trim();
        final String Quantity = editTextQuantity.getText().toString().trim();
        final String Due = editTextDue.getText().toString().trim();
        final String Price = editTextPrice.getText().toString().trim();
        final String Comment = editTextComment.getText().toString().trim();

        class UpdateFood extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewOrder.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewOrder.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_ORDER_ID,Order_ID);
                hashMap.put(Config.KEY_ORDER_CUSTOMER_ID,Customer_ID);
                hashMap.put(Config.KEY_ORDER_FOOD_ID,Food_ID);
                hashMap.put(Config.KEY_ORDER_COOK_ID,Cook_ID);
                hashMap.put(Config.KEY_ORDER_QUANTITY,Quantity);
                hashMap.put(Config.KEY_ORDER_DUE,Due);
                hashMap.put(Config.KEY_ORDER_PRICE,Price);
                hashMap.put(Config.KEY_ORDER_COMMENT,Comment);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_ORDER,hashMap);

                return s;
            }
        }

        UpdateFood ue = new UpdateFood();
        ue.execute();
    }

    private void deleteOrder(){
        class DeleteOrder extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewOrder.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewOrder.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_ORDER, orderid);
                return s;
            }
        }

        DeleteOrder de = new DeleteOrder();
        de.execute();
    }

    private void confirmDeleteFood(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this food?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteOrder();
                        startActivity(new Intent(ViewOrder.this,CustomerOrders.class));
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
            startActivity(new Intent(ViewOrder.this,ViewAllFood.class));
        }

        if(v == buttonDelete){
            confirmDeleteFood();
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

        return super.onOptionsItemSelected(item);
    }

}