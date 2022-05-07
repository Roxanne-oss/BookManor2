package za.ac.bookmanor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import za.ac.bookmanor.User;

import java.lang.String;

public class CreateNewAccountActivity extends AppCompatActivity{
    //Declarations

    private String salt;
    EditText name, mail, pswrd, confPswrd;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        name = (EditText) findViewById(R.id.editTextTextPersonName);
        mail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        pswrd = (EditText) findViewById(R.id.editTextTextPassword);
        confPswrd = (EditText) findViewById(R.id.editTextTextPassword2);
    }



    //Code that executes when create account button is clicked
    public void createAccountButtonClick(View v) throws NoSuchAlgorithmException {
        if(name.getText() != null && mail.getText() != null && pswrd.getText() != null && confPswrd.getText()!= null)
        {
            if(pswrd.getText() == confPswrd.getText())
            {
                //Creates new user and sets the attributes
                User user = new User();
                user.setFullName(name.getText().toString());
                user.setEmail(mail.getText().toString());
                salt = getSalt();  //stores the salt in the salt string
                user.setHashedPassword(get_SHA_512_SecurePassword(pswrd.getText().toString(), salt));  //sets the password using the hashing method

                //getting the instance of the database
                database = FirebaseDatabase.getInstance();

                DatabaseReference ref = database.getReference("users");
                ref.push().setValue(user);

            }
        }
    }

    //Hash password
    private static String get_SHA_512_SecurePassword(String passwordToHash,
                                                     String salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Add salt
    private static String getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

}