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

public class SelectionPanel extends Fragment
{
    private FirebaseAuth auth;

    public SelectionPanel()
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
        return inflater.inflate(R.layout.fragment_selection_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //If you choose image icon, you'll go to function.
        ImageView clotheImage = view.findViewById(R.id.clotheIcon);
        clotheImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothe(v);
            }
        });

        //If you choose furniture icon, you'll go to function.
        ImageView furnitureImage = view.findViewById(R.id.furnitureIcon);
        furnitureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                furniture(v);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        //Initialize options menu here.
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.appointments);
        menuItem.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //If you click sign out, you'll log out.
        if(item.getItemId()==R.id.signout)
        {
            auth.signOut();

            NavDirections action = SelectionPanelDirections.actionSelectionPanelToLoginPanel();
            Navigation.findNavController(requireView()).navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }

    public void clothe(View view)
    {
        //You'll go to clothe panel.
        NavDirections action = SelectionPanelDirections.actionSelectionPanelToClothePanel();
        Navigation.findNavController(view).navigate(action);
    }

    public void furniture(View view)
    {
        //You'll go to furniture panel.
        NavDirections action = SelectionPanelDirections.actionSelectionPanelToFurniturePanel();
        Navigation.findNavController(view).navigate(action);
    }
}