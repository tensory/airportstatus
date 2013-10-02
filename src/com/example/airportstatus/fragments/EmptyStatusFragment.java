package com.example.airportstatus.fragments;

import com.example.airportstatus.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyStatusFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_empty_status, parent, false);
	}

}
