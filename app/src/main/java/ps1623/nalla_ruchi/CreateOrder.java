package ps1623.nalla_ruchi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.HashMap;

import static ps1623.nalla_ruchi.R.id.textfoodName;

/**
 * Created by Prazad Silva on 16/9/2016.
 */
public class CreateOrder extends AppCompatActivity implements View.OnClickListener{

    //JSON Array
    private JSONArray result;

    private Button buttonOrder;

    private Button buttonConfirm;
    private Button buttonCancel;

    private TextView TextFoodName;
    private TextView TextFoodPrice;
    private TextView TextCookName;
    private EditText Comment;
    private EditText Quantity;

    private String cookid;
    private String cookname;
    private String foodid;
    private String foodname;
    private String foodprice;
    private String fooddes;
    private String foodeth;
    private String foodtype;
    private String fooddish;
    private String foodmenu;

    DatePicker datePicker;
    TextView displayDate;
    Button selectDate;
    int month;

    TimePicker timePicker;
    TextView displayTime;
    Button changeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_order);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        cookid = extras.getString(Config.COOK_ID);
        cookname = extras.getString(Config.COOK_FIRSTNAME);
        foodid = extras.getString(Config.FOOD_ID);
        foodname = extras.getString(Config.FOOD_NAME);
        foodprice = extras.getString(Config.FOOD_PRICE);
        fooddes = extras.getString(Config.FOOD_DES);
        foodeth = extras.getString(Config.FOOD_ETH);
        foodtype = extras.getString(Config.FOOD_TYPE);
        fooddish = extras.getString(Config.FOOD_DISH);
        foodmenu = extras.getString(Config.FOOD_MENU_ID);

        TextFoodName = (TextView) findViewById(textfoodName);
        TextFoodPrice = (TextView) findViewById(R.id.textPrice);
        TextCookName = (TextView) findViewById(R.id.textcookName);
        Comment = (EditText) findViewById(R.id.comment);
        Quantity = (EditText) findViewById(R.id.quantity);

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);

        buttonConfirm.setOnClickListener(this);

        TextFoodName.setText(foodname);
        TextFoodPrice.setText(foodprice);
        TextCookName.setText(cookname);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        selectDate = (Button) findViewById(R.id.select_date_button);
        displayDate = (TextView) findViewById(R.id.display_date);
        displayDate.setText("Display Date");

        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        displayDate.setText(currentDate());
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDate.setText(currentDate());
            }
        });

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        displayTime = (TextView) findViewById(R.id.display_time);
        changeTime = (Button) findViewById(R.id.bchange_time);

        timePicker.setIs24HourView(false);
        displayTime.setText(currentTime());
        changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTime.setText(currentTime());
            }
        });
    }

    private void makeOrder(){
        //final String Customer_ID = customerid.toString().trim();
        final String Cook_ID = cookid.toString().trim();
        final String Food_ID = foodid.toString().trim();
        final String Date = dataDate().toString().trim();
        final String FoodQuantity = Quantity.getText().toString().trim();
        final String Price = foodprice.toString().trim();
        final String UserComment = Comment.getText().toString().trim();

        class MakeOrder extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CreateOrder.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(CreateOrder.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_COOK_ID,Cook_ID);
                params.put(Config.KEY_FOOD_ID,Food_ID);
                params.put(Config.KEY_ORDER_DUE,Date);
                params.put(Config.KEY_ORDER_PRICE, Price);
                params.put(Config.KEY_ORDER_QUANTITY, FoodQuantity);
                params.put(Config.KEY_ORDER_COMMENT, UserComment);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_ORDER, params);
                return res;
            }
        }

        MakeOrder mo = new MakeOrder();
        mo.execute();
    }

    public void alertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateOrder.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Your Order...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to order this item?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Cash on Hand", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                makeOrder();
                startActivity(new Intent(CreateOrder.this, cookGalleryMain.class));
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNeutralButton("PayPal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Cancel booking
                startActivity(new Intent(CreateOrder.this, PayPal.class));
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Cancel booking
               dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /*@Override
    public void setOnTimeChangedListener(TimePicker.OnTimeChangedListener onTimeChangedListener)
    {

    }*/

    //Database format also includes time.
    public String dataDate() {
        StringBuilder dataDate = new StringBuilder();
        month = datePicker.getMonth() + 1;
        dataDate.append(datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth() + " " + dataTime());
        return dataDate.toString();
    }

    public String dataTime() {
        if(Build.VERSION.SDK_INT<=22)
        {
            String dataTime = (timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            return dataTime;
        }
        else
        {
            String dataTime = (timePicker.getHour() + ":" + timePicker.getMinute() + ":00");
            return dataTime;
        }
    }

    //Ui format
    public String currentDate() {
        StringBuilder mcurrentDate = new StringBuilder();
        month = datePicker.getMonth() + 1;
        mcurrentDate.append("This is your selected date: " + datePicker.getDayOfMonth() + "/" + month);
        return mcurrentDate.toString();
    }

    public String currentTime() {
        if(Build.VERSION.SDK_INT<=22) {
            String mcurrentTime = ("This is your selected Time: " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
            return mcurrentTime;
        }
        else
        {
            String mcurrentTime = ("This is your selected Time: " + timePicker.getHour() + ":" + timePicker.getMinute());
            return mcurrentTime;
        }
    }

    @Override
    public void onClick(View v) {

        if(v == buttonConfirm){
            alertDialog();
        }

        if(v == buttonCancel){
            startActivity(new Intent(CreateOrder.this,Search.class));
        }
    }
}


