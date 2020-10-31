package com.pny.pny67_68.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.fragment.AddsFragment;
import com.pny.pny67_68.ui.fragment.FirstFragment;

public class FragmentActivity extends AppCompatActivity {

    EditText edit;
    Button setFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        edit = findViewById(R.id.edit);
        setFragment = findViewById(R.id.setFragment);

        linkAddsFragment();

        setFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkMapsFragment();
            }
        });

    }


    public void linkMapsFragment(){
        String editStr = edit.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("edt_input",editStr);

        FirstFragment firstFragment = new FirstFragment();
        firstFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.mapsFragmentContainer,firstFragment);
        fragmentTransaction.commit();

    }

    public void linkAddsFragment(){

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.addsFragmentContainer,new AddsFragment()).
                commit();

    }
}