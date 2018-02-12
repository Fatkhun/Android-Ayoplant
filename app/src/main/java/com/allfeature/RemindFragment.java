package com.allfeature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.allfeature.alarmme.AlarmMe;


public class RemindFragment extends Fragment {
    public RemindFragment(){

    }

    Button btnalarm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remind, container, false);

        Spinner plantName = (Spinner) view.findViewById(R.id.spinner);
        btnalarm = (Button) view.findViewById(R.id.setalarm);

        ArrayAdapter<CharSequence> adapterPlant = ArrayAdapter.createFromResource(getActivity(),R.array.namatumbuhan, android.R.layout.simple_spinner_item);
        adapterPlant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plantName.setAdapter(adapterPlant);

        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AlarmMe.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }

}
