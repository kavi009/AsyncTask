package com.example.subba.asynctask;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        ProgressBar myProgressBar;

        public MyAsyncTask(ProgressBar target) {
            myProgressBar = target;
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(int i=0; i<100; i++){
                publishProgress(i);
                SystemClock.sleep(100);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            myProgressBar.setProgress(values[0]);
        }

    }

    Button buttonStart;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    MyAsyncTask asyncTask1, asyncTask2, asyncTask3, asyncTask4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar1 = (ProgressBar)findViewById(R.id.progressbar1);
        progressBar2 = (ProgressBar)findViewById(R.id.progressbar2);
        progressBar3 = (ProgressBar)findViewById(R.id.progressbar3);
        progressBar4 = (ProgressBar)findViewById(R.id.progressbar4);

        buttonStart = (Button)findViewById(R.id.start);
        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                asyncTask1 = new MyAsyncTask(progressBar1);
                asyncTask1.execute();
                asyncTask2 = new MyAsyncTask(progressBar2);
                StartAsyncTaskInParallel(asyncTask2);
                asyncTask3 = new MyAsyncTask(progressBar3);
                asyncTask3.execute();
                asyncTask4 = new MyAsyncTask(progressBar4);
                StartAsyncTaskInParallel(asyncTask4);
                
            }});

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(MyAsyncTask task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

}

