package stefan.burke.mydit.ie.spotter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

import stefan.burke.mydit.ie.spotter.Classes.Meal;
import stefan.burke.mydit.ie.spotter.Classes.User;
import stefan.burke.mydit.ie.spotter.Classes.Workout;

public class DatabaseCenter {

    // User Table
    public static final String DATABASE_TABLE_USER = "users"; //create variable for table name
    public static final String KEY_ROWID = "_id"; //create variable for table column id
    public static final String KEY_FNAME = "first_name"; //create variable for table column first name
    public static final String KEY_LNAME = "last_name"; //create variable for table column last name
    public static final String KEY_USERNAME = "username"; //create variable for table column username
    public static final String KEY_PASSWORD = "password"; //create variable for table column password
    public static final String KEY_EMAIL = "email"; //create variable for table column email
    public static final String KEY_DOB = "date_of_birth"; //create variable for table column date of birth
    public static final String KEY_SEX = "sex"; //create variable for table column sex

    // Meal Plan Table
    public static final String DATABASE_TABLE_MEALPLAN = "meals"; //create variable for table name
    public static final String KEY_MEALID = "_id"; //create variable for table column id
    public static final String KEY_UNAME = "username"; //create variable for table column username
    public static final String KEY_DATE = "entry_date"; //create variable for table column date
    public static final String KEY_TITLE = "title"; //create variable for table column title
    public static final String KEY_FOOD = "food"; //create variable for table column food
    public static final String KEY_AMOUNT = "amount"; //create variable for table column amount
    public static final String KEY_TIME = "entry_time"; //create variable for table column entry time

    // Workout Plan Table
    public static final String DATABASE_TABLE_WORKOUTS = "workouts"; //create variable for table column
    public static final String KEY_WORKOUTID = "_id"; //create variable for table column id
    public static final String KEY_WORKOUTUNAME = "username"; //create variable for table column username
    public static final String KEY_WORKOUTDATE = "workout_date"; //create variable for table column workout date
    public static final String KEY_WORKOUTTIME = "workout_time"; //create variable for table column workout time
    public static final String KEY_WORKOUTTITLE = "workout_title"; //create variable for table column workout title
    public static final String KEY_WORKOUTEXERCISE1 = "exercise1"; //create variable for table column exercise1
    public static final String KEY_SETS1 = "sets1"; //create variable for table column sets 1
    public static final String KEY_REPS1 = "reps1"; //create variable for table column reps 1
    public static final String KEY_WEIGHT1 = "weight1"; //create variable for table column weight 1
    public static final String KEY_WORKOUTEXERCISE2 = "exercise2"; //create variable for table column exercise 2
    public static final String KEY_SETS2 = "sets2"; //create variable for table column sets 2
    public static final String KEY_REPS2 = "reps2"; //create variable for table column reps 2
    public static final String KEY_WEIGHT2 = "weight2"; //create variable for table column weight 2
    public static final String KEY_WORKOUTEXERCISE3 = "exercise3"; //create variable for table column exercise 3
    public static final String KEY_SETS3 = "sets3"; //create variable for table column sets 3
    public static final String KEY_REPS3 = "reps3"; //create variable for table column reps 3
    public static final String KEY_WEIGHT3 = "weight3"; //create variable for table column weight 3
    public static final String KEY_WORKOUTEXERCISE4 = "exercise4"; //create variable for table column exercise 4
    public static final String KEY_SETS4 = "sets4"; //create variable for table column sets 4
    public static final String KEY_REPS4 = "reps4"; //create variable for table column reps 4
    public static final String KEY_WEIGHT4 = "weight4"; //create variable for table column weight 4


    public static final String DATABASE_NAME = "spotter"; //create variable to hold database name
    public static final int DATABASE_VERSION = 3; //create variable to hold database version

    //Below is string containing the query to create table user with the following attributes and their type and wether they can be null
    public static final String DATABASE_CREATE_USER =
            "create table users (_id integer primary key autoincrement, " +
                    "first_name text not null, " +
                    "last_name text not null, " +
                    "username text not null, " +
                    "password text not null, " +
                    "email text not null, " +
                    "date_of_birth text not null, " +
                    "sex text not null)";

    //Below is string containing the query to create table meals with the following attributes and their type and wether they can be null
    public static final String DATABASE_CREATE_MEALPLAN =
            "create table meals (_id integer primary key autoincrement, " +
                    "username text not null, " +
                    "entry_date text not null, " +
                    "title text not null, " +
                    "food text not null, " +
                    "amount text not null, " +
                    "entry_time text not null)";

    //Below is string containing the query to create table workouts with the following attributes and their type and wether they can be null
    public static final String DATABASE_CREATE_WORKOUTS =
            "create table workouts (_id integer primary key autoincrement, " +
                    "username text not null, " +
                    "workout_date text not null, " +
                    "workout_time text not null, " +
                    "workout_title text not null, " +
                    "exercise1 text not null, " +
                    "sets1 integer not null, " +
                    "reps1 integer not null, " +
                    "weight1 text not null, " +
                    "exercise2 text null, " +
                    "sets2 integer null, " +
                    "reps2 integer null, " +
                    "weight2 text null, " +
                    "exercise3 text null, " +
                    "sets3 integer null, " +
                    "reps3 integer null, " +
                    "weight3 text null, " +
                    "exercise4 text null, " +
                    "sets4 integer null, " +
                    "reps4 integer null, " +
                    "weight4 text null)";

    private final Context context; //create context variable
    private DatabaseMain helper; //create database helper variable
    private SQLiteDatabase db; //create SQLite Db variable

    public DatabaseCenter(Context ctx) //constructor for Database Center
    {
        this.context = ctx; //give the context of where the database helper is being called from
        helper = new DatabaseMain(context); //create new instance of database helper
    }

    private static class DatabaseMain extends SQLiteOpenHelper
    {
        DatabaseMain(Context context) //constructor of database main taking in context
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION); //calling super class constructor
        }

        //Method below ensures that when the class is first ran it will create the tables
        public void onCreate(SQLiteDatabase db)
        {
           db.execSQL(DATABASE_CREATE_USER); //create table
           db.execSQL(DATABASE_CREATE_MEALPLAN); //create table
           db.execSQL(DATABASE_CREATE_WORKOUTS); //create table
        }

        //Below is the on upgrade method, it will detect any change in the DB version and drop the tables and recreate them
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_MEALPLAN);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_WORKOUTS);

            // create new tables
            onCreate(db);
        }

    }

    //Below is the method to open the database
    public DatabaseCenter open() throws SQLException
    {
        db = helper.getWritableDatabase();
        return this;
    }

    //below is the method to close the database
    public void close()
    {
        helper.close();
    }

    //below is the method to delete a table from the DB
    public void deleteTable(String table) throws SQLException
    {
        String query = "DROP TABLE " +table;
        db.execSQL(query);
    }

    //USER TABLE FUNCTIONS
    //below is the method to get a user base on their username
    public User getUser(String username) throws SQLException
    {
        //select query to get all user info where the username passed matches username in DB
        String query = "SELECT _id, first_name, last_name, username, password, email, date_of_birth, sex FROM users WHERE username = '"+username+"'";
        Cursor mCursor = db.rawQuery(query, null); //execute query and return to cursor

        if(mCursor != null) //if the cursor is not null
        {
            mCursor.moveToFirst(); //move to first result
        }

        User user = cursorToUser(mCursor); //convert the cursor to type User

        return user; //return user
    }

    //Below is the method to check if a username is duplicated
    public boolean checkUsername(String uname) throws SQLException
    {
        //Select query to retrieve username that matches username passed in
        String selectQuery = "SELECT _id, username FROM users WHERE username = '"+uname+"'";
        Cursor uChk = db.rawQuery(selectQuery, null); //execute query and return to cursor

        if(uChk.moveToFirst()) //if cursor retrieves an entry
        {
            return true; //return true
        }

        else
            return false; //return false
    }

    //below is method to validate a user logging in, it check if the user exists in the DB
    public boolean loginValidate(String uname, String pass) throws SQLException
    {
        //select query to get the matching names in DB as passed in
        String selectQuery = "SELECT _id, username, password FROM users WHERE username = '"+uname+"' AND password = '"+pass+"'";
        Cursor cursor = db.rawQuery(selectQuery, null); //execute query and pass to cursor

        if(cursor.moveToFirst()) //if user found
        {
            return true; //return true
        }

        else
            return false; //return false
    }

    //below is the method to insert user to database
    public long insertUser(User user) throws SQLException
    {
        //creaate a content values variable
        ContentValues initialValues = new ContentValues();
        //put values of users attributes in variable using KEY, data
        initialValues.put(KEY_FNAME, user.getFname());
        initialValues.put(KEY_LNAME, user.getLname());
        initialValues.put(KEY_USERNAME, user.getUname());
        initialValues.put(KEY_PASSWORD, user.getPass());
        initialValues.put(KEY_EMAIL, user.getEmail());
        initialValues.put(KEY_DOB, user.getDob());
        initialValues.put(KEY_SEX, user.getSex());
        return db.insert(DATABASE_TABLE_USER, null, initialValues); //insert user
    }

    //below is method to delete user based on username passed in
    public long deleteUser(String uname) throws SQLException
    {
        return db.delete(DATABASE_TABLE_USER, "username = ?", new String[]{uname}); //delete user
    }

    //below is a method to convert a cursor to type user for ease of accessing data retrieved from DB
    public User cursorToUser(Cursor cursor)
    {
        User user = new User(); //create new user
        //below sets the attributes based on the values from the cursor passed in
        user.setFname(cursor.getString(1));
        user.setLname(cursor.getString(2));
        user.setUname(cursor.getString(3));
        user.setPass(cursor.getString(4));
        user.setEmail(cursor.getString(5));
        user.setDob(cursor.getString(6));
        user.setSex(cursor.getString(7));
        return user; //return user
    }

    //MEAL PLAN FUNCTIONS
    //below is the fuction to insert user
    public long insertMeal(Meal meal) throws SQLException
    {
        //creaate a content values variable
        ContentValues values = new ContentValues();
        //put values of users attributes in variable using KEY, data
        values.put(KEY_UNAME, meal.getUsername());
        values.put(KEY_DATE, meal.getEntryDate());
        values.put(KEY_TITLE, meal.getTitle());
        values.put(KEY_FOOD, meal.getFood());
        values.put(KEY_AMOUNT, meal.getAmount());
        values.put(KEY_TIME, meal.getTime());
        return db.insert(DATABASE_TABLE_MEALPLAN, null, values); //insert meal
    }

    //below is the method to delete a meal
    public long deleteMeal(int mealId) throws SQLException
    {
        return db.delete(DATABASE_TABLE_MEALPLAN, "_id = ?", new String[]{String.valueOf(mealId)}); //delete meal
    }

    //below is the method to get all meal based on user
    public Cursor getAllMeals(String username) throws SQLException
    {
        //query to select all info about a meal a paticular user entered
        String query = "SELECT "+KEY_MEALID+", "+KEY_UNAME+", "+KEY_DATE+", "+KEY_TITLE+", "
                +KEY_FOOD+", "+KEY_AMOUNT+", "+KEY_TIME+" FROM " +DATABASE_TABLE_MEALPLAN+ " WHERE " +KEY_UNAME+ " = '" +username+ "'";

        return db.rawQuery(query, null); //execute query
    }

    //below is the method to update meal
    public long updateMeal(Meal meal, int id) throws SQLException
    {
        //create content values variable
        ContentValues values = new ContentValues();
        //put values of users attributes in variable using KEY, data
        values.put(KEY_DATE, meal.getEntryDate());
        values.put(KEY_TITLE, meal.getTitle());
        values.put(KEY_FOOD, meal.getFood());
        values.put(KEY_AMOUNT, meal.getAmount());
        values.put(KEY_TIME, meal.getTime());

        return db.update(DATABASE_TABLE_MEALPLAN, values, "_id = ?", new String[]{String.valueOf(id)}); //update query
    }

    //below is the method to convert a cursor to meal
    public Meal cursorToMeal(Cursor cursor) throws SQLException
    {
        //create new meal
        Meal meal = new Meal();
        //set the values of meal based on the cursor passed in, it gets the data by using getTYPE(positionInCursor)
        meal.setId(cursor.getInt(0));
        meal.setUsername(cursor.getString(1));
        meal.setEntryDate(cursor.getString(2));
        meal.setTitle(cursor.getString(3));
        meal.setFood(cursor.getString(4));
        meal.setAmount(cursor.getString(5));
        meal.setTime(cursor.getString(6));
        return meal;
    }

    // Workout Plan Functions
    //below is the function to get all workouts
    public Cursor getAllWorkouts(String username) throws SQLException
    {
        //query to select all workout data based on a specific user
        String query = "SELECT "+KEY_WORKOUTID+", "+KEY_WORKOUTUNAME+", "+KEY_WORKOUTDATE+", "+KEY_WORKOUTTIME+", "+KEY_WORKOUTTITLE+
                ", "+KEY_WORKOUTEXERCISE1+", "+KEY_SETS1+", "+KEY_REPS1+", "+KEY_WEIGHT1+
                ", "+KEY_WORKOUTEXERCISE2+", "+KEY_SETS2+", "+KEY_REPS2+", "+KEY_WEIGHT2+
                ", "+KEY_WORKOUTEXERCISE3+", "+KEY_SETS3+", "+KEY_REPS3+", "+KEY_WEIGHT3+
                ", "+KEY_WORKOUTEXERCISE4+", "+KEY_SETS4+", "+KEY_REPS4+", "+KEY_WEIGHT4+
                " FROM " +DATABASE_TABLE_WORKOUTS+ " WHERE " +KEY_WORKOUTUNAME+ " = '" +username+ "'";

        return db.rawQuery(query, null); //query DB
    }

    //below is the method to insert workout
    public long insertWorkout(Workout workout) throws SQLException
    {
        //gets the exercises and stores them in an array
        String[] exercises = workout.getExercises();
        //gets the sets and reps and stores them in integer arrays
        int[] sets = workout.getSets();
        int[] reps = workout.getReps();
        //gets the weights and stores them in string array
        String[] weights = workout.getWeight();

        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUTUNAME, workout.getUsername());
        values.put(KEY_WORKOUTDATE, workout.getDate());
        values.put(KEY_WORKOUTTIME, workout.getTime());
        values.put(KEY_WORKOUTTITLE, workout.getTitle());
        values.put(KEY_WORKOUTEXERCISE1, exercises[0]);
        values.put(KEY_SETS1, sets[0]);
        values.put(KEY_REPS1, reps[0]);
        values.put(KEY_WEIGHT1, weights[0]);
        values.put(KEY_WORKOUTEXERCISE2, exercises[1]);
        values.put(KEY_SETS2, sets[1]);
        values.put(KEY_REPS2, reps[1]);
        values.put(KEY_WEIGHT2, weights[1]);
        values.put(KEY_WORKOUTEXERCISE3, exercises[2]);
        values.put(KEY_SETS3, sets[2]);
        values.put(KEY_REPS3, reps[2]);
        values.put(KEY_WEIGHT3, weights[2]);
        values.put(KEY_WORKOUTEXERCISE4, exercises[3]);
        values.put(KEY_SETS4, sets[3]);
        values.put(KEY_REPS4, reps[3]);
        values.put(KEY_WEIGHT4, weights[3]);
        return db.insert(DATABASE_TABLE_WORKOUTS, null, values);
    }

    public Workout cursorToWorkout(Cursor cursor) throws SQLException
    {
        Workout workout = new Workout();
        workout.setId(cursor.getInt(0));
        workout.setUsername(cursor.getString(1));
        workout.setDate(cursor.getString(2));
        workout.setTime(cursor.getString(3));
        workout.setTitle(cursor.getString(4));
        workout.setExercises(new String[]{cursor.getString(5), cursor.getString(9), cursor.getString(13), cursor.getString(17)});
        workout.setSets(new int[]{cursor.getInt(6), cursor.getInt(10), cursor.getInt(14), cursor.getInt(18)});
        workout.setReps(new int[]{cursor.getInt(7), cursor.getInt(11), cursor.getInt(15), cursor.getInt(19)});
        workout.setWeight(new String[]{cursor.getString(8), cursor.getString(12), cursor.getString(16), cursor.getString(20)});
        return workout;
    }

    public long updateWorkout(Workout workout, int id) throws SQLException
    {
        String[] exercises = workout.getExercises();
        int[] sets = workout.getSets();
        int[] reps = workout.getReps();
        String[] weights = workout.getWeight();

        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUTUNAME, workout.getUsername());
        values.put(KEY_WORKOUTDATE, workout.getDate());
        values.put(KEY_WORKOUTTIME, workout.getTime());
        values.put(KEY_WORKOUTTITLE, workout.getTitle());
        values.put(KEY_WORKOUTEXERCISE1, exercises[0]);
        values.put(KEY_SETS1, sets[0]);
        values.put(KEY_REPS1, reps[0]);
        values.put(KEY_WEIGHT1, weights[0]);
        values.put(KEY_WORKOUTEXERCISE2, exercises[1]);
        values.put(KEY_SETS2, sets[1]);
        values.put(KEY_REPS2, reps[1]);
        values.put(KEY_WEIGHT2, weights[1]);
        values.put(KEY_WORKOUTEXERCISE3, exercises[2]);
        values.put(KEY_SETS3, sets[2]);
        values.put(KEY_REPS3, reps[2]);
        values.put(KEY_WEIGHT3, weights[2]);
        values.put(KEY_WORKOUTEXERCISE4, exercises[3]);
        values.put(KEY_SETS4, sets[3]);
        values.put(KEY_REPS4, reps[3]);
        values.put(KEY_WEIGHT4, weights[3]);

        return db.update(DATABASE_TABLE_WORKOUTS, values, "_id = ?", new String[]{String.valueOf(id)});
    }

    public long deleteWorkout(int workoutId) throws SQLException
    {
        return db.delete(DATABASE_TABLE_WORKOUTS, "_id = ?", new String[]{String.valueOf(workoutId)});
    }
}