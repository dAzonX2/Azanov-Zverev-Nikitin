package com.example.irl.registration;


import android.content.Intent;
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

import com.example.irl.MainActivity;
import com.example.irl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.irl.registration.CreateAccountFragment.VALID_EMAIL_ADDRESS_REGEX;

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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailORpfone.setError(null);
                password.setError(null);


                if (emailORpfone.getText().toString().isEmpty()){
                    emailORpfone.setError("Повторите");
                    return;
                }
                if (password.getText().toString().isEmpty()){
                    password.setError("Повторите");
                    return;
                }

                if(VALID_EMAIL_ADDRESS_REGEX.matcher(emailORpfone.getText().toString()).find()){
                    progressBar.setVisibility(View.VISIBLE);
                    login(emailORpfone.getText().toString());

                } else if (emailORpfone.getText().toString().matches("\\d{10}")) {
                    progressBar.setVisibility(View.VISIBLE);

                    FirebaseFirestore.getInstance().collection("users").whereEqualTo("phone", emailORpfone.getText().toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<DocumentSnapshot> document = task.getResult().getDocuments();
                                        if (document.isEmpty()) {
                                            emailORpfone.setError("номер телефона не найден");
                                            progressBar.setVisibility(View.INVISIBLE);
                                            return;
                                        } else {
                                            String email = document.get(0).get("email").toString();
                                            login(email);
                                        }
                                    } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }

                            });

                } else {
                    emailORpfone.setError("Повторите ввод коректного Email или Телефона");
                    return;
                }

            }
        });

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

    private void login(String email) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent userIntent = new Intent(getContext(), UsernameActivity.class);
                    startActivity(userIntent);
                    getActivity().finish();
                 }else {
                     String error = task.getException().getMessage();
                     Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                     progressBar.setVisibility(View.INVISIBLE);
                }


            }
        });
    }
}
