package com.erengulbahar.dryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.erengulbahar.dryapp.databinding.RecyclerRowBinding;
import com.erengulbahar.dryapp.model.ApList;
import com.erengulbahar.dryapp.user.AppointmentListDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ApAdapter extends RecyclerView.Adapter<ApAdapter.ApHolder>
{
    private ArrayList<ApList> apListArrayList;
    public ApAdapter(ArrayList<ApList> apListArrayList)
    {
        this.apListArrayList = apListArrayList;
    }

    @NonNull
    @Override
    public ApHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We connect with recycler view.
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ApHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ApHolder holder, int position)
    {
        //We set which feature of product you will see. And what happen when you clicked it.
        holder.recyclerRowBinding.recyclerViewEmailText.setText(apListArrayList.get(position).type);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AppointmentListDirections.ActionAppointmentListToAppointmentDetails action = AppointmentListDirections.actionAppointmentListToAppointmentDetails();
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

    class ApHolder extends RecyclerView.ViewHolder
    {
        RecyclerRowBinding recyclerRowBinding;

        public ApHolder(RecyclerRowBinding recyclerRowBinding)
        {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}