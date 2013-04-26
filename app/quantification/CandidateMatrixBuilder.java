package quantification;

import group.FASTASequence;
import group.Group;
import group.GroupAbundanceWrapper;
import gui.MarinaGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bindingsite.BindingSite;

import matrix.ContingencyMatrix;

/**
 * Candidate over-represented binding-sites are produced given a query and
 * baseline group. These candidate binding-sites must pass several criterion
 * in-order to fully be rendered an over-represented binding sites. This
 * class produces such candidates; modeled as a contingency matrix, a 
 * data-structure frequently used to model multivariate abundances.
 * @author Parsa Hosseini
 * */
public class CandidateMatrixBuilder {
	private Group query;
	private Group baseline;
	
	public CandidateMatrixBuilder() {
		this.setBaseline(MarinaGUI.get().parameterMap().getBaseline());
		this.setQuery(MarinaGUI.get().parameterMap().getQuery());
	}
	
	public CandidateMatrixBuilder(Group query, Group baseline) {
		this.setBaseline(baseline);
		this.setQuery(query);
	}
	
	public Group[] groups() {
		return new Group[]{this.getQuery(), this.getBaseline()};
	}
	
	/**
	 * Get a union as-to the binding-sites shared between both groups.
	 * @return set of BindingSite objects shared between both groups.
	 * */
	public Set<BindingSite> union() {
		// iterate over each group
		Set<BindingSite> union = new HashSet<BindingSite>();
		for (Group group: this.groups()) {
			// iterate over each sequence in the group
			for (FASTASequence seq: group.getParser().getSequences()) {
				union.addAll(seq.getMappings().keySet());
			}
		}
		return union;
	}
	
	/**
	 * Counts across differing groups must be contrasted. A data-structure
	 * to facilitate such a construct is the Contingency Matrix. The core of
	 * this data-structure are two variables, X and not-X (!X). Abundance of
	 * these variables are represented in the matrix cell given categorical
	 * variables G and not-G (!G). Hence, a contingency matrix serves to
	 * quantify abundance of both X and !X across groups G and !G.
	 * @param tfbs BindingSite object to build contingency matrix around.
	 * @param g wrapper for the query group, G.
	 * @param notG wrapper for the baseline group, !G.
	 * @return ContingencyMatrix; potential over-represented binding site.
	 * */
	public ContingencyMatrix build(BindingSite tfbs, 
			GroupAbundanceWrapper g, GroupAbundanceWrapper notG) {
		Integer numX_G = g.getMaps().get(tfbs); // i.e. n(X, G)
		Integer numX_NotG = notG.getMaps().get(tfbs); // i.e. n(X, !G)
		if (numX_G == null) { // if TFBS not found in group, count is 0
			numX_G = 0;
		}
		if (numX_NotG== null) {
			numX_NotG = 0;
		}
		Integer numNotX_G = g.getNumMappings() - numX_G; // i.e n(!X, G)
		Integer numNotX_NotG = notG.getNumMappings() - numX_NotG; // i.e n(!X, !G)
		
		// next, build contingency matrix
		double data[][] = new double[][]{
				{numX_G, numX_NotG}, // n(X, G), n(X, !G), respectively.
				{numNotX_G, numNotX_NotG}}; // n(X, !G), n(!X, !G), respectively.
		ContingencyMatrix cm = new ContingencyMatrix(data);
		cm.setBindingSite(tfbs);
		cm.setName(tfbs.toString());
		return cm;
	}
	
	/**
	 * The crux of this algorithm is to contrast binding-site abundances
	 * given a query and baseline group. Binding-sites within these two groups
	 * must therefore get contrasted so that their respective counts can be
	 * statistically quantified.
	 * @return list of ContingencyMatrix objects.
	 * @throws IOException 
	 * */
	public List<ContingencyMatrix> build() throws IOException {
		List<ContingencyMatrix> matrices = new ArrayList<ContingencyMatrix>();
		GroupAbundanceWrapper wrapBase = this.getBaseline().mappingWrapper();
		GroupAbundanceWrapper wrapQuery = this.getQuery().mappingWrapper();
		for (BindingSite tfbs: this.union()) {
			matrices.add(this.build(tfbs, wrapQuery, wrapBase));
		}
		if (matrices.size() == 0) {
			throw new IOException("No binding-sites shared between groups.");
		}
		return matrices;
	}

	/**
	 * @return the query
	 */
	private Group getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	private void setQuery(Group query) {
		this.query = query;
	}

	/**
	 * @return the baseline
	 */
	private Group getBaseline() {
		return baseline;
	}

	/**
	 * @param baseline the baseline to set
	 */
	private void setBaseline(Group baseline) {
		this.baseline = baseline;
	}
}
