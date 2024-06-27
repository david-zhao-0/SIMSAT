import java.util.HashMap;

/**
 * The Metabolite class which stores metabolite information and peak data. This class only supports the
 * comparison of two peaks for each metabolite.
 * 
 * @author David Zhao
 * @author https://github.com/david-zhao-0
 * 
 * @param name the name of the metabolite
 * @param peak1 lighter isotope m/z value
 * @param peak2 heavier isotope m/z
 * @param peak1Area lighter isotope peak area
 * @param peak2Area heavier isotope peak area
 * @param ratio peakArea1/peakArea2
 * 
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
