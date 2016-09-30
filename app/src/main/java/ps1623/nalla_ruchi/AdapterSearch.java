package ps1623.nalla_ruchi;

/**
 * Created by Carl on 29/09/2016.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        View view=inflater.inflate(R.layout.container_search, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Data current=data.get(position);
        myHolder.textFoodName.setText(current.foodName);
        myHolder.textFoodDescription.setText(current.foodDescription);
        myHolder.textEthnicity.setText("Ethnicity: " + current.foodEthnicity);
        myHolder.textType.setText("Ethnicity: " + current.foodType);
        myHolder.textDishType.setText("Dish Type: " + current.dishType);
        myHolder.textPrice.setText("Price: " + current.foodPrice);
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textFoodName;
        TextView textFoodDescription;
        TextView textEthnicity;
        TextView textType;
        TextView textDishType;
        TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textFoodName= (TextView) itemView.findViewById(R.id.textfoodName);
            textFoodDescription= (TextView) itemView.findViewById(R.id.textfoodDescription);
            textEthnicity = (TextView) itemView.findViewById(R.id.textfoodEthnicity);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textDishType = (TextView) itemView.findViewById(R.id.textDishType);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

            Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();

        }

    }

}

