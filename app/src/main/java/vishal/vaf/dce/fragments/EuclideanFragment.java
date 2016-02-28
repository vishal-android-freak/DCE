package vishal.vaf.dce.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vishal.vaf.dce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EuclideanFragment extends Fragment {


    public EuclideanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_euclidean, container, false);
    }


}
