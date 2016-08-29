package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/08/16.
 */
        import android.app.ProgressDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
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

public class ViewFood extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFood_Id;
    private EditText editTextFood_Name;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextEthnicity;
    private EditText editTextType;
    private EditText editTextMenu_ID;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_food);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.FOOD_ID);

        editTextFood_Id = (EditText) findViewById(R.id.editTextFood_Id);
        editTextFood_Name = (EditText) findViewById(R.id.editTextFood_Name);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextEthnicity = (EditText) findViewById(R.id.editTextEthnicity);
        editTextType = (EditText) findViewById(R.id.editTextType);
        editTextMenu_ID = (EditText) findViewById(R.id.editTextMenu_ID);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextFood_Id.setText(id);

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
                String s = rh.sendGetRequestParam(Config.URL_GET_FOOD,id);
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
            String menu_id = c.getString(Config.TAG_MENU_ID);

            editTextFood_Id.setText(id);
            editTextFood_Name.setText(name);
            editTextPrice.setText(price);
            editTextDescription.setText(des);
            editTextEthnicity.setText(eth);
            editTextType.setText(type);
            editTextMenu_ID.setText(menu_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateFood(){
        final String Food_ID = editTextFood_Id.getText().toString().trim();
        final String Food_Name = editTextFood_Name.getText().toString().trim();
        final String Price = editTextPrice.getText().toString().trim();
        final String Description = editTextDescription.getText().toString().trim();
        final String Ethnicity = editTextEthnicity.getText().toString().trim();
        final String Type = editTextType.getText().toString().trim();
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
                hashMap.put(Config.KEY_MENU_ID,Menu_ID);

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
                String s = rh.sendGetRequestParam(Config.URL_DELETE_FOOD, id);
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
        }

        if(v == buttonDelete){
            confirmDeleteFood();
        }
    }
}