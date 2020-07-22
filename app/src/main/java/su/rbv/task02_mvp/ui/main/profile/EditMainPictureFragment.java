package su.rbv.task02_mvp.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import su.rbv.task02_mvp.R;

public class EditMainPictureFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_main_picture, container, false);

        EditMainPictureListener listener = (EditMainPictureListener) getParentFragment();

        view.findViewById(R.id.edit_main_pic_select_photo).setOnClickListener((v) -> {
            if (listener != null) {
                listener.onSelectPhoto();
            }
            dismiss();
        });

        view.findViewById(R.id.edit_main_pic_take_photo).setOnClickListener((v) -> {
            if (listener != null) {
                listener.onTakePhoto();
            }
            dismiss();
        });

        view.findViewById(R.id.edit_main_pic_delete_photo).setOnClickListener((v) -> {
            if (listener != null) {
                listener.onDeletePhoto();
            }
            dismiss();
        });

        return view;
    }

}