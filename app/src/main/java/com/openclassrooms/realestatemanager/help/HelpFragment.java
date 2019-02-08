package com.openclassrooms.realestatemanager.help;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.openclassrooms.realestatemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    public static final String HELP_DOCUMENT_FILE  = "helpsection.pdf";
    PDFView mPDFView;

    public HelpFragment() {}

    public static HelpFragment newInstance(){
        return new HelpFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_help, container, false);
        mPDFView = view.findViewById(R.id.pdf_viewer);
        mPDFView.fromAsset(HELP_DOCUMENT_FILE).load();
        return view;
    }

}
