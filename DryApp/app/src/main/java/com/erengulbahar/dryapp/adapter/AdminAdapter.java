package com.erengulbahar.dryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.erengulbahar.dryapp.admin.AdminAppointmentList;
import com.erengulbahar.dryapp.admin.AdminAppointmentListDirections;
import com.erengulbahar.dryapp.databinding.AdminRecyclerRowBinding;
import com.erengulbahar.dryapp.model.ApList;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminHolder>
{
    private ArrayList<ApList> apListArrayList;

    public AdminAdapter(ArrayList<ApList> apListArrayList)
    {
        this.apListArrayList = apListArrayList;
    }

    @NonNull
    @Override
    public AdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We connect with recycler view.
        AdminRecyclerRowBinding recyclerRowBinding = AdminRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdminHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHolder holder, int position)
    {
        //We set which feature of product you will see. And what happen when you clicked it.
        holder.recyclerRowBinding.recyclerViewEmailText2.setText(apListArrayList.get(position).email);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAppointmentListDirections.ActionAdminAppointmentListToAdminAppointmentsDetails action = AdminAppointmentListDirections.actionAdminAppointmentListToAdminAppointmentsDetails();
                action.setId(apListArrayList.get(position).id);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return apListArrayList.size();
    }

    public class AdminHolder extends RecyclerView.ViewHolder
    {
        AdminRecyclerRowBinding recyclerRowBinding;

        public AdminHolder(AdminRecyclerRowBinding recyclerRowBinding)
        {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}