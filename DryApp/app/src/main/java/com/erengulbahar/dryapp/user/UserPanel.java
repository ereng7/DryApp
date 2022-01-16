package com.erengulbahar.dryapp.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erengulbahar.dryapp.R;
import com.erengulbahar.dryapp.databinding.FragmentUserPanelBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserPanel extends Fragment
{
    private FragmentUserPanelBinding binding;
    FirebaseAuth auth;

    public UserPanel()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Initialize FirebaseAuth.
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentUserPanelBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser firebaseUser = auth.getCurrentUser();

        //We are checking if user logged in.
        if(firebaseUser != null)
        {
            NavDirections action = UserPanelDirections.actionUserPanelToSelectionPanel();
            Navigation.findNavController(view).navigate(action);
        }

        //If choose sign in icon, you'll go to function.
        binding.userSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                userLogin(v);
            }
        });

        //If choose sign up icon, you'll go to function.
        binding.userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                userRegister(v);
            }
        });
    }

    public void userLogin(View view)
    {
        //We get information in edit text.
        String email = binding.userEmail.getText().toString();
        String password = binding.userPassword.getText().toString();

        //We are checking if text are blanks or not.
        if(email.equals("") || password.equals(""))
        {
            Toast.makeText(getContext(),"Enter email and password !",Toast.LENGTH_SHORT).show();
        }

        //If you write your password and email correctly, you can log in. So we check here.
        else
        {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    NavDirections action = UserPanelDirections.actionUserPanelToSelectionPanel();
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

    public void userRegister(View view)
    {
        //We get information in edit text.
        String email = binding.userEmail.getText().toString();
        String password = binding.userPassword.getText().toString();

        //We are checking if text are blanks or not.
        if(email.equals("") || password.equals(""))
        {
            Toast.makeText(getContext(),"Enter email and password !",Toast.LENGTH_SHORT).show();
        }

        //If you register first time, you have been successfully registered here.
        else
        {
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    NavDirections action = UserPanelDirections.actionUserPanelToSelectionPanel();
                    Navigation.findNavController(view).navigate(action);
                    Toast.makeText(getContext(),"Registration successfully !",Toast.LENGTH_SHORT).show();
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