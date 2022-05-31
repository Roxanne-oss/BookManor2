package za.ac.bookmanor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.Calendar;
import java.util.Date;

public class AddNewBook extends AppCompatActivity {

    private EditText bookTitle, bookAuthor, bookIllustrator, bookPages, bookPageRead;
    private AutoCompleteTextView genreSelection;
    private AutoCompleteTextView folderSelection;
    private Button insertImage, addBook;
    private ImageView imageBook;

    private String[] genres = {"Poetry", "Fiction", "Romance", "Comedy"};
    private String[] folders = {"Folder 1", "Folder 2", "Folder 3", "Folder 4"};

    private ArrayAdapter<String> adapterItem1;
    private ArrayAdapter<String> adapterItem2;

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION=100;

    private DatabaseReference dbReference, dbReferencePoetry, dbReferenceFiction, dbReferenceRomance, dbReferenceComedy;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        bookTitle = (EditText) findViewById(R.id.edtBookTitle);
        bookAuthor = (EditText) findViewById(R.id.edtAuthor);
        bookIllustrator = (EditText) findViewById(R.id.edtIllustrator);
        bookPages = (EditText) findViewById(R.id.edtNumberOfPages);
        bookPageRead = (EditText) findViewById(R.id.edtPageLastRead);
        genreSelection = (AutoCompleteTextView) findViewById(R.id.autoTxtGenreSelection);
        folderSelection = (AutoCompleteTextView) findViewById(R.id.autoTxtFolderSelection);
        insertImage = (Button) findViewById(R.id.btnInsertImage);
        addBook = (Button) findViewById(R.id.btnAddBook);
        imageBook = (ImageView) findViewById(R.id.imgPlaceholder);

        adapterItem1 = new ArrayAdapter<String>(this, R.layout.genre_item, genres);
        adapterItem2 = new ArrayAdapter<String>(this, R.layout.folder_item, folders);

        genreSelection.setAdapter(adapterItem1);
        folderSelection.setAdapter(adapterItem2);

        //dbReference = FirebaseDatabase.getInstance().getReference("Books");
        //dbReferencePoetry = FirebaseDatabase.getInstance().getReference("Genres/Poetry/Books");
        //dbReferenceRomance = FirebaseDatabase.getInstance().getReference("Genres/Romance/Books");
        //dbReferenceFiction = FirebaseDatabase.getInstance().getReference("Genres/Fiction/Books");
        //dbReferenceComedy = FirebaseDatabase.getInstance().getReference("Genres/Comedy/Books");

        genreSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String genre = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AddNewBook.this, "Genre " + genre, Toast.LENGTH_SHORT).show();

                if(genre == "Poetry"){
                    dbReference = FirebaseDatabase.getInstance().getReference("Genres/Poetry/Books");
                }
                if(genre == "Romance"){
                    dbReference = FirebaseDatabase.getInstance().getReference("Genres/Romance/Books");
                }
                if(genre == "Fiction"){
                    dbReference = FirebaseDatabase.getInstance().getReference("Genres/Fiction/Books");
                }
                if(genre == "Comedy"){
                    dbReference = FirebaseDatabase.getInstance().getReference("Genres/Comedy/Books");
                }
            }
        });

        folderSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String folder = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AddNewBook.this, "Folder " + folder, Toast.LENGTH_SHORT).show();
            }
        });

        insertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ActivityCompat.checkSelfPermission(AddNewBook.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    final String[] permissions = {Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(AddNewBook.this, permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
                }
                else
                {
                    takePhoto();
                }
            }
        });

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNewBook();

            }
        });

    }

    public void takePhoto(){

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && data != null)
        {
            String cTitle = bookTitle.getText().toString();

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageBook.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {
            //call method to take a photo
            takePhoto();
        }
    }

    /*
    private String getFileExtension(Uri uri){

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
     */

    public void addNewBook(){

        String cTitle = bookTitle.getText().toString();
        String cAuthor = bookAuthor.getText().toString();
        String cIllustrator = bookIllustrator.getText().toString();
        String cGenre = genreSelection.getText().toString();
        String cFolder = genreSelection.getText().toString();
        String cNoPages = bookPages.getText().toString();
        String cPageLastRead = bookPageRead.getText().toString();
        String cImageName = imageBook.toString();

        Calendar calender = Calendar.getInstance();
        @SuppressLint({"NewApi", "LocalSuppress"}) String cDateAdded = DateFormat.getDateInstance().format(calender.getTime());

        if(cTitle.isEmpty())
        {
            bookTitle.setError("Title is required!");
            bookTitle.requestFocus();
            return;
        }

        if(cAuthor.isEmpty())
        {
            bookAuthor.setError("Author is required!");
            bookAuthor.requestFocus();
            return;
        }

        if(cIllustrator.isEmpty())
        {
            bookIllustrator.setError("Illustrator is required!");
            bookIllustrator.requestFocus();
            return;
        }

        if(cGenre.isEmpty())
        {
            genreSelection.setError("Genre selection is required!");
            genreSelection.requestFocus();
            return;
        }

        if(cFolder.isEmpty())
        {
            folderSelection.setError("Genre selection is required!");
            folderSelection.requestFocus();
            return;
        }

        if(cNoPages.isEmpty())
        {
            bookPages.setError("Number of pages is required!");
            bookPages.requestFocus();
            return;
        }

        if(cPageLastRead.isEmpty())
        {
            bookPageRead.setError("Page number last read is required!");
            bookPageRead.requestFocus();
            return;
        }

        /*
        if(cImage.isEmpty())
        {
            imageBook.setError("Image is required!");
            Toast.makeText(AddNewBook.this, "Image is required!", Toast.LENGTH_LONG).show();
            imageBook.requestFocus();
            return;
        }
         */

        //Books book = new Books(cTitle, cAuthor, cIllustrator, cNoPages, cPageLastRead, cDateAdded);

        //dbReference.push().setValue(book);

        //Toast.makeText(AddNewBook.this, "Data inserted!  :)", Toast.LENGTH_SHORT).show();

        //startActivity(new Intent(AddNewBook.this, ViewBooks.class));

        Books book = new Books(cTitle, cAuthor, cIllustrator, cNoPages, cPageLastRead, cDateAdded);

        dbReference.push().setValue(book);

        Toast.makeText(AddNewBook.this, "Data inserted!  :)", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(AddNewBook.this, ViewBooks.class));

    }
}


