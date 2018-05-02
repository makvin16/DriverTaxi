package zm.com.taxidriver.login.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zm.com.taxidriver.General;
import zm.com.taxidriver.R;
import zm.com.taxidriver.api.ApiService;
import zm.com.taxidriver.api.Client;
import zm.com.taxidriver.login.signup.event.MainEvent;
import zm.com.taxidriver.login.signup.fragment.SignUpAdditionalFragment;
import zm.com.taxidriver.login.signup.fragment.SignUpCarFragment;
import zm.com.taxidriver.login.signup.fragment.SignUpMainFragment;
import zm.com.taxidriver.model.PhoneEmail;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    public static final int MAIN = 0;
    public static final int ADDITIONAL = 1;
    public static final int CAR = 2;

    private Toolbar toolbar;
    private SignUpMainFragment signUpMainFragment;
    private SignUpAdditionalFragment signUpAdditionalFragment;
    private SignUpCarFragment signUpCarFragment;
    private RelativeLayout layoutProgress;

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initToolbar();
        init();
    }

    private void init() {
        signUpMainFragment = new SignUpMainFragment();
        signUpAdditionalFragment = new SignUpAdditionalFragment();
        signUpCarFragment = new SignUpCarFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, signUpMainFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, signUpAdditionalFragment).commit();

        layoutProgress = findViewById(R.id.layout_progress);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView textViewTitle = toolbar.findViewById(R.id.text_view_title);
        ImageView imageViewBack = toolbar.findViewById(R.id.image_view_back);
        textViewTitle.setText("Регистрация");
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void replaceFragment(android.support.v4.app.Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean checkMainNext(boolean isPhone, boolean isEmail) {
        if(isPhone) {
            Toast.makeText(this, "Такой номер телефона уже существует", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isEmail) {
            Toast.makeText(this, "Такой email уже существует", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Subscribe
    public void receiverMain(MainEvent event) {
//        if(fragment == MAIN) replaceFragment(signUpMainFragment);
//        if(fragment == ADDITIONAL) replaceFragment(signUpAdditionalFragment);
//        if(fragment == CAR) replaceFragment(signUpCarFragment);
//        replaceFragment(signUpAdditionalFragment);
        Log.d(TAG, event.getName());
        Log.d(TAG, event.getSurname());
        Log.d(TAG, event.getPhone());
        Log.d(TAG, event.getEmail());
        Log.d(TAG, event.getPassword());
        ApiService api = Client.getApiService();
        Call<PhoneEmail> call = api.apiCheckPhoneEmail(event.getPhone(), event.getEmail());
        call.enqueue(new Callback<PhoneEmail>() {
            @Override
            public void onResponse(Call<PhoneEmail> call, Response<PhoneEmail> response) {
                PhoneEmail result = response.body();
                if(result == null) {
                    General.ShowError.show(getApplicationContext(), General.ShowError.API);
                    return;
                }
                if(checkMainNext(result.isPhone(), result.isEmail())) replaceFragment(signUpAdditionalFragment);
            }

            @Override
            public void onFailure(Call<PhoneEmail> call, Throwable t) {
                General.ShowError.show(getApplicationContext(), General.ShowError.CONNECT);
            }
        });
    }



}
