package com.example.testapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private FirebaseAuth auth;
    public static Boolean setSignUpFragment=false;
    public static  boolean OnResetPasswordFragment=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        frameLayout=findViewById(R.id.register_framelayout);
        if(setSignUpFragment==true)
        {
            setSignUpFragment=false;
            setDefaultFragment(new SignUpFragment());

        }
        else {
            setDefaultFragment(new SignInFragment());
        }


    }
    private  void setDefaultFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            SignUpFragment.isDisableSignUpCloseBtn=false;
            SignInFragment.isDisableSignInCloseBtn=false;

            if(OnResetPasswordFragment)
            {
                setFragment(new SignInFragment());
                OnResetPasswordFragment=false;//again make it false
                return false;
            }


        return super.onKeyDown(keyCode, event);
    }
    private  void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slide_out_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}