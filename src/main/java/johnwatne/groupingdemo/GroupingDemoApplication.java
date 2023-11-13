package johnwatne.groupingdemo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		final Submission testSubmission = getTestSubmissionWithADuplicateParcel();

		final List<Entry<CountyParcelGrouping, Long>> duplicateParcels = testSubmission.getUtas().parallelStream()
				.flatMap(u -> u.getParcels().parallelStream())
				.map(p -> new CountyParcelGrouping(p.getParentUta().getCountyCode(), p.getParcelId()))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().parallelStream()
				.filter(x -> x.getValue() > 1).toList();

		printDuplicates(duplicateParcels);

		System.out.println(
				"*** Second pass: find duplicates without assuming elements can reference their parent elements ***");

		final List<Entry<CountyParcelGrouping, Long>> duplicates2 = testSubmission.getUtas().stream()
				.flatMap(u -> u.getParcels().parallelStream()
						.map(p -> new CountyParcelGrouping(u.getCountyCode(), p.getParcelId())))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().parallelStream()
				.filter(x -> x.getValue() > 1).toList();

		printDuplicates(duplicates2);

		System.out.println("*** Third pass: create variables for intermediate steps and comment ***");
		findDuplicatesDocumentingSteps(testSubmission);
	}

	private static void findDuplicatesDocumentingSteps(final Submission testSubmission) {
		final List<UniqueTaxingArea> utas = testSubmission.getUtas(); // All UTAs in submission.
		final Stream<UniqueTaxingArea> stream = utas.stream(); // Stream of all UTAs.

		// Use flatMap to convert from Stream of UniqueTaxingArea to Stream of
		// CountyParcelGrouping.
		// Create a Stream of all Parcels for the UTA currently being processed fron
		// Stream.
		// For each Parcel, create a new CountyParcelGrouping using the parent UTA's
		// county code and its own parcelId, and add it to the new Stream of these
		// grouping objects.
		final Stream<CountyParcelGrouping> flatMap = stream.flatMap(u -> u.getParcels().parallelStream()
				.map(p -> new CountyParcelGrouping(u.getCountyCode(), p.getParcelId())));

		// From the Stream of CountyParcelGroupings, create a Map of
		// CountyParcelGroupings to the number of times that grouping occurs in the
		// Stream.
		final Map<CountyParcelGrouping, Long> countyParcelCount = flatMap
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		// EntrySet for countyParcelCount.
		final Set<Entry<CountyParcelGrouping, Long>> entrySet = countyParcelCount.entrySet();

		// Convert entrySet to Stream of items to be filtered.
		final Stream<Entry<CountyParcelGrouping, Long>> streamToFilter = entrySet.parallelStream();

		// Filter on those items with value (count) > 1, returning Stream of
		// CountyParcelGroupings with duplicates.
		final Stream<Entry<CountyParcelGrouping, Long>> duplicateStream = streamToFilter.filter(x -> x.getValue() > 1);

		// Convert to List. The List<Entry<>> allows tracking both the parcel
		// identification and the number of occurrences, if desired. If not, another
		// flatMap() method could be used to convert to a simple List of
		// CountyParcelGroupings, or just parcelIds.
		final List<Entry<CountyParcelGrouping, Long>> duplicatesList = duplicateStream.toList();
		printDuplicates(duplicatesList);
	}

	private static Submission getTestSubmissionWithADuplicateParcel() {
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
		return testSubmission;
	}

	private static void printDuplicates(final List<Entry<CountyParcelGrouping, Long>> duplicateParcels) {
		if (duplicateParcels.size() > 0) {
			System.out.println("Duplicate parcels found!");
			duplicateParcels.forEach(p -> {
				System.out.println("parcel " + p.getKey() + " has duplicates");
			});
		}
	}
}
