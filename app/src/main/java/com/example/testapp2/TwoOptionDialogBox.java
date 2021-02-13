package com.example.testapp2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.testapp2.RegisterActivity.setSignUpFragment;

public class TwoOptionDialogBox {
    public static void twoOptionDialogBoxShow(Context context, String OkMessage, String CancelMessage)
    {
        // custom dialog box

        Dialog signIndialog=new Dialog(context);
        signIndialog.setContentView(R.layout.sign_in_dialog_box);
        signIndialog.setCancelable(true);
        signIndialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signInBtn=signIndialog.findViewById(R.id.sign_in_btn_dilaogbox);
        Button signUpBtn=signIndialog.findViewById(R.id.sign_up_btn_dilaogbox);
        signInBtn.setText(OkMessage);
        signUpBtn.setText(CancelMessage);
        Intent registerIntent=new Intent(context,RegisterActivity.class);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkMessage.equals("Sign In"))
                {
                    SignInFragment.isDisableSignInCloseBtn=true;
                    SignUpFragment.isDisableSignUpCloseBtn=true;
                }
                setSignUpFragment=false;
                signIndialog.dismiss();
                context.startActivity(registerIntent);

            }

        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkMessage.equals("Sign In"))
                {
                    SignUpFragment.isDisableSignUpCloseBtn=true;
                    SignInFragment.isDisableSignInCloseBtn=true;
                }
                signIndialog.dismiss();
                setSignUpFragment=true;
                context.startActivity(registerIntent);

            }
        });
        signIndialog.show();

    }
}
