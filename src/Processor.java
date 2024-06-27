import java.io.*;
import java.util.*;

public class Processor {
	private String filePath;
	private static final int COLUMNS_PER_METABOLITE = 6;
	private static List<Sample> samples = new ArrayList<>();

	public static void main(String[] args) {
		try {
			preProcess();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void preProcess() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("processed_data.csv"));

		String[] firstLine = reader.readLine().split(",");
		List<String> mainHeaders = getHeaders(firstLine, 0);
		List<String> subHeaders = getHeaders(firstLine, 1);
		Sample firstSample = new Sample(firstLine[0], getMetaboliteDataForSample(firstLine));

		// Initiates BufferedWriter class, then dumps all items in mainHeaders ArrayList
		// to processed data file, with one empty space
		// between each metabolite (",,,"). Can adjust to have multiple empty spaces,
		// depending on output requirement

		

		// write main headers to file
		writeFirstSample(writer, mainHeaders, subHeaders, firstSample);

		// reads row after first row
		// processes all the rest of the data
		String line = reader.readLine();
		while (line != null) {
			String[] nextLineDelim = line.split(",");
			Sample currentSample = new Sample(nextLineDelim[0], getMetaboliteDataForSample(nextLineDelim));
			writer.write(currentSample.getName() + ",");
			for (int i = 1; i < nextLineDelim.length - 3; i = i + COLUMNS_PER_METABOLITE) {
				writer.write(nextLineDelim[i + 3] + ",");
				writer.write(nextLineDelim[i + 5] + ",,");
			}
			writer.newLine();
			line = reader.readLine();
		}
		reader.close();
		writer.close();

	}

	private static void writeFirstSample(BufferedWriter writer, List<String> mainHeaders, List<String> subHeaders,
			Sample firstSample) throws IOException {
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
		writer.write(firstSample.getName() + ",");

		for (int i = 0; i < firstSample.getMetabolites().size(); i++) {
			double[] peakData = firstSample.getMetabolites().get(i).getPeakData();
			writer.write(peakData[0] + ",");
			writer.write(peakData[1] + ",,");
		}
		writer.newLine();
	}

	public static ArrayList<Sample> sortSamples() {
		return null; // TODO
	}

	public static ArrayList<Double> getAverage(ArrayList<Sample> samples) {
		double sum = 0;
		ArrayList<Double> averageRatios = new ArrayList<Double>();
		int numberOfMetabolites = samples.get(0).getMetabolites().size();
		for (int i = 0; i < numberOfMetabolites; i++) {
			for (int j = 0; j < samples.size(); j++) {
				Sample s = samples.get(j);
				Metabolite m = s.getMetabolites().get(i);
				sum += m.getRatio();
			}
			averageRatios.add(sum / samples.size());
		}
		return averageRatios;
	}

	public static void writeSamplesToFile(ArrayList<Sample> samples) {
		// TODO
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
