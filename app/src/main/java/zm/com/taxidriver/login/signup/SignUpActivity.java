package zm.com.taxidriver.login.signup;

import android.content.Intent;
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
import zm.com.taxidriver.login.signup.event.AdditionalEvent;
import zm.com.taxidriver.login.signup.event.CarEvent;
import zm.com.taxidriver.login.signup.event.MainEvent;
import zm.com.taxidriver.login.signup.fragment.SignUpAdditionalFragment;
import zm.com.taxidriver.login.signup.fragment.SignUpCarFragment;
import zm.com.taxidriver.login.signup.fragment.SignUpMainFragment;
import zm.com.taxidriver.model.result.PhoneEmail;
import zm.com.taxidriver.model.result.ResultCar;
import zm.com.taxidriver.model.result.ResultDriver;
import zm.com.taxidriver.start.StartActivity;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    public static final int MAIN = 0;
    public static final int ADDITIONAL = 1;
    public static final int CAR = 2;

    private Toolbar toolbar;
    private SignUpMainFragment signUpMainFragment;
    private SignUpAdditionalFragment signUpAdditionalFragment;
    private SignUpCarFragment signUpCarFragment;
    //private RelativeLayout layoutProgress;

    private String name, surname, phone, email, password, photo;
    private boolean isAnimals, isDrunk, isFood, isSmoking;
    private int type_car, year, pas;
    private String brand, model, number;

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
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, signUpMainFragment).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, signUpAdditionalFragment).commit();

        General.Progress.init((RelativeLayout) findViewById(R.id.layout_progress));
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
        name = event.getName();
        surname = event.getSurname();
        phone = event.getPhone();
        email = event.getEmail();
        password = event.getPassword();
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

    @Subscribe
    public void receiverAdditional(AdditionalEvent event) {
        Log.d(TAG, name);
        Log.d(TAG, surname);
        Log.d(TAG, phone);
        Log.d(TAG, email);
        Log.d(TAG, password);
        photo = event.getPhoto();
        isAnimals = event.isAnimals();
        isDrunk = event.isDrunk();
        isFood = event.isDrunk();
        isSmoking = event.isSmoking();
        Log.d(TAG, event.getPhoto());
        Log.d(TAG, event.isAnimals()+"");
        Log.d(TAG, event.isFood()+"");
        Log.d(TAG, event.isDrunk()+"");
        Log.d(TAG, event.isSmoking()+"");
        replaceFragment(signUpCarFragment);
    }

    @Subscribe
    public void receiverCar(CarEvent event) {
        type_car = event.getId_type();
        brand = event.getBrand();
        model = event.getModel();
        year = event.getYear();
        number = event.getNumber();
        pas = event.getPassenger();
        Log.d(TAG, event.getId_type()+"");
        Log.d(TAG, event.getBrand());
        Log.d(TAG, event.getModel());
        Log.d(TAG, event.getYear()+"");
        Log.d(TAG, event.getNumber());
        Log.d(TAG, event.getPassenger()+"");
        apiCar();
    }

    private void apiCar() {
        General.Progress.show();
        ApiService api = Client.getApiService();
        Call<ResultCar> call = api.apiSetCar(type_car, brand, model, year, "white", number, pas);
        call.enqueue(new Callback<ResultCar>() {
            @Override
            public void onResponse(Call<ResultCar> call, Response<ResultCar> response) {
                ResultCar result = response.body();
                if(result == null) {
                    General.ShowError.show(getApplicationContext(), General.ShowError.API);
                    General.Progress.hide();
                    return;
                }
                if(!result.isSuccess()) {
                    Toast.makeText(SignUpActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                } else {
                    apiGetCarById();
                }
                General.Progress.hide();
            }

            @Override
            public void onFailure(Call<ResultCar> call, Throwable t) {
                General.ShowError.show(getApplicationContext(), General.ShowError.CONNECT);
                General.Progress.hide();
            }
        });
    }

    private void apiGetCarById() {
        General.Progress.show();
        ApiService api = Client.getApiService();
        Call<ResultCar> call = api.apiGetCarByNumber(number);
        call.enqueue(new Callback<ResultCar>() {
            @Override
            public void onResponse(Call<ResultCar> call, Response<ResultCar> response) {
                ResultCar result = response.body();
                if(result == null) {
                    General.ShowError.show(getApplicationContext(), General.ShowError.API);
                    General.Progress.hide();
                    return;
                }
                if(result.isSuccess()) {
                    apiSignUpDriver(result.getCar().getId());
                } else General.ShowError.show(getApplicationContext(), General.ShowError.API);
                General.Progress.hide();
            }

            @Override
            public void onFailure(Call<ResultCar> call, Throwable t) {
                General.ShowError.show(getApplicationContext(), General.ShowError.CONNECT);
                General.Progress.hide();
            }
        });
    }

    private void apiSignUpDriver(final int id_car) {
        General.Progress.show();
        ApiService api = Client.getApiService();
        Call<ResultDriver> call = api.apiSetDriver(name, surname, email, phone, password, photo, isAnimals, isDrunk, isFood, isSmoking, id_car);
        call.enqueue(new Callback<ResultDriver>() {
            @Override
            public void onResponse(Call<ResultDriver> call, Response<ResultDriver> response) {
                ResultDriver result = response.body();
                if(result == null) {
                    General.ShowError.show(getApplicationContext(), General.ShowError.API);
                    General.Progress.hide();
                    return;
                }
                if(result.isSuccess()) {
                    //write to db
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
                            driver.setName(name);
                            driver.setSurname(surname);
                            driver.setEmail(email);
                            driver.setPhone(phone);
                            driver.setPassword(password);
                            driver.setPhoto(photo);
                            driver.setAnimals(isAnimals);
                            driver.setDrunk(isDrunk);
                            driver.setFood(isFood);
                            driver.setSmoking(isSmoking);
                            driver.setId_car(id_car);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            startActivity(new Intent(SignUpActivity.this, StartActivity.class));
                            finish();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(SignUpActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else General.ShowError.show(getApplicationContext(), General.ShowError.API);
                General.Progress.hide();
            }

            @Override
            public void onFailure(Call<ResultDriver> call, Throwable t) {
                General.ShowError.show(getApplicationContext(), General.ShowError.CONNECT);
                General.Progress.hide();
            }
        });
    }

}
