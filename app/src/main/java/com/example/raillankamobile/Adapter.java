package com.example.raillankamobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.MyviewHolder> {

    Context context;
    ArrayList<TrainTimeShedule> list;

    public Adapter(Context context, ArrayList<TrainTimeShedule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V= LayoutInflater.from(context).inflate(R.layout.trainshedulelist,parent ,false);
        return new MyviewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        TrainTimeShedule trainTimeShedule=list.get(position);
        holder.arrivalTextView.setText(trainTimeShedule.getArival());
        holder.departureTextView.setText(trainTimeShedule.getDepature());
        holder.destinationTextView.setText(trainTimeShedule.getFinaldestination());
        holder.delaysTextView.setText(trainTimeShedule.getDealys());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{

        public TextView arrivalTextView;
        public TextView delaysTextView;
        public TextView departureTextView;
        public TextView destinationTextView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);


            arrivalTextView = itemView.findViewById(R.id.arrivalTimeTextView);
            delaysTextView = itemView.findViewById(R.id.delaysTextView);
            departureTextView = itemView.findViewById(R.id.departureTimeTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
        }
    }
}
