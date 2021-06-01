package com.example.irl.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.irl.MainActivity;
import com.example.irl.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsernameActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private Button removeBtn, createAccountBtn;
    private EditText username;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        init();

         profileImageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Dexter.withContext(UsernameActivity.this)
                         .withPermissions(
                                 Manifest.permission.READ_EXTERNAL_STORAGE,
                                 Manifest.permission.WRITE_EXTERNAL_STORAGE
                         ).withListener(new MultiplePermissionsListener() {
                     @Override
                     public void onPermissionsChecked(MultiplePermissionsReport report) {

                         if (report.areAllPermissionsGranted()){
                             selectImage();
                         }else {
                             Toast.makeText(UsernameActivity.this, "please allow Permissions", Toast.LENGTH_SHORT).show();
                         }

                     }

                     @Override
                     public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken permissionToken) {

                     }

                     @Override
                     public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                 }).check();

             }
         });
    }

    private void init(){
        profileImageView= findViewById(R.id.profile_image);
        createAccountBtn= findViewById(R.id.create_account_btn);
        removeBtn= findViewById(R.id.resend_btn);
        progressBar= findViewById(R.id.progressbar);
        username= findViewById(R.id.username);

    }

    private void selectImage(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityMenuIconColor(getResources().getColor(R.color.colorAccent))
                .setBackgroundColor(getResources().getColor(android.R.color.white))
                .setActivityTitle("Profile photo")
                .setFixAspectRatio(true)
                .setAspectRatio(1,1)
                .start(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri photoUrl = result.getUri();

                Glide
                        .with(this)
                        .load(photoUrl)
                        .centerCrop()
                        .placeholder(R.drawable.profileplaceholder)
                        .into(profileImageView);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
