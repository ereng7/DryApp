package com.erengulbahar.dryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.erengulbahar.dryapp.admin.AdminAppointmentFListDirections;
import com.erengulbahar.dryapp.databinding.AdminRecyclerRowBinding;
import com.erengulbahar.dryapp.databinding.AdminRecyclerfRowBinding;
import com.erengulbahar.dryapp.model.ApFList;

import java.util.ArrayList;

public class AdminFAdapter extends RecyclerView.Adapter<AdminFAdapter.AdminFHolder>
{
    private ArrayList<ApFList> apFListArrayList;

    public AdminFAdapter(ArrayList<ApFList> apFListArrayList)
    {
        this.apFListArrayList = apFListArrayList;
    }

    @NonNull
    @Override
    public AdminFHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We connect with recycler view.
        AdminRecyclerfRowBinding recyclerfRowBinding = AdminRecyclerfRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new AdminFHolder(recyclerfRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFHolder holder, int position)
    {
        //We set which feature of product you will see. And what happen when you clicked it.
        holder.recyclerfRowBinding.recyclerViewEmailText3.setText(apFListArrayList.get(position).email2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AdminAppointmentFListDirections.ActionAdminAppointmentFListToAdminAppointmentsFDetails action = AdminAppointmentFListDirections.actionAdminAppointmentFListToAdminAppointmentsFDetails();
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

    public class AdminFHolder extends RecyclerView.ViewHolder
    {
        AdminRecyclerfRowBinding recyclerfRowBinding;

        public AdminFHolder(AdminRecyclerfRowBinding recyclerfRowBinding)
        {
            super(recyclerfRowBinding.getRoot());
            this.recyclerfRowBinding = recyclerfRowBinding;
        }
    }
}