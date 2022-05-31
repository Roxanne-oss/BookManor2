package za.ac.bookmanor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter {

    private Activity mContext;
    private List<Books> booksList;
    private TextView aTitle, aAuthor, aIllustrator, aPages, aPagesRead, aDate;

    public BookAdapter(Activity mContext, List<Books> booksList){
        super(mContext, R.layout.book_item, booksList);

        this.mContext = mContext;
        this.booksList = booksList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.book_item, null, true);

        aTitle = listItemView.findViewById(R.id.txtTitle1);
        aAuthor = listItemView.findViewById(R.id.txtAuthor1);
        aIllustrator = listItemView.findViewById(R.id.txtIllustrator1);
        aPages = listItemView.findViewById(R.id.txtPages1);
        aPagesRead = listItemView.findViewById(R.id.txtPagesRead1);
        aDate = listItemView.findViewById(R.id.txtDate1);

        Books books = booksList.get(position);

        aTitle.setText(books.getTitle());
        aAuthor.setText(books.getAuthor());
        aIllustrator.setText(books.getIllustrator());
        aPages.setText(books.getPages());
        aPagesRead.setText(books.getPagesRead());
        aDate.setText(books.getDateBookAdded());

        return listItemView;
    }
}