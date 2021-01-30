package com.example.livza.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MyAdressesFragment extends Fragment {
    private FloatingActionButton add_new_adresses;
    private ListView adresses_list;
    private AdressesAdapter adressesAdapter;
    private ArrayList<Adresse> adresses;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

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
        add_new_adresses = view.findViewById(R.id.my_adresses_add);
        init();
        readData();
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


    public void init() {
        adresses = new ArrayList<>();
        adressesAdapter = new AdressesAdapter(getActivity(), adresses);
        adresses_list.setAdapter(adressesAdapter);

        //firebase
        mRef= FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
    }

    private void readData(){
        mRef.child("addresses").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adresses.clear();
                String name;
                for (DataSnapshot ds:snapshot.getChildren()){
                    name=ds.child("name").getValue(String.class);
                    adresses.add(new Adresse(name));
                }
                adressesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}