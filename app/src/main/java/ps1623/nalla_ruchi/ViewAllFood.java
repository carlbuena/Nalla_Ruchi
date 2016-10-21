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

public class ViewAllFood extends BaseActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_food);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        getJSON();
    }

    private void showFood(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME);
                String price = jo.getString(Config.TAG_PRICE);
                String description = jo.getString(Config.TAG_DES);
                String ethnicity = jo.getString(Config.TAG_ETH);
                String type = jo.getString(Config.TAG_TYPE);
                String dish = jo.getString(Config.TAG_DISH);
                String menu = jo.getString(Config.TAG_FOOD_MENU_ID);

                HashMap<String,String> food = new HashMap<>();
                food.put(Config.TAG_ID,id);
                food.put(Config.TAG_NAME,name);
                food.put(Config.TAG_PRICE,price);
                food.put(Config.TAG_DES,description);
                food.put(Config.TAG_ETH,ethnicity);
                food.put(Config.TAG_TYPE,type);
                food.put(Config.TAG_DISH,dish);
                food.put(Config.TAG_FOOD_MENU_ID,menu);
                list.add(food);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewAllFood.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_PRICE,Config.TAG_DES,Config.TAG_ETH,Config.TAG_TYPE,Config.TAG_DISH,Config.TAG_FOOD_MENU_ID},
                new int[]{R.id.Food_ID, R.id.Food_Name, R.id.Price, R.id.Description, R.id.Ethnicity, R.id.Type, R.id.Dish, R.id.Menu_ID});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAllFood.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showFood();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CreateOrder.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);

        //Reading the Preferences File
        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String Uname = userDetails.getString("username", "");
        String Utype = userDetails.getString("usertype", "");

        String foodid = map.get(Config.TAG_ID).toString();
        String foodname = map.get(Config.TAG_NAME).toString();
        String foodprice = map.get(Config.TAG_PRICE).toString();
        String fooddes = map.get(Config.TAG_DES).toString();
        String foodeth = map.get(Config.TAG_ETH).toString();
        String foodtype = map.get(Config.TAG_TYPE).toString();
        String fooddish = map.get(Config.TAG_DISH).toString();
        String foodmenu = map.get(Config.TAG_FOOD_MENU_ID).toString();

        Bundle extras = new Bundle();

        extras.putString(Config.FOOD_ID,foodid);
        extras.putString(Config.FOOD_NAME,foodname);
        extras.putString(Config.FOOD_PRICE,foodprice);
        extras.putString(Config.FOOD_DES,fooddes);
        extras.putString(Config.FOOD_ETH,foodeth);
        extras.putString(Config.FOOD_TYPE,foodtype);
        extras.putString(Config.FOOD_DISH,fooddish);
        extras.putString(Config.FOOD_MENU_ID,foodmenu);

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
            case R.id.view_food: return true;
        }

        return super.onOptionsItemSelected(item);
    }

}