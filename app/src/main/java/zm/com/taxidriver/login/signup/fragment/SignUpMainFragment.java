package zm.com.taxidriver.login.signup.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import org.greenrobot.eventbus.EventBus;

import zm.com.taxidriver.General;
import zm.com.taxidriver.R;
import zm.com.taxidriver.login.signup.event.MainEvent;

public class SignUpMainFragment extends Fragment {
    private static final String TAG = "Main";

    private CountryCodePicker countryCodePicker;
    private EditText etName, etSurname, etPhone, etEmail, etPassword, etPassword2;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "create");
        View view = inflater.inflate(R.layout.fragment_sign_up_main, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
    }

    private void init(final View view) {
        Button btnNext = view.findViewById(R.id.btnNext);
        etName = view.findViewById(R.id.edit_text_name);
        etSurname = view.findViewById(R.id.edit_text_surname);
        etPhone = view.findViewById(R.id.edit_text_phone);
        etEmail = view.findViewById(R.id.edit_text_email);
        etPassword = view.findViewById(R.id.edit_text_password);
        etPassword2 = view.findViewById(R.id.edit_text_password2);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String surname = etSurname.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String password2 = etPassword2.getText().toString().trim();

                if(checkInput(name, surname, phone, email, password, password2)) {
                    String code_phone = (countryCodePicker.getSelectedCountryCode() + phone).replace(" ", "");
                    EventBus.getDefault().post(new MainEvent(name, surname, code_phone, email, password));
                }
            }
        });

        countryCodePicker = view.findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(etPhone);
    }

    private boolean checkInput(String name, String surname, String phone, String email, String password, String password2) {

        if(name.equals("")) {
            Toast.makeText(getActivity(), "Введите имя", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(surname.equals("")) {
            Toast.makeText(getActivity(), "Введите фамилию", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(phone.equals("")) {
            Toast.makeText(getActivity(), "Введите номер телефона", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!countryCodePicker.isValidFullNumber()) {
            Toast.makeText(getActivity(), "Введите корректный номер телефона", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(email.equals("")) {
            Toast.makeText(getActivity(), "Введите email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!General.isEmailValid(email)) {
            Toast.makeText(getActivity(), "Введите корректный email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.equals("")) {
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length() < 6) {
            Toast.makeText(getActivity(), "Пароль должен быть больше 5-ти символов", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(password2)) {
            Toast.makeText(getActivity(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
