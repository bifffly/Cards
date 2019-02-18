package leitner;

import leitner.Card;
import leitner.LeitnerEnum;
import leitner.LeitnerSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LeitnerSetReader {
    private LeitnerSet leitnerSet;
    private File file;
    private int line;
    private Scanner scanner;
    private List<String> fieldNames;
    private LeitnerEnum[] enums = LeitnerEnum.values();

    class ReadException extends RuntimeException {
        ReadException(String message) { super(message); }
    }

    public LeitnerSetReader(File file) {
        String filePath = file.getPath();
        if (!filePath.endsWith(".deck")) {
            throw new ReadException("Invalid file extension.");
        }
        this.leitnerSet = new LeitnerSet();
        this.file = file;
        this.line = 0;
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.fieldNames = new ArrayList<>();
    }

    public void read() {
        while (scanner.hasNext()) {
            line++;
            scanCard();
        }
    }

    private void scanCard() {
        String line = scanner.nextLine();
        String[] card = readSides(line);
        leitnerSet.getLeitnerBox(LeitnerEnum.NOTINTRODUCED).add(new Card(card[0], card[1]));
    }

    private String[] readSides(String string) {
        int i = 0;
        String[] strings = string.split(",\\s*");
        if (strings.length == 2) {
            return strings;
        }
        else {
            throw new ReadException("Malformed card on line " + line + ".");
        }
    }

    public LeitnerSet getLeitnerSet() {
        return leitnerSet;
    }
}
