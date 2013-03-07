package marina.group;

import java.util.HashSet;
import java.util.Set;

import marina.bindingsite.BindingSite;
import marina.bindingsite.LinearDNAMotif;

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
	private Group[] groups;
	
	public GroupEnumerator(Group ... groups) {
		this.setGroups(groups);
	}
	
	/**
	 * Get a union as-to the binding-sites shared between both groups.
	 * @return set of BindingSite objects shared between both groups.
	 * */
	public Set<BindingSite> union() {
		// iterate over each group
		Set<BindingSite> union = new HashSet<BindingSite>();
		for (Group group: this.getGroups()) {
			// iterate over each sequence in the group
			for (FASTASequence seq: group.getParser().getSequences()) {
				union.addAll(seq.getMappings().keySet());
			}
			
			for (BindingSite tfbs: group.mappings().keySet()) {
				System.out.println(((LinearDNAMotif)tfbs).getGene() + "\t" + group.mappings().get(tfbs));
			}
			System.out.println();
			
		}
		return union;
	}

	/**
	 * @return the groups
	 */
	private Group[] getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	private void setGroups(Group[] groups) {
		this.groups = groups;
	}
}
