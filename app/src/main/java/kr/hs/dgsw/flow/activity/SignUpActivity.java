package kr.hs.dgsw.flow.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.helper.Validation;
import kr.hs.dgsw.flow.interfaces.FlowService;
import kr.hs.dgsw.flow.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private User user;

    /**
     * Android UI module value
     */

    private Spinner genderSpinner;
    private Spinner classSpinner;
    private Spinner numberSpinner;

    private Button signupBtn;

    private EditText emailTxt;
    private EditText passwordTxt;
    private EditText passwordReTxt;
    private EditText nameTxt;
    private EditText mobileTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setViewId();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignUpData();
            }
        });
    }

    /**
     * @description
     *
     * 서버로 보낼 회원가입 데이터들을 검증한다.
     */
    private void verifySignUpData() {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString(); // abcDe23@#$%2145
        String passwordRe = passwordReTxt.getText().toString(); // abcDe23@#$%2145
        String name = nameTxt.getText().toString();
        String mobile = mobileTxt.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String classRoom = classSpinner.getSelectedItem().toString();
        String classNumber = numberSpinner.getSelectedItem().toString();

        switch (classRoom) {
            case "3학년 1반":
                classRoom = "1";
                break;
            case "3학년 2반":
                classRoom = "2";
                break;
            default:
                classRoom = "1";
        }

        if (!Validation.isValidEmail(email)) {
            Toast.makeText(getApplicationContext(), "이메일이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            emailTxt.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            Toast.makeText(getApplicationContext(), "비밀번호는 ( 소문자, 대문자, 특수문자 포함해서 8~16 이어야 합니다. )", Toast.LENGTH_SHORT).show();
            passwordTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(password)) {
            Toast.makeText(getApplicationContext(), "비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            passwordTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(passwordRe)) {
            Toast.makeText(getApplicationContext(), "비밀번호 재입력이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            passwordReTxt.requestFocus();
            return;
        }

        if (!Validation.isComparingPassword(password, passwordRe)) {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            passwordReTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(name)) {
            Toast.makeText(getApplicationContext(), "이름이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            nameTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(mobile)) {
            Toast.makeText(getApplicationContext(), "전화 번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            mobileTxt.requestFocus();
            return;
        }

        // encrypt password
        // set request model object.


    }

    /**
     * @description
     *
     *  View ID 객체들을 가르킨다.
     *  Spinner 아이템들을 셋팅하는 함수를 호출
     */

    private void setViewId() {

        /**
         * Button dependencies
         */
        signupBtn = findViewById(R.id.signupBtn);

        /**
        * EditText dependencies
        */
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        passwordReTxt = findViewById(R.id.passwordReTxt);
        nameTxt = findViewById(R.id.nameTxt);
        mobileTxt = findViewById(R.id.mobileTxt);

        /**
         *  Spinner dependencies
         */
        genderSpinner = (Spinner)findViewById(R.id.gender_spinner);
        classSpinner = (Spinner)findViewById(R.id.class_spinner);
        numberSpinner = (Spinner)findViewById(R.id.number_spinner);

        setSpinnerItem(); // Set Spinner Item
    }

    /**
     * 스피너 메뉴 아이템 설정 및 이벤트 리스너 등록
     */

    private void setSpinnerItem() {

        ArrayAdapter genderSpinnerAdapter;
        ArrayAdapter classSpinnerAdapter;
        ArrayAdapter numberSpinnerAdapter;

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