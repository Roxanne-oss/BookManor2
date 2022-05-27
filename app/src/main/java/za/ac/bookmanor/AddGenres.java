package za.ac.bookmanor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGenres extends AppCompatActivity implements View.OnClickListener {

    //Declarations
    Button genreIconButton;
    EditText etCustomGenreName;
    EditText etBooksGoal;
    Button addFolderInGenreButton;
    Button createGenreButton;
    private static final int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_genres);
        genreIconButton = (Button) findViewById(R.id.genreIconButton);
        etCustomGenreName = (EditText) findViewById(R.id.etCustomGenreName);
        etBooksGoal = (EditText)  findViewById(R.id.etBooksGoal);
        addFolderInGenreButton = (Button) findViewById(R.id.addFolderInGenreButton);
        createGenreButton = (Button) findViewById(R.id.createGenreButton);

        //sets on click listener
        genreIconButton.setOnClickListener(this);
        addFolderInGenreButton.setOnClickListener(this);
        createGenreButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.genreIconButton:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                break;
            case R.id.createGenreButton:

                break;
            case R.id.addFolderInGenreButton:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && requestCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
        }
    }
}