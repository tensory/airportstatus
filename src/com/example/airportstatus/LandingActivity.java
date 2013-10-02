package com.example.airportstatus;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class LandingActivity extends Activity {
	int airportIndex;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		context = this;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		getLocation();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing, menu);
		return true;
	}
	
	private void getLocation() {
		// Get the stored preference airport code
		// If that doesn't exist, launch the SearchFragment view of the StatusListActivity

				
		LocationResult locationResult = new LocationResult() {
			@Override
			public void receivedLocation(Location location) {
				Log.d("LOCATION_RECEIVED", location.toString());
				LocationPreferences.setLastLocationPreferences(context, location.getLatitude(), location.getLongitude());
				try {
					Airport airportDetails = LocationPreferences.getAirport(context);

					Intent i = new Intent(context, QueryActivity.class);
					i.putExtra("airport_code", airportDetails.code);
					i.putExtra("airport_index", String.valueOf(airportDetails.index));
					startActivity(i);
				} catch (Exception e) {
					Intent i = new Intent(context, StatusListActivity.class);
					startActivity(i);
				}
			}
		};
		
		LocationPreferences locPrefs = new LocationPreferences();
		locPrefs.getCurrentLocation(this.getBaseContext(), locationResult);
	}
}
