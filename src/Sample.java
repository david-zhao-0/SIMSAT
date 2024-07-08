import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Sample class which stores information about individual samples run in the
 * mass spectrometer. For the purposes of
 * our study, four experimental groups are defined, LF/LB/HF/HB, where LF
 * represents a low fat diet, HF represents a
 * high fat diet, and LB/HB represent the respective diets with 1.5X BCAA
 * supplementation for 12 weeks.
 * 
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 * 
 * @param name        name of the sample
 * @param sampleType  for our experiments we distinguished between LF/LB/HF/HB
 * @param metabolites the metabolites to consider for this sample
 * 
 */

public class Sample implements Comparable<Sample> {
	private String name;
	private int sampleType = -1, sampleNumber = -1, timePoint = -1;
	private List<Metabolite> metabolites;

	public Sample(String name, List<Metabolite> metaboliteList) {
		this.name = name;
		this.metabolites = metaboliteList;
	}

	public String getName() {
		return name;
	}

	public int getSampleType() {
		return sampleType;
	}

	public int getSampleNumber() {
		return sampleNumber;
	}

	public int getTimePoint() {
		return timePoint;
	}

	public List<Metabolite> getMetabolites() {
		return this.metabolites;
	}

	public void setSampleType(int sampleType) {
		this.sampleType = sampleType;
	}

	public void setSampleNumber(int sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	public void setTimePoint(int timePoint) {
		this.timePoint = timePoint;
	}

	@Override
	public int compareTo(Sample s) {
		int result = this.sampleType - s.getSampleType();
		if (result == 0) {
			result = this.sampleNumber - s.getSampleNumber();
			if (result == 0) {
				result = this.timePoint - s.getTimePoint();
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Sample) {
			return ((Sample) o).getSampleType() == this.getSampleType()
					&& ((Sample) o).getSampleNumber() == this.getSampleNumber()
					&& ((Sample) o).getTimePoint() == this.getTimePoint();
		} else {
			return false;
		}
	}

	/**
	 * This method is used to extract sample information from the name of the
	 * sample. As this implementation is highly specific to my problem, this should
	 * be rewritten for other applications, or the Sample class should be extracted
	 * to an interface.
	 * 
	 * @param sampleName the string to be processed to get data for fields in Sample
	 */

	public void getSampleInformationFromSampleName() throws InvalidSampleName {
		if (this.getTimePoint() < 0 || this.getSampleType() < 0 || this.getSampleNumber() < 0) {
			Pattern pattern = Pattern.compile("([L|H][F|B])(\\d+)[_](\\d+)");
			Matcher matcher = pattern.matcher(this.name);
			if (matcher.find()) {
				switch (matcher.group(1)) {
					case "LF":
						this.setSampleType(0);
						break;
					case "LB":
						this.setSampleType(1);
						break;
					case "HF":
						this.setSampleType(2);
						break;
					case "HB":
						this.setSampleType(3);
						break;
				}
				this.setSampleNumber(Integer.parseInt(matcher.group(2)));
				this.setTimePoint(Integer.parseInt(matcher.group(3)));
			} else {
				throw new InvalidSampleName(this.getName());
			}
		} else {
			return;
		}
	}

}