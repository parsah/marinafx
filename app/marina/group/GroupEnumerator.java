package marina.group;

import java.util.HashSet;
import java.util.Set;

import marina.bindingsite.BindingSite;

/**
 * Upon successful binding site mapping be-it from DNA motifs or PWMs,
 * the abundance of each binding site per group is to be tabulated. In doing
 * so, these raw-counts can be used to identify produce magnitudes of 
 * over-representation. Without such tabulation, FASTA sequences simply 
 * reference a list of binding site mappings and this is not very useful as
 * it does not shed light on how abundant is may really be.
 * @author Parsa Hosseini
 * */
public class GroupEnumerator {
	private Group query;
	private Group baseline;
	
	public GroupEnumerator(Group query, Group baseline) {
		this.setBaseline(baseline);
		this.setQuery(query);
	}
	
	private Group[] groups() {
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
	 * Binding sites shared between the baseline and control groups are given.
	 * In doing so, one can then extract counts for each binding site
	 * and place such counts in a contingency matrix. Since a binding-site
	 * union is produced, a binding site may not always be found in the
	 * contrasting-group. In such instances, its abundance will be zero.
	 * */
	public void enumerateAlignments() {
		GroupMappingWrapper wrapBase = this.getBaseline().mappingWrapper();
		GroupMappingWrapper wrapQuery = this.getQuery().mappingWrapper();
		for (BindingSite tfbs: this.union()) {
			GroupMappingWrapper.toContingencyMatrix(tfbs, wrapQuery, wrapBase);
		}
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
