package stefan.burke.mydit.ie.spotter.Classes;

import java.io.Serializable;

/**
 * Created by le on 04/04/2015.
 */
public class Meal implements Serializable {

    private int id;
    private String username;
    private String entryDate;
    private String title;
    private String food;
    private String amount;
    private String time;

    public Meal()
    {

    }

    public Meal(String uname, String eDate, String titleEnt, String foodEnt, String amountEnt, String timeEnt)
    {
        this();
        this.setUsername(uname);
        this.setEntryDate(eDate);
        this.setTitle(titleEnt);
        this.setFood(foodEnt);
        this.setAmount(amountEnt);
        this.setTime(timeEnt);
    }

    public int getId() { return id; }

    public void setId(int ID) { this.id = ID; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
