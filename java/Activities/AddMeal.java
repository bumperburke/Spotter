package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.Meal;
import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;

public class AddMeal extends Activity implements View.OnClickListener {

    EditText dd, mm, yyyy, title, food, amount, min, hrs; //Edit text variables for meal to add
    Spinner amPm, metric; //spinner for time and metric selection
    ImageButton add; //image button for adding
    Meal meal; //create instance of Meal class to hold meal data
    User user; //create instance of User class to hold user data
    DatabaseCenter db; //create instance of DatabaseCenter to access all DB functions
    Intent i; //Create an intent for switching activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal);

        db = new DatabaseCenter(this); //instantiate db variable passing in context
        try {
            db.open(); //open the DB
        } catch (SQLException e) {
            e.printStackTrace(); //print stack trace if error occurs
        }

        i = getIntent(); //get passed intent from previous activity and assign to i
        user = (User)i.getSerializableExtra("user"); //get extra from passed intent and cast and assign to user

        dd = (EditText)findViewById(R.id.day); //edit text for day
        mm = (EditText)findViewById(R.id.month); //edit text for month
        yyyy = (EditText)findViewById(R.id.year); //edit text for year
        title = (EditText)findViewById(R.id.titleEnt); //edit text for title of meal
        food = (EditText)findViewById(R.id.foodEnt); //edit text for type of food
        amount = (EditText)findViewById(R.id.amountEnt); //edit text for amount
        metric = (Spinner)findViewById(R.id.metricSel); //spinner for weight of food
        min = (EditText)findViewById(R.id.minEnt); //edit text for minutes in time
        hrs = (EditText)findViewById(R.id.hrsEnt); //edit text for hours in time
        amPm = (Spinner)findViewById(R.id.amPmSel); //spinner for am or pm
        add = (ImageButton)findViewById(R.id.addEntry); //image button to add meal
        add.setOnClickListener(this); //set onclick listener on add button
    }

    @Override
    public void onClick(View v) {
        String date, titleEntered, foodEntered, amountEntered, time; //variables to store info retrieved from edit text fields above

        date = dd.getText().toString()+"-"+mm.getText().toString()+"-"+yyyy.getText().toString(); //converting day, month, year edit texts into a single date string
        titleEntered = title.getText().toString(); //converting title edit text to string
        foodEntered = food.getText().toString(); //converting food edit text to string
        amountEntered = amount.getText().toString()+" "+metric.getSelectedItem().toString(); //converting amount edit text and metric spinner into one weight string
        time = hrs.getText().toString()+":"+min.getText().toString()+" "+amPm.getSelectedItem().toString(); //converting minutes and hours edit text and am/pm spinner into single string

        meal = new Meal(user.getUname(), date, titleEntered, foodEntered, amountEntered, time); //instantiating meal instance with variables required by constructor

        try {
            db.insertMeal(meal); //call insert meal method in Database Center passing in meal object
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(AddMeal.this, "Entry Added: "+titleEntered, Toast.LENGTH_SHORT).show(); //make a toast to acknowledge entry to DB

        setResult(RESULT_OK, i); //set result to ok and pass the intent i back to MealPlan.java to let it know the activity is done and worked
        finish(); //finish this activity
    }
}
