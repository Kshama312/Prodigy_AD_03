package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTime;
    private ImageView Start, Stop, Reset;
    private Handler handler;
    private long startTime, timeInMilliseconds = 0;
    private boolean running;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTime = findViewById(R.id.textViewTime);
        Start = findViewById(R.id.Start);
        Stop = findViewById(R.id.Stop);
        Reset = findViewById(R.id.Reset);

        handler = new Handler();

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch();
            }
        });
    }

    private void startStopwatch() {
        if (!running) {
            startTime = System.currentTimeMillis() - timeInMilliseconds;
            handler.postDelayed(updateTimerThread, 0);
            running = true;
        }
    }

    private void stopStopwatch() {
        if (running) {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimerThread);
            running = false;
        }
    }

    private void resetStopwatch() {
        if (running) {
            handler.removeCallbacks(updateTimerThread);
            running = false;
        }
        timeInMilliseconds = 0;
        textViewTime.setText("00:00:0");
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime;

            int seconds = (int) (timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            int milliseconds = (int) ((timeInMilliseconds % 1000) / 100); // Get the first digit of milliseconds

            seconds = seconds % 60;

            textViewTime.setText(String.format("%02d:%02d:%d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 100); // Update every 100 milliseconds
        }
    };
}
