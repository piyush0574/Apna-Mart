package com.example.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.testapp2.RegisterActivity.setSignUpFragment;

public class DialogBox {
    public static void twoOptionDialogBoxShowWithImage(Context context, String OkMessage, String CancelMessage)
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
public static void SignOutDialog(Context context, Intent intent)
{
    // custom dialog box

    Dialog signIndialog=new Dialog(context);
    signIndialog.setContentView(R.layout.two_option_message_dailogbox);
    signIndialog.setCancelable(true);
    signIndialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    TextView ok=signIndialog.findViewById(R.id.yes_btn);
    TextView cancel=signIndialog.findViewById(R.id.no_btn);
    ok.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            DBqueries.clearData();
            signIndialog.dismiss();
            context.startActivity(intent);

        }

    });
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            signIndialog.dismiss();

        }
    });
    signIndialog.show();

}
    public static void RemoveFromCartDialog(Context context, Intent intent)
    {
        // custom dialog box

        Dialog signIndialog=new Dialog(context);
        signIndialog.setContentView(R.layout.two_option_message_dailogbox);
        signIndialog.setCancelable(true);
        signIndialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView ok=signIndialog.findViewById(R.id.yes_btn);
        TextView cancel=signIndialog.findViewById(R.id.no_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                signIndialog.dismiss();
                context.startActivity(intent);

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIndialog.dismiss();

            }
        });
        signIndialog.show();

    }

}
