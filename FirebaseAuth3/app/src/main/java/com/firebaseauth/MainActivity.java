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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//click functionality implement view
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise the progress bar object
        progressDialog = new ProgressDialog(this);
        //initialise firebase object
        //we can use this object to register user to the server
        firebaseAuth = FirebaseAuth.getInstance();
        //if user is already logged in ,we can directly navigate him to profile Activity
        if(firebaseAuth.getCurrentUser() != null) {
            //Profile Activity Here
            //finsih current activity before starting new activity
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }


        //initialise our views
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        //for buttons
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }
    //register user method
    private void registerUser(){
        //get email & pass
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
        progressDialog.setMessage("Registering User, Please Wait...");
        progressDialog.show();
        //take two string arg email & pass
        //create user with email & pass on firebase console
        //we will also attach a LISTENER to check wether registration is done or not
        //this listner with take current acticity context
        //then this method - task is succesuful
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //check if successful
                            if(task.isSuccessful()){
                                //user is successfully registerd
                                // we will start profile activity here
                                //right now let it display toast only
                                if(firebaseAuth.getCurrentUser() != null) {
                                    //Profile Activity Here
                                    //finsih current activity before starting new activity
                                    finish();
                                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                                }
                            }
                        else{
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,"Could not Register, Please Try Again" ,Toast.LENGTH_SHORT).show();
                            }
                    }
                });
    }

    @Override
    public void onClick (View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //will open the login activity
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
