package vishal.vaf.dce.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import vishal.vaf.dce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareRsaFragment extends DialogFragment {

    ShareClicksListener shareClickListener;

    public ShareRsaFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Share")
                .setMultiChoiceItems(R.array.checkboxes_rsa, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        shareClickListener.onCheckBoxClick(dialog, which, isChecked);
                    }
                })
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shareClickListener.onPositiveButtonClick(dialog);
                    }
                });

        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            shareClickListener = (ShareClicksListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement ShareClickListener");
        }
    }

    public interface ShareClicksListener {
        void onCheckBoxClick(DialogInterface dialog, int which, boolean isChecked);
        void onPositiveButtonClick(DialogInterface dialog);
    }
}
