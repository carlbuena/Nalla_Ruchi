package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/09/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Data> data= Collections.emptyList();
    Data current;

    // create constructor to initialize context and data sent from MainActivity
    public AdapterSearch(Context context, List<Data> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_search, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Data current = data.get(position);
        myHolder.textCookID.setText(current.cookID);
        myHolder.textCookName.setText("Cook Name: " + current.cookFirstName + " " + current.cookSurname);
        myHolder.textFoodID.setText(current.foodID);
        myHolder.textFoodName.setText("Food Name: " + current.foodName);
        myHolder.textFoodDescription.setText("Description: " + current.foodDescription);
        myHolder.textEthnicity.setText("Ethnicity: " + current.foodEthnicity);
        myHolder.textType.setText("Type: " + current.foodType);
        myHolder.textDishType.setText("Dish Type: " + current.dishType);
        myHolder.textPrice.setText(current.foodPrice);
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Views
        TextView textCookID;
        TextView textCookName;
        TextView textFoodID;
        TextView textFoodName;
        TextView textFoodDescription;
        TextView textEthnicity;
        TextView textType;
        TextView textDishType;
        TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textFoodID= (TextView) itemView.findViewById(R.id.textfoodID);
            textCookID= (TextView) itemView.findViewById(R.id.textcookID);
            textCookName= (TextView) itemView.findViewById(R.id.textcookName);
            textFoodName= (TextView) itemView.findViewById(R.id.textfoodName);
            textFoodDescription= (TextView) itemView.findViewById(R.id.textfoodDescription);
            textEthnicity = (TextView) itemView.findViewById(R.id.textfoodEthnicity);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textDishType = (TextView) itemView.findViewById(R.id.textDishType);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), CreateOrder.class);

            String cookid = textCookID.getText().toString();
            String cookname = textCookName.getText().toString();
            String foodid = textFoodID.getText().toString();
            String foodname = textFoodName.getText().toString();
            String foodprice = textPrice.getText().toString();
            String fooddes = textFoodDescription.getText().toString();
            String foodeth = textEthnicity.getText().toString();
            String foodtype = textType.getText().toString();
            String fooddish = textDishType.getText().toString();

            Bundle extras = new Bundle();

            extras.putString(Config.COOK_ID, cookid);
            extras.putString(Config.COOK_FIRSTNAME,cookname);
            extras.putString(Config.FOOD_ID, foodid);
            extras.putString(Config.FOOD_NAME,foodname);
            extras.putString(Config.FOOD_PRICE,foodprice);
            extras.putString(Config.FOOD_DES,fooddes);
            extras.putString(Config.FOOD_ETH,foodeth);
            extras.putString(Config.FOOD_TYPE,foodtype);
            extras.putString(Config.FOOD_DISH,fooddish);

            intent.putExtras(extras);

            context.startActivity(intent);

        }
    }

    }



