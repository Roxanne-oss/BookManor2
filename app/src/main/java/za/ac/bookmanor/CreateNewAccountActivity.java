package za.ac.bookmanor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;


import java.lang.String;

public class CreateNewAccountActivity extends AppCompatActivity{
    //Declarations
    private String fullName;
    private String email;
    private String password;
    private String hashedPassword;
    private String salt;
    EditText name, mail, pswrd, confPswrd;


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

    //Getters and setters
    public String getFullName()
    {
        return fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    //Code that executes when create account button is clicked
    public void createAccountButtonClick(View v) throws NoSuchAlgorithmException {
        if(name.getText() != null && mail.getText() != null && pswrd.getText() != null && confPswrd.getText()!= null)
        {
            if(pswrd.getText() == confPswrd.getText())
            {
                setFullName(name.getText().toString());
                setEmail(mail.getText().toString());
                salt = getSalt();
                hashedPassword = get_SHA_512_SecurePassword(pswrd.getText().toString(), salt);
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