package stefan.burke.mydit.ie.spotter.Activities;

import android.app.Activity;
import android.app.AlertDialog;
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

import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Classes.Workout;
import stefan.burke.mydit.ie.spotter.Database.DatabaseCenter;
import stefan.burke.mydit.ie.spotter.R;

public class WorkoutPlan extends Activity implements View.OnClickListener {

    DatabaseCenter db;
    TextView noEntry;
    ImageButton add;
    ListView list;
    User user;
    Workout w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_plan);

        db = new DatabaseCenter(this);

        user = (User) getIntent().getSerializableExtra("user");

        try
        {
            db.open();

            final Cursor allWorkouts;
            allWorkouts = db.getAllWorkouts(user.getUname());


            String[] columns = new String[]{"workout_date", "workout_time", "workout_title",
                    "exercise1", "sets1", "reps1", "weight1",
                    "exercise2", "sets2", "reps2", "weight2",
                    "exercise3", "sets3", "reps3", "weight3",
                    "exercise4", "sets4", "reps4", "weight4"};

            int[] to = new int[]{R.id.dateOfWorkout, R.id.timeOfWorkout, R.id.titleOfWorkout,
                    R.id.exercise1, R.id.sets1, R.id.reps1, R.id.weight1,
                    R.id.exercise2, R.id.sets2, R.id.reps2, R.id.weight2,
                    R.id.exercise3, R.id.sets3, R.id.reps3, R.id.weight3,
                    R.id.exercise4, R.id.sets4, R.id.reps4, R.id.weight4};


            if(!allWorkouts.moveToFirst())
            {
                noEntry = (TextView) findViewById(R.id.noEntries);
                noEntry.setText(R.string.noEntry);
                add = (ImageButton) findViewById(R.id.addEntry);
                add.setImageResource(R.drawable.add);
                add.setOnClickListener(this);
            }

            if(allWorkouts.moveToFirst())
            {
                list = (ListView)findViewById(android.R.id.list);
                final ListAdapter adapter;
                adapter = new SimpleCursorAdapter(this, R.layout.workout_row, allWorkouts, columns, to, 0);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                    {

                        w = new Workout();
                        try {
                            w = db.cursorToWorkout((Cursor) adapter.getItem(position));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //Refrence: http://stackoverflow.com/questions/12244297/how-to-add-multiple-buttons-on-a-single-alertdialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutPlan.this);
                        builder.setTitle("Edit/Delete Entry");
                        builder.setItems(new CharSequence[]
                                        {"Edit", "Delete", "Cancel"},
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // The 'which' argument contains the index position
                                        // of the selected item
                                        switch (which) {
                                            case 0:
                                                Intent i = new Intent(WorkoutPlan.this, EditWorkout.class);
                                                Bundle b = new Bundle();
                                                b.putSerializable("user", user);
                                                b.putSerializable("workout", w);
                                                i.putExtras(b);
                                                startActivityForResult(i, 1);
                                                break;

                                            case 1:
                                                try {
                                                    db.deleteWorkout(w.getId());
                                                    refresh();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            case 2:
                                                break;

                                        }
                                    }
                                });
                        builder.create().show();
                        //Reference End

                    }
                });

                add = (ImageButton)findViewById(R.id.add);
                add.setImageResource(R.drawable.add);
                add.setOnClickListener(this);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void refresh()
    {
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addEntry || v.getId() == R.id.add)
        {
            Intent i = new Intent(WorkoutPlan.this, AddWorkout.class);
            Bundle bund = new Bundle();
            bund.putSerializable("user", user);
            i.putExtras(bund);
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        refresh();
    }
}
