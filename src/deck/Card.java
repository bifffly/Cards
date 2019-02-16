package deck;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Card {
    private List<Field> fields;

    public Card() {
        fields = new ArrayList<>();
    }

    public Card(List<Field> fields) {
        this.fields = fields;
    }

    public String getField(String name) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field.getValue();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        for (Field field : fields) {
            StringJoiner fieldJoiner = new StringJoiner(": ", "{", "}");
            fieldJoiner.add(field.getName()).add(field.getValue());
            sj.add(fieldJoiner.toString());
        }
        return sj.toString();
    }
}
