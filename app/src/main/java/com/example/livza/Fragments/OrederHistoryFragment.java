package com.example.livza.Fragments;

import android.os.Bundle;

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

import java.util.ArrayList;

public class OrederHistoryFragment extends Fragment {

    private RecyclerView OrderecyclerView;
    private ArrayList<OrderItem> OrderItem;

    public static int cart_pos=0;
    private HistoryItemAdapter.OnHistoryItemlistner onHistoryItemlistner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OrderItem=new ArrayList<>();
        OrderItem.add(new OrderItem("12589","5985Da","received","received"));
        OrderItem.add(new OrderItem("12589","5985Da","received","received"));
        OrderItem.add(new OrderItem("12589","5985Da","received","received"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_oreder_history, container, false);

        OrderecyclerView=view.findViewById(R.id.history_orders);
        HistoryItemAdapter historyItemAdapter=new HistoryItemAdapter(getContext(),OrderItem,onHistoryItemlistner);
        OrderecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderecyclerView.setAdapter(historyItemAdapter);
        OrderecyclerView.setItemAnimator(new DefaultItemAnimator());

        /*
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(view.getContext());
        OrderecyclerView.setLayoutManager(manager);
        OrderecyclerView.setItemAnimator(new DefaultItemAnimator());

        OrderItem=new ArrayList<OrderItem>();
        com.example.livza.FireClasses.OrderItem itm0=new OrderItem("125485","152"+"DA","11/25/2021","Received");OrderItem.add(itm0);
        com.example.livza.FireClasses.OrderItem itm1=new OrderItem("125485","152"+"DA","11/25/2021","Received");OrderItem.add(itm1);
        com.example.livza.FireClasses.OrderItem itm2=new OrderItem("125485","152"+"DA","11/25/2021","Received");OrderItem.add(itm2);
        historyItemAdapter.notifyDataSetChanged();*/
        return view;

    }

}