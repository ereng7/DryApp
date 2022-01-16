package com.erengulbahar.dryapp.admin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erengulbahar.dryapp.R;
import com.erengulbahar.dryapp.database.DBHelper;
import com.google.firebase.auth.FirebaseAuth;

public class AdminListPanel extends Fragment
{
    private FirebaseAuth auth;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    TextView jacket;
    TextView coat;
    TextView shirt;
    TextView t_shirt;
    TextView trouser;
    TextView shorts;
    TextView furniture;
    TextView carpet;
    TextView pillow;

    TextView price1;
    TextView price2;
    TextView price3;
    TextView price4;
    TextView price5;
    TextView price6;
    TextView price7;
    TextView price8;
    TextView price9;

    public AdminListPanel()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_list_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        jacket = view.findViewById(R.id.jackets1);
        coat = view.findViewById(R.id.coats1);
        shirt = view.findViewById(R.id.shirts1);
        t_shirt = view.findViewById(R.id.tshirts1);
        trouser = view.findViewById(R.id.trousers1);
        shorts = view.findViewById(R.id.shrts1);
        furniture = view.findViewById(R.id.furnitures1);
        carpet = view.findViewById(R.id.carpets1);
        pillow = view.findViewById(R.id.pillows1);

        price1 = view.findViewById(R.id.price1);
        price2 = view.findViewById(R.id.price2);
        price3 = view.findViewById(R.id.price3);
        price4 = view.findViewById(R.id.price4);
        price5 = view.findViewById(R.id.price5);
        price6 = view.findViewById(R.id.price6);
        price7 = view.findViewById(R.id.price7);
        price8 = view.findViewById(R.id.price8);
        price9 = view.findViewById(R.id.price9);

        //We get datas from local database and we put on text view. So we see which product is how much.
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM Costs",null);
        cursor.moveToLast();

        int jacketCost = cursor.getColumnIndex("Jacket");
        int coatCost = cursor.getColumnIndex("Coat");
        int shirtCost = cursor.getColumnIndex("Shirt");
        int tshirtCost = cursor.getColumnIndex("TShirt");
        int trouserCost = cursor.getColumnIndex("Trouser");
        int shortsCost = cursor.getColumnIndex("Short");
        int furnitureCost = cursor.getColumnIndex("Furniture");
        int carpetCost = cursor.getColumnIndex("Carpet");
        int pillowCost = cursor.getColumnIndex("Pillow");

        price1.setText(cursor.getString(jacketCost) + " TL");
        price2.setText(cursor.getString(coatCost) + " TL");
        price3.setText(cursor.getString(shirtCost) + " TL");
        price4.setText(cursor.getString(tshirtCost) + " TL");
        price5.setText(cursor.getString(trouserCost) + " TL");
        price6.setText(cursor.getString(shortsCost) + " TL");
        price7.setText(cursor.getString(furnitureCost) + " TL");
        price8.setText(cursor.getString(carpetCost) + " TL");
        price9.setText(cursor.getString(pillowCost) + " TL");

        cursor.close();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.admin_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.logout)
        {
            auth.signOut();

            NavDirections action = AdminListPanelDirections.actionAdminListPanelToLoginPanel();
            Navigation.findNavController(requireView()).navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }
}