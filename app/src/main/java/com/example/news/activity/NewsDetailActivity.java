package com.example.news.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import com.example.news.BuildConfig;
import com.example.news.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    private String title,description,content,imageURL,url;
    private ImageView detailNewsImage;
    private TextView detailNewsTitle, detailNewsDescription, detailNewsContent;
    private Button btnReadMore;
    private Context context;
    private Toolbar toolbar;
    private ImageView backButton, shareButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        context = getApplicationContext();

        toolbar = findViewById(R.id.toolbar);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("imageURL");
        url = getIntent().getStringExtra("url");

        initView();
    }

    private void initView(){
        detailNewsTitle = findViewById(R.id.detailNewsTitle);
        detailNewsDescription = findViewById(R.id.detailNewsDescription);
        detailNewsContent = findViewById(R.id.detailNewsContent);
        detailNewsImage = findViewById(R.id.detailNewsImage);
        btnReadMore = findViewById(R.id.btnReadMore);

        detailNewsTitle.setText(title);
        detailNewsDescription.setText(description);
        detailNewsContent.setText(content);
        Picasso.get().load(imageURL).into(detailNewsImage);
        btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebActivity.class);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLink();
            }
        });
    }

    public void shareLink(){
        try{
            Intent share = new Intent(Intent.ACTION_SEND);

            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "News");
            String shareMessage = "\nCool Shit\n";
            shareMessage = shareMessage + url + "\n";

            share.putExtra(Intent.EXTRA_TEXT,shareMessage);
            startActivity(Intent.createChooser(share,"Choose one"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                onBackPressed();
//                break;
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}