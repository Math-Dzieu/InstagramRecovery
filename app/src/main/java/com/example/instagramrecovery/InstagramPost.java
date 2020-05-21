package com.example.instagramrecovery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.squareup.picasso.Picasso;

import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class InstagramPost extends AppCompatActivity {
    public ImageView picture;
    public TextView comment;
    public ImageButton btn_back;
    public ImageButton btn_download;
    private final int PERMISSION_REQUEST_CODE = 1000;

    public String PicUrl;
    public String Comment;

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instagram_post);
        Intent intent = getIntent();
        PicUrl = intent.getStringExtra("PicUrl");
        Comment = intent.getStringExtra("Comment");
        setPicUrl(PicUrl);
        setComment(Comment);

        btn_back = (ImageButton) findViewById(R.id.BackButton);
        btn_download = (ImageButton) findViewById(R.id.DownloadButton);
        picture = (ImageView) findViewById(R.id.InstaImage);
        comment = (TextView) findViewById(R.id.InstaComment);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        Picasso.get().load(PicUrl).into(picture);
        comment.setText(Comment);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(InstagramPost.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(InstagramPost.this, "You should grant permission", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    return;
                }
                else{
                    AlertDialog dialog = new SpotsDialog.Builder()
                            .setContext(InstagramPost.this)
                            .setMessage("Downloading")
                            .build();
                    dialog.show();
                    dialog.setMessage("Downloading");

                    String fileName = UUID.randomUUID().toString()+"jpg";
                    Picasso.get().load(getPicUrl()).into(new SaveImageHelper(getBaseContext(), dialog, getApplicationContext().getContentResolver(), fileName, "Image description"));
                }
            }
        });
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
