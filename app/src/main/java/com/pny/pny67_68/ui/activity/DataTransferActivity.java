package com.pny.pny67_68.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.pny.pny67_68.R;

public class DataTransferActivity extends AppCompatActivity {

    TextView outputShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transfer);

        outputShow = findViewById(R.id.outputShow);


        Intent previousActivityIntent = getIntent();

        if(previousActivityIntent != null){
            if(previousActivityIntent.hasExtra("input_data")){
                String data = previousActivityIntent.getStringExtra("input_data");
                outputShow.setText(data);
            }
        }

        Log.d("DataTransferActivityPNY","onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DataTransferActivityPNY","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DataTransferActivityPNY","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DataTransferActivityPNY","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DataTransferActivityPNY","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DataTransferActivityPNY","onDestroy");
    }
}