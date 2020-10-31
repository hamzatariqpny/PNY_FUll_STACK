package com.pny.pny67_68.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pny.pny67_68.R;


public class Maps2Fragment extends Fragment {

    TextView maps2Txt;

    public Maps2Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_maps2, container, false);

        maps2Txt = v.findViewById(R.id.maps2Txt);

        maps2Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkMaps2Fragment();
            }
        });

        Bundle b = getArguments();

        if(b!= null){
            if(b.containsKey("edit_age")){
                int edt_input = b.getInt("edit_age");
                maps2Txt.setText(edt_input+"");
            }
        }

        return v;
    }

    public void linkMaps2Fragment(){

        getActivity().getSupportFragmentManager().
                beginTransaction().
                add(R.id.mapsFragmentContainer,new FirstFragment()).
                commit();

    }
}