package stefan.burke.mydit.ie.spotter.Classes;

import java.io.Serializable;

/**
 * Created by le on 30/03/2015.
 */
public class User implements Serializable {

    private int id;
    private String fname;
    private String lname;
    private String uname;
    private String pass;
    private String email;
    private String dob;
    private String sex;

    public User()
    {

    }

    public User(String firstName, String lastName, String username, String password, String eMail, String dateOfBirth, String sexSel)
    {
        this();
        this.fname = firstName;
        this.lname = lastName;
        this.uname = username;
        this.pass = password;
        this.email = eMail;
        this.dob = dateOfBirth;
        this.sex = sexSel;
    }

    public int getId() { return id; }

    public void setId(int iD) { this.id = iD; }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
