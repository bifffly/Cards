package io;

import deck.Card;
import deck.Field;
import leitner.LeitnerEnum;
import leitner.LeitnerSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LeitnerSetReader {
    private LeitnerSet leitnerSet;
    private File file;
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
                leitnerSet.setName(titleLine.substring(0, titleLine.length() - 1));
            }
            else {
                throw new ReadException("Expect title string as first line.");
            }
            while (scanner.hasNext()) {
                scanCard();
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

    private void scanCard() {
        String line = scanner.nextLine();
        int enumInteger = Integer.parseInt(line.substring(line.length() - 1, line.length()));
        LeitnerEnum leitnerEnum = enums[enumInteger];
        line = line.substring(1, line.length() - 3);

        leitnerSet.getLeitnerBox(leitnerEnum).add(new Card(readFields(line)));
    }

    private List<Field> readFields(String string) {
        List<Field> fields = new ArrayList<>();
        for (String field : string.split(",")) {
            field = field.replaceAll("\\s+", "");
            field = field.substring(1, field.length() - 1);
            String[] arr = field.split(":");
            if (arr.length != 2) {
                throw new ReadException("Malformed card.");
            }
            fields.add(new Field(arr[0], arr[1]));
        }
        return fields;
    }

    public LeitnerSet getLeitnerSet() {
        return leitnerSet;
    }
}
