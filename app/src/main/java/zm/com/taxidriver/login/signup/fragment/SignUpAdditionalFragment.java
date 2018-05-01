package zm.com.taxidriver.login.signup.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import zm.com.taxidriver.R;

public class SignUpAdditionalFragment extends Fragment {
    private static final String TAG = "Additional";

    private ImageView ivPhoto, ivAnimals, ivFood, ivDrunk, ivSmoking;
    private CheckBox cbAnimals, cbFood, cbDrunk, cbSmoking;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "create");
        View view = inflater.inflate(R.layout.fragment_sign_up_additional, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
    }

    private void init(View view) {
        ivPhoto = view.findViewById(R.id.image_view_photo);
        ivAnimals = view.findViewById(R.id.image_view_animals);
        ivFood = view.findViewById(R.id.image_view_food);
        ivDrunk = view.findViewById(R.id.image_view_drunk);
        ivSmoking = view.findViewById(R.id.image_view_smoking);

        cbAnimals = view.findViewById(R.id.checkbox_animals);
        cbFood = view.findViewById(R.id.checkbox_food);
        cbDrunk = view.findViewById(R.id.checkbox_drunk);
        cbSmoking = view.findViewById(R.id.checkbox_smoking);

        Button btnChangePhoto = view.findViewById(R.id.btnChangePhoto);
        Button btnNext = view.findViewById(R.id.btnNext);

        btnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean animals = cbAnimals.isChecked();
                boolean food = cbFood.isChecked();
                boolean drunk = cbDrunk.isChecked();
                boolean smoking = cbSmoking.isChecked();

                //EventBus.getDefault().post(new NextFragmentEvent(SignUpActivity.CAR));
            }
        });
    }
}
