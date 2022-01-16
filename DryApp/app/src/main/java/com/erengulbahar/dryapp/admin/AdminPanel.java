package com.erengulbahar.dryapp.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erengulbahar.dryapp.R;
import com.erengulbahar.dryapp.databinding.FragmentAdminPanelBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminPanel extends Fragment
{
    private FirebaseAuth auth;
    private FragmentAdminPanelBinding binding;

    public AdminPanel()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentAdminPanelBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.adminSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLogin(v);
            }
        });
    }

    public void adminLogin(View view)
    {
        //We log in admin panel.
        String email = binding.adminEmail.getText().toString();
        String password = binding.adminPassword.getText().toString();

        if(email.equals("") || password.equals(""))
        {
            Toast.makeText(getContext(),"Enter email and password !",Toast.LENGTH_SHORT).show();
        }

        else
        {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    NavDirections action = AdminPanelDirections.actionAdminPanelToAdminSettings();
                    Navigation.findNavController(view).navigate(action);
                    Toast.makeText(getContext(),"Login successfully !",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}