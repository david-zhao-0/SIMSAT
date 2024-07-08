import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Contains directions on how to write the names of the metabolites to the
 * output file.
 * 
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 */

public class MainHeader extends Header {

    public MainHeader(String[] delimitedLine) {
        for (int i = 1; i < delimitedLine.length; i = i + super.getColumnsPerMetabolite()) {
            super.getHeaderList().add(delimitedLine[i]);
        }
    }

    @Override
    public void writeHeader(BufferedWriter writer) {
        try {
            writer.write(Processor.DELIMITER);
            for (int i = 0; i < super.getHeaderList().size(); i++) {
                writer.write(super.getHeaderList().get(i) + Processor.DELIMITER + Processor.DELIMITER + Processor.DELIMITER);
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
