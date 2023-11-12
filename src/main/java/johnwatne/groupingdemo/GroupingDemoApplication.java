package johnwatne.groupingdemo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import johnwatne.groupingdemo.model.CountyParcelGrouping;
import johnwatne.groupingdemo.model.Parcel;
import johnwatne.groupingdemo.model.Submission;
import johnwatne.groupingdemo.model.UniqueTaxingArea;

/**
 * Test using Stream operations to count totals by grouping and look for
 * duplicates.
 */
public class GroupingDemoApplication {
	public static final void main(final String[] args) {
		final Submission testSubmission = new Submission(BigDecimal.ONE);
		final UniqueTaxingArea uta1 = new UniqueTaxingArea(testSubmission, "27", "Minneapolis");
		final Parcel parcel11 = new Parcel(uta1, "1");
		final Parcel parcel12 = new Parcel(uta1, "2");
		uta1.setParcels(Arrays.asList(parcel11, parcel12));
		final UniqueTaxingArea uta2 = new UniqueTaxingArea(testSubmission, "27", "Richfield");
		final Parcel parcel21 = new Parcel(uta2, "1");
		final Parcel parcel23 = new Parcel(uta2, "3");
		uta2.setParcels(Arrays.asList(parcel21, parcel23));
		testSubmission.setUtas(Arrays.asList(uta1, uta2));

		final List<Entry<CountyParcelGrouping, Long>> duplicateParcels = testSubmission.getUtas().stream()
				.flatMap(u -> u.getParcels().stream())
				.map(p -> new CountyParcelGrouping(p.getParentUta().getCountyCode(), p.getParcelId()))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.filter(x -> x.getValue() > 1).toList();

		if (duplicateParcels.size() > 0) {
			System.out.println("Duplicate parcels found!");
			duplicateParcels.forEach(p -> {
				System.out.println("parcel " + p.getKey() + " has duplicates");
			});
		}
	}
}
