package zm.com.taxidriver.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import zm.com.taxidriver.R;

public class SignInActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initToolbar();
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

    public void clickSignIn(View view) {
        Toast.makeText(this, "signin", Toast.LENGTH_SHORT).show();
    }
}
