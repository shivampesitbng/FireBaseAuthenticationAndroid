package com.firebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

//Declare the Objects
    private Button buttonSingIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialise the progress bar object
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        //To Check Whether user is Logged in currently or not
        if(firebaseAuth.getCurrentUser() != null) {
            //Profile Activity Here
            //finsih current activity before starting new activity
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        //Initaialise View object finding By Id
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSingIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

        //Attach Listerner
        buttonSingIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //if Email is Empty using textutils cls
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            //stopping the func to execute further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //if Password is Empty
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            //stopping the func to execute further
            return;
        }
        //if Validation are ok we will show progessdiaalog
        progressDialog.setMessage("Loging In, Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //Start the PROFILE ACTIVITY
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    }
                });
    }

        public void onClick (View view) {
            if(view == buttonSingIn){
                userLogin();
            }
            if(view == textViewSignUp){
                finish();
                startActivity(new Intent(this,MainActivity.class));
            }
    }
}
