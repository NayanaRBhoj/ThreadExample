package com.nayana.bhoj.apps.threadexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    Button btn_start;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.btn_start);

        ExampleThread exampleThread = new ExampleThread(10);
        exampleThread.run();

        //one way of invocation
        ExampleRunnable exampleRunnable1 = new ExampleRunnable(10);
        new Thread(exampleRunnable1).start();

        //second way of invocation
        ExampleRunnable exampleRunnable2 = new ExampleRunnable(10);
        exampleRunnable2.run();
    }

    class ExampleThread extends Thread {
        int seconds;

        public ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "started thread: " + i);
                if (i == 5) {
                    //one way of ui updation
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            btn_start.setText("50%");
                        }
                    });

                    //second way of ui updation
                    btn_start.post(new Runnable() {
                        @Override
                        public void run() {
                            btn_start.setText("50%");
                        }
                    });

                    //third way of ui update
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_start.setText("50%");
                        }
                    });
                    //fourth way is creating handler here itself
                    Handler innerThread = new Handler(Looper.getMainLooper());
                    innerThread.post(new Runnable() {
                        @Override
                        public void run() {
                            btn_start.setText("50%");
                        }
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleRunnable implements Runnable {
        int seconds;

        public ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "started thread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
