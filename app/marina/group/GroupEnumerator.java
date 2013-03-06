package marina.group;

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
	
	public GroupEnumerator(Group[] groups) {
		this.setGroups(groups);
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
