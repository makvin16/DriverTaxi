package zm.com.taxidriver.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import zm.com.taxidriver.R;
import zm.com.taxidriver.db.Driver;
import zm.com.taxidriver.login.LoginActivity;
import zm.com.taxidriver.start.fragment.BookingFragment;
import zm.com.taxidriver.start.fragment.MessageFragment;
import zm.com.taxidriver.start.fragment.ProfileFragment;

public class StartActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(toggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true) ;

        navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Создать новый фрагмент и задать фрагмент для отображения
        // на основе нажатия на элемент навигации
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_profile:
                fragmentClass = ProfileFragment.class;
                Toast.makeText(this, "pro", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_msg:
                fragmentClass = MessageFragment.class;
                Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_booking:
                fragmentClass = BookingFragment.class;
                Toast.makeText(this, "boo", Toast.LENGTH_SHORT).show();
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вставить фрагмент, заменяя любой существующий
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//
//        // Выделение существующего элемента выполнено с помощью
//        // NavigationView
//        menuItem.setChecked(true);
//        // Установить заголовок для action bar'а
//        setTitle(menuItem.getTitle());
//        // Закрыть navigation drawer
//        drawerLayout.closeDrawers();
    }

    public void clickExit(View view) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Driver> rows = realm.where(Driver.class).findAll();
                rows.deleteAllFromRealm();
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
