package com.intellidrink.my_sql_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.SharedPreferences;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONObject;

public class MainActivity extends Activity{

    Button btnViewCustomers;
    Button btnNewCustomer;
    Button btnSetKiosk;
    Button btnShowKioskName;
    Button btnCustomKioskCommand;
    String kioskName;
    String kioskPassword;
    JSONParser jParser;
    protected Kiosk thisKiosk = new Kiosk();
    String Output;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences SaveFile = getSharedPreferences(Kiosk.PREFERENCE_FILE, Context.MODE_PRIVATE);
        if (SaveFile.contains(Kiosk.KEY_KIOSK_NAME)) {
            thisKiosk.setKNAME(SaveFile.getString(Kiosk.KEY_KIOSK_NAME, null));
        }

        if (SaveFile.contains(Kiosk.KEY_KIOSK_PASSWORD)) {
            thisKiosk.setKPASSWD(SaveFile.getString(Kiosk.KEY_KIOSK_PASSWORD, null));
        }

        // Buttons
        btnViewCustomers = (Button) findViewById(R.id.btnViewProducts);
        btnNewCustomer = (Button) findViewById(R.id.btnCreateProduct);
        btnSetKiosk = (Button) findViewById(R.id.btnNamekiosk);
        btnShowKioskName = (Button)findViewById(R.id.btnShowKioskName);
        btnCustomKioskCommand = (Button)findViewById(R.id.btnCustomKioskCommand);

        btnNewCustomer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
/*
                List<NameValuePair> params = new ArrayList<NameValuePair>();

 //               params.add(new BasicNameValuePair("username", "Kiosk_1"));
 //               params.add(new BasicNameValuePair("password", "password"));

                JSONObject json = jParser.makeHttpRequest(Kiosk.BASE_URL + Kiosk.CONFIGURE_KIOSK, "GET", params);

                Log.d("All Products: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(Kiosk.TAG_SUCCESS);

                    if (success == 1) {

                        // products found
                        // Getting Array of Products
                        System.out.print("Success\n");

                        // looping through All Products

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
*/
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                // getting JSON string from URL
                JSONObject json = jParser.makeHttpRequest("http://192.168.1.4/IntelliDrink/configure_kiosk_database.php", "GET", params);

                // Check your log cat for JSON reponse
                Log.d("All Products: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(Kiosk.TAG_SUCCESS);

                    if (success == 1) {

                        System.out.print("Success\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
        // view products click event
        btnViewCustomers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllCustomers.class);
                startActivity(i);

            }
        });
        btnCustomKioskCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), CustomerTransactions.class);
                startActivity(i);

            }
        });
        // view products click event

        btnSetKiosk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.print("Hello");
                final SharedPreferences SaveFile = getSharedPreferences(Kiosk.PREFERENCE_FILE, Context.MODE_PRIVATE);
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                Context context = view.getContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                //dialog.setTitle("Set the Kiosk Name");

                final EditText EditText_KioskName = new EditText(view.getContext());
                EditText_KioskName.setHint("Kiosk Name");
                layout.addView(EditText_KioskName);


                final EditText EditText_KioskPassword = new EditText(view.getContext());
                EditText_KioskPassword.setHint("Kiosk Password");
                layout.addView(EditText_KioskPassword);

                final EditText EditText_KioskPassword2 = new EditText(view.getContext());
                EditText_KioskPassword2.setHint("Retype Password");
                layout.addView(EditText_KioskPassword2);

                dialog.setView(layout);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(EditText_KioskPassword.getText().toString().equals(EditText_KioskPassword2.getText().toString())){
                            kioskPassword = EditText_KioskPassword.getText().toString();
                            kioskName = EditText_KioskName.getText().toString();
                            SaveFile.edit().putString(Kiosk.KEY_KIOSK_NAME, kioskName).apply();
                            SaveFile.edit().putString(Kiosk.KEY_KIOSK_PASSWORD, kioskPassword).apply();
                            thisKiosk.setKNAME(kioskName);
                            thisKiosk.setKPASSWD(kioskPassword);
                            if (SaveFile.contains(Kiosk.KEY_KIOSK_NAME)) {
                                Output = SaveFile.getString(Kiosk.KEY_KIOSK_NAME, null);
                            }
                            Toast.makeText(getApplicationContext(), "The Kiosk Name is: [" + Output + "]", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "The passwords did not match.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        btnShowKioskName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                String Taco = "";
                SharedPreferences sharedpreferences = getSharedPreferences(Kiosk.PREFERENCE_FILE, Context.MODE_PRIVATE);
                Taco = sharedpreferences.getString(Kiosk.KEY_KIOSK_NAME, null );
                Toast.makeText(getApplicationContext(), "Kiosk Name: [" + Taco + "]", Toast.LENGTH_SHORT).show();
            }
        });
    }
}