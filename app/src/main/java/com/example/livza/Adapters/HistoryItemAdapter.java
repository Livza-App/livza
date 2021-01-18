package com.example.livza.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.livza.FireClasses.OrderItem;
import com.example.livza.R;
import java.util.ArrayList;

public class HistoryItemAdapter extends  RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> {
    private ArrayList<OrderItem> Order_Items;
    private OnHistoryItemlistner onHistoryItemlistner ;
    private int lastPosition=-1;

    public HistoryItemAdapter(ArrayList<OrderItem> Order_Items, OnHistoryItemlistner onHistoryItemlistner){
        this.Order_Items=Order_Items;
        this.onHistoryItemlistner=onHistoryItemlistner;
        }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryItemAdapter.ViewHolder(view,onHistoryItemlistner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //slide to delet
        /*if (holder instanceof  HistoryItemAdapter.ViewHolder){
            if(holder.getAdapterPosition()>lastPosition){
                Animation slideleft= AnimationUtils.loadAnimation(context,R.anim.anim_slaide_in_left);
                holder.itemView.startAnimation(slideleft);
                lastPosition=holder.getAdapterPosition();
            }
           // lastPosition=-1;
        }*/
        holder.id_order.setText(Order_Items.get(position).getId());
        holder.order_stat.setText(Order_Items.get(position).getStatus());
        holder.order_price.setText(Order_Items.get(position).getPrice());
        holder.order_date.setText(Order_Items.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return Order_Items.size();
    }
    public void setOnItemClickListener( OnHistoryItemlistner listener) {
        onHistoryItemlistner = listener;
    }

    //this class describe each row of our recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout history_item;
        TextView id_order,order_stat,order_date,order_price;
        Button btn_see_more;
        ImageView status_icon;
        HistoryItemAdapter.OnHistoryItemlistner onHistoryItemlistner;
        public ViewHolder(@NonNull View itemView, OnHistoryItemlistner onHistoryItemlistner) {
            super(itemView);
            this.onHistoryItemlistner=onHistoryItemlistner;
            history_item=itemView.findViewById(R.id.history_item);
            id_order=itemView.findViewById(R.id.id_order);
            order_stat=itemView.findViewById(R.id.order_stat);
            order_date=itemView.findViewById(R.id.order_date);
            order_price=itemView.findViewById(R.id.order_price);
            btn_see_more=itemView.findViewById(R.id.btn_see_more);
            status_icon=itemView.findViewById(R.id.status_icon);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onHistoryItemlistner.OnHistoryItemlistner(getAdapterPosition());
        }
    }




    //interface of listner containing itemlistener
    public interface OnHistoryItemlistner{
        void OnHistoryItemlistner(int position);
        //to change id
        void onIdChange(int posistion);
        // to change  Status Order
        void onStatusChange(int position);
        //to change Order Date
        void onDateChnage(int position);
        //to change price order
        void onPriceChange(int position);
    }
}
