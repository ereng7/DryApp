package com.erengulbahar.dryapp.user;

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
import com.erengulbahar.dryapp.adapter.ApFAdapter;
import com.erengulbahar.dryapp.databinding.FragmentAppointmentFListBinding;
import com.erengulbahar.dryapp.model.ApFList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AppointmentFList extends Fragment
{
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FragmentAppointmentFListBinding appointmentFListBinding;
    ArrayList<ApFList> apFListArrayList;
    ApFAdapter apFAdapter;
    String myEmail;

    public AppointmentFList()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        appointmentFListBinding = FragmentAppointmentFListBinding.inflate(inflater,container,false);
        View view = appointmentFListBinding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getData();

        //We connect adapter here.
        apFListArrayList = new ArrayList<>();

        appointmentFListBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));
        apFAdapter = new ApFAdapter(apFListArrayList);
        appointmentFListBinding.recyclerView2.setAdapter(apFAdapter);
    }

    public void getData()
    {
        //We list down datas here with recycler view.
        myEmail = firebaseAuth.getCurrentUser().getEmail();

        firebaseFirestore.collection("AppointmentsF").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("useremail",myEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                {
                    Toast.makeText(getContext(), error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
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

                    apFAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}