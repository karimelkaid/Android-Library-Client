package AndroidBooksClient.androidbooksclient.Model;

public class Book {
    private int id;

    private String title;
    private int publication_year;
    private int authorId;

    public Book(int id, String title, int authorId) {
        this.id = id;
        this.title = title;
        this.publication_year = -1;
        this.authorId = authorId;
    }
    public Book(int id, String title, int publication_year, int authorId) {
        this.id = id;
        this.title = title;
        this.publication_year = publication_year;
        this.authorId = authorId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
