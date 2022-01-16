package com.erengulbahar.dryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.erengulbahar.dryapp.user.UserPanelDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPanel extends Fragment
{
    public LoginPanel()
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
        return inflater.inflate(R.layout.fragment_login_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //If you choose admin icon, you'll go to function.
        ImageView adminView = view.findViewById(R.id.adminIcon);
        adminView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin(v);
            }
        });

        //If you choose user icon, you'll go to function.
        ImageView userView = view.findViewById(R.id.userIcon);
        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user(v);
            }
        });
    }

    public void admin(View view)
    {
        //You are going to admin panel.
        NavDirections action = LoginPanelDirections.actionLoginPanelToAdminPanel();
        Navigation.findNavController(view).navigate(action);
    }

    public void user(View view)
    {
        //You are going to user panel.
        NavDirections action = LoginPanelDirections.actionLoginPanelToUserPanel();
        Navigation.findNavController(view).navigate(action);
    }
}