package com.example.livza.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.livza.Adapters.AdressesAdapter;
import com.example.livza.FireClasses.Adresse;
import com.example.livza.MapsActivity;
import com.example.livza.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MyAdressesFragment extends Fragment {
    private FloatingActionButton add_new_adresses;
    private ListView adresses_list;
    private AdressesAdapter adressesAdapter;
    private ArrayList<Adresse> adresses;
    private static final int PICK_ADRESSE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_adresses, container, false);
        //init vars
        add_new_adresses = view.findViewById(R.id.my_adresses_add);
        adresses_list = view.findViewById(R.id.my_adresses_list);
        init();
        add_new_adresses = view.findViewById(R.id.my_adresses_add);
        addnewadresse();
        return view;

    }

    public void addnewadresse() {
        add_new_adresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(getActivity(), MapsActivity.class);
                //startActivityForResult(map, PICK_ADRESSE);
                startActivity(map);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_ADRESSE && resultCode == RESULT_OK && data != null) {

        }
    }

    public void init() {
        adresses = new ArrayList<>();
        adresses.add(new Adresse("Home1"));
        adresses.add(new Adresse("Home2"));
        adresses.add(new Adresse("Work"));
        adressesAdapter = new AdressesAdapter(getActivity(), adresses);
        adresses_list.setAdapter(adressesAdapter);
    }
}