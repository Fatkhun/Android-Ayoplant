package com.allfeature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {
    LineChart lineChart;

    private static final String TAG = "PlantDetailActivity";

    private static final String BASE_IMAGE_URL = "http://45.77.171.22:3000/img-plant/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        lineChart = findViewById(R.id.lineChart);
        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();

        double x = 0;
        int numDataPoints = 1000;
        for (int i = 0; i<numDataPoints;i++){
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            yAXESsin.add(new Entry(sinFunction,i));
            yAXEScos.add(new Entry(cosFunction,i));
            //xAXES.add(i,String.valueOf(x));
        }
        /*String[] xaxes = new String[xAXES.size()];
        for (int i = 0; i<xAXES.size();i++){
            xaxes[i] = xAXES.get(i).toString();
        }*/

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos,"Temperature");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(R.color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin,"Humadity");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(R.color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(65f);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        TextView plantDetailNameTextView = findViewById(R.id.tv_plant_detail_name);
        TextView plantDetailTypeTextView = findViewById(R.id.tv_plant_detail_type);
        TextView plantDetailTempTextView = findViewById(R.id.tv_plant_detail_temp);
        TextView plantDetailHumadityTextView = findViewById(R.id.tv_plant_detail_humadity);
        TextView plantDetailStatusTextView = findViewById(R.id.tv_plant_detail_status);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");
        float temp = intent.getFloatExtra("temp", 0);
        float humadity = intent.getFloatExtra("humadity", 0);

        String status = "";
        if (temp >= 25.0 && temp <= 31.0 && humadity >= 25.0 && humadity <= 31.0){
            status = "Normal";
        }else if (temp <= 24.0 && humadity <= 24.0){
            status = "The plant is not ideal, you must check in Pedia feature for searching temperature and humadity ideal";
        }else {
            status = "The plant is not ideal, you must check in Pedia feature for searching temperature and humadity ideal";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(name);
        }

        String namestring = "Name Plant : " + String.valueOf(name);
        String typesrting = "Type Plant : " + String.valueOf(type);
        String tempstring = "Temperature : " + String.valueOf(temp) + " °C";
        String humaditystring = "Humadity : " + String.valueOf(humadity) + " °C";
        String statusstring = "Status : " + String.valueOf(status);

        plantDetailNameTextView.setText(namestring);
        plantDetailTypeTextView.setText(typesrting);
        plantDetailTempTextView.setText(tempstring);
        plantDetailHumadityTextView.setText(humaditystring);
        plantDetailStatusTextView.setText(statusstring);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
