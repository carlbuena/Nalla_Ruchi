package ps1623.nalla_ruchi;

/**
 * Created by Prageeth on 10/17/2016.
 */

public class cookGallery {

    //Data Variables
    private int cookID;
    private String imageUrl;
    private String firstname;
    private String surname;


    public int getcookID() {
        return cookID;
    }

    public void setCookID(int name)
    {
        this.cookID = name;
    }



    //Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String fname) {
        this.firstname = fname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String sname) {
        this.surname = sname;
    }
}