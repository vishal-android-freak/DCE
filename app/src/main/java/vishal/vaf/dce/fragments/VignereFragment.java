package vishal.vaf.dce.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import vishal.vaf.dce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VignereFragment extends Fragment {

    private Button button;
    private EditText string, key;
    private TextView stringDisplay1, stringDisplay2, keyDisplay1, keyDisplay2, cipher1, cipher2;
    private ProgressDialog progressDialog;

    FloatingActionButton floatingActionButton;

    String plainText, keyText;
    int lenPlainText, lenKey, x;
    char [] plainChar;
    char [] keyChar;
    char [] cipher;

    public VignereFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_vignere, container, false);

        string = (EditText) view1.findViewById(R.id.string);
        key = (EditText) view1.findViewById(R.id.key);

        floatingActionButton = (FloatingActionButton) view1.findViewById(R.id.fab);

        stringDisplay1 = (TextView) view1.findViewById(R.id.string1);
        stringDisplay2 = (TextView) view1.findViewById(R.id.string2);
        keyDisplay1 = (TextView) view1.findViewById(R.id.key1);
        keyDisplay2 = (TextView) view1.findViewById(R.id.key2);
        cipher1 = (TextView) view1.findViewById(R.id.cipher1);
        cipher2 = (TextView) view1.findViewById(R.id.cipher2);

        button = (Button) view1.findViewById(R.id.encode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!string.getText().toString().equals("") && !key.getText().toString().equals("")) {
                    x = 0;
                    cipherTextGeneration();
                } else {
                    Toast.makeText(getActivity(), "Please enter valid details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view1;
    }


    public void cipherTextGeneration() {

        stringDisplay2.setText("");
        keyDisplay2.setText("");
        cipher2.setText("");

        progressDialog = ProgressDialog.show(getActivity(), "Encoding", "Please Wait....", false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                plainText = string.getText().toString();
                keyText = key.getText().toString();

                lenPlainText = plainText.length();
                lenKey = keyText.length();

                int temp = lenPlainText % lenKey;
                temp = lenKey - temp;
                int len = lenPlainText + temp;
                plainChar = new char[len];
                keyChar = new char[len];
                cipher = new char[len];

                for (int i = 0; i < lenPlainText; i++) {
                    plainChar[i] = plainText.charAt(i);
                    stringDisplay2.setText(stringDisplay2.getText() + String.valueOf(plainChar[i]));
                }

                for (int i = (len - 1); i >= lenPlainText; i--) {
                    plainChar[i] = 'Z';
                    stringDisplay2.setText(stringDisplay2.getText() + String.valueOf(plainChar[i]));

                }

                for (int i = 0; i < (len / lenKey); i++) {
                    for (int j = 0; j < lenKey; j++) {
                        keyChar[x] = keyText.charAt(j);
                        keyDisplay2.setText(keyDisplay2.getText() + String.valueOf(keyChar[x]));
                        x++;
                    }
                }

                for (int i = 0; i < len; i++) {
                    x = (plainChar[i] + keyChar[i]) % 26;
                    cipher[i] = (char) (x + 'A');
                    cipher2.setText(cipher2.getText() + String.valueOf(cipher[i]));
                }

                stringDisplay1.setText("String used :");
                keyDisplay1.setText("Key used for encoding:");
                cipher1.setText("Cipher Text is:");

                progressDialog.dismiss();
                floatingActionButton.show();
            }
        }, 5000);
    }
}
