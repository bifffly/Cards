package leitner;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.StringJoiner;

public class Card implements Serializable {
    private String front;
    private String back;
    private LocalDate dateLastAccessed;

    public Card() {
        this(null, null);
    }

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
        this.dateLastAccessed = LocalDate.now();
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
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
        return front + ", " + back;
    }
}
