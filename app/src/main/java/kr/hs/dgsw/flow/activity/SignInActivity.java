package kr.hs.dgsw.flow.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.database.DBManager;
import kr.hs.dgsw.flow.database.DatabaseHelper;
import kr.hs.dgsw.flow.helper.Encryption;
import kr.hs.dgsw.flow.helper.ToastSingleton;
import kr.hs.dgsw.flow.helper.Validation;
import kr.hs.dgsw.flow.interfaces.FlowService;
import kr.hs.dgsw.flow.model.request.UserSignIn;
import kr.hs.dgsw.flow.model.response.UserResponseData;
import kr.hs.dgsw.flow.model.response.UserResponseFormat;
import kr.hs.dgsw.flow.network.RetrofitSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signInBtn; // 로그인 버튼

    private TextView goSignup;
    private ProgressBar progressBar;

    private EditText emailTxt;
    private EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setViewId();

        signInBtn.setOnClickListener(this);
        goSignup.setOnClickListener(this);
    }



    public void loginOnClick(View v) {
        /*
        * 로그인
        * -
        * 1. id, pw 값을 가져옴
        * */
//        progressBar.setVisibility(View.GONE);
    }

    public void setViewId() {
        signInBtn = findViewById(R.id.signInBtn);
        goSignup = findViewById(R.id.goSignup);

        progressBar = findViewById(R.id.progressBar);

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
    }

    public void verifySignInData() {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if (!Validation.isValidEmail(email)) {
            String message = "이메일이 올바르지 않습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            emailTxt.requestFocus();
            return;
        }

        if (Validation.isBeEmptyValue(password)) {
            String message = "비밀번호가 입력되지 않았습니다.";
            ToastSingleton.showMessage(getApplicationContext(), message);
            passwordTxt.requestFocus();
            return;
        }

        // encrypt password
        password = Encryption.getSHA512(password);

        UserSignIn userSignIn = new UserSignIn();
        userSignIn.setEmail(email);
        userSignIn.setPw(password);
        userSignIn.setRegistrationToken("token");

        signIn(userSignIn);
    }

    public void signIn(UserSignIn userSignInData) {
        FlowService service = RetrofitSingleton.getInstance();
        Call<UserResponseFormat> request = service.signIn(userSignInData);

        request.enqueue(new Callback<UserResponseFormat>() {
            @Override
            public void onResponse(Call<UserResponseFormat> call, Response<UserResponseFormat> response) {
                UserResponseFormat rf = response.body();

                int status = rf.getStatus();

                switch (status) {
                    case 200: // 성공
                        ToastSingleton.showMessage(getApplicationContext(), rf.getMessage());
                        try{
                            DatabaseHelper dbManager = new DatabaseHelper(getApplicationContext());
                            dbManager.deleteMyProfile(); // 계정 DB 삭제
                            dbManager.saveMyProfile(rf); // 계정 DB 등록

                            Intent schoolMealsIntent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(schoolMealsIntent);
                            finish();
                        } catch (Exception e) {
                            Log.d("error", e.toString());
                        }
                        break;
                    case 400: // 요청파라미터 에러
                        ToastSingleton.showMessage(getApplicationContext(), rf.getMessage());
                        break;
                    case 401: // 이메일 또는 비밀번호가 일치하지 않는 경우
                        ToastSingleton.showMessage(getApplicationContext(), rf.getMessage());
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserResponseFormat> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버 에러", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInBtn: // 로그인 버튼
                verifySignInData(); // 로그인 데이터 검사 및 로그인
                break;
            case R.id.goSignup: // 회원가입 액티비티 이동
                Intent goSignupIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(goSignupIntent);
                break;
        }
    }
}
