package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Classes.Workout;
import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;

public class EditWorkout extends Activity implements View.OnClickListener {

    EditText dd, mm, yyyy, title, min, hrs; //Edit text variables for workout to add
    EditText  exercise1, sets1, reps1, weight1, exercise2, sets2, reps2, weight2, exercise3, sets3, reps3, weight3, exercise4, sets4, reps4, weight4;
    //Above are the edit texts required to take in each detail of each exercise.
    Spinner amPm, metric1, metric2, metric3, metric4; //Spinners for am/pm selection and also for metric of weight for each exercise
    ImageButton add; //image button for adding
    User user; //Create instance of user class to hold user information
    Workout workout, passedWorkout; //Create instance of workout class to hold workout information and passedWorkout to hold the clicked on item
    DatabaseCenter db; //create instance of DatabaseCenter to access all DB functions
    TextView header; //text view for header of class
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_workout);

        db = new DatabaseCenter(this); //instantiate db variable passing in context
        try {
            db.open(); //open the DB
        } catch (SQLException e) {
            e.printStackTrace(); //print stack trace if error occurs
        }

        i = getIntent(); //get the passed intent
        user = (User) i.getSerializableExtra("user"); //get the passed object from the intent passed from previous activity

        passedWorkout = new Workout(); //instantiate passedworkout
        passedWorkout = (Workout)getIntent().getSerializableExtra("workout"); //get the passed workout from the intent

        header = (TextView)findViewById(R.id.editWorkout); //text view for the header
        header.setText(R.string.editEntry+" "+passedWorkout.getTitle()); //set the text of the header to include the name of clicked on item

        dd = (EditText)findViewById(R.id.workoutDay); //edit text for day
        mm = (EditText)findViewById(R.id.workoutMonth); //edit text for month
        yyyy = (EditText)findViewById(R.id.workoutYear); //edit text for year
        hrs = (EditText)findViewById(R.id.workoutHrsEnt); //edit text for hours of time
        min = (EditText)findViewById(R.id.workoutMinEnt); //edit text for minutes of time
        amPm = (Spinner)findViewById(R.id.workoutAmPmSel); //Spinner for am/pm selection
        title = (EditText)findViewById(R.id.workoutTitleAdd); //edit text for title of workout

        exercise1 = (EditText)findViewById(R.id.exerciseAdd1); //edit text for exercise1
        sets1 = (EditText)findViewById(R.id.setsAdd1); //edit text for number of sets
        reps1 = (EditText)findViewById(R.id.repsAdd1); //edit text for number of repititions
        weight1 = (EditText)findViewById(R.id.weightAdd1); //edit text for weight of sets
        metric1 = (Spinner)findViewById(R.id.metricSelect1); //edit text for metric of weight

        exercise2 = (EditText)findViewById(R.id.exerciseAdd2); //edit text for exercise1
        sets2 = (EditText)findViewById(R.id.setsAdd2); //edit text for number of sets
        reps2 = (EditText)findViewById(R.id.repsAdd2); //edit text for number of repititions
        weight2 = (EditText)findViewById(R.id.weightAdd2); //edit text for weight of sets
        metric2 = (Spinner)findViewById(R.id.metricSelect2); //edit text for metric of weight

        exercise3 = (EditText)findViewById(R.id.exerciseAdd3); //edit text for exercise1
        sets3 = (EditText)findViewById(R.id.setsAdd3); //edit text for number of sets
        reps3 = (EditText)findViewById(R.id.repsAdd3); //edit text for number of repititions
        weight3 = (EditText)findViewById(R.id.weightAdd3); //edit text for weight of sets
        metric3 = (Spinner)findViewById(R.id.metricSelect3); //edit text for metric of weight

        exercise4 = (EditText)findViewById(R.id.exerciseAdd4); //edit text for exercise1
        sets4 = (EditText)findViewById(R.id.setsAdd4); //edit text for number of sets
        reps4 = (EditText)findViewById(R.id.repsAdd4); //edit text for number of repititions
        weight4 = (EditText)findViewById(R.id.weightAdd4); //edit text for weight of sets
        metric4 = (Spinner)findViewById(R.id.metricSelect4); //edit text for metric of weight
        add = (ImageButton)findViewById(R.id.addEntry); //Image button for adding workout to DB
        add.setOnClickListener(this); //set on click listener to add button
    }


    @Override
    public void onClick(View v) {
        String date, time, titleEnt; //variables to hold workout information
        String[] exercises, weights; //variable arrays to hold the data for ecercises and weights
        int[] sets, reps; //variable arrays to hold the data for sets and reps

        date = dd.getText().toString()+"-"+mm.getText().toString()+"-"+yyyy.getText().toString(); //converting day, month, year edit texts into a single date string
        time = hrs.getText().toString()+":"+min.getText().toString()+" "+amPm.getSelectedItem().toString(); //converting minutes and hours edit text and am/pm spinner into single string
        titleEnt = title.getText().toString();  //converting title edit text to string
        exercises = new String[]{exercise1.getText().toString(), exercise2.getText().toString(), exercise3.getText().toString(), exercise4.getText().toString(),}; //converting all exercise edit texts to a string array
        sets = new int[]{Integer.parseInt(sets1.getText().toString()), Integer.parseInt(sets2.getText().toString()), Integer.parseInt(sets3.getText().toString()), Integer.parseInt(sets4.getText().toString())}; //converting all sets edit texts to an integer array
        reps = new int[]{Integer.parseInt(reps1.getText().toString()), Integer.parseInt(reps2.getText().toString()), Integer.parseInt(reps3.getText().toString()), Integer.parseInt(reps4.getText().toString())}; //converting all reps edit texts to an integer array
        weights = new String[]{weight1.getText().toString(), weight2.getText().toString(), weight3.getText().toString(), weight4.getText().toString(),}; //converting all weight edit texts to a string array

        workout = new Workout(user.getUname(), date, time, titleEnt, exercises, sets, reps, weights); //instantiating workout passing in the variables required by constructor

        try {
            db.updateWorkout(workout, passedWorkout.getId()); //call update workout method in database center, passing in the new values and id of old workout
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(EditWorkout.this, "Entry: " + titleEnt + " Updated.", Toast.LENGTH_SHORT).show(); //make toats to acknowledge update

        setResult(RESULT_OK, i); //set result to ok and pass the intent i back to MealPlan.java to let it know the activity is done and worked
        finish(); //finish this activity
    }
}