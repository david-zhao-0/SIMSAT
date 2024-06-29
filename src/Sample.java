import java.util.*;

/**
 * The Sample class which stores information about individual samples run in the mass spectrometer. For the purposes of
 * our study, four experimental groups are defined, LF/LB/HF/HB, where LF represents a low fat diet, HF represents a
 * high fat diet, and LB/HB represent the respective diets with 1.5X BCAA supplementation for 12 weeks.
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 * 
 * @param name name of the sample 
 * @param sampleType for our experiments we distinguished between LF/LB/HF/HB
 * @param metabolites the metabolites to consider for this sample
 * 
 */

public class Sample implements Comparable<Sample> {
	private String name;
	private String sampleType;
	private List<Metabolite> metabolites;
	
	public Sample(String name, List<Metabolite> metaboliteList) {
		this.name = name;
		this.metabolites = metaboliteList;
	}
	
	// Copy constructor
	public Sample(Sample sample) {
		this.name = sample.name;
		this.sampleType = sample.sampleType;
		this.metabolites = sample.metabolites;
	}
	
	// Name Getter
	public String getName() {
		return name;
	}
	
	// Name Setter
	public void setName(String newName) {
		this.name = newName;
	}
	
	// Sample type getter
	public String getSampleType() {
		return sampleType;
	}

	// Sample type setter
	public void setSampleType(String sampleName) {
		if (sampleName.contains("LF")) {
			this.sampleType = "LF";
		} else if (sampleName.contains("LB")) {
			this.sampleType = "LB";
		} else if (sampleName.contains("HF")) {
			this.sampleType = "HF";
		} else if (sampleName.contains("HB")) {
			this.sampleType = "HB";
		} else {
			this.sampleType = "N/A";
		}
	}
	
	public int getSortOrder() {
		switch (name.substring(0, 2)) {
		case "LF":
			return 0;
		case "LB":
			return 1;
		case "HF":
			return 2;
		case "HB":
			return 3;
		default:
			return -1;
		}
	}
	
	// metabolites getter
	public List<Metabolite> getMetabolites() {
		return this.metabolites;
	}
	
	// metabolites setter
	public void addMetabolite(Metabolite metabolite) {
		this.metabolites.add(metabolite);
	}
	
	public void setMetaboliteList(ArrayList<Metabolite> mList) {
		this.metabolites = (List<Metabolite>) mList.clone();		
	}

	@Override
	public int compareTo(Sample o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
	}
}