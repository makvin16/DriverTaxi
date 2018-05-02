package zm.com.taxidriver.login.signup.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import zm.com.taxidriver.R;
import zm.com.taxidriver.login.signup.event.AdditionalEvent;

public class SignUpAdditionalFragment extends Fragment {
    private static final String TAG = "Additional";
    private static final int REQUEST_PERMISSIONS = 123;
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_GALLERY = 1;
    private static final int ANIMALS = 0;
    private static final int FOOD = 1;
    private static final int DRUNK = 2;
    private static final int SMOKING = 3;

    private ImageView ivPhoto, ivAnimals, ivFood, ivDrunk, ivSmoking;
    private CheckBox cbAnimals, cbFood, cbDrunk, cbSmoking;

    private AlertDialog alertDialogChooser, alertDialogInfo;
    private TextView textViewAlertInfo;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != getActivity().RESULT_OK || data == null) {
            Toast.makeText(getActivity(), "Произошла ошибка при получении изображения", Toast.LENGTH_SHORT).show();
            alertDialogChooser.dismiss();
            return;
        }
        if(requestCode == REQUEST_CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(bitmap);
            alertDialogChooser.dismiss();
        }
        if(requestCode == REQUEST_GALLERY) {
            Uri uri = data.getData();
            ivPhoto.setImageURI(uri);
            alertDialogChooser.dismiss();
        }
    }

    @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "create");
        View view = inflater.inflate(R.layout.fragment_sign_up_additional, container, false);
        init(view);
        initDialog(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
    }

    private void initDialog(View view) {
        initChooser(view);
        initInfo(view);
    }

    private void initChooser(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        final View v = inflater.inflate(R.layout.alert_choose_camera_gallery, null);
        RelativeLayout btnCamera = v.findViewById(R.id.btn_camera);
        RelativeLayout btnGallery = v.findViewById(R.id.btn_gallery);
        RelativeLayout btnCancel = v.findViewById(R.id.btn_cancel);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogChooser.dismiss();
            }
        });
        builder.setView(v);
        builder.setCancelable(false);
        alertDialogChooser = builder.create();
        alertDialogChooser.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void initInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        final View v = inflater.inflate(R.layout.alert_info, null);
        textViewAlertInfo = v.findViewById(R.id.text_view);
        RelativeLayout btnCancel = v.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogInfo.dismiss();
            }
        });
        builder.setView(v);
        builder.setCancelable(false);
        alertDialogInfo = builder.create();
        alertDialogInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void init(View view) {
        cbAnimals = view.findViewById(R.id.checkbox_animals);
        cbFood = view.findViewById(R.id.checkbox_food);
        cbDrunk = view.findViewById(R.id.checkbox_drunk);
        cbSmoking = view.findViewById(R.id.checkbox_smoking);

        ivPhoto = view.findViewById(R.id.image_view_photo);
        ivAnimals = view.findViewById(R.id.image_view_animals);
        ivFood = view.findViewById(R.id.image_view_food);
        ivDrunk = view.findViewById(R.id.image_view_drunk);
        ivSmoking = view.findViewById(R.id.image_view_smoking);

        ivAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(ANIMALS);
            }
        });

        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(FOOD);
            }
        });

        ivDrunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(DRUNK);
            }
        });

        ivSmoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(SMOKING);
            }
        });

        Button btnChangePhoto = view.findViewById(R.id.btnChangePhoto);
        Button btnNext = view.findViewById(R.id.btnNext);

        btnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                alertDialogChooser.show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean animals = cbAnimals.isChecked();
                boolean food = cbFood.isChecked();
                boolean drunk = cbDrunk.isChecked();
                boolean smoking = cbSmoking.isChecked();
                EventBus.getDefault().post(new AdditionalEvent("photo", animals, food, drunk, smoking));
            }
        });
    }

    private void showInfo(int info) {
        Resources res = getResources();
        switch (info) {
            case ANIMALS: textViewAlertInfo.setText(res.getText(R.string.info_animals)); break;
            case FOOD: textViewAlertInfo.setText(res.getText(R.string.info_food)); break;
            case DRUNK: textViewAlertInfo.setText(res.getText(R.string.info_drunk)); break;
            case SMOKING: textViewAlertInfo.setText(res.getText(R.string.info_smoking)); break;
            default: textViewAlertInfo.setText("");
        }
        alertDialogInfo.show();
    }

    private void showCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> arrPer = new ArrayList<>();
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                arrPer.add(Manifest.permission.CAMERA);
            }
            if(arrPer.size() != 0) {
                String[] per = new String[arrPer.size()];
                for(int i = 0; i < per.length; i++)
                    per[i] = arrPer.get(i);
                ActivityCompat.requestPermissions(getActivity(), per, REQUEST_PERMISSIONS);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private void showGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> arrPer = new ArrayList<>();
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                arrPer.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if(arrPer.size() != 0) {
                String[] per = new String[arrPer.size()];
                for(int i = 0; i < per.length; i++)
                    per[i] = arrPer.get(i);
                ActivityCompat.requestPermissions(getActivity(), per, REQUEST_PERMISSIONS);
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Выбирите файл"), REQUEST_GALLERY);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent.createChooser(intent, "Выбирите файл"), REQUEST_GALLERY);
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> arrPer = new ArrayList<>();
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                arrPer.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                arrPer.add(Manifest.permission.CAMERA);
            }
            if(arrPer.size() != 0) {
                String[] per = new String[arrPer.size()];
                for(int i = 0; i < per.length; i++)
                    per[i] = arrPer.get(i);
                ActivityCompat.requestPermissions(getActivity(), per, REQUEST_PERMISSIONS);
            }
        }
    }
}
