package com.example.clubevents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DevProfile extends Fragment {
    private FragmentListener fragmentListener;
    private View rootView;
    private TextView fb,insta,git,sendMail;
    public DevProfile(FragmentListener fragmentListener){
        this.fragmentListener=fragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView= inflater.inflate(R.layout.dev_profile, container, false);

        fb=rootView.findViewById(R.id.fb);
        insta=rootView.findViewById(R.id.insta);
        git=rootView.findViewById(R.id.git);
        sendMail=rootView.findViewById(R.id.sendMail);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.FB_DEV);
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.INSTA_DEV);
            }
        });
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.GIT_DEV);
            }
        });
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onClickText(Links.SENDMAIL_DEV);
            }
        });
        return rootView;
    }

}
