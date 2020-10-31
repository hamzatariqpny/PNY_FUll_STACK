package com.pny.pny67_68.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pny.pny67_68.R;


public class FirstFragment extends Fragment {


    TextView mapTxt;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        mapTxt = v.findViewById(R.id.mapTxt);


        Bundle b = getArguments();

        mapTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkMaps2Fragment();
            }
        });


        if(b!= null){
            if(b.containsKey("edt_input")){
                String edt_input = b.getString("edt_input");
                mapTxt.setText(edt_input);
            }
        }



        return v ;
    }

    public void linkMaps2Fragment(){

        Maps2Fragment maps2Fragment = new Maps2Fragment();


        Bundle bundle = new Bundle();
        bundle.putInt("edit_age",30);

        maps2Fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().
                beginTransaction().
                add(R.id.mapsFragmentContainer,maps2Fragment).
                commit();

    }

}