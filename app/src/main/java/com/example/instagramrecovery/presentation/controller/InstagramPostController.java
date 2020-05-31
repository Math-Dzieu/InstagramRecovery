package com.example.instagramrecovery.presentation.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.instagramrecovery.SaveImageHelper;
import com.example.instagramrecovery.presentation.view.InstagramPost;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class InstagramPostController {

    private final int PERMISSION_REQUEST_CODE = 1000;
    private InstagramPost view;

    public InstagramPostController(InstagramPost instagramPost) {
        this.view = instagramPost;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onStart(){
        if(ActivityCompat.checkSelfPermission(view, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            view.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onDownloadButtonClick(){
        if(ActivityCompat.checkSelfPermission(view, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(view, "You should grant permission", Toast.LENGTH_SHORT).show();
            view.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            return;
        }
        else{
            AlertDialog dialog = new SpotsDialog.Builder()
                    .setContext(view)
                    .setMessage("Downloading")
                    .build();
            dialog.show();
            dialog.setMessage("Downloading");

            String fileName = UUID.randomUUID().toString()+"jpg";
            Picasso.get().load(view.getPicUrl()).into(new SaveImageHelper(view.getBaseContext(), dialog, view.getApplicationContext().getContentResolver(), fileName, "Image description"));
            Toast.makeText(view.getApplicationContext(), "Image saved !", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(view, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(view, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
