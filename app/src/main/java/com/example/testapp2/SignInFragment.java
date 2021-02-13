package com.example.testapp2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ScheduledExecutorService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private TextView dontHaveAccount,forgotPassword;
    private ProgressBar progressBar;
    private FrameLayout parentFrameLayout;
    private EditText email,password;
    private Button signBtn;
    private ImageButton signClosebtn;
    FirebaseAuth firebaseAuth;
    public static boolean isDisableSignInCloseBtn=false;

    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAccount=view.findViewById(R.id.tv_dont_have_account_sign_in);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        email=view.findViewById(R.id.sign_in_email);
        password=view.findViewById(R.id.sign_in_password);
        signBtn=view.findViewById(R.id.sign_in_btn);
        signClosebtn=view.findViewById(R.id.sign_in_close_btn);
        progressBar=view.findViewById(R.id.sign_in_progress_bar);
        forgotPassword=view.findViewById(R.id.sign_in_forgot_password);
        firebaseAuth=FirebaseAuth.getInstance();
        if(isDisableSignInCloseBtn)
        {
            signClosebtn.setVisibility(View.GONE);
        }
        else
        {
            signClosebtn.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDisableSignInCloseBtn=false;
        SignUpFragment.isDisableSignUpCloseBtn=false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkEmailAndPassword();

            }
        });
        signClosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHomeScreen();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This way we can from one fragment to another fragment of same activity (
                RegisterActivity.OnResetPasswordFragment=true;
                setFragment(new ResetPasswordFragment());

            }
        });

    }
    private  void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs()
    {
        if(!TextUtils.isEmpty(email.getText()))
        {
            if(!TextUtils.isEmpty(password.getText()))
            {
                signBtn.setEnabled(true);
                signBtn.setTextColor(Color.rgb(255,255,255));

            }else
            {
                signBtn.setEnabled(false);
                signBtn.setTextColor(Color.argb(50,255,255,255));

            }

        }else{
            signBtn.setEnabled(false);
            signBtn.setTextColor(Color.argb(50,255,255,255));

        }

    }
    private  void checkEmailAndPassword()
    {
        Drawable customErrorIcon=getResources().getDrawable(R.mipmap.error_icon);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());
        if(email.getText().toString().matches(emailPattern))
        {
            if(password.length()>=6)
            {
                signBtn.setEnabled(false);
                signBtn.setTextColor(Color.argb(50,255,255,255));
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    moveToHomeScreen();

                                }else
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signBtn.setEnabled(true);
                                    signBtn.setTextColor(Color.rgb(255,255,255));
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                }
                            }
                        });


            }else
                {
                    Toast.makeText(getActivity(),"Password Length must be greater than 8",Toast.LENGTH_SHORT).show();

            }

        }else
        {
            Toast.makeText(getActivity(),"Wrong Email format",Toast.LENGTH_SHORT).show();

        }

    }
    private void moveToHomeScreen()
    {
      if(isDisableSignInCloseBtn)
      {
          isDisableSignInCloseBtn=false;
      }
      else
      {
          //This way we can move from one acticity to another
          Intent mainIntent=new Intent(getActivity(),HomeScreenActivity.class);
          isDisableSignInCloseBtn=false;
          //checkpoints
          startActivity(mainIntent);
      }

        // once the user do succcesful sign in ,then again make close btn visible for future
        getActivity().finish();

    }

}