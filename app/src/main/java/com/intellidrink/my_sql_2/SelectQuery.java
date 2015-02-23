package com.intellidrink.my_sql_2;

/**
 * Created by David on 1/14/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class SelectQuery extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
    protected Kiosk TheKiosk = new Kiosk();


    // Creating JSON Parser object
    JSONParser jParser;

    ArrayList<HashMap<String, String>> productsList;

    // url to get all products list
    private static String CUSTOM_KIOSK_COMMAND = Kiosk.BASE_URL + Kiosk.SELECT_QUERY;

    // products JSONArray
    JSONArray products= null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences SaveFile = getSharedPreferences(Kiosk.PREFERENCE_FILE, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_allproducts);

        if (SaveFile.contains(Kiosk.KEY_KIOSK_NAME)) {
            TheKiosk.setKNAME(SaveFile.getString(Kiosk.KEY_KIOSK_NAME, null));
        }

        if (SaveFile.contains(Kiosk.KEY_KIOSK_PASSWORD)) {
            TheKiosk.setKPASSWD(SaveFile.getString(Kiosk.KEY_KIOSK_PASSWORD, null));
        }
        jParser = new JSONParser(TheKiosk);
        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                // Starting new intent

            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

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
            pDialog = new ProgressDialog(SelectQuery.this);
            pDialog.setMessage("Loading products. Please wait...");
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
            JSONObject json = jParser.makeHttpRequest(CUSTOM_KIOSK_COMMAND, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(Kiosk.TAG_SUCCESS);

                if (success == 1) {

                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(Kiosk.TAG_CUSTOM);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

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
                        productsList.add(map);

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
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            SelectQuery.this, productsList,
                            R.layout.list_item_2, new String[] { Kiosk.TAG_ID,
                            Kiosk.TAG_LITERAL_NAME, Kiosk.TAG_GENERIC_ID_NUMBER},
                            new int[] { R.id.id_2, R.id.leftValue, R.id.rightValue});
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}
