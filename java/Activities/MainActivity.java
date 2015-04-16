package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;


public class MainActivity extends Activity implements View.OnClickListener {

    ImageView logo; //image view for app logo
    TextView title; //text view for title
    EditText user, pass; //edit text for log in credentials
    Button login, register; //buttons for login and register
    DatabaseCenter db; //database center instance to access DB methods and functions


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseCenter(this); //instantiate datbase center
        try {
            db.open(); //open DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

        title = (TextView)findViewById(R.id.title); //create title
        logo = (ImageView)findViewById(R.id.logo); //create logo
        user = (EditText)findViewById(R.id.uname); //create edit text for username
        pass = (EditText)findViewById(R.id.pword); //create edit text for password

        login = (Button)findViewById(R.id.log); //create button for login
        login.setOnClickListener(this); //set on click listener on this button

        register = (Button)findViewById(R.id.register); //create button for registering
        register.setOnClickListener(this); //set on click listener for this button
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.log) //if login is clicked
        {
            final String username, password; //create variables to hold credentils
            username = user.getText().toString(); //retrieve username from edit text
            password = pass.getText().toString(); //retrieve password from edit text

            if(username.trim().length() == 0) //if no username entered
            {
                user.setError("Please Enter Username!"); //set error message
            }

            else if(password.trim().length() == 0) //if no password entered
            {
                pass.setError("Please Enter Password!"); //set error message
            }

            else //if information added
            {
                launchRingDialog(v); //launch the progress dialog

                try
                {
                    if (db.loginValidate(username, password)) //if the credentiials are correct
                    {
                        //Reference: http://stackoverflow.com/questions/17237287/how-can-i-wait-10-second-without-locking-ui-in-android
                        Handler handler = new Handler(); //create a handler to handle the progress dialog
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 5 seconds
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show(); //make toast to acknowledge login successful
                                Intent i = new Intent(MainActivity.this, Home.class); //create new intent
                                Bundle extras = new Bundle(); //create bundle
                                extras.putString("username", username); //put the username in bundle
                                i.putExtras(extras); //put bundle in intent
                                startActivity(i); //start new activity
                            }
                        }, 3000); //wait 3 seconds

                    }

                    else //if the login credentials are wrong
                    {
                        Handler handler = new Handler(); //create handler to handle alert
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create(); //create an alert box
                                alert.setTitle("Incorrect Login Credentials"); //set the title of alert
                                alert.setMessage("Please Try Again."); //set the message of alert
                                alert.setButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { //create ok button
                                        //dismiss alert
                                    }
                                });

                                alert.show(); //show the alert
                            }
                        }, 5000); //dissapear after 5 seconds if not clicked

                        //Reference End
                        user.requestFocus();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if(v.getId() == R.id.register) //if register is clicked
        {
            Intent reg = new Intent(this, Register.class); //create intent
            startActivity(reg); //start new activity
        }
    }

    //Reference: http://examples.javacodegeeks.com/android/core/ui/progressdialog/android-progressdialog-example/
    public void launchRingDialog(View view) {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this,
                "Please wait ...", "Authenticating Login Credentials ...", true); //creates a ring process diaglog with the message given
        ringProgressDialog.setCancelable(true); //allows it to be canceled by touching the screen
        new Thread(new Runnable()
        {
            @Override
            public void run()
            { //creates a new thread to run the process on
                try
                {
                    // Let the progress ring for 3 seconds...
                    Thread.sleep(3000);

                   /*
                    * Initial plan was to have DB set up on Raspberry Pi and retrieve data from there but I had not got the time
                    * to learn all the rapberry pi configuration. This is why I have the progress ring. Decided to leave it in
                    * because it looks good
                    */
                } catch (Exception e) {}

                ringProgressDialog.dismiss(); //dismiss after sleep
            }
        }).start(); //start thread
        //Reference End
    }
}
