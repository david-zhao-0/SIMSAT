import java.io.BufferedWriter;
import java.io.IOException;

/**
 * The SubHeader class which contains directions on how to write the isotope values to the output file.
 * 
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 */

public class SubHeader extends Header{

    public SubHeader(String[] delimitedLine) {
        for (int i = 1; i < delimitedLine.length; i = i + super.getColumnsPerMetabolite()) {
            super.getHeaderList().add(delimitedLine[i + super.getPeakNameColumns()[0]]);
            super.getHeaderList().add(delimitedLine[i + super.getPeakNameColumns()[1]] + Processor.DELIMITER);
        }
    }
    
    @Override
    public void writeHeader(BufferedWriter writer) {
        try {
            writer.write(Processor.DELIMITER);
            for (int i = 0; i < super.getHeaderList().size(); i++) {
                writer.write(super.getHeaderList().get(i) + Processor.DELIMITER);
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
