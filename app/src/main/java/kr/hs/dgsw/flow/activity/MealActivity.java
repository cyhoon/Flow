package kr.hs.dgsw.flow.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.hyunjun.school.SchoolMenu;

import java.util.Calendar;
import java.util.List;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.helper.DateTime;
import kr.hs.dgsw.flow.interfaces.MealResponse;
import kr.hs.dgsw.flow.network.SchoolMeals;

public class MealActivity extends AppCompatActivity implements MealResponse {
    private static int choiceYear;
    private static int choiceMonth;
    private static int choiceDay;

    private DateTime dateTime = null;

    private static Calendar nowTime = Calendar.getInstance();
    private static Calendar breakfastTime = Calendar.getInstance();
    private static Calendar lunchTime = Calendar.getInstance();
    private static Calendar dinnerTime = Calendar.getInstance();

    private static String eatTime;
    private static List<SchoolMenu> mealInfo;
    private static SchoolMeals meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getToday(); // 오늘 날짜 설정
        setMealInfo(); // 급식 가져오기, default: 오늘의 급식

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setInitTodayMealUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.now_meal) {
            setTodayMealUI();
            return true;
        } else if (id == R.id.choice_meal) {
            drawDatePicker(); // date picker 호출
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getToday() {

        dateTime = new DateTime();

        nowTime.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), Calendar.getInstance().get(Calendar.SECOND)); // 현재 시간
        breakfastTime.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), 7, 20, 0); // 아침 식사 시간
        lunchTime.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), 12, 40, 0); // 점심 시간
        dinnerTime.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), 18, 40, 0); // 저녁 시간
    }

    public void setMealInfo() {
        SchoolMeals schoolMeals = new SchoolMeals();
        schoolMeals.mealResponse = this;
        try {
            mealInfo = (List<SchoolMenu>) schoolMeals.execute(dateTime.getYear(), dateTime.getMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String eatTimeConfirm() {

        String state = null;

        if (nowTime.before(breakfastTime)) { // 현재 시간이 아침 시간보다 이전일 경우
            state = "breakfast";
        } else if (nowTime.before(lunchTime)) { // 현재 시간이 점심 시간보다 이전일 경우
            state = "lunch";
        } else if (nowTime.before(dinnerTime)) { // 현재시간이 저녁 시간보다 이전일 경우
            state = "dinner";
        } else if (nowTime.after(dinnerTime)) { // 현재 시간이 저녁 시간보다 이후일 경우
            state = "next_breakfast";
        }

        return state;
    }

    public void setInitTodayMealUI() {
        findViewById(R.id.have_top).setVisibility(View.INVISIBLE);
        findViewById(R.id.top_menu).setVisibility(View.INVISIBLE);
        findViewById(R.id.have_bottom).setVisibility(View.INVISIBLE);
        findViewById(R.id.bottom_menu).setVisibility(View.INVISIBLE);
    }

    public void setTodayMealUI() {

        setInitTodayMealUI();

        TextView choiceDate = findViewById(R.id.choice_date);
        TextView haveEat = findViewById(R.id.have_center);
        TextView mealMenu = findViewById(R.id.center_menu);

        String today = String.valueOf(dateTime.getYear()) + "년 " + String.valueOf(dateTime.getMonth()) + "월 " + String.valueOf(dateTime.getDay()) + "일";

        choiceDate.setText(today);
        String eatConfirm = eatTimeConfirm();

        switch (eatConfirm) {
            case "breakfast":
                haveEat.setText("아침");
                mealMenu.setText(mealInfo.get(dateTime.getDay() - 1).breakfast); // -1를 한 이유는 배열이 0부터이기 때문에
                break;
            case "lunch":
                haveEat.setText("점심");
                mealMenu.setText(mealInfo.get(dateTime.getDay() - 1).lunch);
                break;
            case "dinner":
                haveEat.setText("저녁");
                mealMenu.setText(mealInfo.get(dateTime.getDay() - 1).dinner);
                break;
            case "next_breakfast":
                haveEat.setText("아침");
                mealMenu.setText(mealInfo.get(dateTime.getDay()).breakfast);
                break;
        }
    }

    public void setChoiceMealUI() {

        TextView choiceDate = findViewById(R.id.choice_date);

        // Top
        TextView haveTop = findViewById(R.id.have_top);
        TextView topMenu = findViewById(R.id.top_menu);

        // Center
        TextView haveCenter = findViewById(R.id.have_center);
        TextView centerMenu = findViewById(R.id.center_menu);

        // Bottom
        TextView haveBottom = findViewById(R.id.have_bottom);
        TextView bottomMenu = findViewById(R.id.bottom_menu);

        haveTop.setVisibility(View.VISIBLE);
        topMenu.setVisibility(View.VISIBLE);
        haveBottom.setVisibility(View.VISIBLE);
        bottomMenu.setVisibility(View.VISIBLE);

        String userChoiceDate = String.valueOf(choiceYear) + "년 " + String.valueOf(choiceMonth) + "월 " + String.valueOf(choiceDay) + "일";
        choiceDate.setText(userChoiceDate);

        haveTop.setText("아침");
        topMenu.setText(mealInfo.get(choiceDay - 1).breakfast);

        haveCenter.setText("점심");
        centerMenu.setText(mealInfo.get(choiceDay - 1).lunch);

        haveBottom.setText("저녁");
        bottomMenu.setText(mealInfo.get(choiceDay - 1).dinner);
    }

    public void drawDatePicker() {

        Context context = new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context = this;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            choiceYear = year;
            choiceMonth = monthOfYear;
            choiceDay = dayOfMonth;

            setChoiceMealUI();
        }
    };

    @Override
    public void getMealsFinish(Object o) {
        mealInfo = (List<SchoolMenu>) o;
        setTodayMealUI();
    }
}
