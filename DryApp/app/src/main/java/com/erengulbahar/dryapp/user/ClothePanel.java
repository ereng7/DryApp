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

public class ClothePanel extends Fragment
{
    private FirebaseAuth auth;

    public ClothePanel()
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
        return inflater.inflate(R.layout.fragment_clothe_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //If you choose jacket icon, you'll go to function.
        ImageView jacket = view.findViewById(R.id.jacketIcon);
        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                selectJacket(v);
            }
        });

        //If you choose coat icon, you'll go to function.
        ImageView coat = view.findViewById(R.id.coatIcon);
        coat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCoat(v);
            }
        });

        //If you choose shirt icon, you'll go to function.
        ImageView shirt = view.findViewById(R.id.shirtIcon);
        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectShirt(v);
            }
        });

        //If you choose t-shirt icon, you'll go to function.
        ImageView tShirt = view.findViewById(R.id.tShirtIcon);
        tShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTShirt(v);
            }
        });

        //If you choose trouser icon, you'll go to function.
        ImageView trouser = view.findViewById(R.id.trouserIcon);
        trouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTrouser(v);
            }
        });

        //If you choose short icon, you'll go to function.
        ImageView shrt = view.findViewById(R.id.shrtIcon);
        shrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectShrt(v);
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
            NavDirections action = ClothePanelDirections.actionClothePanelToLoginPanel();
            Navigation.findNavController(requireView()).navigate(action);
        }

        //If you click appointments, you'll see appointment lists.
        else if(item.getItemId() == R.id.appointments)
        {
            NavDirections action = ClothePanelDirections.actionClothePanelToAppointmentList();
            Navigation.findNavController(requireView()).navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectJacket(View view)
    {
        //You'll go to appointment panel.
        ClothePanelDirections.ActionClothePanelToAppointmentPanel action = ClothePanelDirections.actionClothePanelToAppointmentPanel();
        action.setType("Jacket");
        Navigation.findNavController(view).navigate(action);
    }

    public void selectCoat(View view)
    {
        //You'll go to appointment panel.
        ClothePanelDirections.ActionClothePanelToAppointmentPanel action = ClothePanelDirections.actionClothePanelToAppointmentPanel();
        action.setType("Coat");
        Navigation.findNavController(view).navigate(action);
    }

    public void selectShirt(View view)
    {
        //You'll go to appointment panel.
        ClothePanelDirections.ActionClothePanelToAppointmentPanel action = ClothePanelDirections.actionClothePanelToAppointmentPanel();
        action.setType("Shirt");
        Navigation.findNavController(view).navigate(action);
    }

    public void selectTShirt(View view)
    {
        //You'll go to appointment panel.
        ClothePanelDirections.ActionClothePanelToAppointmentPanel action = ClothePanelDirections.actionClothePanelToAppointmentPanel();
        action.setType("T-Shirt");
        Navigation.findNavController(view).navigate(action);
    }

    public void selectTrouser(View view)
    {
        //You'll go to appointment panel.
        ClothePanelDirections.ActionClothePanelToAppointmentPanel action = ClothePanelDirections.actionClothePanelToAppointmentPanel();
        action.setType("Trouser");
        Navigation.findNavController(view).navigate(action);
    }

    public void selectShrt(View view)
    {
        //You'll go to appointment panel.
        ClothePanelDirections.ActionClothePanelToAppointmentPanel action = ClothePanelDirections.actionClothePanelToAppointmentPanel();
        action.setType("Short");
        Navigation.findNavController(view).navigate(action);
    }
}