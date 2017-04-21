
package com.example.android.facebookloginsample;

/**
 * User Model.
 * Public for easy access, should not be used to modify object
 */
public class User {


    public String name;

    public String email;

    public String facebookID;

    public String gender;
    
    public User(){}

    public User(String name, String email, String facebookID, String gender) {
        this.name = name;
        this.email = email;
        this.facebookID = facebookID;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User: " + this.name + " ID " + facebookID;
    }
}
