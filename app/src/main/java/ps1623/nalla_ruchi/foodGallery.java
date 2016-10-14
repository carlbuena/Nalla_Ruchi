package ps1623.nalla_ruchi;


/**
 * Created by Belal on 12/5/2015.
 */

public class foodGallery {

    //Data Variables
    private int foodID;
    private String imageUrl;
    private String foodname;
    private String dishtype;

    //Getters and Setters
    public int getfoodID() {
        return foodID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return foodname;
    }

    public void setfoodID(int name) {
        this.foodID = name;
    }

    public void setName(String name) {
        this.foodname = name;
    }

    public String getPublisher() {
        return dishtype;
    }

    public void setPublisher(String publisher) {
        this.dishtype = publisher;
    }
}