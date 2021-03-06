package com.shopping.electronics.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class deals with common functionalities for application
 */
public class CommonActivity extends AppCompatActivity {

    /**
     * Checks for Internet connection
     * @param context ApplicationContext
     * @return returns boolean value
     */
    protected boolean isNetworkConnectionAvailable(Context context) {
        boolean isNetworkConnectionAvailable = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null) {
                isNetworkConnectionAvailable = activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isNetworkConnectionAvailable;
    }

    /**
     * Common error message to show in application
     * @param message Message to be shown
     */
    protected void showErrorMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    protected boolean isValidEmail(String email) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    /**
     * Stores in application data
     * @param fileName
     * @param object
     */
    protected void saveObject(String fileName, Object object) {
        try {
//            String path = Environment.getDownloadCacheDirectory()+"/"+getString(R.string.app_name)+"/";
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            objectOut.close();
            System.out.println("The Object is saved successfully");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CommonActivity", e.getMessage());
        }
    }

    /**
     * Retrieves from application data
     * @param fileName
     * @return
     */
    protected Object getObject(String fileName) {
        Object object = null;
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("CommonActivity", e.getMessage());
        }
        return object;
    }
}
