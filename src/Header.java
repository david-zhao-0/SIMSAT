import java.util.ArrayList;
import java.util.List;

public class Header {
    public static final int COLUMNS_PER_METABOLITE = 6;
    private List<String> headerList = new ArrayList<>();
    private int headerType = -1;

    public Header(String[] delimitedLine, int headerType) {
        if (headerType == 0) { // main header
            this.headerType = headerType;
            for (int i = 1; i < delimitedLine.length; i = i + COLUMNS_PER_METABOLITE) {
                headerList.add(delimitedLine[i]);
            }
        } else if (headerType == 1) { // sub header
            this.headerType = headerType;
            for (int i = 1; i < delimitedLine.length; i = i + COLUMNS_PER_METABOLITE) {
                headerList.add(delimitedLine[i + 2]);
                headerList.add(delimitedLine[i + 4] + Processor.DELIMITER);
            }
        }
    }

    public List<String> getHeaderList() {
        return headerList;
    }

    public int getHeaderType() {
        return headerType;
    }
}
