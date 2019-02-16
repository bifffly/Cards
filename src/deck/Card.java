package deck;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;

public class Card implements Serializable {
    private Side front;
    private Side back;
    private LocalDate dateLastAccessed;

    public Card() {
        this(new Side("front"), new Side("back"));
    }

    public Card(Side front, Side back) {
        this.front = front;
        this.back = back;
        this.dateLastAccessed = LocalDate.now();
    }

    public Side getFront() {
        return front;
    }

    public Side getBack() {
        return back;
    }

    public void updateDate() {
        dateLastAccessed = LocalDate.now();
    }

    public LocalDate getDateLastAccessed() {
        return dateLastAccessed;
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
