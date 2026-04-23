package com.swe.brainblox;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    TextView tvReport;
    DBHelper db;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        tvReport = findViewById(R.id.tvReport);
        db = new DBHelper(this);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM results", null);

        int total = c.getCount();
        int correct = 0;

        while (c.moveToNext()) {
            if (c.getInt(3) == 1) correct++;
        }

        int wrong = total - correct;

        tvReport.setText(
                "Total Games: " + total +
                        "\nCorrect: " + correct +
                        "\nWrong: " + wrong
        );
    }
}