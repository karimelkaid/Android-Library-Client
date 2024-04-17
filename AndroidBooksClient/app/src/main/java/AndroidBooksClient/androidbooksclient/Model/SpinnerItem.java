package AndroidBooksClient.androidbooksclient.Model;

public class SpinnerItem {
    private String name;
    private int value;

    public SpinnerItem(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
