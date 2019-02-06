package read;

import deck.Card;
import deck.Deck;
import deck.Field;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DeckReader {
    private Deck deck;
    private File file;
    private Scanner scanner;
    private List<String> fieldNames;

    class ReadException extends RuntimeException {
        ReadException(String message) { super(message); }
    }

    public DeckReader(File file) {
        this.deck = new Deck();
        this.file = file;
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
        if (scanner.hasNext()) {
            String titleLine = scanner.nextLine();
            if (titleLine.endsWith(":")) {
                deck.setName(titleLine.substring(0, titleLine.length() - 1));
            }
            else {
                throw new ReadException("Expect title string as first line.");
            }
            if (scanner.hasNext()) {
                fieldNames = getFieldNames();
            }
            while (scanner.hasNext()) {
                deck.addCard(scanCard());
            }
        }
        else {
            throw new ReadException("Expect title string as first line.");
        }
    }

    private List<String> getFieldNames() {
        String line = scanner.nextLine();
        String[] fieldArr = line.split(",");
        return Arrays.asList(fieldArr);
    }

    private Card scanCard() {
        String line = scanner.nextLine();
        String[] fieldArr = line.split(",");
        if (fieldArr.length != fieldNames.size()) {
            throw new ReadException("Mismatching field lengths.");
        }
        List<String> fieldList = Arrays.asList(fieldArr);
        Iterator<String> fieldIterator = fieldNames.iterator();
        Iterator<String> cardIterator = fieldList.iterator();
        List<Field> fields = new ArrayList<>();
        while (fieldIterator.hasNext() && cardIterator.hasNext()) {
            fields.add(new Field(fieldIterator.next(), cardIterator.next()));
        }
        return new Card(fields);
    }

    public Deck getDeck() {
        return deck;
    }
}
