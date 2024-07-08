/**
 * Custom exception thrown when input string for sample name contains illegal formatting
 * 
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 */

public class InvalidSampleName extends Exception {
    private String message = "Input sample does not match pattern: ";
    private String sampleName;

    public InvalidSampleName(String message) {
        super(message);
    }

    public InvalidSampleName(String message, Throwable err) {
        super(message, err);
    }

    public String getMessage() {
        return message + sampleName;
    }

}
