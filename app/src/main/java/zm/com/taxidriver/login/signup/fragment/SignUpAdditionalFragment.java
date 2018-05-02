package zm.com.taxidriver.login.signup.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import java.util.ArrayList;

import zm.com.taxidriver.R;

public class SignUpAdditionalFragment extends Fragment {
    private static final String TAG = "Additional";
    private static final int REQUEST_PERMISSIONS = 123;
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_GALLERY = 1;

    private ImageView ivPhoto, ivAnimals, ivFood, ivDrunk, ivSmoking;
    private CheckBox cbAnimals, cbFood, cbDrunk, cbSmoking;

    private AlertDialog alertDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != getActivity().RESULT_OK || data == null) {
            Toast.makeText(getActivity(), "Произошла ошибка при получении изображения", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
            return;
        }
        if(requestCode == REQUEST_CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(bitmap);
            alertDialog.dismiss();
        }
        if(requestCode == REQUEST_GALLERY) {
            Uri uri = data.getData();
            ivPhoto.setImageURI(uri);
            alertDialog.dismiss();
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
                alertDialog.dismiss();
            }
        });
        builder.setView(v);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                checkPermission();
                alertDialog.show();
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
