package kr.hs.dgsw.flow.network;

import android.os.AsyncTask;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolMenu;

import java.util.List;

import kr.hs.dgsw.flow.interfaces.MealResponse;

public class SchoolMeals extends AsyncTask {
    public MealResponse mealResponse = null;

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        mealResponse = new MealResponse() {
//            @Override
//            public Object getMealsFinish() {
//                return null;
//            }
//        };
//    }

    @Override
    protected List<SchoolMenu> doInBackground(Object... objects) {
        List<SchoolMenu> menu = null;

        int year = (int) objects[0];
        int month = (int) objects[1];

        School api = new School(School.Type.HIGH, School.Region.DAEGU, "D100000282");

        try {
            menu = api.getMonthlyMenu(year, month);
        } catch (SchoolException e) {
            e.printStackTrace();
        }

        return menu;
    }

    @Override
    protected void onPostExecute(Object menu) {
        super.onPostExecute(menu);
        mealResponse.getMealsFinish(menu);
    }
}

