package deck;

public class Side {
    private String name;
    private String value;

    public Side(String name) {
        this.name = name;
        this.value = null;
    }

    public Side(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
