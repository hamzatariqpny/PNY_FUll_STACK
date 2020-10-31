package com.pny.pny67_68.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pny.pny67_68.R;

public class MainActivity extends AppCompatActivity {

    Button outputButton;
    EditText inputEdt;
    TextView outputTxt;

    String inputStr;

    // Starting point of an Activity .
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  this line links you java to layout
        setContentView(R.layout.activity_main);
        Log.d("MainActivityPNY","onCreate");
        outputButton = findViewById(R.id.showOutput);
        inputEdt = findViewById(R.id.inputEdt);
        outputTxt = findViewById(R.id.outputTxt);

        outputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputStr = inputEdt.getText().toString();

                Intent goToNextActivity = new Intent(MainActivity.this,DataTransferActivity.class);
                goToNextActivity.putExtra("input_data",inputStr);
                startActivity(goToNextActivity);


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivityPNY","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivityPNY","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivityPNY","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivityPNY","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivityPNY","onDestroy");
    }
}