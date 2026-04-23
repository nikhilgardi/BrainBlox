package com.swe.brainblox;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvNumber;
    EditText etAnswer;
    Button btnStart, btnSubmit;

    String currentSequence = "";
    DBHelper db;
    Button btnBack;

    long startTime;
    boolean isGameRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumber = findViewById(R.id.tvNumber);
        etAnswer = findViewById(R.id.etAnswer);
        btnStart = findViewById(R.id.btnStart);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        db = new DBHelper(this);

        btnStart.setOnClickListener(v -> startGame());

        btnSubmit.setOnClickListener(v -> {

            if (!isGameRunning) {
                Toast.makeText(this, "Start the game first!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userInput = etAnswer.getText().toString();

            long endTime = System.currentTimeMillis();
            int timeTaken = (int)((endTime - startTime) / 1000);

            int correct = userInput.equals(currentSequence) ? 1 : 0;

            // ✅ Updated insert with time
            db.insert(currentSequence, userInput, correct, timeTaken);

            Toast.makeText(this,
                    (correct == 1 ? "Correct! " : "Wrong! ") + "Time: " + timeTaken + "s",
                    Toast.LENGTH_SHORT).show();

            isGameRunning = false;
        });
    }

    private void startGame() {

        isGameRunning = true;

        new Thread(() -> {

            StringBuilder seq = new StringBuilder();
            Random r = new Random();

            for (int i = 0; i < 4; i++) {
                seq.append(r.nextInt(10));
            }

            currentSequence = seq.toString();

            runOnUiThread(() -> tvNumber.setText(currentSequence));

            try { Thread.sleep(3000); } catch (Exception ignored) {}

            runOnUiThread(() -> {
                tvNumber.setText("Enter now");
                etAnswer.setText("");

                // ⏱️ Start timer AFTER hiding number
                startTime = System.currentTimeMillis();
            });

        }).start();
    }
}