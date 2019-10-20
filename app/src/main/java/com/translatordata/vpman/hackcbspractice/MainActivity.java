package com.translatordata.vpman.hackcbspractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.translatordata.vpman.hackcbspractice.ModelClass.User;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN=1;

    AppCompatButton login,GoogleLogin;
    MaterialTextView registerText;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.
                LayoutParams.FLAG_FULLSCREEN);

        login=findViewById(R.id.login);
        GoogleLogin=findViewById(R.id.GoogleLogin);
        registerText=findViewById(R.id.registerText);



        registerText.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            finish();
        });

        GoogleLogin.setOnClickListener(view -> {
            FirebaseApp.initializeApp(MainActivity.this);
            firebaseAuth.addAuthStateListener(authStateListener);
        });

        final AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_main)
                .setGoogleButtonId(R.id.GoogleLogin)
                .build();
        AuthUI.getInstance().createSignInIntentBuilder()
                .setAuthMethodPickerLayout(customLayout).build();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Intent intent = new Intent(getApplicationContext(), MainMap.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAuthMethodPickerLayout(customLayout).setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            if (resultCode==RESULT_OK)
            {
                String name=firebaseAuth.getCurrentUser().getDisplayName();
                String mobileNo=firebaseAuth.getCurrentUser().getPhoneNumber();
                String Email=firebaseAuth.getCurrentUser().getEmail();
                User user=new User(name,Email,mobileNo);
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"Sucessfully Login", Toast.LENGTH_LONG).show();

                        }
                    }
                });

                Toast.makeText(this,"Signed in",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainMap.class);
                startActivity(intent);
                finish();
            }
            else
            {
                //  Toast.makeText(this,"Signed in",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener!=null)
        {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(getApplicationContext(), MainMap.class);
            startActivity(intent);


            finish();
            Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
