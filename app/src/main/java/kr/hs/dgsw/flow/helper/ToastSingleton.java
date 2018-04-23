package kr.hs.dgsw.flow.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastSingleton {
    private static Toast toastInstance = null;

    private ToastSingleton() {}

    public static void showMessage(Context context, String message) {
        if (toastInstance == null) {
            toastInstance = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toastInstance.setText(message);
        }
        toastInstance.show();
    }
}
