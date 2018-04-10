package kr.hs.dgsw.flow.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.interfaces.FlowService;
import kr.hs.dgsw.flow.model.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class SignUpActivity extends AppCompatActivity {

    private Profile profile;

    /**
     * Android UI module value
     */

    private Spinner genderSpinner;
    private Spinner classSpinner;
    private Spinner numberSpinner;

    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setSpinnerItem();

        signupBtn = findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitTest();
            }
        });
    }

    /**
     * 스피너 메뉴 아이템 설정 및 이벤트 리스너 등록
     */

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

    private void retrofitTest() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.80.163.99:4000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        FlowService service = retrofit.create(FlowService.class);
        Call<JSONObject> request = service.getTest();

        request.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d("response", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }
}