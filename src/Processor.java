import java.io.*;
import java.util.*;

/**
 * The Processor class which does the data processing by collecting a list of samples from a csv
 * file.
 * @param filePath the path to the file to be read
 * @param metaboliteCount the amount of metabolites to be read for each sample
 * @param samples 
 */
public class Processor {
	private String filePath;
	private static final int COLUMNS_PER_METABOLITE = 6;
	private static int metaboliteCount;
	private static List<Sample> samples = new ArrayList<>();

	public static void main(String[] args) {
		try {
			process();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void process() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("processed_data.csv"));

		String[] firstLine = reader.readLine().split(",");
		List<String> mainHeaders = getHeaders(firstLine, 0);
		List<String> subHeaders = getHeaders(firstLine, 1);
		Sample firstSample = new Sample(firstLine[0], getMetaboliteDataForSample(firstLine));
		samples.add(firstSample);

		// write main and sub headers to file
		writeHeaders(writer, mainHeaders, subHeaders);

		// reads row after first row
		String line = reader.readLine();
		while (line != null) {
			String[] delimitedLine = line.split(",");
			Sample currentSample = new Sample(delimitedLine[0], getMetaboliteDataForSample(delimitedLine));
			samples.add(currentSample);
			line = reader.readLine();
		}
		reader.close();

		for (Sample s : samples) {
			writeSample(writer, s);
		}

		writer.close();
	}

	private static void writeHeaders(BufferedWriter writer, List<String> mainHeaders, List<String> subHeaders) throws IOException {
		writer.write(",");
		for (int i = 0; i < mainHeaders.size(); i++) {
			writer.write(mainHeaders.get(i) + ",,,");
		}

		writer.newLine();
		writer.write(",");

		// writes subheaders into processed data file
		for (int i = 0; i < subHeaders.size(); i++) {
			writer.write(subHeaders.get(i) + ",");
		}
		writer.newLine();
	}

	private static void writeSample(BufferedWriter writer, Sample sample) throws IOException {
		writer.newLine();
		writer.write(sample.getName() + ",");
		for (int i = 0; i < metaboliteCount; i++) {
			double peakData[] = sample.getMetabolites().get(i).getPeakData();
			writer.write(peakData[0] + ",");
			writer.write(peakData[1] + ",,");
		}
	}

	public static ArrayList<Double> getMetaboliteRatioAverages(ArrayList<Sample> samples) {
		double sum = 0;
		ArrayList<Double> averageRatios = new ArrayList<Double>();

		for (Sample s : samples) {
			for (Metabolite m : s.getMetabolites()) {
				sum += m.getRatio();
			}
			averageRatios.add(sum / samples.size());
		}

		return averageRatios;
	}

	/**
	 * @param line contains information for one sample delimited by commas
	 * @param headerType 0 for mainHeader, 1 for subHeaders
	 * @return a list of headers to use for all samples
	 */
	private static List<String> getHeaders(String[] delimitedLine, int headerType) {
		List<String> headers = new ArrayList<>();
		if (headerType == 0) { // main header case - metabolite names
			for (int i = 1; i < delimitedLine.length; i = i + COLUMNS_PER_METABOLITE) {
				headers.add(delimitedLine[i]);
			}
			metaboliteCount = headers.size();
		} else if (headerType == 1) { // subheader case - isotope numbers
			for (int i = 1; i < delimitedLine.length; i = i + COLUMNS_PER_METABOLITE) {
				headers.add(delimitedLine[i + 2]);
				headers.add(delimitedLine[i + 4] + ",");
			}
		} else {
			return null; // TODO
		}
		return headers;
	}

	/**
	 * @param delimitedLine the line read by the bufferedreader
	 * @return the list of metabolites in the line of data.
	 */
	private static List<Metabolite> getMetaboliteDataForSample(String[] delimitedLine) {
		List<Metabolite> metaboliteList = new ArrayList<>();
		for (int i = 1; i < delimitedLine.length; i = i + COLUMNS_PER_METABOLITE) {
			Metabolite currentMetabolite = new Metabolite(delimitedLine[i]);
			currentMetabolite.getPeakData()[0] = Double.parseDouble(delimitedLine[i + 3]);
			currentMetabolite.getPeakData()[1] = Double.parseDouble(delimitedLine[i + 5]);
			metaboliteList.add(currentMetabolite);
		}
		return metaboliteList;
	}

}
