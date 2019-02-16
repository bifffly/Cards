package deck;

import java.util.Arrays;
import java.util.StringJoiner;

public class Card {
    private Side front;
    private Side back;

    public Card() {
        this.front = new Side("front");
        this.back = new Side("back");
    }

    public Card(Side front, Side back) {
        this.front = front;
        this.back = back;
    }

    public Side getFront() {
        return front;
    }

    public Side getBack() {
        return back;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        for (Side side : Arrays.asList(front, back)) {
            StringJoiner fieldJoiner = new StringJoiner(": ", "{", "}");
            fieldJoiner.add(side.getName()).add(side.getValue());
            sj.add(fieldJoiner.toString());
        }
        return sj.toString();
    }
}
