package com.example.instagramrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InstagramPost extends AppCompatActivity {
    public ImageView picture;
    public TextView comment;
    public Button btn_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instagram_post);
        Intent intent = getIntent();
        String PicUrl = intent.getStringExtra("PicUrl");
        String Comment = intent.getStringExtra("Comment");
        btn_back = (Button) findViewById(R.id.BackButton);
        picture = (ImageView) findViewById(R.id.InstaImage);
        comment = (TextView) findViewById(R.id.InstaComment);

        comment.setText(Comment);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
