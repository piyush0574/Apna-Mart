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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseAuth firebaseAuth;
    private TextView alreadyHaveAnAccount ;
    private FrameLayout parentFrameLayout;
    private EditText email,fullName,password,confirmPassword;
    private ImageButton closeBtn;
    private Button signUpBtn;
    private ProgressBar progressBar;
    private FirebaseFirestore firebaseFirestore;
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAnAccount=view.findViewById(R.id.tv_already_have_an_account);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        email=getActivity().findViewById(R.id.sign_in_email);
        fullName=getActivity().findViewById(R.id.sign_up_fullname);
        password=getActivity().findViewById(R.id.sign_up_password);
        confirmPassword=getActivity().findViewById(R.id.sign_up_confirm_password);
        closeBtn=getActivity().findViewById(R.id.sign_in_close_btn);
        signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
        progressBar=getActivity().findViewById(R.id.sign_up_progress_bar);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
        alreadyHaveAnAccount=view.findViewById(R.id.tv_already_have_an_account);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        email=getActivity().findViewById(R.id.sign_in_email);
        fullName=getActivity().findViewById(R.id.sign_up_fullname);
        password=getActivity().findViewById(R.id.sign_up_password);
        confirmPassword=getActivity().findViewById(R.id.sign_up_confirm_password);
        closeBtn=getActivity().findViewById(R.id.sign_in_close_btn);
        signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                CheckInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                CheckInputs();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();

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
                CheckInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                CheckInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHomeScreen();
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
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
    private void CheckInputs()
    {
        if(!TextUtils.isEmpty(email.getText()))
        {
            if(!TextUtils.isEmpty(fullName.getText()))
            {
                if(!TextUtils.isEmpty(password.getText()) && password.length()>=2)
                {
                    if(!TextUtils.isEmpty(confirmPassword.getText()))
                    {
                        signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(Color.rgb(255,255,255));
                    }else
                    {
                        signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50,255,255,255));
                    }
                }else
                {
                    signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50,255,255,255));
                }

            }else
                {
                    signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50,255,255,255));

            }

        }else{
            signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50,255,255,255));

        }
    }
    private void checkEmailAndPassword()
    {
        Drawable customErrorIcon=getResources().getDrawable(R.mipmap.error_icon);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());
        if(email.getText().toString().matches(emailPattern))
        {
            if(password.getText().toString().matches(confirmPassword.getText().toString()))
            {
                progressBar= getActivity().findViewById(R.id.sign_up_progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task)
                           {
                              if(task.isSuccessful()){
                                //  Map<Object,String > users = new HashMap<>();
                                  HashMap<Object,String> userdata = new HashMap<>();
                                  userdata.put("FullName",fullName.getText().toString());
                                  firebaseFirestore.collection("USERS").add(userdata)
                                          .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                              @Override
                                              public void onComplete(@NonNull Task<DocumentReference> task) {
                                                  if(task.isSuccessful())
                                                  {
                                                      moveToHomeScreen();

                                                  }
                                                  else
                                                  {
                                                      progressBar=getActivity().findViewById(R.id.sign_up_progress_bar);
                                                      progressBar.setVisibility(View.INVISIBLE);
                                                      signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
                                                      signUpBtn.setEnabled(true);
                                                      signUpBtn.setTextColor(Color.rgb(255,255,255));
                                                      String error=task.getException().getMessage();
                                                      // Toast.makeText(getActivity(),error,).show();
                                                      Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                                  }

                                              }
                                          });




                              }else
                              {
                                  progressBar=getActivity().findViewById(R.id.sign_up_progress_bar);
                                  progressBar.setVisibility(View.INVISIBLE);
                                  signUpBtn=getActivity().findViewById(R.id.sign_up_btn);
                                  signUpBtn.setEnabled(true);
                                  signUpBtn.setTextColor(Color.rgb(255,255,255));
                                  String error=task.getException().getMessage();
                                 // Toast.makeText(getActivity(),error,).show();
                                  Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                              }

                           }
                       });

            }else
            {
                confirmPassword.setError("Password does't match",customErrorIcon);

            }
        }
        else
        {
            email.setError("Invalid Email.",customErrorIcon);

        }

    }
    private void moveToHomeScreen()
    {
        Intent mainIntent=new Intent(getActivity(),HomeScreenActivity.class);
        //checkpoints
        startActivity(mainIntent);
        getActivity().finish();

    }




}