package com.erengulbahar.dryapp.admin;

import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.erengulbahar.dryapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminAppointmentPanel extends Fragment
{
    private FirebaseAuth auth;

    ImageView clothe;
    ImageView furniture;
    TextView clotheText;
    TextView furnitureText;

    public AdminAppointmentPanel()
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
        return inflater.inflate(R.layout.fragment_admin_appointment_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        clotheText = view.findViewById(R.id.clotheText2);
        furnitureText = view.findViewById(R.id.furnitureText2);

        clothe = view.findViewById(R.id.clotheIcon2);
        furniture = view.findViewById(R.id.furnitureIcon2);

        //We choose icon which we want.
        clothe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectClothe(v);
            }
        });

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFurniture(v);
            }
        });
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

            NavDirections action = AdminAppointmentPanelDirections.actionAdminAppointmentPanelToLoginPanel();
            Navigation.findNavController(requireView()).navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectClothe(View view)
    {
        NavDirections action = AdminAppointmentPanelDirections.actionAdminAppointmentPanelToAdminAppointmentList();
        Navigation.findNavController(view).navigate(action);
    }

    public void selectFurniture(View view)
    {
        NavDirections action = AdminAppointmentPanelDirections.actionAdminAppointmentPanelToAdminAppointmentFList();
        Navigation.findNavController(view).navigate(action);
    }
}