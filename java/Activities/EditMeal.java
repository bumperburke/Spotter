package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.Meal;
import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;

public class EditMeal extends Activity implements View.OnClickListener {

    TextView header; //text view for header
    EditText dd, mm, yyyy, title, food, amount, min, hrs; //edit text to hold the data for a meal
    Spinner amPm, metric; //spinners for the am/pm selection and metric selection
    Button update; //button to update entry
    Meal meal, passedMeal; //Create objects of Meal class to hole the new meal and passed meal from intent
    User user; //create a User object to hold user data
    DatabaseCenter db; //create DatabaseCenter object to perform operations on DB
    Intent i; //create an intent to retrieve objects from intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meal);

        db = new DatabaseCenter(this); //instantiate db
        try {
            db.open(); //open the DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

        i = getIntent(); //get the passed intent
        user = (User)i.getSerializableExtra("user"); //get the object from intent and assign to user

        passedMeal = new Meal(); //instantiate a new meal
        passedMeal = (Meal)getIntent().getSerializableExtra("meal"); //get the object from intent and assign it to passed meal

        header = (TextView)findViewById(R.id.mealEdit); //header text view
        header.setText(R.string.editEntry+" "+passedMeal.getTitle()); //set the text of header

        dd = (EditText)findViewById(R.id.editDay); //edit text for the day
        mm = (EditText)findViewById(R.id.editMonth); //edit text for the month
        yyyy = (EditText)findViewById(R.id.editYear); //edit text for the year
        title = (EditText)findViewById(R.id.editTitleEnt); //edit text for the meal title
        food = (EditText)findViewById(R.id.editFoodEnt); //edit text for the food type
        amount = (EditText)findViewById(R.id.editAmountEnt); //edit text for the amount
        metric = (Spinner)findViewById(R.id.editMetricSel); //spinner for the metric
        min = (EditText)findViewById(R.id.editMinEnt); //edit text for the minutes
        hrs = (EditText)findViewById(R.id.editHrsEnt); //edit text for the hours
        amPm = (Spinner)findViewById(R.id.editAmPmSel); //spinner for the am/pm selection
        update = (Button)findViewById(R.id.editEntry); //button for the update
        update.setOnClickListener(this); //set on click listener to the button
    }

    @Override
    public void onClick(View v) {
        String date, titleEntered, foodEntered, amountEntered, time; //variables to hold the data for the meal gotten from edit texts above

        date = dd.getText().toString()+"-"+mm.getText().toString()+"-"+yyyy.getText().toString(); //converting day, month, year edit texts into a single date string
        titleEntered = title.getText().toString(); //converting title edit text to string
        foodEntered = food.getText().toString(); //converting food edit text to string
        amountEntered = amount.getText().toString()+" "+metric.getSelectedItem().toString(); //converting amount edit text and metric spinner into one weight string
        time = hrs.getText().toString()+":"+min.getText().toString()+" "+amPm.getSelectedItem().toString(); //converting minutes and hours edit text and am/pm spinner into single string

        meal = new Meal(user.getUname(), date, titleEntered, foodEntered, amountEntered, time); //instantiating meal instance with variables required by constructor

        try {
            db.updateMeal(meal, passedMeal.getId()); //call method update meal in database center, passing the new values and id of old meal
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(EditMeal.this, "Entry: "+passedMeal.getTitle()+" Updated.", Toast.LENGTH_SHORT).show(); //make toast to acknowledge update


        setResult(RESULT_OK, i); //set result to ok and pass the intent i back to MealPlan.java to let it know the activity is done and worked
        finish(); //finish this activity
    }
}