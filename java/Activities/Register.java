package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;

/**
 * Created by le on 23/03/2015.
 */
public class Register extends Activity implements View.OnClickListener {

    DatabaseCenter db; //create datbase center variable
    EditText fname, lname, user, pass, passConf, email, dd, mm, yyyy; //create edit texts to hold information entered by user
    RadioGroup sex; //radio group for sex select
    RadioButton selectedSex; //readio button for male or female
    Button register; //register button
    User usr; //create user variable to hold user data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = new DatabaseCenter(this); //instantiate database center variable
        try {
            db.open(); //call open DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fname = (EditText)findViewById(R.id.fname); //create edit text to hold first name
        lname = (EditText)findViewById(R.id.lname); //edit text to holf last name
        user = (EditText)findViewById(R.id.uname); //edit text to hold username
        pass = (EditText)findViewById(R.id.pword); //edit text to hold password
        passConf = (EditText)findViewById(R.id.passconf); //edit text to hold password confirm
        email = (EditText)findViewById(R.id.email); //edit text to hold email
        dd = (EditText)findViewById(R.id.day); //edit text to hold day
        mm = (EditText)findViewById(R.id.month); //edit text to hold month
        yyyy = (EditText)findViewById(R.id.year); //edit text to hold year
        sex = (RadioGroup)findViewById(R.id.radioSex); //radio group to hold radio buttons
        register = (Button)findViewById(R.id.register); //button to register
        register.setOnClickListener(this); //set on click listener to this button
    }


    @Override
    public void onClick(View v) {
        String firstName, lastName, username, password, passwordConfirm, mail, dob, sexSel; //variables to hold data entered in edit texts
        int selected = sex.getCheckedRadioButtonId(); //get the selection of the sex

        selectedSex = (RadioButton) findViewById(selected); //radio button for selected sex

        firstName = fname.getText().toString(); //convert firstname edit text to string
        lastName = lname.getText().toString(); //convert lastname edit text to string
        username = user.getText().toString(); //convert username edit text to string
        password = pass.getText().toString(); //convert password edit text to string
        passwordConfirm = passConf.getText().toString(); //convert password confirm edit text to string
        mail = email.getText().toString(); //convert email edit text to string
        dob = dd.getText().toString() + "-" + mm.getText().toString() + "-" + yyyy.getText().toString(); //converting day, month, year edit texts into a single date string
        sexSel = selectedSex.getText().toString(); //convert selected radio button to string

        if(firstName.trim().equals("")) //if firstname empty
        {
            fname.setError("First Name Required!"); //set error
        }

        if(lastName.trim().equals("")) //if lastname empty
        {
            lname.setError("Last Name Required!"); //set error
        }

        if(username.trim().equals("")) //if username empty
        {
            user.setError("Username Name Required!"); //set error
        }

        if(password.trim().equals("")) //if password empty
        {
            pass.setError("Password Required!"); //set error
        }

        if(passwordConfirm.trim().equals("")) //if password confirm empty
        {
            passConf.setError("Please Re-Enter Password!"); //set error
        }

        if(mail.trim().equals("")) //if email empty
        {
            email.setError("Email Required!"); //set error
        }


        usr = new User(firstName, lastName, username, password, mail, dob, sexSel); //create new user instance pasing in required parameters


        //Reference: http://stackoverflow.com/questions/16030806/password-validation-confirmation-in-android
        //Method to assure username is unique
        try {
            if (db.checkUsername(username)) { //if method check user returns true
                AlertDialog alert = new AlertDialog.Builder(Register.this).create(); //create alert dialog
                alert.setTitle("OOOPPPS!"); //set title
                alert.setMessage("This Username is Already in Use. Please Select Another."); //set the message
                alert.setButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //create the button
                        //dismiss alert
                    }
                });

                alert.show(); //show the alert

                user.requestFocus(); //request focus on username edit text
            }
            else
            {
                if (!password.equals(passwordConfirm)) //if password and password confirm fields do not match
                {
                    passConf.setError("Passwords do not match!"); //set error
                }

                //Reference: http://stackoverflow.com/questions/22348212/android-check-if-an-email-address-is-valid-or-not
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) //if email address is incorrect format
                {
                    //Reference End
                    email.setError("Invalid Email Address!"); //set error
                }

                else
                {
                    db.insertUser(usr); //insert the user to DB

                    Toast.makeText(this, "Account Successfully Created, You May Now Log In.", Toast.LENGTH_LONG).show(); //make a toast to acknowledge entry

                    Intent i = new Intent(this, MainActivity.class); //create intent
                    startActivity(i); //start activity
                }
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
