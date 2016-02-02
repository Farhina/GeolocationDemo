package com.example.lynxit.geolocationdemo;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity  {

    public Double latitude = new Double("12.0");
    public Double longitude = new Double("-45.78");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.example.lynxit.currentlocationprovider/latlong");
        ContentProviderClient contentProviderClient = getContentResolver().acquireContentProviderClient(uri);
        try {
            Cursor cursor = contentProviderClient.query(uri,null,null,null,null);
            if(cursor!=null) {
                while (cursor.moveToNext()) {
                    latitude = Double.parseDouble(cursor.getString(0));
                    longitude = Double.parseDouble(cursor.getString(1));
                    Toast.makeText(this,"latitude: "+latitude.toString()+"longitude :"+longitude.toString(),Toast.LENGTH_LONG).show();
                }

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchLocation(View view)
    {

      //  Double latitude = new Double("40.714"); //hardcoded
       // Double longitude = new Double("-73.961"); //hardcoded
        ListView addressListView = (ListView) findViewById(R.id.addressList);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 5);
                if(addressList!=null && addressList.size()>0)
                {
                    ArrayAdapter<Address> addressArrayAdapter = new ArrayAdapter<Address>(
                            this,android.R.layout.simple_list_item_1,
                            addressList
                    );

                    addressListView.setAdapter(addressArrayAdapter);
        
                }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
