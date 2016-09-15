package ps1623.nalla_ruchi;

/**
 * Created by Carl on 15/09/16.
 */
public class Category {

    private int id;
    private String name;
    private String surname;

    public Category(){}

    public Category(int id, String name, String surname){
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

}

