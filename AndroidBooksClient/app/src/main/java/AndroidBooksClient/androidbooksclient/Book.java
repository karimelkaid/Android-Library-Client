package AndroidBooksClient.androidbooksclient;

public class Book {
    private static int nextId = 0;
    private int id;

    private String title;
    private String author;
    private String description;

    public Book(String title, String author, String description) {
        this.id = nextId;
        nextId++;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
