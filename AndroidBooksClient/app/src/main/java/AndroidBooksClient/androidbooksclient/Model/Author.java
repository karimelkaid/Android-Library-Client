package AndroidBooksClient.androidbooksclient.Model;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private int id;
    private String fist_name;
    private String last_name;
    private List<Book> books;

    public Author(int id, String fist_name, String last_name, List<Book> books_of_author) {
        this.id = id;
        this.fist_name = fist_name;
        this.last_name = last_name;
        this.books = new ArrayList<>(books_of_author);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFist_name() {
        return fist_name;
    }

    public void setFist_name(String fist_name) {
        this.fist_name = fist_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }


    public void addBook(Book book) {
        books.add(book);
    }
}
