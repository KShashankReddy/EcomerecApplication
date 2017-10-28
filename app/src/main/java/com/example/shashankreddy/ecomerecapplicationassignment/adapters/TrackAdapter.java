package com.example.shashankreddy.ecomerecapplicationassignment.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shashankreddy.ecomerecapplicationassignment.R;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Order;

import java.util.ArrayList;


public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackHolder> {
    private final Context mContext;
    ArrayList<Order> mOrderHistory;
    public TrackAdapter(Context context, ArrayList<Order> orderHistory) {
        this.mContext = context;
        mOrderHistory = orderHistory;
    }

    @Override
    public TrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trackorder_layout,parent,false);
        return new TrackHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackHolder holder, int position) {
        CardView trackStatusCard = holder.trackStatusCardView;
        TextView orderId= holder.orderId;
        TextView items= holder.items;
        TextView quantity= holder.totalPrice;
        CheckBox checkBox1=holder.checkBox1;
        CheckBox checkBox2=holder.checkBox2;
        CheckBox checkBox3=holder.checkBox3;
        CheckBox checkBox4=holder.checkBox4;
        View layout1 = holder.layout1;
        View layout2 = holder.layout2;
        View layout3 = holder.layout3;
        final View  orderCheckStatus = holder.orderCheckStatus;
        orderId.setText(mOrderHistory.get(position).getOrderId());
        items.setText(mOrderHistory.get(position).getItems());
        quantity.setText(mOrderHistory.get(position).getTotal());

        if(mOrderHistory.get(position).getOrderStatus().equals("1")){
            checkBox1.setChecked(true);
            layout1.setBackgroundColor(mContext.getResources().getColor(R.color.accent));
        }else if (mOrderHistory.get(position).getOrderStatus().equals("2")){
            checkBox1.setChecked(true);
            checkBox2.setChecked(true);
            layout1.setBackgroundColor(mContext.getResources().getColor(R.color.secondary_text));
            layout2.setBackgroundColor(mContext.getResources().getColor(R.color.accent));
        }else if (mOrderHistory.get(position).getOrderStatus().equals("3")){
            checkBox1.setChecked(true);
            checkBox2.setChecked(true);
            checkBox3.setChecked(true);
            layout1.setBackgroundColor(mContext.getResources().getColor(R.color.secondary_text));
            layout2.setBackgroundColor(mContext.getResources().getColor(R.color.secondary_text));
            layout3.setBackgroundColor(mContext.getResources().getColor(R.color.accent));
        }else if (mOrderHistory.get(position).getOrderStatus().equals("4")){
            checkBox1.setChecked(true);
            checkBox2.setChecked(true);
            checkBox3.setChecked(true);
            checkBox4.setChecked(true);
            layout1.setBackgroundColor(mContext.getResources().getColor(R.color.secondary_text));
            layout2.setBackgroundColor(mContext.getResources().getColor(R.color.secondary_text));
            layout3.setBackgroundColor(mContext.getResources().getColor(R.color.secondary_text));
        }

        trackStatusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderCheckStatus.isShown())
                    orderCheckStatus.setVisibility(View.GONE);
                else
                    orderCheckStatus.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mOrderHistory.size();
    }

     public class TrackHolder extends RecyclerView.ViewHolder {
         CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
         CardView trackStatusCardView;
         TextView orderId,items,totalPrice;
         View layout1,layout2,layout3,orderCheckStatus;
         public TrackHolder(View itemView) {
            super(itemView);
            trackStatusCardView = (CardView) itemView.findViewById(R.id.orderStatusTrackId);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            items= (TextView) itemView.findViewById(R.id.orderItems);
            totalPrice= (TextView) itemView.findViewById(R.id.orderTotal);
             checkBox1 = (CheckBox) itemView.findViewById(R.id.checkBox1);
             checkBox2 = (CheckBox) itemView.findViewById(R.id.checkBox2);
             checkBox3 = (CheckBox) itemView.findViewById(R.id.checkBox3);
             checkBox4 = (CheckBox) itemView.findViewById(R.id.checkBox4);
             layout1 = itemView.findViewById(R.id.layout1);
             layout2 = itemView.findViewById(R.id.layout2);
             layout3 = itemView.findViewById(R.id.layout3);
             orderCheckStatus = itemView.findViewById(R.id.orderStatusCheckList);
        }
    }
    public  void  notifyOnDataChange(){
        notifyDataSetChanged();
    }
}
