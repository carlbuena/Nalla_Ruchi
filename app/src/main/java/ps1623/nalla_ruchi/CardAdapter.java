package ps1623.nalla_ruchi;


        import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Prageeth on 11/9/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<foodGallery> foodGalleries;

    //Constructor of this class
    public CardAdapter(List<foodGallery> foodGalleries, Context context){
        super();
        //Getting all superheroes
        this.foodGalleries = foodGalleries;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        foodGallery foodGallery =  foodGalleries.get(position);

        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(foodGallery.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.drawable.image, android.R.drawable.ic_dialog_alert));

        //Showing data on the views
        holder.imageView.setImageUrl(foodGallery.getImageUrl(), imageLoader);
        holder.textViewFoodName.setText(foodGallery.getName());
        holder.textViewFoodType.setText(foodGallery.getPublisher());

    }

    @Override
    public int getItemCount() {
        return foodGalleries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public NetworkImageView imageView;
        public TextView textViewFoodName;
        public TextView textViewFoodType;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageFoodG);
            textViewFoodName = (TextView) itemView.findViewById(R.id.textViewFoodNameG);
            textViewFoodType = (TextView) itemView.findViewById(R.id.textViewFoodTypeG);
        }
    }
}