package com.example.livza.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.livza.Adapters.HistoryItemAdapter;
import com.example.livza.FireClasses.OrderItem;
import com.example.livza.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrederHistoryFragment extends Fragment {

    private RecyclerView OrderecyclerView;
    private ArrayList<OrderItem> OrderItems;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private HistoryItemAdapter historyItemAdapter;

    public static int cart_pos=0;
    private HistoryItemAdapter.OnHistoryItemlistner onHistoryItemlistner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init(){
        OrderItems=new ArrayList<>();
        historyItemAdapter=new HistoryItemAdapter(OrderItems,onHistoryItemlistner);

        //Firebase
        mAuth=FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference().child("Historique").child(mAuth.getCurrentUser().getUid());
    }

    private void readHistorique(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id,price,date,status;
                for (DataSnapshot ds: snapshot.getChildren()){
                    id=ds.getKey();
                    price=ds.child("price").getValue(String.class);
                    price=price.substring(0, price.length()-2).trim();
                    date=ds.child("date").getValue(String.class);
                    status=ds.child("state").getValue(String.class);
                    OrderItems.add(new OrderItem(id,price,date,status));
                }
                historyItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_oreder_history, container, false);
        OrderecyclerView=view.findViewById(R.id.history_orders);
        //OrderecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        init();
        readHistorique();
        OrderecyclerView.setAdapter(historyItemAdapter);
        OrderecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        OrderecyclerView.setLayoutManager(manager);

        return view;

    }

}