package zm.com.taxidriver;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zm.com.taxidriver.model.result.ResultType;

public class General {
    public static final String SERVER = "http://mytaxi.zzz.com.ua";

    public static ArrayList<ResultType.Type> DB_TYPES = new ArrayList<>();

    public static void initTypesDB(ResultType result) {
        DB_TYPES.clear();
        for(ResultType.Type type : result.getTypes()) {
            DB_TYPES.add(type);
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static class ShowError {
        public static final int API = 0;
        public static final int CONNECT = 1;

        public static void show(Context context, int type) {
            if(type == API) Toast.makeText(context, "Ошибка сервера", Toast.LENGTH_SHORT).show();
            if(type == CONNECT) Toast.makeText(context, "Ошибка подключения", Toast.LENGTH_SHORT).show();
        }
    }

    public static class Progress {
        private static RelativeLayout layout;
        public static void init(RelativeLayout l) {
            layout = l;
        }

        public static void show() {
            layout.setVisibility(View.VISIBLE);
        }

        public static void hide() {
            layout.setVisibility(View.GONE);
        }
    }
}
