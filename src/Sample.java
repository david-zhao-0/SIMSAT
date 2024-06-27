import java.util.*;

public class Sample {
	private String name;
	private String sampleType;
	private int sortOrder;
	private List<Metabolite> metabolites;
	
	public Sample(String name) {
		this.name = name;
		this.metabolites = new ArrayList<Metabolite>();
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
}