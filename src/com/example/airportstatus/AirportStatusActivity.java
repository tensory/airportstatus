package com.example.airportstatus;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.airportstatus.models.Favorite;

public class AirportStatusActivity extends Activity {

	Button btnGo;
	AutoCompleteTextView tvAirportCode;
	LocationManager locationManager;
	LocationListener locationListener;
	SharedPreferences locationPrefs;
	
	public static final String AIRPORT_CODE = "airport_code";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_status);
        setupLocationStorage();
        setupLocationListener();
        
        setupButton();
        setupTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.airport_status, menu);
        
        if (Favorite.getAllCodes().size() > 0) {
	        // Set up the action bar to show a dropdown list.
	        final ActionBar actionBar = getActionBar();
	        actionBar.setDisplayShowTitleEnabled(false);
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	        
	        ArrayList<String> dropdownValues = Favorite.getAllCodes();
	        dropdownValues.add(0, getResources().getString(R.string.txtFavoritesPlaceholder));
        }
        return true;
    }

    @Override
    public void onDestroy() {
    	super.onDestroy();
    	locationManager.removeUpdates(locationListener);
    	
    }
    
    public void onClick(View v) {
    	String textEntered = tvAirportCode.getText().toString();
    	String code = Airport.IATA_CODES.get(textEntered);
    	if (code != null) {
	    	Toast.makeText(this, "Searching for " + code + "...", Toast.LENGTH_SHORT).show();
	    	Intent i = new Intent(this, QueryActivity.class);
	    	i.putExtra(AIRPORT_CODE, code);
	    	startActivity(i);
    	} else {
    		Toast.makeText(this,  "Could not find airport code " + textEntered, Toast.LENGTH_SHORT).show();
    	}
    }

    @SuppressLint("InlinedApi")
    private void setupButton() {
    	btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
    }

    private void setupTextView() {
    	ArrayList<String> codes = new ArrayList<String> (Airport.IATA_CODES.keySet());
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, codes);
    	tvAirportCode = (AutoCompleteTextView) findViewById(R.id.tvAirportCode);
    	tvAirportCode.setAdapter(adapter);
    }
    
    private void setupLocationStorage() {
        locationPrefs = getSharedPreferences(LocationPreferences.PREFS_NAME, 0);    	
    }
    
    private void setupLocationListener() {
    	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	locationListener = new LocationListener() {
    		@Override
    		public void onLocationChanged(Location location) {
    			SharedPreferences locationPrefs = getSharedPreferences(LocationPreferences.PREFS_NAME, MODE_PRIVATE);
    			SharedPreferences.Editor editor = locationPrefs.edit();
    			editor.putFloat(LocationPreferences.PREFS_LATITUDE, (float) location.getLatitude());
    			editor.putFloat(LocationPreferences.PREFS_LONGITUDE, (float) location.getLongitude());
    			editor.commit();
    		}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
			}
    	};
    	
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
    }
    
}