package su.rbv.task02_mvp.ui.main.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import su.rbv.task02_mvp.R;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends MvpAppCompatFragment implements IProfileView, EditMainPictureListener {

    private static final String BUNDLE_PICTURE_TEMP_PATH_KEY = "bundle_picture_temp_path";

    private static final String EDIT_MAIN_PICTURE_FRAGMENT_TAG = "edit_main_picture_fragment_tag";

    private static final String TEMP_PHOTO_FILE_NAME = "task02_temp_photo";
    private static final String TEMP_PHOTO_FILE_EXT = "jpg";
    private static final String FILE_PROVIDER = "su.rbv.task02_mvp.android.provider";

    private String tempPhotoPath;    // temporary path to camera photo file, while camera is taking picture

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_SELECT_PHOTO = 2;

    private static final int FIRST_PHOTO_INDEX = 0;

    private ImageView mainPicture;

    @InjectPresenter
    ProfilePresenter profilePresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
        toolbar.inflateMenu(R.menu.profile_menu);

        mainPicture = view.findViewById(R.id.profile_main_picture);
        // click main photo on profile screen
        mainPicture.setOnClickListener((v) -> openMainPictureDialog());

        if (savedInstanceState != null) {
            tempPhotoPath = savedInstanceState.getString(BUNDLE_PICTURE_TEMP_PATH_KEY);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(BUNDLE_PICTURE_TEMP_PATH_KEY, tempPhotoPath);
    }

    private void openMainPictureDialog() {
        DialogFragment editMainPictureDialog = new EditMainPictureFragment();
        editMainPictureDialog.show(getChildFragmentManager(), EDIT_MAIN_PICTURE_FRAGMENT_TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    profilePresenter.savePictureFileName(tempPhotoPath);
                    break;
                case REQUEST_SELECT_PHOTO:
                    try {
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        if (data != null && data.getData() != null) {
                            Cursor cursor = requireActivity().getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[FIRST_PHOTO_INDEX]);
                                String photoPath = cursor.getString(columnIndex);
                                cursor.close();
                                profilePresenter.savePictureFileName(photoPath);
                            }
                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(getContext(), getResources().getString(R.string.edit_main_text_filename_error), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    }

    /**
     * on click "select photo" event
     * check permissions and select photo if permissions granted
     *
     */
    @Override
    public void onSelectPhoto() {
        if (ContextCompat.checkSelfPermission(requireContext(),  Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_SELECT_PHOTO);
        } else {
            selectPhoto();
        }
    }

    /**
     * Start activity with native gallery to select picture
     */
    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.edit_main_text_photo_select_photo)), REQUEST_SELECT_PHOTO);
    }

    /**
     * on click "take photo" event
     * check permissions and take photo if permissions granted
     */
    @Override
    public void onTakePhoto() {
        if (ContextCompat.checkSelfPermission(requireContext(),  Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(),  Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0) {
            for (int result: grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) return;
            }
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    takePhoto();
                    break;
                case REQUEST_SELECT_PHOTO:
                    selectPhoto();
                    break;
            }
        }
    }

    /**
     * Start activity with native camera to take photo
     */
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            File photoFile;
            try {
                File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile(TEMP_PHOTO_FILE_NAME, TEMP_PHOTO_FILE_EXT, storageDir);
            } catch (IOException ex) {
                Toast.makeText(requireContext(), getResources().getString(R.string.edit_main_text_temp_filename_error), Toast.LENGTH_SHORT).show();
                return;
            }
            tempPhotoPath = photoFile.getAbsolutePath();
            Uri photoURI = FileProvider.getUriForFile(requireContext(), FILE_PROVIDER, photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    /**
     * Show picture from @photoPath (selected from filesystem file)
     */
    @Override
    public void showMainPicture(String photoPath) {
        Glide.with(this)
            .load(photoPath)
            .centerCrop()
            .listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Toast.makeText(getContext(), getResources().getString(R.string.edit_main_text_filename_error), Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            })
            .into(mainPicture);
    }


    /**
     * Click "Delete" on "edit main picture" dialog fragment
     */
    @Override
    public void onDeletePhoto() {
        profilePresenter.deletePhoto();
    }

    @Override
    public void setMainImagePlaceholder() {
        mainPicture.setImageResource(R.drawable.user_icon_nobg);
    }

}