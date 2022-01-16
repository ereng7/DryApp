package com.erengulbahar.dryapp.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.erengulbahar.dryapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Map;


public class AdminAppointmentsDetails extends Fragment
{
    private FirebaseFirestore firebaseFirestore;
    Calendar calendar;

    TextView type;
    TextView name;
    TextView surname;
    TextView address;
    TextView phone;
    TextView count;
    TextView totalPrice;
    TextView dateText;

    public AdminAppointmentsDetails()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_appointments_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        type = view.findViewById(R.id.typeText2);
        name = view.findViewById(R.id.nameText4);
        surname = view.findViewById(R.id.surnameText4);
        address = view.findViewById(R.id.addressText4);
        phone = view.findViewById(R.id.phoneNumber4);
        count = view.findViewById(R.id.countText4);
        totalPrice = view.findViewById(R.id.totalPrice4);
        dateText = view.findViewById(R.id.dateText3);

        //We get datas from Firebase Firestore.
        if(getArguments() != null)
        {
            String pos = AdminAppointmentsDetailsArgs.fromBundle(getArguments()).getId();

            firebaseFirestore.collection("Appointments").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null)
                    {
                        Toast.makeText(getContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }

                    if(value != null)
                    {
                        for(DocumentSnapshot snapshot : value.getDocuments())
                        {
                            Map<String, Object> data = snapshot.getData();

                            if(data.get("id") == pos)
                            {
                                String typeOfClothe = (String) data.get("type");
                                String userName = (String) data.get("username");
                                String userSurname = (String) data.get("usersurname");
                                String userAddress = (String) data.get("useraddress");
                                String userPhone = (String) data.get("userphone");
                                String userCount = (String) data.get("usercount");
                                String userPrice = (String) data.get("userprice");
                                Object time = data.get("date");

                                calendar.setTimeInMillis((Long) time);
                                String date = ""+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR);
                                String dateTime = ""+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);

                                type.setText("TUR: "+typeOfClothe);
                                name.setText("AD: "+userName);
                                surname.setText("SOYAD: "+userSurname);
                                address.setText("ADRES: "+userAddress);
                                phone.setText("CEP NO: "+userPhone);
                                count.setText("ADET: "+userCount);
                                totalPrice.setText(userPrice);
                                dateText.setText("TARIH: " + date + " " + dateTime);
                            }
                        }
                    }
                }
            });
        }
    }
}