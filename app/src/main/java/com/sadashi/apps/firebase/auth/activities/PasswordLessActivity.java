package com.sadashi.apps.firebase.auth.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.sadashi.apps.firebase.auth.R;

public class PasswordLessActivity extends AppCompatActivity {
    private static final String TAG = PasswordLessActivity.class.getSimpleName();

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_less);
        editText = findViewById(R.id.email_address);

        Button send = findViewById(R.id.send_mail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

        Uri uri = getIntent().getData();
        if (uri != null) {
            verifyLinkAndSignIn(uri);
        }
    }

    private void sendMail() {
        String email = editText.getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        .setUrl("https://authsample-74469.firebaseapp.com")
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                                "com.sadashi.apps.firebase.auth",
                                true,
                                null)
                        .build();
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Succeed to send email verification.");
                        }
                    }
                });
    }

    private void verifyLinkAndSignIn(Uri uri) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String emailLink = uri.toString();
        String email = editText.getText().toString();

        if (auth.isSignInWithEmailLink(emailLink)) {
            auth.signInWithEmailLink(email, emailLink)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Successfully signed in with email link!");
                                AuthResult result = task.getResult();
                            } else {
                                Log.e(TAG, "Error signing in with email link: "
                                        + task.getException().getMessage());
                            }
                        }
                    });
        }
    }
}
