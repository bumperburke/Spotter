package stefan.burke.mydit.ie.spotter.Classes;

import java.io.Serializable;

/**
 * Created by le on 07/04/2015.
 */
public class Workout implements Serializable {

    private int id;
    private String username;
    private String date;
    private String time;
    private String title;
    private String[] exercises;
    private int[] sets;
    private int[] reps;
    private String[] weight;


    public Workout()
    {

    }

    public Workout(String uname, String workoutDate, String workoutTime, String workoutTitle, String[] workoutExercise, int[] set, int[] rep, String[] load)
    {
        this();
        this.setUsername(uname);
        this.setDate(workoutDate);
        this.setTime(workoutTime);
        this.setTitle(workoutTitle);
        this.setExercises(workoutExercise);
        this.setSets(set);
        this.setReps(rep);
        this.setWeight(load);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getExercises() {
        return exercises;
    }

    public void setExercises(String[] exercises) {
        this.exercises = exercises;
    }

    public int[] getSets() {
        return sets;
    }

    public void setSets(int[] sets) {
        this.sets = sets;
    }

    public int[] getReps() {
        return reps;
    }

    public void setReps(int[] reps) {
        this.reps = reps;
    }

    public String[] getWeight() {
        return weight;
    }

    public void setWeight(String[] weight) {
        this.weight = weight;
    }
}
