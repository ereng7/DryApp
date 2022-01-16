package com.erengulbahar.dryapp.user;

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

import com.erengulbahar.dryapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class FurniturePanel extends Fragment
{
    private FirebaseAuth auth;

    public FurniturePanel()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        //Add features of menu.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_furniture_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //If you choose furniture icon, you'll go to function.
        ImageView furnitures = view.findViewById(R.id.furnituresIcon);
        furnitures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFurnitures(v);
            }
        });

        //If you choose carpet icon, you'll go to function.
        ImageView carpet = view.findViewById(R.id.carpetIcon);
        carpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCarpet(v);
            }
        });

        //If you choose pillow icon, you'll go to function.
        ImageView pillow = view.findViewById(R.id.pillowIcon);
        pillow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPillow(v);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        //Initialize options menu here.
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //If you click sign out, you'll log out.
        if(item.getItemId()==R.id.signout)
        {
            auth.signOut();
            NavDirections action = FurniturePanelDirections.actionFurniturePanelToLoginPanel();
            Navigation.findNavController(requireView()).navigate(action);
        }

        //If you choose appointments, you'll see appointment lists.
        else if(item.getItemId() == R.id.appointments)
        {
            NavDirections action = FurniturePanelDirections.actionFurniturePanelToAppointmentFList();
            Navigation.findNavController(requireView()).navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectFurnitures(View view)
    {
        //You'll go to appoinmentf panel.
        FurniturePanelDirections.ActionFurniturePanelToAppointmentFPanel action = FurniturePanelDirections.actionFurniturePanelToAppointmentFPanel();
        action.setType("Furniture");
        Navigation.findNavController(view).navigate(action);
    }

    public void selectCarpet(View view)
    {
        //You'll go to appointmentf panel.
        FurniturePanelDirections.ActionFurniturePanelToAppointmentFPanel action = FurniturePanelDirections.actionFurniturePanelToAppointmentFPanel();
        action.setType("Carpet");
        Navigation.findNavController(view).navigate(action);
    }

    public void selectPillow(View view)
    {
        //You'll go to appointmentf panel.
        FurniturePanelDirections.ActionFurniturePanelToAppointmentFPanel action = FurniturePanelDirections.actionFurniturePanelToAppointmentFPanel();
        action.setType("Pillow");
        Navigation.findNavController(view).navigate(action);
    }
}