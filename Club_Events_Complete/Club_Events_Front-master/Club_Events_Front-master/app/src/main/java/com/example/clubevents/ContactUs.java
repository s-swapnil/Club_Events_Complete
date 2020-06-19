package com.example.clubevents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactUs extends Fragment {
    private FragmentListener fragmentListener;
    private View rootView;
    private TextView fb,page,git,sendMail;
    public ContactUs(FragmentListener fragmentListener){
        this.fragmentListener=fragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView= inflater.inflate(R.layout.contactus_pg, container, false);

        fb=rootView.findViewById(R.id.code_fb);
        page=rootView.findViewById(R.id.code_clubPg);
        git=rootView.findViewById(R.id.code_git);
        sendMail=rootView.findViewById(R.id.code_sendmail);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.CODE_FB);
            }
        });
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.CODE_WEB);
            }
        });
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.CODE_GIT);
            }
        });
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.SENDMAIL_CODE);
            }
        });
        return rootView;
    }
}
