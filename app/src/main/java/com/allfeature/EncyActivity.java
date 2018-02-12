package com.allfeature;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class EncyActivity extends AppCompatActivity {

    private static final String TAG = "EncyDetailActivity";

    private static final String BASE_IMAGE_URL = "http://45.77.171.22:3000/img-ency/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ency);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageView encyDetailImageImageView = findViewById(R.id.iv_ency_detail_image);
        TextView encyDetailDescriptionTextView = findViewById(R.id.tv_ency_detail_description);
        TextView encyDetailTempTextView = findViewById(R.id.tv_ency_detail_temp);
        TextView encyDetailHumadityTextView = findViewById(R.id.tv_ency_detail_humadity);
        Intent intent = getIntent();

        String imagePath = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("desc");
        float temp = intent.getFloatExtra("temp", 0);
        float humadity = intent.getFloatExtra("humidity", 0);

        Uri imageUrl = Uri.parse(BASE_IMAGE_URL + imagePath);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(name);
        }

        GlideApp.with(encyDetailImageImageView)
                .load(imageUrl)
                .placeholder(R.drawable.anggrek_violet)
                .into(encyDetailImageImageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            encyDetailDescriptionTextView.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            encyDetailDescriptionTextView.setText(Html.fromHtml(description));
        }

        String tempstring = String.valueOf(temp) + " °C adalah suhu ideal";
        String humaditysrting = String.valueOf(humadity) + " °C adalah kelembapan ideal";

        encyDetailTempTextView.setText(tempstring);
        encyDetailHumadityTextView.setText(humaditysrting);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
