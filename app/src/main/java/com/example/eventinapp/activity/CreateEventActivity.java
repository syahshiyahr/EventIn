package com.example.eventinapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventinapp.R;
import com.example.eventinapp.api.ApiClient;
import com.example.eventinapp.api.MyApi;
import com.example.eventinapp.responses.BaseResponse;
import com.example.eventinapp.utils.AppPreference;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.Calendar;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtDate, edtEventName;
    private RequestBody name, date;
    DatePickerDialog datePickerDialog;
    private TextView upload, fileChoosen;
    private Button create;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    private String pdfFileName;
    public MultipartBody.Part part;
    private String nameEvent, dateEvent;
    private MyApi myApi;
    public ProgressDialog pDialog;
    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        edtDate = findViewById(R.id.edt_date);
        edtEventName = findViewById(R.id.edt_event_name);
        upload = findViewById(R.id.upload_file_create);
        fileChoosen = findViewById(R.id.name_file_create);
        create = findViewById(R.id.btn_create);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            upload.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        } else {
            upload.setEnabled(true);
        }

        upload.setOnClickListener(this);
        fileChoosen.setOnClickListener(this);
        create.setOnClickListener(this);
        edtEventName.setOnClickListener(this);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog= new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDate.setText(day+"-"+(month+1)+"-"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

//        initDialog();
        myApi = ApiClient.getClient().create(MyApi.class);
        appPreference = new AppPreference(this);

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
            case R.id.btn_create:
                createEvent();
                break;

            case R.id.upload_file_create:
                launchPicker();
                break;
        }
    }

    private void createEvent() {
//        showpDialog();
        // ngambil text dari edit text
        nameEvent = edtEventName.getText().toString();
        dateEvent = edtDate.getText().toString();

        name = RequestBody.create(MediaType.parse("text/plain"), nameEvent);
        date = RequestBody.create(MediaType.parse("text/plain"), dateEvent);


        myApi = ApiClient.getClient().create(MyApi.class);

        String token = appPreference.getToken();
        Call<BaseResponse> regeventCall = myApi.regevent("Bearer "+ token, name, date, part);



        regeventCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
//                        hidepDialog();
                        BaseResponse baseResponse = response.body();
                        Toast.makeText(getApplicationContext(), baseResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        //untuk berpindah activity dari parameter kiri ke parameter kanan
                        Intent intent_signup = new Intent(CreateEventActivity.this, MainActivity.class);
                        startActivity(intent_signup);
                        finish();
                    }
                }
                else {
//                    hidepDialog();
                    Toast.makeText(getApplicationContext(), "problem file : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
//                hidepDialog();
                Log.v("Response gotten is", t.getMessage());
                Toast.makeText(getApplicationContext(), "problem uploading file " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        //untuk berpindah activity dari parameter kiri ke parameter kanan
        Intent signup = new Intent(CreateEventActivity.this, MainActivity.class);
        startActivity(signup);
        finishAffinity();
    }
//    protected void initDialog() {
//
//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage(getString(R.string.msg_loading));
//        pDialog.setCancelable(true);
//    }
//
//
//    private void hidepDialog() {
//        if (pDialog.isShowing()) pDialog.dismiss();
//    }
//
//    private void showpDialog() {
//
//            if (!pDialog.isShowing()) pDialog.show();
//    }

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
            if (pdfPath == null) {
                Toast.makeText(this, "please select a file ", Toast.LENGTH_LONG).show();
                return;
            } else {
                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));

                part = MultipartBody.Part.createFormData("validationFile", file.getName(), requestBody);

            }
        }

    }


    @SuppressLint("WrongViewCast")
    private void displayFromFile(File file) {

        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        pdfFileName = getFileName(uri);
        fileChoosen = findViewById(R.id.name_file_create);
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


}
