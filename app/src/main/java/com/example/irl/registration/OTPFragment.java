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

import com.example.irl.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OTPFragment extends Fragment {


    public OTPFragment() {
        // Required empty public constructor
    }

    private EditText otp;
    private ProgressBar progressBar;
    private Button verifyBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init (view);
    }

    private void init (View view){

        otp = view.findViewById(R.id.otp);
        progressBar =view.findViewById(R.id.progressbar);
        verifyBtn =view.findViewById(R.id.verify_btn);
    }
}
