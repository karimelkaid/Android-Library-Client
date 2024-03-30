package AndroidBooksClient.androidbooksclient.Model;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private int id;
    private String name;
    private List<Book> books;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
        this.books = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        books.add(book);
    }
}
