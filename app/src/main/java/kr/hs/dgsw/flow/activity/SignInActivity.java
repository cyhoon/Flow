package kr.hs.dgsw.flow.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import kr.hs.dgsw.flow.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView goSignup;
    private Button loginBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        /*
        * UI Component 등록
        */
        goSignup = findViewById(R.id.goSignup);
        loginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goSignup: // 회원가입 액티비티 이동
                Intent goSignupIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(goSignupIntent);
                break;
        }
    }
}
