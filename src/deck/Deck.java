package deck;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;
    private int size;
    private List<Card> cards;

    public Deck() {
        this.name = null;
        this.size = 0;
        this.cards = new ArrayList<>();
    }

    public Deck(List<Card> cards) {
        this.size = cards.size();
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCardAt(int index) {
        return cards.get(index);
    }

    public void removeCardAt(int index) {
        cards.remove(index);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + "\n");
        for (Card card : cards) {
            sb.append(card.toString() + "\n");
        }
        return sb.toString();
    }
}
