package com.example.airportstatus;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.airportstatus.fragments.EmptyStatusFragment;
import com.example.airportstatus.fragments.SavedFragment;
import com.example.airportstatus.fragments.SearchFragment;
import com.example.airportstatus.fragments.StatusFragment;
import com.example.airportstatus.models.TravelTimeEstimate;



public class StatusListActivity extends FragmentActivity implements TabListener {
	
	StatusFragment statusFragment;
	SearchFragment searchFragment;
	SavedFragment savedFragment;
	private Tab tabStatus;
	private Tab tabSaved;
	private Tab tabFind;
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_list);
		
		statusFragment = new StatusFragment();
		searchFragment = new SearchFragment();
		savedFragment = new SavedFragment();
		setupNavigationTabs();
		
		if (getIntentBundle() != null) {
			getActionBar().selectTab(tabStatus);
		} else {
			getActionBar().selectTab(tabFind);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status_list, menu);
		return true;
	}
	private void setupNavigationTabs() {
		ActionBar actionBar= getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		tabStatus= actionBar.newTab().setText("Status")
				.setTag("StatusFragment")
				.setTabListener(this);
		tabSaved = actionBar.newTab().setText("Saved")
				.setTag("SavedFragment")
				.setTabListener(this);
		tabFind = actionBar.newTab().setText("Find")
				.setTag("SearchFragment")
				.setTabListener(this);
		actionBar.addTab(tabStatus);
		actionBar.addTab(tabSaved);
		actionBar.addTab(tabFind);
	}
	
	private Bundle getIntentBundle() {
		return getIntent().getBundleExtra("data");
	}
	
	public int getAirportIndex() {
		if (getIntentBundle() == null)
			return -1;
		return Integer.parseInt(getIntentBundle().getString("airport_index"));
	}
	
	public String getAirportCode() {
		if (getIntentBundle() == null)
			return "";
		return getIntentBundle().getString("airport_code");
		
	}
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "StatusFragment") {
			if (getAirportCode() != "") {
				fts.replace(R.id.frame_container, statusFragment);
			} else {
				fts.replace(R.id.frame_container, new EmptyStatusFragment());
			}
		} else if (tab.getTag() == "SavedFragment") {
			fts.replace(R.id.frame_container, savedFragment);
		} else if (tab.getTag() == "SearchFragment") {
			fts.replace(R.id.frame_container, searchFragment);
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public void onClickDrivingMapButton(View v) {
		if (!getIntentBundle().containsKey("origin")) {
			Toast.makeText(getApplicationContext(), "Missing origin data", Toast.LENGTH_SHORT).show();
			return;
		}
		String origin = getIntentBundle().getString("origin");
		launchMapIntent(TravelTimeEstimate.getDrivingMapUrl(origin, getAirportCode()));
	}
	
	public void onClickTransitMapButton(View v) {
		if (!getIntentBundle().containsKey("origin")) {
			Toast.makeText(getApplicationContext(), "Missing origin data", Toast.LENGTH_SHORT).show();
			return;
		}
		String origin = getIntentBundle().getString("origin");
		launchMapIntent(TravelTimeEstimate.getTransitMapUrl(origin, getAirportCode()));
	}
	
	private void launchMapIntent(String url) {
		try {
			
		} catch (Exception e) {
			Log.e("MAP_LAUNCHER", e.getMessage());
			Toast.makeText(getApplicationContext(), R.string.txtRoutingError, Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(intent);
	}
	
	public void onSearchBtnClick(View v) {
    	searchFragment.onSearchBtnClick(v);
    }
	
	public void onFavoriteAction(View v) {
		statusFragment.onFavoriteAction(v);
	}
}
