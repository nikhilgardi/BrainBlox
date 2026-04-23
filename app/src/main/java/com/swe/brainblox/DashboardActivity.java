package com.swe.brainblox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout cardStart, cardReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cardStart = findViewById(R.id.cardStart);
        cardReport = findViewById(R.id.cardReport);

        cardStart.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));

        cardReport.setOnClickListener(v ->
                startActivity(new Intent(this, ReportActivity.class)));
    }
}