package com.erengulbahar.dryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.erengulbahar.dryapp.databinding.RecyclerfRowBinding;
import com.erengulbahar.dryapp.model.ApFList;
import com.erengulbahar.dryapp.user.AppointmentFListDirections;

import java.util.ArrayList;

public class ApFAdapter extends RecyclerView.Adapter<ApFAdapter.ApFHolder>
{
    private ArrayList<ApFList> apFListArrayList;

    public ApFAdapter(ArrayList<ApFList> apFListArrayList)
    {
        this.apFListArrayList = apFListArrayList;
    }

    @NonNull
    @Override
    public ApFHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We connect with recycler view.
        RecyclerfRowBinding recyclerfRowBinding = RecyclerfRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ApFHolder(recyclerfRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ApFHolder holder, int position)
    {
        //We set which feature of product you will see. And what happen when you clicked it.
        holder.recyclerfRowBinding.recyclerViewEmailText2.setText(apFListArrayList.get(position).type2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AppointmentFListDirections.ActionAppointmentFListToAppointmentsFDetails action = AppointmentFListDirections.actionAppointmentFListToAppointmentsFDetails();
                action.setId(apFListArrayList.get(position).id2);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return apFListArrayList.size();
    }

    public class ApFHolder extends RecyclerView.ViewHolder
    {
        RecyclerfRowBinding recyclerfRowBinding;

        public ApFHolder(RecyclerfRowBinding recyclerfRowBinding)
        {
            super(recyclerfRowBinding.getRoot());
            this.recyclerfRowBinding = recyclerfRowBinding;
        }
    }
}