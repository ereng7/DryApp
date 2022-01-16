package com.erengulbahar.dryapp.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erengulbahar.dryapp.R;
import com.erengulbahar.dryapp.adapter.AdminFAdapter;
import com.erengulbahar.dryapp.adapter.ApAdapter;
import com.erengulbahar.dryapp.adapter.ApFAdapter;
import com.erengulbahar.dryapp.databinding.FragmentAdminAppointmentFListBinding;
import com.erengulbahar.dryapp.databinding.FragmentAdminAppointmentListBinding;
import com.erengulbahar.dryapp.databinding.FragmentAppointmentFListBinding;
import com.erengulbahar.dryapp.databinding.FragmentAppointmentListBinding;
import com.erengulbahar.dryapp.model.ApFList;
import com.erengulbahar.dryapp.model.ApList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AdminAppointmentFList extends Fragment
{
    private FirebaseFirestore firebaseFirestore;
    private FragmentAdminAppointmentFListBinding adminAppointmentFListBinding;
    ArrayList<ApFList> apFListArrayList;
    AdminFAdapter adminFAdapter;

    public AdminAppointmentFList()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        adminAppointmentFListBinding = FragmentAdminAppointmentFListBinding.inflate(inflater,container,false);
        View view = adminAppointmentFListBinding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getData();

        //We connect adapter here.
        apFListArrayList = new ArrayList<>();

        adminAppointmentFListBinding.recyclerView4.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adminFAdapter = new AdminFAdapter(apFListArrayList);
        adminAppointmentFListBinding.recyclerView4.setAdapter(adminFAdapter);
    }

    public void getData()
    {
        //We list down datas here with recycler view.
        firebaseFirestore.collection("AppointmentsF").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                {
                    Toast.makeText(getContext(), error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null)
                {
                    for(DocumentSnapshot snapshot : value.getDocuments())
                    {
                        Map<String, Object> data = snapshot.getData();

                        String typeOfClothe = (String) data.get("type");
                        String userId = (String) data.get("id");
                        String userEmail = (String) data.get("useremail");
                        String userName = (String) data.get("username");
                        String userSurname = (String) data.get("usersurname");
                        String userAddress = (String) data.get("useraddress");
                        String userPhone = (String) data.get("userphone");
                        String userCount = (String) data.get("usercount");
                        String userPrice = (String) data.get("userprice");
                        Object time = data.get("date");

                        ApFList apFList = new ApFList(typeOfClothe,userId,userEmail,userName,userSurname,userAddress,userPhone,userCount,userPrice,time);
                        apFListArrayList.add(apFList);
                    }

                    adminFAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}