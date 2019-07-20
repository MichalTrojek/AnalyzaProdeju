package cz.mtr.analyzaprodeju.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.navigation.NavigationView;

public class KeyboardHider {

    public static void hideKeyboard() {

    }

    public static void closeKeyboard(Context context, NavigationView navigationView) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(navigationView.getWindowToken(), 0);
    }

}
