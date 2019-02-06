package leitner;

import deck.Card;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class LeitnerSet {
    private Set<LeitnerBox> leitnerSet;

    public LeitnerSet() {
        leitnerSet = new HashSet<>();
        leitnerSet.add(new LeitnerBox(null));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.DAILY));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.BIWEEKLY));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.WEEKLY));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.TWOWEEKS));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.THREEWEEKS));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.MONTHLY));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.SIXWEEKS));
        leitnerSet.add(new LeitnerBox(LeitnerEnum.TWOMONTHS));
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
            case DAILY: return LeitnerEnum.BIWEEKLY;
            case BIWEEKLY: return LeitnerEnum.WEEKLY;
            case WEEKLY: return LeitnerEnum.TWOWEEKS;
            case TWOWEEKS: return LeitnerEnum.THREEWEEKS;
            case THREEWEEKS: return LeitnerEnum.MONTHLY;
            case MONTHLY: return LeitnerEnum.SIXWEEKS;
            case SIXWEEKS: return LeitnerEnum.TWOWEEKS;
            case TWOMONTHS: return LeitnerEnum.TWOMONTHS;
            default: return null;
        }
    }

    private LeitnerEnum getDemoted(LeitnerEnum leitnerEnum) {
        switch (leitnerEnum) {
            case DAILY: return LeitnerEnum.DAILY;
            case BIWEEKLY: return LeitnerEnum.DAILY;
            case WEEKLY: return LeitnerEnum.BIWEEKLY;
            case TWOWEEKS: return LeitnerEnum.WEEKLY;
            case THREEWEEKS: return LeitnerEnum.TWOWEEKS;
            case MONTHLY: return LeitnerEnum.THREEWEEKS;
            case SIXWEEKS: return LeitnerEnum.MONTHLY;
            case TWOMONTHS: return LeitnerEnum.SIXWEEKS;
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
        return getContainer(card) == null;
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
}
