package zm.com.taxidriver.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zm.com.taxidriver.R;
import zm.com.taxidriver.login.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickSignIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void clickSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
