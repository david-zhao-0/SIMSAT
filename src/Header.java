import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Header class used to collect the names of the metabolites as the main
 * header and the isotope identifiers as the subheaders.
 * 
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 * 
 * @param headerList contains the values to be written as the header
 */

public abstract class Header {
    public static final int COLUMNS_PER_METABOLITE = 6;
    public static final int[] PEAK_NAME_COLUMNS = new int[] {2, 4};
    private List<String> headerList = new ArrayList<>();

    public Header() {
        super();
    }

    public abstract void writeHeader(BufferedWriter writer);

    public List<String> getHeaderList() {
        return headerList;
    }

    public int getColumnsPerMetabolite() {
        return COLUMNS_PER_METABOLITE;
    }

    public int[] getPeakNameColumns() {
        return PEAK_NAME_COLUMNS;
    }

}
