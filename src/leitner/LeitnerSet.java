package leitner;

import deck.Card;

import java.util.*;
import static leitner.LeitnerEnum.*;

public class LeitnerSet {
    private List<LeitnerBox> leitnerSet;
    private String name;
    private static Map<String, Integer> map;

    static {
        map = new HashMap<>();
        int i = 0;
        for (LeitnerEnum leitnerEnum : LeitnerEnum.values()) {
            map.put(leitnerEnum.toString(), i);
            i++;
        }
    }

    public LeitnerSet() {
        leitnerSet = new ArrayList<>();
        for (LeitnerEnum value : LeitnerEnum.values()) {
            leitnerSet.add(new LeitnerBox(value));
        }
    }

    public LeitnerBox getLeitnerBox(LeitnerEnum leitnerEnum) {
        for (LeitnerBox box : leitnerSet) {
            if (box.getLeitnerEnum() == leitnerEnum) {
                return box;
            }
        }
        throw new NoSuchElementException();
    }

    private LeitnerEnum getPromoted(LeitnerEnum leitnerEnum) {
        switch (leitnerEnum) {
            case NOTINTRODUCED: return DAILY;
            case DAILY: return BIWEEKLY;
            case BIWEEKLY: return WEEKLY;
            case WEEKLY: return TWOWEEKS;
            case TWOWEEKS: return THREEWEEKS;
            case THREEWEEKS: return MONTHLY;
            case MONTHLY: return SIXWEEKS;
            case SIXWEEKS: return TWOWEEKS;
            case TWOMONTHS: return TWOMONTHS;
            default: return null;
        }
    }

    private LeitnerEnum getDemoted(LeitnerEnum leitnerEnum) {
        switch (leitnerEnum) {
            case NOTINTRODUCED: return NOTINTRODUCED;
            case DAILY: return DAILY;
            case BIWEEKLY: return DAILY;
            case WEEKLY: return BIWEEKLY;
            case TWOWEEKS: return WEEKLY;
            case THREEWEEKS: return TWOWEEKS;
            case MONTHLY: return THREEWEEKS;
            case SIXWEEKS: return MONTHLY;
            case TWOMONTHS: return SIXWEEKS;
            default: return null;
        }
    }

    private LeitnerBox getContainer(Card card) {
        for (LeitnerBox leitnerBox : leitnerSet) {
            if (leitnerBox.contains(card)) {
                return leitnerBox;
            }
        }
        return null;
    }

    private boolean contains(Card card) {
        return getContainer(card) != null;
    }

    public void promoteCard(Card card) {
        if (contains(card)) {
            LeitnerBox leitnerBox = getContainer(card);
            LeitnerEnum destinationEnum = getPromoted(leitnerBox.getLeitnerEnum());
            LeitnerBox destination = getLeitnerBox(destinationEnum);
            leitnerBox.remove(card);
            destination.add(card);
        }
    }

    public void demoteCard(Card card) {
        if (contains(card)) {
            LeitnerBox leitnerBox = getContainer(card);
            LeitnerEnum destinationEnum = getDemoted(leitnerBox.getLeitnerEnum());
            LeitnerBox destination = getLeitnerBox(destinationEnum);
            leitnerBox.remove(card);
            destination.add(card);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + ":\n");
        for (LeitnerBox leitnerBox : leitnerSet) {
            for (Card card : leitnerBox.getCards()) {
                sb.append(card.toString() + ":" + map.get(leitnerBox.getLeitnerEnum().toString()) + "\n");
            }
        }
        return sb.toString();
    }
}
