package com.example.instagramrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class InstagramPost extends AppCompatActivity {
    public ImageView picture;
    public TextView comment;
    public ImageButton btn_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instagram_post);
        Intent intent = getIntent();
        String PicUrl = intent.getStringExtra("PicUrl");
        String Comment = intent.getStringExtra("Comment");
        btn_back = (ImageButton) findViewById(R.id.BackButton);
        picture = (ImageView) findViewById(R.id.InstaImage);
        comment = (TextView) findViewById(R.id.InstaComment);

        Picasso.get().load(PicUrl).into(picture);
        comment.setText(Comment);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
