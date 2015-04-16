package stefan.burke.mydit.ie.spotter.Activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.Meal;
import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;


public class MealPlan extends ListActivity implements View.OnClickListener {

    DatabaseCenter db; //create database center variable
    TextView noEntry; //create text view if no entries
    ImageButton add; //create image button for add
    ListView list; //create list view to insert added meals
    User user; //create user variable to hold user data
    Meal m; //create meal variable to hold meal data

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan);

        user = (User) getIntent().getSerializableExtra("user"); //get the object from intent

        db = new DatabaseCenter(this); //instantiate database center

        try {

            db.open(); //open DB
            final Cursor allMeals; //create cursor to retrieve meals from DB

            allMeals = db.getAllMeals(user.getUname()); //get all meals from database passing in username


            String[] columns = new String[]{"entry_date", "title", "food", "amount", "entry_time"}; //columns to get the data from

            int[] to = new int[]{R.id.retDate, R.id.retTitle, R.id.retFood, R.id.retAmount, R.id.retTime}; //columns to map the data to


            if(!allMeals.moveToFirst()) //if cursor returns null
            {
                noEntry = (TextView) findViewById(R.id.noEntries); //create text view for no entries
                noEntry.setText(R.string.noEntry); //set the text
                add = (ImageButton) findViewById(R.id.addEntry); //create image for add button
                add.setImageResource(R.drawable.add); //set the image resource
                add.setOnClickListener(this); //add on click listener to this button
            }

            if(allMeals.moveToFirst()) //if cursor has data
            {
                list = (ListView)findViewById(android.R.id.list); //create list view to hold meals
                final ListAdapter adapter; //create list adapter
                adapter = new SimpleCursorAdapter(this, R.layout.meal_row, allMeals, columns, to, 0); //create new simple cursor adapter to map the data and use a row layout
                list.setAdapter(adapter); //set the adapter on list

                list.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                    { //if an item in the list is clicked

                        m = new Meal(); //create new meal
                        try {
                            m = db.cursorToMeal((Cursor) adapter.getItem(position)); //convert the clicked on item to a meal
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //Refrence: http://stackoverflow.com/questions/12244297/how-to-add-multiple-buttons-on-a-single-alertdialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(MealPlan.this); //create an alert box
                        builder.setTitle("Edit/Delete Entry"); //set the title
                        builder.setItems(new CharSequence[]
                                        {"Edit", "Delete", "Cancel"}, //create the buttons
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // The 'which' argument contains the index position
                                        // of the selected item
                                        switch (which) {
                                            case 0: //if edit clicked
                                                Intent i = new Intent(MealPlan.this, EditMeal.class); //create new intent
                                                Bundle b = new Bundle(); //create new bundle
                                                b.putSerializable("user", user); //put object user in bundle
                                                b.putSerializable("meal", m); //put object meal in bundle
                                                i.putExtras(b); //put bundle in intent
                                                startActivityForResult(i, 1); //start activity and await result
                                                break; //exit

                                            case 1: //if delete pressed
                                                try {
                                                    db.deleteMeal(m.getId()); //delete the clicked on item
                                                    refresh(); //refresh the activity to show change
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                break; //exit

                                            case 2: //if cancel clicked
                                                break; //exit

                                        }
                                    }
                                });
                        builder.create().show(); //show the alert
                        //Reference End

                    }
                });

                add = (ImageButton)findViewById(R.id.add); //create the add button
                add.setImageResource(R.drawable.add); //set the resource of image
                add.setOnClickListener(this); //set onclick listener on this button
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void refresh() //method to refresh screen
    {
        finish(); //finish this activity
        startActivity(getIntent()); //get the activity again
    }


    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.addEntry || v.getId() == R.id.add) //if add is clicked when there are/are not entries
        {
            Intent i = new Intent(MealPlan.this, AddMeal.class); //create intent
            Bundle bund = new Bundle(); //create bundle
            bund.putSerializable("user", user); //put object user in bundle
            i.putExtras(bund); //put bundle in intent
            startActivityForResult(i, 1); //start activity and await result
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //when the activity is returned a result
        super.onActivityResult(requestCode, resultCode, data); //call super class method

        refresh(); //refresh the screen
    }
}
