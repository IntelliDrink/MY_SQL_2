package com.intellidrink.my_sql_2;

/**
 * Created by David on 1/14/2015.
 */

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerDatabaseHelper {

    private ProgressDialog pDialog;
    protected Kiosk TheKiosk;
    protected Context context;
    JSONArray response= null;

    // Creating JSON Parser object
    JSONParser jParser;

    ArrayList<HashMap<String, String>> responseList;


            public ServerDatabaseHelper(Kiosk passedInKiosk, Context passedInContext){
                TheKiosk = passedInKiosk;
                context = passedInContext;
                jParser = new JSONParser(TheKiosk);
                responseList = new ArrayList<HashMap<String, String>>();
            }


    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            params.add(new BasicNameValuePair("username", "Kiosk_1"));
            params.add(new BasicNameValuePair("password", "password"));
            JSONObject json = jParser.makeHttpRequest("", "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(Kiosk.TAG_SUCCESS);

                if (success == 1) {

                    // products found
                    // Getting Array of Products
                    response = json.getJSONArray(Kiosk.TAG_CUSTOM);

                    // looping through All Products
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject c = response.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(Kiosk.TAG_ID);
                        String literalName = c.getString(Kiosk.TAG_LITERAL_NAME);
                        String genericIDNumber = c.getString(Kiosk.TAG_GENERIC_ID_NUMBER);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(Kiosk.TAG_ID, id);
                        map.put(Kiosk.TAG_LITERAL_NAME, literalName);
                        map.put(Kiosk.TAG_GENERIC_ID_NUMBER, genericIDNumber);

                        // adding HashList to ArrayList
                        responseList.add(map);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
        }

    }
}
