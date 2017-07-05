package com.example.hachemmasghouni.ireport1;

/**
 * Created by hachemmasghouni on 05.07.17.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Checks if User is logged in or not
 */

public class AutoLogOn {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;


    // Constructor
    public AutoLogOn(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    //Used to set if the User is logged in or not
    public void setLoggedin(boolean loggedin){
        editor.putBoolean("loggedInmode", loggedin);
        editor.commit();
    }

    public void setName( String name ) {
        editor.putString( "name", name );
        editor.commit();
    }

    //public void setID( int id ) {
      //  editor.putInt( "id", id );
        //editor.commit();
    //}

    public void setMail( String mail ) {
        editor.putString( "mail", mail );
        editor.commit();
    }

    //public void setColor( int color ) {
      //  editor.putInt( "color", color );
        //editor.commit();
    //}

    //Checks if User is logged in
    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

    // get colour int for account picture
    //public int getAccountColour() {
      //  return prefs.getInt( "color", 0xFF000000 );
    //}

    //public int getAccountID() {
      //  return prefs.getInt( "id", -1 );
    //}

    public String getAccountName() {
        return prefs.getString( "name", "DEFAULT" );
    }

    public String getAccountMail() {
        return prefs.getString( "mail", "mail@mail.mail" );
    }
}
