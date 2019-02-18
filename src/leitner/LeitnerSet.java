package leitner;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import static leitner.LeitnerEnum.*;

public class LeitnerSet implements Serializable {
    private List<LeitnerBox> leitnerSet;
    private static Map<String, Integer> map;

    static {
        map = new HashMap<>();
        int i = 0;
        for (LeitnerEnum leitnerEnum : LeitnerEnum.values()) {
            map.put(leitnerEnum.toString(), i++);
        }
    }

    public LeitnerSet() {
        leitnerSet = new ArrayList<>();
        for (LeitnerEnum value : LeitnerEnum.values()) {
            leitnerSet.add(new LeitnerBox(value));
        }
    }

    private List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        for (LeitnerBox leitnerBox : leitnerSet) {
            cards.addAll(leitnerBox.getCards());
        }
        return cards;
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
        throw new NoSuchElementException();
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

    public void relegateCard(Card card) {
        if (contains(card)) {
            LeitnerBox leitnerBox = getContainer(card);
            LeitnerBox destination = getLeitnerBox(DAILY);
            leitnerBox.remove(card);
            destination.add(card);
        }
    }

    private LocalDate getDateToShow(Card card) {
        LeitnerEnum leitnerEnum = getContainer(card).getLeitnerEnum();
        long interval = leitnerEnum.getInterval();
        return card.getDateLastAccessed().plusDays(interval);
    }

    private boolean isTimeToShow(Card card) {
        return !LocalDate.now().isBefore(getDateToShow(card));
    }

    public Queue<Card> cardsToShow() {
        List<Card> cardsToIntroduce = new ArrayList<>();
        for (Card card : getLeitnerBox(NOTINTRODUCED).getCards()) {
            if (cardsToIntroduce.size() < 20) {
                cardsToIntroduce.add(card);
            }
        }
        List<Card> cards = new ArrayList<>();
        for (LeitnerBox leitnerBox : leitnerSet) {
            if (leitnerBox.getLeitnerEnum() != NOTINTRODUCED) {
                for (Card card : leitnerBox.getCards()) {
                    if (isTimeToShow(card)) {
                        cards.add(card);
                    }
                }
            }
        }
        cards.addAll(cardsToIntroduce);
        return new ArrayDeque<>(cards);
    }

    public CategoryEnum categorizeCard(Card card) {
        if (contains(card)) {
            LeitnerEnum leitnerEnum = getContainer(card).getLeitnerEnum();
            switch (leitnerEnum) {
                case NOTINTRODUCED: return CategoryEnum.UNSEEN;
                case DAILY:
                case BIWEEKLY:
                case WEEKLY:
                case TWOWEEKS:
                case THREEWEEKS: return CategoryEnum.YOUNG;
                default: return CategoryEnum.MATURE;
            }
        }
        throw new NoSuchElementException();
    }

    public int getCountOfCardsInCategory(CategoryEnum categoryEnum) {
        int count = 0;
        for (Card card : getAllCards()) {
            count += (categoryEnum == categorizeCard(card) ? 1 : 0);
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LeitnerBox leitnerBox : leitnerSet) {
            for (Card card : leitnerBox.getCards()) {
                sb.append(card.toString() + '\n');
            }
        }
        return sb.toString();
    }
}
