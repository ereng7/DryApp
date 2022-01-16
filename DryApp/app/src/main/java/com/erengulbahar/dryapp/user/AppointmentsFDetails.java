package com.erengulbahar.dryapp.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erengulbahar.dryapp.R;
import com.erengulbahar.dryapp.databinding.FragmentAppointmentsFDetailsBinding;
import com.erengulbahar.dryapp.model.ApFList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class AppointmentsFDetails extends Fragment
{
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FragmentAppointmentsFDetailsBinding appointmentsFDetailsBinding;
    Calendar calendar;

    TextView type;
    TextView name;
    TextView surname;
    TextView address;
    TextView phone;
    TextView count;
    TextView totalPrice;
    TextView dateText;
    Button cancelButton;
    String myId;
    String userEmail;
    String myEmail;

    public AppointmentsFDetails()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Initialize FirebaseFirestore and FirebaseAuth.
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        calendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        appointmentsFDetailsBinding = FragmentAppointmentsFDetailsBinding.inflate(inflater,container,false);
        View view = appointmentsFDetailsBinding.getRoot();

        return view;
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
        cancelButton = view.findViewById(R.id.cancelButton2);
        dateText = view.findViewById(R.id.dateText2);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(v);
            }
        });

        //We get datas from Firebase Firestore.
        if(getArguments() != null)
        {
            myEmail = firebaseAuth.getCurrentUser().getEmail();

            String pos = AppointmentsFDetailsArgs.fromBundle(getArguments()).getId();

            firebaseFirestore.collection("AppointmentsF").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("useremail",myEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null)
                    {
                        Toast.makeText(getContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }

                    if(value != null)
                    {
                        for(DocumentSnapshot snapshot : value.getDocuments())
                        {
                            Map<String, Object> data = snapshot.getData();

                            if(data.get("id") == pos)
                            {
                                String userId = (String) data.get("docId");
                                myId = userId;
                                String typeOfClothe = (String) data.get("type");
                                String userName = (String) data.get("username");
                                String userSurname = (String) data.get("usersurname");
                                String userAddress = (String) data.get("useraddress");
                                String userPhone = (String) data.get("userphone");
                                String userCount = (String) data.get("usercount");
                                String userPrice = (String) data.get("userprice");
                                userEmail = (String) data.get("useremail");
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

    public void deleteData(View view)
    {
        //We check to equal user which logged in and email which saved on firebase. If they are equal, we'll delete datas from Firebase.
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String currentEmail = firebaseUser.getEmail();

        if(currentEmail.equals(userEmail))
        {
            firebaseFirestore.collection("AppointmentsF").whereEqualTo("docId",myId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    if(task.isComplete() && !task.getResult().isEmpty())
                    {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String documentId = documentSnapshot.getId();

                        firebaseFirestore.collection("AppointmentsF").document(documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused)
                            {
                                Toast.makeText(getContext(),"Data has been deleted successfully !",Toast.LENGTH_SHORT).show();
                                NavDirections action = AppointmentsFDetailsDirections.actionAppointmentsFDetailsToAppointmentFList();
                                Navigation.findNavController(view).navigate(action);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getContext(),"Data couldn't be deleted !",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }
}