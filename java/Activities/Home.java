package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;


public class Home extends Activity implements View.OnClickListener, LocationListener {

    TextView welcome; //textview to display welcome message
    ImageButton meals, workouts, progress, map; //image buttons for each individual feature
    DatabaseCenter db; //database center variable to access DB functions
    Intent intent; //intent to pas to another screen
    Bundle bundle; //bundle to wrap objects and variable up and pass in intent
    String passedUser; //string to hold the logged in users name
    User user; //user instance to hold user data
    private LocationManager locationManager; //location manager to check device location
    double longitude = 0.0, latitude = 0.0; //variables to hold co-ordinates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setUpLocationService(); //call set up location service method

        intent = getIntent(); //get the intent passed from previous activity
        bundle = intent.getExtras(); //get the extras from intent and assign to bundle
        passedUser = bundle.getString("username"); //get the logged in user from the intent

        db = new DatabaseCenter(this); //instantiate the database center object
        try {
            db.open(); //open the DB
            user = db.getUser(passedUser); //get the details of logged in user
        } catch (SQLException e) {
            e.printStackTrace();
        }

        welcome = (TextView)findViewById(R.id.HomeWelcome); //textview for welcome message
        welcome.append(" "+user.getFname()); //set the text of the welcome message

        meals = (ImageButton)findViewById(R.id.meal); //image button for meal planner
        meals.setOnClickListener(this); //set on click listener to this button

        workouts = (ImageButton)findViewById(R.id.workout); //image button for workout planner
        workouts.setOnClickListener(this); //set on click listener to this button

        progress = (ImageButton)findViewById(R.id.progress); //image button for progress tracker
        progress.setOnClickListener(this); //set on click listener to this button

        map = (ImageButton)findViewById(R.id.map); //image button for find a gym
        map.setOnClickListener(this); //set on click listener to this button
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.meal) { //if meal button clicked
            Intent mealPlanner = new Intent(this, MealPlan.class); //create new intent
            Bundle bund = new Bundle(); //create bundle
            bund.putSerializable("user", user); //put object user in bundle
            mealPlanner.putExtras(bund); //put the bundle in intent
            startActivity(mealPlanner); //start activity
        }

        if(v.getId() == R.id.workout) { //if workout button clicked
            Intent workoutPlanner = new Intent(this, WorkoutPlan.class); //create new intent
            Bundle b = new Bundle(); //create new bundle
            b.putSerializable("user", user); //put object user in bundle
            workoutPlanner.putExtras(b); //put the bundle in inetnt
            startActivity(workoutPlanner); //start the activity
        }

        if(v.getId() == R.id.progress) { //if progress button clicked
            Intent progress = new Intent(this, Progress.class); //create the intent
            startActivity(progress); //start the activity
        }

        if(v.getId() == R.id.map) //if find gym clicked
        {
            //Reference: https://developers.google.com/maps/documentation/android/intents
            Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"?z=10&q=gyms"); //parse the geo location taken from location services with a zoom of ten and query for gyms
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri); //create intent with created URi query for google maps
            mapIntent.setPackage("com.google.android.apps.maps"); //set the package to be google maps
            startActivity(mapIntent); //start activity
            //Reference End
        }
    }

    private void setUpLocationService()
    {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); //allow for location to be retrieved by device
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 5, this); //request location updates every 5 seconds or every 5 metres traveled
    }

    protected void onResume() //when the activity is resumed
    {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 5, this); //request location updates every 5 seconds or every 5 metres traveled
    }

    protected void onPause() //when activity is paused
    {
        super.onPause();
        locationManager.removeUpdates(this); //remove updates to save battery
    }

    @Override
    public void onLocationChanged(Location location) { //when location has changed
        longitude = location.getLongitude(); //update longitude
        latitude = location.getLongitude(); //update latitude
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}