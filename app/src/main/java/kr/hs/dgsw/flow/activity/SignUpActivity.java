package kr.hs.dgsw.flow.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.dgsw.flow.R;

public class SignUpActivity extends AppCompatActivity {
    private Spinner genderSpinner;
    private Spinner classSpinner;
    private Spinner numberSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setSpinnerItem();
    }

    private void setSpinnerItem() {

        ArrayAdapter genderSpinnerAdapter;
        ArrayAdapter classSpinnerAdapter;
        ArrayAdapter numberSpinnerAdapter;

        genderSpinner = (Spinner)findViewById(R.id.gender_spinner);
        classSpinner = (Spinner)findViewById(R.id.class_spinner);
        numberSpinner = (Spinner)findViewById(R.id.number_spinner);

        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("남성");
        genderList.add("여성");

        ArrayList<String> classList = new ArrayList<>();
        classList.add("3학년 1반");
        classList.add("3학년 2반");

        ArrayList<String> numberList = new ArrayList<>();
        for (int i=1; i<=20; i++) { numberList.add(String.valueOf(i)); }

        genderSpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, genderList);
        classSpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, classList);
        numberSpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, numberList);

        genderSpinner.setAdapter(genderSpinnerAdapter);
        classSpinner.setAdapter(classSpinnerAdapter);
        numberSpinner.setAdapter(numberSpinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
