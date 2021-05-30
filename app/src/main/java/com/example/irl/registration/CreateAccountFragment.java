package com.example.irl.registration;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {


    public CreateAccountFragment() {
        // Required empty public constructor
    }

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    private EditText email, password, confirmPassword;
    private Button createAccountBtn;
    private ProgressBar progressBar;
    private TextView loginTV;
    private FirebaseAuth firebaseAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init (view);

        firebaseAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).setFrament(new LoginFragment());
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                password.setError(null);
                confirmPassword.setError(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if(email.getText().toString().isEmpty()){
                        email.setError("required!");
                        return;

                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if(password.getText().toString().isEmpty()){
                        password.setError("required!");
                        return;

                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if(confirmPassword.getText().toString().isEmpty()){
                        confirmPassword.setError("required!");
                        return;

                    }
                }
                if(!email.getText().toString().matches(EMAIL_REGEX)){
                    email.setError("Пожалуйста, введите существующую почту");
                    return;
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    confirmPassword.setError("Пароли не совпадают!");
                    return;
                }

                createAccount();

            }
        });
    }

    private void init (View view){

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirm_password);
        createAccountBtn = view.findViewById(R.id.create_account_btn);
        progressBar =view.findViewById(R.id.progressbar);
        loginTV =view.findViewById(R.id.login_text);
    }

    private void createAccount(){
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    ((RegisterActivity)getActivity()).setFrament(new OTPFragment());
                }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
