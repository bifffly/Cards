package io;

import leitner.LeitnerSet;

import java.io.*;

public class LeitnerSetWriter {
    private File file;
    private LeitnerSet leitnerSet;

    public LeitnerSetWriter(File file, LeitnerSet leitnerSet) {
        this.file = file;
        this.leitnerSet = leitnerSet;
    }

    public void write() {
        try {
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(leitnerSet.toString());
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
