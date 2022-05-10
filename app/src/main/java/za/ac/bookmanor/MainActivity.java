package za.ac.bookmanor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private TextView directToCreateAcc, directToForgottenPw;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
        signIn = (Button) findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();

        directToCreateAcc = (TextView) findViewById(R.id.txtDirectCreateAcc);
        directToForgottenPw = (TextView) findViewById(R.id.txtDirectForgotPswrd);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userSignIn();
            }
        });

        directToCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, CreateNewAccount.class));
            }
        });

        directToForgottenPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "????Really????", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void userSignIn() {

        String cMail = email.getText().toString();
        String cPassword = password.getText().toString();

        if(cMail.isEmpty())
        {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(cMail).matches())
        {
            email.setError("Invalid email, please try again!");
            email.requestFocus();
            return;
        }

        if(cPassword.isEmpty())
        {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(cPassword.length() < 6)
        {
            password.setError("Min length of 6 characters for password!");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(cMail, cPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Successfully signed in!!", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(MainActivity.this, Dashboard.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Sign in was unsuccessful due to: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


