import deck.Card;
import leitner.LeitnerEnum;
import leitner.LeitnerSet;
import io.LeitnerSetReader;
import io.LeitnerSetWriter;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        LeitnerSetReader leitnerSetReader = new LeitnerSetReader(new File("src/Deck"));
        leitnerSetReader.read();
        LeitnerSet leitnerSet = leitnerSetReader.getLeitnerSet();

        LeitnerSetWriter leitnerSetWriter = new LeitnerSetWriter(new File("src/Deck2"), leitnerSet);

        Card card = leitnerSet.getLeitnerBox(LeitnerEnum.NOTINTRODUCED).getCards().get(0);
        leitnerSet.promoteCard(card);

        Card card1 = leitnerSet.getLeitnerBox(LeitnerEnum.NOTINTRODUCED).getCards().get(0);
        leitnerSet.demoteCard(card1);

        Card card2 = leitnerSet.getLeitnerBox(LeitnerEnum.TWOMONTHS).getCards().get(0);
        leitnerSet.promoteCard(card2);

        Card card3 = leitnerSet.getLeitnerBox(LeitnerEnum.SIXWEEKS).getCards().get(0);
        leitnerSet.demoteCard(card3);
        leitnerSetWriter.write();
    }
}
