package kr.hs.dgsw.flow.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.helper.Encryption;
import kr.hs.dgsw.flow.helper.ToastSingleton;
import kr.hs.dgsw.flow.helper.Validation;
import kr.hs.dgsw.flow.interfaces.FlowService;
import kr.hs.dgsw.flow.model.ResponseFormat;
import kr.hs.dgsw.flow.model.User;
import kr.hs.dgsw.flow.model.request.UserSignUp;
import kr.hs.dgsw.flow.model.response.UserResponseFormat;
import kr.hs.dgsw.flow.network.RetrofitSingleton;
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
            String message = "이메일이 올바르지 않습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            emailTxt.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            String message = "비밀번호는 ( 소문자, 대문자, 특수문자 포함해서 8~16 이어야 합니다. )";
            ToastSingleton.showMessage(getApplicationContext(), message);
            passwordTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(password)) {
            String message = "비밀번호가 입력되지 않았습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            passwordTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(passwordRe)) {
            String message = "비밀번호 재입력이 입력되지 않았습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            passwordReTxt.requestFocus();
            return;
        }

        if (!Validation.isComparingPassword(password, passwordRe)) {
            String message = "비밀번호가 일치하지 않습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            passwordReTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(name)) {
            String message = "이름이 입력되지 않았습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            nameTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(mobile)) {
            String message = "전화 번호가 입력되지 않았습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            mobileTxt.requestFocus();
            return;
        }

        // encrypt password
        password = Encryption.getSHA512(password);

        // set request model object.
        UserSignUp userSignUpData = new UserSignUp();
        userSignUpData.setEmail(email);
        userSignUpData.setPw(password);
        userSignUpData.setName(name);
        userSignUpData.setGender(gender);
        userSignUpData.setMobile(mobile);
        userSignUpData.setClassIdx(classRoom);
        userSignUpData.setClassNumber(classNumber);

        signUp(userSignUpData);
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

    private void signUp(UserSignUp userSignUpData) {
        FlowService service = RetrofitSingleton.getInstance();
        Call<ResponseFormat> request = service.signUp(userSignUpData);

        request.enqueue(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                ResponseFormat rf = response.body();

                int status = rf.getStatus();

                switch (status) {
                    case 200: // 성공
                        ToastSingleton.showMessage(getApplicationContext(), rf.getMessage());
                        Intent goLoginIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(goLoginIntent); // 로그인 액티비티로 이동
                        finish();
                        break;
                    case 400: // 요청파라미터 에러
                        ToastSingleton.showMessage(getApplicationContext(), rf.getMessage());
                        break;
                    case 409: // 해당 이메일로 계정이 존재
                        ToastSingleton.showMessage(getApplicationContext(), rf.getMessage());
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버 에러", Toast.LENGTH_SHORT).show();
            }
        });
    }
}