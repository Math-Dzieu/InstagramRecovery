package com.example.instagramrecovery.presentation.view;

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

import com.example.instagramrecovery.R;
import com.example.instagramrecovery.SaveImageHelper;
import com.example.instagramrecovery.presentation.controller.InstagramPostController;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class InstagramPost extends AppCompatActivity {
    public ImageView picture;
    public TextView comment;
    public ImageButton btn_back;
    public ImageButton btn_download;
    public InstagramPostController instagramPostController;
    public String PicUrl;
    public String Comment;



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

        instagramPostController = new InstagramPostController(this);
        instagramPostController.onStart();

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
                instagramPostController.onDownloadButtonClick();
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
