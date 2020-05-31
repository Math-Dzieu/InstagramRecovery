package com.example.instagramrecovery.presentation.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramrecovery.R;
import com.example.instagramrecovery.presentation.controller.InstagramPostController;
import com.squareup.picasso.Picasso;

/**
 * This activity is the activity who shows the details of the item you clicked on
 */
public class InstagramPost extends AppCompatActivity {
    public ImageView picture;
    public TextView comment;
    public ImageButton btn_back;
    public ImageButton btn_download;
    public InstagramPostController instagramPostController;
    public String PicUrl;
    public String Comment;


    /**
     * When the activity start, defined all element int the activity, recovered Extra data put during the creation of the activity and run function from the controller
     */
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

    /**
     * getter of the pic url
     * @return the pic url
     */
    public String getPicUrl() {
        return PicUrl;
    }

    /**
     * setter of the pic url
     * @param picUrl the pic url
     */
    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    /**
     * getter of the comment
     * @return the comment
     */
    public String getComment() {
        return Comment;
    }

    /**
     * setter of the comment
     * @param comment the comment
     */
    public void setComment(String comment) {
        Comment = comment;
    }
}
