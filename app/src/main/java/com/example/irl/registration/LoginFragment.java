package com.example.irl.registration;


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

import com.example.irl.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }

    private EditText emailORpfone, password;
    private Button loginBtn;
    private ProgressBar progressBar;
    private TextView createAccountTV, forgotPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init (view);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).setFrament(new ForgotPasswordFragment());
            }
        });

        createAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).setFrament(new CreateAccountFragment());
            }
        });

    }

    private void init (View view){

        emailORpfone = view.findViewById(R.id.email_or_phone);
        password = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.login_btn);
        progressBar =view.findViewById(R.id.progressbar);
        createAccountTV =view.findViewById(R.id.create_account_text);
        forgotPassword =view.findViewById(R.id.forgot_password);
    }
}
