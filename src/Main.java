import leitner.LeitnerSet;
import read.LeitnerSetReader;
import write.LeitnerSetWriter;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        LeitnerSetReader leitnerSetReader = new LeitnerSetReader(new File("src/Deck"));
        leitnerSetReader.read();
        LeitnerSet leitnerSet = leitnerSetReader.getLeitnerSet();

        LeitnerSetWriter leitnerSetWriter = new LeitnerSetWriter(new File("src/Deck2"), leitnerSet);
        leitnerSetWriter.write();

        leitnerSetReader = new LeitnerSetReader(new File("src/Deck2"));
        leitnerSetReader.read();
        System.out.println(leitnerSetReader.getLeitnerSet());
    }
}
