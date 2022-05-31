package za.ac.bookmanor;
import android.widget.ImageView;

public class Books {

    private String title, author, illustrator, pages, pagesRead, dateBookAdded;
    //private int pages, pagesRead;
    //private ImageView image;


    public Books() {

    }

    public Books(String title, String author, String illustrator, String pages, String pagesRead, String dateBookAdded) {
        this.title = title;
        this.author = author;
        this.illustrator = illustrator;
        this.pages = pages;
        this.pagesRead = pagesRead;
        this.dateBookAdded = dateBookAdded;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPagesRead() {
        return pagesRead;
    }

    public void setPagesRead(String pagesRead) {
        this.pagesRead = pagesRead;
    }

    public String getDateBookAdded() {
        return dateBookAdded;
    }

    public void setDateBookAdded(String dateBookAdded) {
        this.dateBookAdded = dateBookAdded;
    }

}
