package vishal.vaf.dce.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vishal.vaf.dce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaesarFragment extends Fragment implements ShareVignereCeasarFragment.ShareClicksListener {

    Button button;
    EditText string, key;
    TextView stringDisplay1, stringDisplay2, keyDisplay1, keyDisplay2, cipher1, cipher2;
    ProgressDialog progressDialog;

    FloatingActionButton floatingActionButton;

    String plainText, encryptedText, shareText="";
    int keyText;
    boolean isEncodedKeySelected, isEncodedTextSelected, isOnlyEncodedSelected;

    public CaesarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cipher, container, false);

        string = (EditText) view.findViewById(R.id.string);
        key = (EditText) view.findViewById(R.id.key);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.encode_vc);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFABClick();
            }
        });

        stringDisplay1 = (TextView) view.findViewById(R.id.string1);
        stringDisplay2 = (TextView) view.findViewById(R.id.string2);
        keyDisplay1 = (TextView) view.findViewById(R.id.key1);
        keyDisplay2 = (TextView) view.findViewById(R.id.key2);
        cipher1 = (TextView) view.findViewById(R.id.cipher1);
        cipher2 = (TextView) view.findViewById(R.id.cipher2);

        button = (Button) view.findViewById(R.id.encode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!string.getText().toString().equals("") && !key.getText().toString().equals("")) {
                    cipherTextGeneration();
                } else {
                    Toast.makeText(getActivity(), "Please enter valid details", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    @Override
    public void onPositiveButtonClick(DialogInterface dialog) {
        if(isEncodedKeySelected) {
            shareText = shareText + "Key used for encoding: " + key.getText().toString();
        }
        if(isEncodedTextSelected) {
            shareText = shareText + "\n\nEncoded text is " + encryptedText;
        }
        if(isOnlyEncodedSelected) {
            shareText = shareText + encryptedText;
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share With"));
        dialog.dismiss();
    }

    @Override
    public void onCheckBoxClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked) {
            switch (which) {
                case 0:
                    isEncodedKeySelected = true;
                    break;
                case 1:
                    isEncodedTextSelected = true;
                    break;
                case 2:
                    isOnlyEncodedSelected = true;
                    break;
            }
        } else {
            isEncodedTextSelected = false;
            isEncodedKeySelected = false;
            isOnlyEncodedSelected = false;
        }
    }

    public void cipherTextGeneration() {

        encryptedText = "";

        progressDialog = ProgressDialog.show(getActivity(), "Encoding", "Please Wait....", false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                plainText = string.getText().toString();
                keyText = Integer.parseInt(key.getText().toString());

                for (int i = 0; i < plainText.length(); i++) {
                    int c = plainText.charAt(i);
                    if (Character.isUpperCase(c)) {
                        c = c + (keyText % 26);
                        if (c > 'z')
                            c = c - 26;
                    } else if (Character.isLowerCase(c)) {
                        c = c + (keyText % 26);
                        if (c > 'z')
                            c = c - 26;
                    }
                    encryptedText += (char) c;
                }

                stringDisplay1.setText("String entered:");
                stringDisplay2.setText(plainText);
                keyDisplay1.setText("Key used for encoding:");
                keyDisplay2.setText(key.getText());
                cipher1.setText("Cipher Text is:");
                cipher2.setText(encryptedText);

                progressDialog.dismiss();
                floatingActionButton.show();
            }
        }, 5000);
    }

    public void onFABClick() {
        ShareVignereCeasarFragment shareVignereCeasarFragment = new ShareVignereCeasarFragment();
        shareVignereCeasarFragment.setTargetFragment(this, 0);
        shareVignereCeasarFragment.show(getFragmentManager(), "Share");
    }
}
