package com.erengulbahar.dryapp.admin;

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

public class AdminSettings extends Fragment
{
    private FirebaseAuth auth;

    TextView settingsText;
    TextView listText;
    TextView appointmentText;

    ImageView settingsView;
    ImageView listView;
    ImageView appointmentView;

    public AdminSettings()
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
        return inflater.inflate(R.layout.fragment_admin_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        settingsText = view.findViewById(R.id.settingsText);
        listText = view.findViewById(R.id.listText);
        appointmentText = view.findViewById(R.id.appointmentText);

        settingsView = view.findViewById(R.id.settingsIcon);
        listView = view.findViewById(R.id.listsIcon);
        appointmentView = view.findViewById(R.id.appointmentIcon);

        //We choose icon which we want.
        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSettings(v);
            }
        });

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLists(v);
            }
        });

        appointmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAppointments(v);
            }
        });
    }

    public void selectSettings(View view)
    {
        NavDirections action = AdminSettingsDirections.actionAdminSettingsToCostPanel();
        Navigation.findNavController(view).navigate(action);
    }

    public void selectLists(View view)
    {
        NavDirections action = AdminSettingsDirections.actionAdminSettingsToAdminListPanel();
        Navigation.findNavController(view).navigate(action);
    }

    public void selectAppointments(View view)
    {
        NavDirections action = AdminSettingsDirections.actionAdminSettingsToAdminAppointmentPanel2();
        Navigation.findNavController(view).navigate(action);
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

            NavDirections action = AdminSettingsDirections.actionAdminSettingsToLoginPanel();
            Navigation.findNavController(requireView()).navigate(action);
        }

        return super.onOptionsItemSelected(item);
    }
}