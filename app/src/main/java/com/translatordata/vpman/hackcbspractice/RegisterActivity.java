package com.translatordata.vpman.hackcbspractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.translatordata.vpman.hackcbspractice.ModelClass.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    MaterialTextView loginpeJao;
    AppCompatButton registerbut;
    EditText name_reg,mobile_No,emailedit_reg,password_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.
                LayoutParams.FLAG_FULLSCREEN);
        loginpeJao=findViewById(R.id.loginpeJao);
        name_reg=findViewById(R.id.name_reg);
        mAuth = FirebaseAuth.getInstance();
        mobile_No=findViewById(R.id.mobile_No);
        emailedit_reg=findViewById(R.id.emailedit_reg);
        password_reg=findViewById(R.id.password_reg);
        loginpeJao.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            finish();
        });
        registerbut=findViewById(R.id.registerbut);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);


            finish();
            Toast.makeText(RegisterActivity.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
        }
        registerbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailedit_reg.getText().toString().trim();

                String mobileNo=mobile_No.getText().toString().trim();

                String name=name_reg.getText().toString().trim();

                String password = password_reg.getText().toString().trim();

                if (email.isEmpty())
                {
                    emailedit_reg.setError("Email is required");
                    emailedit_reg.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailedit_reg.setError("Please enter a valid email");
                    emailedit_reg.requestFocus();
                    return;
                }
                if (name.isEmpty())
                {
                    name_reg.setError("Name is required");
                    name_reg.requestFocus();
                    return;
                }
                if (mobileNo.isEmpty()) {
                    mobile_No.setError("Mobile No is required");
                    mobile_No.requestFocus();
                    return;
                }
                if (!Patterns.PHONE.matcher(mobileNo).matches())
                {
                    mobile_No.setError("Please enter a valid mobile No");
                    mobile_No.requestFocus();
                    return;
                }
                if (password.isEmpty())
                {
                    password_reg.setError("Password is required");
                    password_reg.requestFocus();
                    return;
                }
                if (password.length()<6)
                {
                    password_reg.setError("Minimum lenght of password should be 6");
                    password_reg.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {

                            User user1=new User(name,email,mobileNo);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                                    .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegisterActivity.this,"Sucessfully Register", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this,MapActivity.class));
                                        finish();
                                    }
                                    else
                                        {
                                        //display a failure message
                                    }
                                }
                            });

/*                            finish();
                            startActivity(new Intent(RegisterActivity.this,MapActivity.class));*/
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this, "This Email is already registered", Toast.LENGTH_SHORT).show();
                                emailedit_reg.setError("This Email is already registered");
                                emailedit_reg.requestFocus();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                });
            }
        });
    }
}
