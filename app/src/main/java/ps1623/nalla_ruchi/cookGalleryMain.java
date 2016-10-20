package ps1623.nalla_ruchi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View.OnClickListener;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;


import java.util.List;

import static ps1623.nalla_ruchi.R.id.navigationView;


public class cookGalleryMain extends BaseActivity implements RecyclerView.OnScrollChangeListener{

    private int cookID;
    //Creating a List of cooks
    private List<cookGallery> listCookPhotos;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;



    //Volley Request Queue
    private RequestQueue requestQueue;

    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_gallery);

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //Initializing our superheroes list
        listCookPhotos = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        //Calling method to get data to fetch data
        getData();

        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);


        //initializing our adapter
        adapter = new CardAdapter_Cook(listCookPhotos, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL_COOKIMAGES + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(cookGalleryMain.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the cook object
            cookGallery cookGallery = new cookGallery();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                cookGallery.setCookID(json.getInt(Config.TAG_COOK_ID));
                cookGallery.setImageUrl(json.getString(Config.TAG_COOK_IMAGE));
                cookGallery.setFirstName(json.getString(Config.TAG_COOK_FIRSTNAME));
                cookGallery.setSurname(json.getString(Config.TAG_COOK_SURNAME));

                cookID = cookGallery.getcookID();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the foodGalllery object to the list
            listCookPhotos.add(cookGallery);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    //Overriden method to detect scrolling
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            getData();
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
            case R.id.customer_home: return true;
        }

        return super.onOptionsItemSelected(item);
    }




}




