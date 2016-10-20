package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerOrders extends BaseActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_customer_orders);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        getJSON();
    }

    private void showOrder(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String orderid = jo.getString(Config.TAG_ORDER_ID);
                String customerid = jo.getString(Config.TAG_ORDER_CUSTOMER_ID);
                String foodid = jo.getString(Config.TAG_ORDER_FOOD_ID);
                String cookid = jo.getString(Config.TAG_ORDER_COOK_ID);
                String quantity = jo.getString(Config.TAG_ORDER_QUANTITY);
                String price = jo.getString(Config.TAG_ORDER_PRICE);
                String due = jo.getString(Config.TAG_ORDER_DUE);
                String comment = jo.getString(Config.TAG_ORDER_COMMENT);

                HashMap<String,String> order = new HashMap<>();
                order.put(Config.TAG_ORDER_ID,orderid);
                order.put(Config.TAG_ORDER_CUSTOMER_ID,customerid);
                order.put(Config.TAG_ORDER_FOOD_ID,foodid);
                order.put(Config.TAG_ORDER_COOK_ID,cookid);
                order.put(Config.TAG_ORDER_QUANTITY,quantity);
                order.put(Config.TAG_ORDER_PRICE,price);
                order.put(Config.TAG_ORDER_DUE,due);
                order.put(Config.TAG_ORDER_COMMENT,comment);
                list.add(order);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                CustomerOrders.this, list, R.layout.items_customer_orders,
                new String[]{Config.TAG_ORDER_ID,Config.TAG_ORDER_CUSTOMER_ID,Config.TAG_ORDER_FOOD_ID,Config.TAG_ORDER_COOK_ID,Config.TAG_ORDER_QUANTITY,Config.TAG_ORDER_PRICE,Config.TAG_ORDER_DUE,Config.TAG_ORDER_COMMENT},
                new int[]{R.id.Order_ID, R.id.Customer_ID, R.id.Food_ID, R.id.Cook_ID, R.id.Quantity, R.id.Price, R.id.Due, R.id.Comment});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CustomerOrders.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showOrder();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_ORDERS);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewOrder.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);

        //Reading the Preferences File
        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String Uname = userDetails.getString("username", "");
        String Utype = userDetails.getString("usertype", "");

        String orderid = map.get(Config.TAG_ORDER_ID).toString();
        String customerid = map.get(Config.TAG_ORDER_CUSTOMER_ID).toString();
        String foodid = map.get(Config.TAG_ORDER_FOOD_ID).toString();
        String cookid = map.get(Config.TAG_ORDER_COOK_ID).toString();
        String quantity = map.get(Config.TAG_ORDER_QUANTITY).toString();
        String due = map.get(Config.TAG_ORDER_DUE).toString();
        String price = map.get(Config.TAG_ORDER_PRICE).toString();
        String comment = map.get(Config.TAG_ORDER_COMMENT).toString();

        Bundle extras = new Bundle();

        extras.putString(Config.ORDER_ID,orderid);
        extras.putString(Config.ORDER_CUSTOMER_ID,customerid);
        extras.putString(Config.ORDER_FOOD_ID,foodid);
        extras.putString(Config.ORDER_COOK_ID,cookid);
        extras.putString(Config.ORDER_QUANTITY,quantity);
        extras.putString(Config.ORDER_DUE,due);
        extras.putString(Config.ORDER_PRICE,price);
        extras.putString(Config.ORDER_COMMENT,comment);

        intent.putExtras(extras);

        if(Utype.equalsIgnoreCase("cook")) {
            startActivity(intent);
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
            //change id
            case R.id.view_food: return true;
        }

        return super.onOptionsItemSelected(item);
    }

}