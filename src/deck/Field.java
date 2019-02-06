package deck;

public class Field {
    private String name;
    private String value;

    public Field() {
        this(null, null);
    }

    public Field(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Field) {
            Field f = (Field)o;

            boolean namebool;
            if (name == null) {
                namebool = f.name == null;
            }
            else {
                namebool = f.name.equals(name);
            }

            boolean valuebool;
            if (value == null) {
                valuebool = f.value == null;
            }
            else {
                valuebool = f.value.equals(value);
            }

            return namebool && valuebool;
        }
        return false;
    }
}
