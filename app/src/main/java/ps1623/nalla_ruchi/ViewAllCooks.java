package ps1623.nalla_ruchi;

/**
 * Created by Carl on 15/09/16.
 */

        import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllCooks extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_cooks);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showCook(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String cook_id = jo.getString(Config.TAG_COOK_ID);
                String role = jo.getString(Config.TAG_COOK_ROLE);
                String firstName = jo.getString(Config.TAG_COOK_FIRSTNAME);
                String surname = jo.getString(Config.TAG_COOK_SURNAME);
                String mobile = jo.getString(Config.TAG_COOK_MOBILE);
                String email = jo.getString(Config.TAG_COOK_EMAIL);
                String address = jo.getString(Config.TAG_COOK_ADDRESS);
                String suburb = jo.getString(Config.TAG_COOK_SUBURB);
                String state = jo.getString(Config.TAG_COOK_STATE);
                String dor = jo.getString(Config.TAG_COOK_DOR);

                HashMap<String,String> cook = new HashMap<>();
                cook.put(Config.TAG_COOK_ID,cook_id);
                cook.put(Config.TAG_COOK_ROLE,role);
                cook.put(Config.TAG_COOK_FIRSTNAME,firstName);
                cook.put(Config.TAG_COOK_SURNAME,surname);
                cook.put(Config.TAG_COOK_MOBILE,mobile);
                cook.put(Config.TAG_COOK_EMAIL,email);
                cook.put(Config.TAG_COOK_ADDRESS,address);
                cook.put(Config.TAG_COOK_SUBURB,suburb);
                cook.put(Config.TAG_COOK_STATE,state);
                cook.put(Config.TAG_COOK_DOR,dor);
                list.add(cook);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewAllCooks.this, list, R.layout.list_cook,
                new String[]{Config.TAG_COOK_ID,Config.TAG_COOK_ROLE,Config.TAG_COOK_FIRSTNAME,Config.TAG_COOK_SURNAME,Config.TAG_COOK_MOBILE,Config.TAG_COOK_EMAIL,Config.TAG_COOK_ADDRESS,Config.TAG_COOK_SUBURB,Config.TAG_COOK_STATE,Config.TAG_COOK_DOR},
                new int[]{R.id.Cook_ID,R.id.Role, R.id.FirstName, R.id.Surname, R.id.Mobile, R.id.Email, R.id.Address, R.id.Suburb, R.id.State, R.id.DateOfRegistration});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAllCooks.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showCook();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_COOKS);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewCook.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);

        String cook_id = map.get(Config.TAG_COOK_ID).toString();
        String role = map.get(Config.TAG_COOK_ROLE).toString();
        String firstName = map.get(Config.TAG_COOK_FIRSTNAME).toString();
        String surname = map.get(Config.TAG_COOK_SURNAME).toString();
        String mobile = map.get(Config.TAG_COOK_MOBILE).toString();
        String email = map.get(Config.TAG_COOK_EMAIL).toString();
        String address = map.get(Config.TAG_COOK_ADDRESS).toString();
        String suburb = map.get(Config.TAG_COOK_SUBURB).toString();
        String state = map.get(Config.TAG_COOK_STATE).toString();
        String dor = map.get(Config.TAG_COOK_DOR).toString();

        Bundle extras = new Bundle();

        extras.putString(Config.TAG_COOK_ID,cook_id);
        extras.putString(Config.TAG_COOK_ROLE,role);
        extras.putString(Config.TAG_COOK_FIRSTNAME,firstName);
        extras.putString(Config.TAG_COOK_SURNAME,surname);
        extras.putString(Config.TAG_COOK_MOBILE,mobile);
        extras.putString(Config.TAG_COOK_EMAIL,email);
        extras.putString(Config.TAG_COOK_ADDRESS,address);
        extras.putString(Config.TAG_COOK_SUBURB,suburb);
        extras.putString(Config.TAG_COOK_STATE,state);
        extras.putString(Config.TAG_COOK_DOR,dor);

        intent.putExtras(extras);

        startActivity(intent);
    }
}
