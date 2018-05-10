package zm.com.taxidriver.login.signup.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import zm.com.taxidriver.General;
import zm.com.taxidriver.R;
import zm.com.taxidriver.login.signup.event.CarEvent;

public class SignUpCarFragment extends Fragment {
    private static final String TAG = "Car";

    private Spinner sType;
    private EditText etBrand, etModel, etYear, etNumber, etPassenger;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "create");
        View view = inflater.inflate(R.layout.fragment_sign_up_car, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
    }

    private void init(View view) {
        sType = view.findViewById(R.id.spinner_type);
        String[] arr_str = getNameTypesFromDB();
        Toast.makeText(getActivity(), arr_str.length+"", Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, arr_str);
        sType.setAdapter(adapter);
        etBrand = view.findViewById(R.id.edit_text_brand);
        etModel = view.findViewById(R.id.edit_text_model);
        etYear = view.findViewById(R.id.edit_text_year);
        etNumber = view.findViewById(R.id.edit_text_number);
        etPassenger = view.findViewById(R.id.edit_text_passenger);

        Button btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand = etBrand.getText().toString().trim();
                String model = etModel.getText().toString().trim();
                String year = etYear.getText().toString().trim();
                String number = etNumber.getText().toString().trim();
                String passenger = etPassenger.getText().toString().trim();
                if(checkInput(brand, model, year, number, passenger)) {
                    int y = -1;
                    int p = -1;
                    try {
                        y = Integer.valueOf(year);
                        p = Integer.valueOf(passenger);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(y != -1 && p != -1) EventBus.getDefault().post(new CarEvent(brand, model, number, y, p, sType.getSelectedItemPosition()));
                }
            }
        });
    }

    private String[] getNameTypesFromDB() {
        String[] arr = new String[General.DB_TYPES.size()];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = General.DB_TYPES.get(i).getName();
        }
        return arr;
     }

    private boolean checkInput(String brand, String model, String year, String number, String passenger) {

        if(brand.equals("")) {
            Toast.makeText(getActivity(), "Введите марку автомобиля", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(model.equals("")) {
            Toast.makeText(getActivity(), "Введите модель автомобиля", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(year.equals("")) {
            Toast.makeText(getActivity(), "Введите год автомобиля", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(number.equals("")) {
            Toast.makeText(getActivity(), "Введите номер автомобиля", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(passenger.equals("")) {
            Toast.makeText(getActivity(), "Введите количество пассажирских мест в автомобиле", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
