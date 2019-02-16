import io.LeitnerSetReader;
import leitner.LeitnerSet;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        LeitnerSetReader lsr = new LeitnerSetReader(new File("src/Test.deck"));
        lsr.read();
        LeitnerSet ls = lsr.getLeitnerSet();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/Test.dobj"));
            oos.writeObject(ls);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/Test.dobj"));
            LeitnerSet ls1 = (LeitnerSet)ois.readObject();
            System.out.println(ls1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
