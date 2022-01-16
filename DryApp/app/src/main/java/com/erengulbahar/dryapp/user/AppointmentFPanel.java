package com.erengulbahar.dryapp.user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.erengulbahar.dryapp.R;
import com.erengulbahar.dryapp.database.DBHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class AppointmentFPanel extends Fragment
{
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    EditText name;
    EditText surname;
    EditText address;
    EditText phone;
    EditText count;
    TextView totalPrice;

    String typeOfClothe;
    String id;

    int temporary;
    int temp;
    int furnitureCost;
    int carpetCost;
    int pillowCost;
    int price1;
    int price2;
    int price3;

    public AppointmentFPanel()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_f_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM Costs",null);
        cursor.moveToLast();

        furnitureCost = cursor.getColumnIndex("Furniture");
        carpetCost = cursor.getColumnIndex("Carpet");
        pillowCost = cursor.getColumnIndex("Pillow");

        price1 = Integer.parseInt(cursor.getString(furnitureCost));
        price2 = Integer.parseInt(cursor.getString(carpetCost));
        price3 = Integer.parseInt(cursor.getString(pillowCost));

        cursor.close();

        name = view.findViewById(R.id.nameText2);
        surname = view.findViewById(R.id.surnameText2);
        address = view.findViewById(R.id.addressText2);
        phone = view.findViewById(R.id.phoneNumber2);
        count = view.findViewById(R.id.countText2);
        totalPrice = view.findViewById(R.id.totalPrice2);

        id="0";

        if(getArguments() != null)
        {
            typeOfClothe = AppointmentFPanelArgs.fromBundle(getArguments()).getType();
        }

        Button confirmButton = view.findViewById(R.id.confirmButton2);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton2(v);
            }
        });

        Button calculateButton = view.findViewById(R.id.calculateButton2);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateButton2(v);
            }
        });
    }

    public void confirmButton2(View view)
    {
        //If whole of texts aren't blank, we can save these datas to Firebase.
        FirebaseUser user = auth.getCurrentUser();

        String userName = name.getText().toString();
        String userSurname = surname.getText().toString();
        String userAddress = address.getText().toString();
        String userPhoneNumber = phone.getText().toString();
        String userCount = count.getText().toString();
        String userPrice = totalPrice.getText().toString();
        String userEmail = user.getEmail();

        if(userName.equals("") || userSurname.equals("") || userAddress.equals("") || userPhoneNumber.equals("") || userCount.equals(""))
        {
            Toast.makeText(getContext(),"Please fill the gaps !",Toast.LENGTH_SHORT).show();
        }

        else if(userPrice.equals("Total Price: 0"))
        {
            Toast.makeText(getContext(),"Please click to calculate button !",Toast.LENGTH_SHORT).show();
        }

        else
        {
            HashMap<String, Object> datas = new HashMap<>();

            String uniqueId = UUID.randomUUID().toString();

            datas.put("type",typeOfClothe);
            datas.put("id",id);
            datas.put("useremail",userEmail);
            datas.put("username",userName);
            datas.put("usersurname",userSurname);
            datas.put("useraddress",userAddress);
            datas.put("userphone",userPhoneNumber);
            datas.put("usercount",userCount);
            datas.put("userprice",userPrice);
            datas.put("docId",uniqueId);
            datas.put("date", System.currentTimeMillis());

            temporary = Integer.parseInt(id);
            temporary++;
            id = Integer.toString(temporary);

            firebaseFirestore.collection("AppointmentsF").add(datas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    NavDirections action = AppointmentFPanelDirections.actionAppointmentFPanelToAppointmentFList();
                    Navigation.findNavController(view).navigate(action);
                    Toast.makeText(getContext(),"Appointment added successfully !",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void calculateButton2(View view)
    {
        //We learn how much it is.
        String counter = count.getText().toString();

        if(count.getText().toString().matches(""))
        {
            Toast.makeText(getContext(),"Please enter count !",Toast.LENGTH_SHORT).show();
            totalPrice.setText("Total Price: 0");
        }

        else
        {
            if(typeOfClothe == "Furniture")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price1;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }

            else if(typeOfClothe == "Carpet")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price2;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }

            else if(typeOfClothe == "Pillow")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price3;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }
        }
    }
}