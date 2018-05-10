package zm.com.taxidriver.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zm.com.taxidriver.General;
import zm.com.taxidriver.R;
import zm.com.taxidriver.api.ApiService;
import zm.com.taxidriver.api.Client;
import zm.com.taxidriver.db.Driver;
import zm.com.taxidriver.model.result.ResultDriver;
import zm.com.taxidriver.start.StartActivity;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = SignInActivity.class.getSimpleName();
    private Toolbar toolbar;
    private EditText etPhone, etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initToolbar();
        init();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView textViewTitle = toolbar.findViewById(R.id.text_view_title);
        ImageView imageViewBack = toolbar.findViewById(R.id.image_view_back);
        textViewTitle.setText("Авторизация");
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        etPhone = findViewById(R.id.edit_text_phone);
        etPassword = findViewById(R.id.edit_text_password);
        General.Progress.init((RelativeLayout) findViewById(R.id.layout_progress));
    }

    public void clickSignIn(View view) {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(isCheck(phone, password)) apiSignInDriver(phone, password);
    }

    private void apiSignInDriver(String phone, String password) {
        General.Progress.show();
        ApiService api = Client.getApiService();
        Call<ResultDriver> call = api.apiSignInDriver(phone, password);
        call.enqueue(new Callback<ResultDriver>() {
            @Override
            public void onResponse(Call<ResultDriver> call, Response<ResultDriver> response) {
                final ResultDriver result = response.body();
                if(result == null) {
                    General.ShowError.show(getApplicationContext(), General.ShowError.API);
                    General.Progress.hide();
                    return;
                }
                if(result.isSuccess()) {
                    Log.d(TAG, result.getDriver().getName() + " " + result.getDriver().getSurname());
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<Driver> rows = realm.where(Driver.class).findAll();
                            rows.deleteAllFromRealm();
                        }
                    });
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm r) {
                            Driver driver = r.createObject(Driver.class);
                            zm.com.taxidriver.model.Driver d = result.getDriver();
                            driver.setName(d.getName());
                            driver.setSurname(d.getSurname());
                            driver.setEmail(d.getEmail());
                            driver.setPhone(d.getPhone());
                            driver.setPassword(d.getPassword());
                            driver.setPhoto(d.getPhoto());
                            driver.setAnimals(d.isAnimals());
                            driver.setDrunk(d.isDrunk());
                            driver.setFood(d.isFood());
                            driver.setSmoking(d.isSmoking());
                            driver.setId_car(d.getIdCar());
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            startActivity(new Intent(SignInActivity.this, StartActivity.class));
                            finish();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(SignInActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else Toast.makeText(SignInActivity.this, "Номер телефона или пароль не верны", Toast.LENGTH_SHORT).show();
                General.Progress.hide();
            }

            @Override
            public void onFailure(Call<ResultDriver> call, Throwable t) {
                General.ShowError.show(getApplicationContext(), General.ShowError.CONNECT);
                General.Progress.hide();
            }
        });
    }

    private boolean isCheck(String phone, String password) {
        if(phone.equals("")) {
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.equals("")) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
