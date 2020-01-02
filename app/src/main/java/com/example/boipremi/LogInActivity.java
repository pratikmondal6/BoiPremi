package com.example.boipremi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

   private TextView login_email, login_pass;
   private Button login;
   private Button SignUp;
   private FirebaseAuth mAuth;
   private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        login_email=findViewById(R.id.email_log);
        login_pass=findViewById(R.id.pass_log);
        login=findViewById(R.id.btn_login);
        SignUp=findViewById(R.id.signup);
        progressBar=findViewById(R.id.progress);

        login.setOnClickListener(this);
        SignUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View view) {

        switch ((view.getId()))
        {
            case R.id.btn_login:
                userLogin();
                break;

            case R.id.signup:
                Intent intent=new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userLogin() {

        String email= login_email.getText().toString().trim();
        String password=login_pass.getText().toString().trim();

        if(email.isEmpty())
        {
            login_email.setError("Enter email ");
            login_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            login_pass.setError("Enter Valid email");
            login_pass.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            login_pass.setError("Enter password ");
            login_pass.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            login_pass.setError("Enter 6 digit password");
            login_pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful())
                {

                    Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }else
                {
                    Toast.makeText(getApplicationContext(),"Error :" +task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        });




    }
}
