package vishal.vaf.dce.fragments;



import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
public class RSAFragment extends Fragment implements ShareRsaFragment.ShareClicksListener {

    private EditText prime1, prime2, public_key, message;
    private TextView stringDisplay1, stringDisplay2, keyDisplay1, keyDisplay2, cipher1, cipher2;
    private Button button;
    private ProgressDialog progressDialog;
    private FloatingActionButton floatingActionButton;
    Snackbar snackbar;

    int primeNumber1, primeNumber2, phi, publicKey, privateKey, mMessage, n, cipherText;
    boolean isPhiSelected, isProductSelected, isEncodedSelected, isKeySelected, isOnlyEncodedSelected;
    String shareText="";

    public RSAFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rsa, container, false);

        stringDisplay1 = (TextView) view.findViewById(R.id.string1);
        stringDisplay2 = (TextView) view.findViewById(R.id.string2);
        keyDisplay1 = (TextView) view.findViewById(R.id.key1);
        keyDisplay2 = (TextView) view.findViewById(R.id.key2);
        cipher1 = (TextView) view.findViewById(R.id.cipher1);
        cipher2 = (TextView) view.findViewById(R.id.cipher2);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_rsa);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFABClick();
            }
        });

        prime1 = (EditText) view.findViewById(R.id.prime1);
        prime2 = (EditText) view.findViewById(R.id.prime2);
        public_key = (EditText) view.findViewById(R.id.value_e);
        message = (EditText) view.findViewById(R.id.message);

        public_key.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!prime1.getText().toString().equals("") && !prime2.getText().toString().equals(""))
                {
                    primeNumber1 = Integer.parseInt(prime1.getText().toString());
                    primeNumber2 = Integer.parseInt(prime2.getText().toString());

                    phi = (primeNumber1 - 1) * (primeNumber2 - 1);

                    snackbar = Snackbar.make(v, "The Value of Phi is : " + String.valueOf(phi), Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }
                else {
                    Toast.makeText(getActivity(), "Please enter valid details", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        button = (Button) view.findViewById(R.id.encode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prime1.getText().toString().equals("") && !prime2.getText().toString().equals("") && !public_key.getText().toString().equals("") && !message.getText().toString().equals("")) {
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
        if(isPhiSelected) {
            shareText = shareText + "The valur of phi is: " + String.valueOf(phi);
        }
        if(isProductSelected) {
            shareText = shareText + "\n\nThe product of prime numbers used, is: " + String.valueOf(n);
        }
        if(isKeySelected) {
            shareText = shareText + "\n\nPublic key is: " + String.valueOf(publicKey);
        }
        if(isEncodedSelected) {
            shareText = shareText + "\n\nThe encoded string is: " + String.valueOf(cipherText);
        }
        if(isOnlyEncodedSelected) {
            shareText = shareText + String.valueOf(cipherText);
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        Log.d("DCE", String.valueOf(cipherText));
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share With"));
        dialog.dismiss();
    }

    @Override
    public void onCheckBoxClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked) {
            switch (which) {
                case 0:
                    isPhiSelected = true;
                    break;
                case 1:
                    isProductSelected = true;
                    break;
                case 2:
                    isEncodedSelected = true;
                    break;
                case 3:
                    isKeySelected = true;
                    break;
                case 4:
                    isOnlyEncodedSelected = true;
                default:
                    break;
            }
        } else {
            isKeySelected = false;
            isPhiSelected = false;
            isProductSelected = false;
            isEncodedSelected = false;
            isOnlyEncodedSelected = false;
        }
    }

    public void cipherTextGeneration() {

        snackbar.dismiss();
        progressDialog = ProgressDialog.show(getActivity(), "Encoding", "Please Wait....", false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                primeNumber1 = Integer.parseInt(prime1.getText().toString());
                primeNumber2 = Integer.parseInt(prime2.getText().toString());
                publicKey = Integer.parseInt(public_key.getText().toString());
                mMessage = Integer.parseInt(message.getText().toString());

                n = primeNumber1 * primeNumber2;

                phi = (primeNumber1 - 1) * (primeNumber2 - 1);

                for (int i = 1; i <= phi; i++) {
                    int x = (i * publicKey) % phi;

                    if (x == 1) {
                        privateKey = i;
                        break;
                    }
                }

                cipherText = (int)(Math.pow((double)mMessage,(double)publicKey)) % n;

                stringDisplay1.setText("Message Entered:");
                stringDisplay2.setText(String.valueOf(mMessage));
                keyDisplay1.setText("Public key is:");
                keyDisplay2.setText(String.valueOf(publicKey));
                cipher1.setText("Cipher text is:");
                cipher2.setText(String.valueOf(cipherText));

                progressDialog.dismiss();
                floatingActionButton.show();
            }
        },5000);
    }

    public void onFABClick() {
        ShareRsaFragment shareRsaFragment = new ShareRsaFragment();
        shareRsaFragment.setTargetFragment(this, 0);
        shareRsaFragment.show(getFragmentManager(), "Share");
    }
}
