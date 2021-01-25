package com.example.testapp2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    private TextView forgotPasswordGoBack;
    private FrameLayout parentFrameLayout;
    private EditText resisteredEmail;
    private Button resetPasswordBtn;

    private FirebaseAuth firebaseAuth;
    private  ViewGroup emailIconContainer;
    private ImageButton emailIconInProgesss,emailIconSent;
    private TextView emailIconText;
    private ProgressBar progressBar;
    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reset_password, container, false);
        forgotPasswordGoBack=view.findViewById(R.id.tv_forgot_password_goback);
        resisteredEmail=view.findViewById(R.id.forgot_password_email);
        resetPasswordBtn=view.findViewById(R.id.reset_password_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        emailIconInProgesss=view.findViewById(R.id.forgot_password_email_icon_progess);
        emailIconSent=view.findViewById(R.id.forgot_password_email_icon_sent);
        progressBar=view.findViewById(R.id.forgot_password_email_progressbar);
        emailIconContainer=view.findViewById(R.id.forgot_email_pass_container);
        emailIconText=view.findViewById(R.id.forgot_password_email_icontext);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgotPasswordGoBack=view.findViewById(R.id.tv_forgot_password_goback);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        resisteredEmail=view.findViewById(R.id.forgot_password_email);
        firebaseAuth=FirebaseAuth.getInstance();
        emailIconInProgesss=view.findViewById(R.id.forgot_password_email_icon_progess);
        emailIconSent=view.findViewById(R.id.forgot_password_email_icon_sent);
        progressBar=view.findViewById(R.id.forgot_password_email_progressbar);
        emailIconContainer=view.findViewById(R.id.forgot_email_pass_container);
        emailIconText=view.findViewById(R.id.forgot_password_email_icontext);
        forgotPasswordGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This way we can from one fragment to another fragment of same activity (
                setFragment(new SignInFragment());

            }
        });
        resisteredEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkValidEmail();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPasswordBtn.setEnabled(false);
                resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
                emailIconInProgesss.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                emailIconSent.setVisibility(View.INVISIBLE);
                firebaseAuth.sendPasswordResetEmail(resisteredEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                           // TransitionManager.beginDelayedTransition(emailIconContainer);
                            emailIconText.setVisibility(View.VISIBLE);
                            emailIconSent.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            emailIconInProgesss.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(),"Email sent successfully",Toast.LENGTH_LONG).show();

                        }else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                            emailIconText.setVisibility(View.INVISIBLE);
                            emailIconInProgesss.setVisibility(View.INVISIBLE);



                        }
                        resetPasswordBtn.setEnabled(true);
                       // emailIconSent.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                        resetPasswordBtn.setTextColor(Color.rgb(255,255,255));

                    }
                });

            }
        });



    }
    private  void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slide_out_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkValidEmail()
    {
        if(!TextUtils.isEmpty(resisteredEmail.getText().toString()))
        {
            resetPasswordBtn.setEnabled(true);
            resetPasswordBtn.setTextColor(Color.rgb(255, 255, 255));

        }
        else
        {
            resetPasswordBtn.setEnabled(false);
            resetPasswordBtn.setTextColor(Color.argb(50,255, 255, 255));
        }
    }

}
