package ps1623.nalla_ruchi;

/**
 * Created by Prageeth on 10/17/2016.
 */


        import android.content.Context;
        import android.media.Image;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.android.volley.toolbox.ImageLoader;
        import com.android.volley.toolbox.NetworkImageView;

        import org.w3c.dom.Text;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Belal on 11/9/2015.
 */
public class CardAdapter_Cook extends RecyclerView.Adapter<CardAdapter_Cook.ViewHolder> {

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all cooks
    List<cookGallery> cookGalleries;

    //Constructor of this class
    public CardAdapter_Cook(List<cookGallery> cookGalleries, Context context){
        super();
        //Getting all cooks
        this.cookGalleries = cookGalleries;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cook_images_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        cookGallery cookGallery = cookGalleries.get(position);

        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(cookGallery.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.drawable.image, android.R.drawable.ic_dialog_alert));

        //Showing data on the views
        holder.imageView.setImageUrl(cookGallery.getImageUrl(), imageLoader);
        holder.textViewFirstName.setText(cookGallery.getFirstName());
        holder.textViewSurname.setText(cookGallery.getSurname());

    }

    @Override
    public int getItemCount() {
        return cookGalleries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public NetworkImageView imageView;
        public TextView textViewFirstName;
        public TextView textViewSurname;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageCookG);
            textViewFirstName = (TextView) itemView.findViewById(R.id.textCookFirstNameG);
            textViewSurname = (TextView) itemView.findViewById(R.id.textCookSurnameG);
        }
    }
}