package com.allfeature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomepageActivity extends AppCompatActivity {
    private Button plantsfragments;
    private Button encyfragments;
    private Button profilefragments;
    private Button remindfragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        plantsfragments = (Button) findViewById(R.id.buttonplant);
        encyfragments = (Button) findViewById(R.id.button3);
        profilefragments = (Button) findViewById(R.id.buttonprofile);
        remindfragments = (Button) findViewById(R.id.button4);

        plantsfragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                intent.putExtra("index", 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        encyfragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ency = new Intent(getApplicationContext(), TabbedActivity.class);
                ency.putExtra("index", 1);
                ency.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ency);
                finish();
            }
        });

        profilefragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), TabbedActivity.class);
                profile.putExtra("index", 2);
                profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(profile);
                finish();
            }
        });

        remindfragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reminder = new Intent(getApplicationContext(), TabbedActivity.class);
                reminder.putExtra("index", 3    );
                reminder.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(reminder);
                finish();
            }
        });

    }
}
