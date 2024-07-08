/**
 * The Metabolite class which stores metabolite information and peak data. This class only supports the
 * comparison of two isotope peaks for each metabolite.
 * 
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 * 
 * @param name the name of the metabolite
 * @param peakData contains peak areas for lighter and heavier isotopes
 * @param ratio ratio of the lighter isotope to the heavier isotope
 */

public class Metabolite {
	private static final int PEAKS_PER_METABOLITE = 2;
	private String name;
	private double[] peakData = new double[PEAKS_PER_METABOLITE]; 
	private double ratio;
	
	public Metabolite(String name) {
		this.name = name;
	}

	public Metabolite(Metabolite metabolite) {
		this.name = metabolite.name;
		this.peakData = metabolite.peakData;
		this.ratio = metabolite.ratio;
	}

	public String getName() {
		return name;
	}

	public double[] getPeakData() {
		return peakData;
	}
	
	public double getRatio() {
		return (peakData[0]/peakData[1] != Double.POSITIVE_INFINITY) ? peakData[0] / peakData[1] : 0;
	}
}
