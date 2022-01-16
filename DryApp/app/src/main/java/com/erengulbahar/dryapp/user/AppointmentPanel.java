package com.erengulbahar.dryapp.user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AppointmentPanel extends Fragment
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
    String userName;
    String userSurname;
    String userAddress;
    String userPhoneNumber;
    String userCount;
    String userPrice;
    String userEmail;

    int temp;
    int temporary;
    int jacketCost;
    int coatCost;
    int shirtCost;
    int tshirtCost;
    int trouserCost;
    int shortsCost;
    int furnitureCost;
    int carpetCost;
    int pillowCost;

    int price1;
    int price2;
    int price3;
    int price4;
    int price5;
    int price6;
    int price7;
    int price8;
    int price9;

    public AppointmentPanel()
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
        return inflater.inflate(R.layout.fragment_appointment_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM Costs",null);
        cursor.moveToLast();

        jacketCost = cursor.getColumnIndex("Jacket");
        coatCost = cursor.getColumnIndex("Coat");
        shirtCost = cursor.getColumnIndex("Shirt");
        tshirtCost = cursor.getColumnIndex("TShirt");
        trouserCost = cursor.getColumnIndex("Trouser");
        shortsCost = cursor.getColumnIndex("Short");
        furnitureCost = cursor.getColumnIndex("Furniture");
        carpetCost = cursor.getColumnIndex("Carpet");
        pillowCost = cursor.getColumnIndex("Pillow");

        price1 = Integer.parseInt(cursor.getString(jacketCost));
        price2 = Integer.parseInt(cursor.getString(coatCost));
        price3 = Integer.parseInt(cursor.getString(shirtCost));
        price4 = Integer.parseInt(cursor.getString(tshirtCost));
        price5 = Integer.parseInt(cursor.getString(trouserCost));
        price6 = Integer.parseInt(cursor.getString(shortsCost));
        price7 = Integer.parseInt(cursor.getString(furnitureCost));
        price8 = Integer.parseInt(cursor.getString(carpetCost));
        price9 = Integer.parseInt(cursor.getString(pillowCost));

        cursor.close();

        name = view.findViewById(R.id.nameText);
        surname = view.findViewById(R.id.surnameText);
        address = view.findViewById(R.id.addressText);
        phone = view.findViewById(R.id.phoneNumber);
        count = view.findViewById(R.id.countText);
        totalPrice = view.findViewById(R.id.totalPrice);

        id="0";

        //We get type of clothe.
        if(getArguments()!=null)
        {
            typeOfClothe = AppointmentPanelArgs.fromBundle(getArguments()).getType();
        }

        Button confirmButton = view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton(v);
            }
        });

        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                calculateButton(v);
            }
        });
    }

    public void confirmButton(View view)
    {
        //If whole of texts aren't blank, we can save these datas to Firebase.
        FirebaseUser user = auth.getCurrentUser();

        userName = name.getText().toString();
        userSurname = surname.getText().toString();
        userAddress = address.getText().toString();
        userPhoneNumber = phone.getText().toString();
        userCount = count.getText().toString();
        userPrice = totalPrice.getText().toString();
        userEmail = user.getEmail();

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

            firebaseFirestore.collection("Appointments").add(datas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    NavDirections action = AppointmentPanelDirections.actionAppointmentPanelToAppointmentList();
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

    public void calculateButton(View view)
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
            if(typeOfClothe == "Jacket")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price1;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }

            else if(typeOfClothe == "Coat")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price2;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }

            else if(typeOfClothe == "Shirt")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price3;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }

            else if(typeOfClothe == "T-Shirt")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price4;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }

            else if(typeOfClothe == "Trouser")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price5;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }

            else if(typeOfClothe == "Short")
            {
                temp = Integer.parseInt(counter);
                int totalOfPrice = temp*price6;
                String sTotal = Integer.toString(totalOfPrice);
                totalPrice.setText("Total Price: "+sTotal +" TL");
            }
        }
    }
}