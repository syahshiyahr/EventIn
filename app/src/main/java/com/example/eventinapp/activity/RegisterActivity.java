package com.example.eventinapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.eventinapp.R;
import com.example.eventinapp.api.ApiClient;
import com.example.eventinapp.api.MyApi;
import com.example.eventinapp.responses.BaseResponse;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtEmail, edtPassword, edtPhoneNum, edtIdentity;
    private Button register;
    private TextView toSignin, upload, fileChoosen;
    private String nameUser, emailUser, passwordUser, phoneNumUser, identityUser;
    public MultipartBody.Part part;
    private MyApi myApi;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    public ProgressDialog pDialog;
    private String pdfPath;
    private String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toSignin = findViewById(R.id.to_sign_text);
        register = findViewById(R.id.btn_register);
        edtName = findViewById(R.id.edt_nama_lengkap);
        edtEmail = findViewById(R.id.edt_email_reg);
        edtPassword = findViewById(R.id.edt_pass_reg);
        edtPhoneNum = findViewById(R.id.edt_notelp_reg);
        upload = findViewById(R.id.upload_file_reg);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            upload.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        } else {
            upload.setEnabled(true);
        }

        toSignin.setOnClickListener(this);
        upload.setOnClickListener(this);
        register.setOnClickListener(this);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                upload.setEnabled(true);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_sign_text:
                Intent toSign = new Intent (RegisterActivity.this, LoginActivity.class);
                startActivity(toSign);
                break;
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.upload_file_reg:
                launchPicker();
                break;

    }
}


    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }
    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }
    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }


    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle("Select PDF file")
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(path);
            displayFromFile(file);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
            }
        }

    }

    @SuppressLint("WrongViewCast")
    private void displayFromFile(File file) {

        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        pdfFileName = getFileName(uri);
        fileChoosen = findViewById(R.id.file_choosen);
        fileChoosen.setText(pdfFileName);



    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


    private void registerUser() {
        if (pdfPath == null) {
            Toast.makeText(this, "please select a file ", Toast.LENGTH_LONG).show();
            return;
        } else {
            showpDialog();

            // Map is used to multipart the file using okhttp3.RequestBody
            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(pdfPath);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);

            part = MultipartBody.Part.createFormData("file",file.getName(), requestBody);


        // ngambil text dari edit text
        nameUser = edtName.getText().toString();
        emailUser = edtEmail.getText().toString();
        passwordUser = edtPassword.getText().toString();
        phoneNumUser = edtPhoneNum.getText().toString();



        myApi = ApiClient.getClient().create(MyApi.class);

        Call<BaseResponse> registerCall = myApi.register(nameUser, emailUser, passwordUser, phoneNumUser, part);

        registerCall.enqueue(new Callback<BaseResponse>() {
            @Override
               public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                   if (response.isSuccessful()){
                        if (response.body() != null){
                            hidepDialog();
                            BaseResponse baseResponse = response.body();
                            Toast.makeText(getApplicationContext(), baseResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    //untuk berpindah activity dari parameter kiri ke parameter kanan
                    Intent intent_signup = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent_signup);
                    finish();

                        }
                    }else {
                        hidepDialog();
                        Toast.makeText(getApplicationContext(), "problem file", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    hidepDialog();
                    Log.v("Response gotten is", t.getMessage());
                    Toast.makeText(getApplicationContext(), "problem uploading file " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
        });
            //untuk berpindah activity dari parameter kiri ke parameter kanan
            Intent signup = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(signup);
            finishAffinity();


    }
}}
