package com.yombu.waiverlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityTermsAndConditions extends ActivityBase {

    private TextView tvTermsAndConditions;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        initializeViews();
        attachListeners();

        tvTermsAndConditions.setText(getTerms());
    }

    private void attachListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    private void initializeViews() {
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String getTerms() {
        String tcNarrative = "";

        tcNarrative = getString(R.string.terms_and_conditions);

        return tcNarrative;
    }

}