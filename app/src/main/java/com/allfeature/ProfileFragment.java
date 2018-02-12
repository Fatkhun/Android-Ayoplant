package com.allfeature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment {
    public ProfileFragment(){

    }
    @BindView(R.id.input_nama)
    EditText inputNama;
    @BindView(R.id.input_no)
    EditText inputNo;

    @BindView(R.id.simpan_button)
    Button simpan_button;

    @BindView(R.id.ubahpass_button)
    Button btnubahpassword;

    @BindView(R.id.hasil_nama)
    TextView hasilNama;
    @BindView(R.id.hasil_no)
    TextView hasilNo;

    @OnClick(R.id.simpan_button)
    public void submit() {
        String nama = inputNama.getText().toString();
        hasilNama.setText(nama);
        String no = inputNo.getText().toString();
        hasilNo.setText(no);
    }


    private Spinner spinnerr;
    TextView textlihat;
    String[] negara;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(getActivity());

        spinnerr = (Spinner) view.findViewById(R.id.spinner1);
        textlihat = (TextView) view.findViewById(R.id.hasil_negara);
        negara = getResources().getStringArray(R.array.android_dropdown_arrays);
        btnubahpassword = (Button) view.findViewById(R.id.ubahpass_button);

        btnubahpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });

        spinnerr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                textlihat.setText(negara[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }


}
