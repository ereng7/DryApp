package com.erengulbahar.dryapp.admin;

import android.content.Context;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CostPanel extends Fragment
{
    DBHelper dbHelper;

    TextView jacket;
    TextView coat;
    TextView shirt;
    TextView t_shirt;
    TextView trouser;
    TextView shorts;
    TextView furniture;
    TextView carpet;
    TextView pillow;

    EditText jacketCost;
    EditText coatCost;
    EditText shirtCost;
    EditText t_shirtCost;
    EditText trouserCost;
    EditText shortsCost;
    EditText furnitureCost;
    EditText carpetCost;
    EditText pillowCost;

    Button saveButton;

    public CostPanel()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cost_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());

        jacket = view.findViewById(R.id.infoText);
        coat = view.findViewById(R.id.infoText2);
        shirt = view.findViewById(R.id.infoText3);
        t_shirt = view.findViewById(R.id.infoText4);
        trouser = view.findViewById(R.id.infoText5);
        shorts = view.findViewById(R.id.infoText6);
        furniture = view.findViewById(R.id.infoText7);
        carpet = view.findViewById(R.id.infoText8);
        pillow = view.findViewById(R.id.infoText9);

        jacketCost = view.findViewById(R.id.jacketCost);
        coatCost = view.findViewById(R.id.coatCost);
        shirtCost = view.findViewById(R.id.shirtCost);
        t_shirtCost = view.findViewById(R.id.tshirtCost);
        trouserCost = view.findViewById(R.id.trouserCost);
        shortsCost = view.findViewById(R.id.shortCost);
        furnitureCost = view.findViewById(R.id.furnitureCost);
        carpetCost = view.findViewById(R.id.carpetCost);
        pillowCost = view.findViewById(R.id.pillowCost);

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(v);
            }
        });
    }

    public void saveData(View view)
    {
        //We set cost of goods and save local database.
        String cost1 = jacketCost.getText().toString();
        String cost2 = coatCost.getText().toString();
        String cost3 = shirtCost.getText().toString();
        String cost4 = t_shirtCost.getText().toString();
        String cost5 = trouserCost.getText().toString();
        String cost6 = shortsCost.getText().toString();
        String cost7 = furnitureCost.getText().toString();
        String cost8 = carpetCost.getText().toString();
        String cost9 = pillowCost.getText().toString();

        if(cost1.equals("") || cost2.equals("") || cost3.equals("") || cost4.equals("") || cost5.equals("") || cost6.equals("") || cost7.equals("") || cost8.equals("") || cost9.equals(""))
        {
            Toast.makeText(getContext(),"Please fill the gaps !",Toast.LENGTH_LONG).show();
        }

        else
        {
            dbHelper.insertData(cost1,cost2,cost3,cost4,cost5,cost6,cost7,cost8,cost9);

            NavDirections action = CostPanelDirections.actionCostPanelToAdminSettings();
            Navigation.findNavController(view).navigate(action);
        }
    }
}