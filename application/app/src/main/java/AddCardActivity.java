package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import DataBase.UserCardEntity;

public class AddCardActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Button btnGetPhoto, btnSaveCard;
    private ImageView picture;
    private EditText etCardText, etFileName;
    private Uri mCropImageUri;
    private Uri resultImageUri;

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        btnGetPhoto = findViewById(R.id.btn_get_photo);
        btnSaveCard = findViewById(R.id.btn_save_card);
        picture = findViewById(R.id.image);

        etCardText = findViewById(R.id.et_card_text);
        etFileName = findViewById(R.id.et_file_name);

        btnGetPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropImageUri = null;
                resultImageUri = null;
                onSelectImageClick();
            }
        });

        btnSaveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkExternalPermission()) {
                    tryToSaveImage();
                } else {
                    requestExternalPermission();
                }
            }
        });
    }

    private void tryToSaveImage() {
        String cardText = etCardText.getText().toString();
        String fileNameText = etFileName.getText().toString();
        if (resultImageUri != null && !cardText.isEmpty()) {
            saveImage(resultImageUri, fileNameText, cardText);
        } else {
            Toast.makeText(AddCardActivity.this, "Фото не выбрано или текст карточки не задан", Toast.LENGTH_SHORT).show();
        }
    }

    public static final int PICK_IMAGE = 1;

    @SuppressLint("NewApi")
    public void onSelectImageClick() {
        if (CropImage.isExplicitCameraPermissionRequired(this)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(this);
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
//                Uri resultUri = data.getData();
                showImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.startPickImageActivity(this);
                } else {
                    Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
                }
                break;

            case CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE:
                if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCropImageActivity(mCropImageUri);
                } else {
                    Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_EXTERNAL_STORAGE:
                if(Build.VERSION.SDK_INT > 28){
                    tryToSaveImage();
                }else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tryToSaveImage();
                } else {
                    Toast.makeText(AddCardActivity.this, "Разрешения не даны", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

    private void saveImage(Uri imageUri, String fileName, String text) {
        File imageFile = new File(imageUri.getPath());

        String userCardsFolder = "/UserCards";

        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        File folder = new File(externalStorageDirectory + userCardsFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String resultFileName = imageFile.getName();

        File[] listFiles = folder.listFiles();
        if (listFiles != null && listFiles.length > 0)
            for (File file : listFiles) {
                if (file.getName().equals(resultFileName)) {
                    Toast.makeText(this, "Файл с таким именем уже существует", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        resultFileName = resultFileName.substring(0, resultFileName.length() - 3) + "html";
        File resultImageFile = new File(folder.getPath(), resultFileName);

        try {
            new Client().execute(imageFile.getPath(), resultImageFile.getPath());
            FileOutputStream outstream = new FileOutputStream(resultImageFile);
            UserCardEntity userCardEntity = new UserCardEntity(text, resultImageFile.getPath());
            ((App) getApplication()).getDatabase().userCardDao().insert(userCardEntity);
            outstream.close();
            onBackPressed();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }


    private void showImage(Uri imageUri) {
        picture.setImageURI(imageUri);
        resultImageUri = imageUri;
    }

    private String getFileExtension(File file) {
        return file.getPath().substring(file.getPath().lastIndexOf("."));
    }

    private boolean checkExternalPermission() {
        int result = ContextCompat.checkSelfPermission(AddCardActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestExternalPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddCardActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(AddCardActivity.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AddCardActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
    }
}

