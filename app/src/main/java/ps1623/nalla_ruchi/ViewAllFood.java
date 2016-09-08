package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;

public class ViewAllFood extends AppCompatActivity implements ListView.OnItemClickListener {

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

                HashMap<String,String> food = new HashMap<>();
                food.put(Config.TAG_ID,id);
                food.put(Config.TAG_NAME,name);
                food.put(Config.TAG_PRICE,price);
                food.put(Config.TAG_DES,description);
                food.put(Config.TAG_ETH,ethnicity);
                food.put(Config.TAG_TYPE,type);
                food.put(Config.TAG_DISH,dish);
                list.add(food);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewAllFood.this, list, R.layout.list_item,
                new String[]{Config.TAG_ID,Config.TAG_NAME,Config.TAG_PRICE,Config.TAG_DES,Config.TAG_ETH,Config.TAG_TYPE,Config.TAG_DISH},
                new int[]{R.id.Food_ID, R.id.Food_Name, R.id.Price, R.id.Description, R.id.Ethnicity, R.id.Type, R.id.Dish});

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
        Intent intent = new Intent(this, ViewAllFood.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String foodid = map.get(Config.TAG_ID).toString();
        intent.putExtra(Config.FOOD_ID,foodid);
        startActivity(intent);
    }
}