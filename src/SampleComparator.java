import java.util.*;

public class SampleComparator implements Comparator<Sample> {

	@Override
	public int compare(Sample s1, Sample s2) {
		return Integer.compare(s1.getSortOrder(), s2.getSortOrder());
	}

}
