import java.io.*;
import java.util.*;

/**
 * The Processor class which does the data processing by collecting a list of
 * samples from a csv
 * file.
 * 
 * @param inputFilePath        the path to the file to be read
 * @param metaboliteCount the amount of metabolites to be read for each sample
 * @param samples
 */

public class Processor {
	private String inputFilePath;
	public static final String DELIMITER = ",";
	private static Set<Sample> samples = new TreeSet<>();

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

		String[] delimitedFirstLine = reader.readLine().split(DELIMITER);
		Header metaboliteHeaders = new Header(delimitedFirstLine, 0);
		Header isotopeHeaders = new Header(delimitedFirstLine, 1);
		Sample firstSample = new Sample(delimitedFirstLine[0], extractMetaboliteDataFromDelimitedString(delimitedFirstLine));
		try {
			firstSample.getSampleInformationFromSampleName();
			samples.add(firstSample);
		} catch (InvalidSampleName ex) {
			System.out.println(ex.getMessage());
		}

		// write main and sub headers to file
		writeHeader(writer, metaboliteHeaders);
		writeHeader(writer, isotopeHeaders);

		// reads row after first row
		String line = reader.readLine();
		while (line != null) {
			String[] delimitedLine = line.split(DELIMITER);
			Sample currentSample = new Sample(delimitedLine[0], extractMetaboliteDataFromDelimitedString(delimitedLine));
			try {
				currentSample.getSampleInformationFromSampleName();
			} catch (InvalidSampleName ex) {
				System.out.println(ex.getMessage());
			}
			if (samples.add(currentSample)) {
				line = reader.readLine();
			} else {
				System.out.println("Duplicate sample: " + currentSample.getName());
				line = reader.readLine();
			}
		}

		reader.close();

		for (Sample s : samples) {
			writeSample(writer, s);
		}

		writer.close();
	}

	private static void writeHeader(BufferedWriter writer, Header header) throws IOException {
		writer.write(DELIMITER);
		if (header.getHeaderType() == 0) { // writes main headers
			for (int i = 0; i < header.getHeaderList().size(); i++) {
				writer.write(header.getHeaderList().get(i) + DELIMITER + DELIMITER + DELIMITER);
			}
			
		} else if (header.getHeaderType() == 1) { // write subheaders
			for (int i = 0; i < header.getHeaderList().size(); i++) {
				writer.write(header.getHeaderList().get(i) + DELIMITER);
			}
		}
		writer.newLine();
	}

	private static void writeSample(BufferedWriter writer, Sample sample) throws IOException {
		writer.newLine();
		writer.write(sample.getName() + DELIMITER);
		for (int i = 0; i < sample.getMetabolites().size(); i++) {
			double peakData[] = sample.getMetabolites().get(i).getPeakData();
			writer.write(peakData[0] + DELIMITER);
			writer.write(peakData[1] + DELIMITER + DELIMITER);
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
	 * @param delimitedLine the line read by the bufferedreader
	 * @return the list of metabolites in the line of data.
	 */
	private static List<Metabolite> extractMetaboliteDataFromDelimitedString(String[] delimitedLine) {
		List<Metabolite> metaboliteData = new ArrayList<>();
		for (int i = 1; i < delimitedLine.length; i = i + Header.COLUMNS_PER_METABOLITE) {
			Metabolite currentMetabolite = new Metabolite(delimitedLine[i]);
			currentMetabolite.getPeakData()[0] = Double.parseDouble(delimitedLine[i + 3]);
			currentMetabolite.getPeakData()[1] = Double.parseDouble(delimitedLine[i + 5]);
			metaboliteData.add(currentMetabolite);
		}
		return metaboliteData;
	}

}
