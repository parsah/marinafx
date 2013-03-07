package marina.group;

import java.util.Map;

import marina.bindingsite.BindingSite;

/**
 * A GroupAlignmentWrapper encapsulates various behaviors and states as-to
 * how many binding sites mapped to a specific group, and how many times a
 * specific binding site is found within a particular group. 
 * */
public class GroupMappingWrapper {
	private Map<BindingSite, Integer> maps;
	private int numMappings; // total number of TFBss mapping to group
	
	public GroupMappingWrapper(Map<BindingSite, Integer> maps) {
		this.setMaps(maps);
		this.computeNumMappings();
	}
	
	/**
	 * Compute the total number of binding sites which map to this specific
	 * group. This is necessary since this count will contribute to a 
	 * contingency matrix; used to determine magnitude of binding site
	 * over-representation
	 * */
	private void computeNumMappings() {
		int sum = 0;
		for (BindingSite tfbs: this.getMaps().keySet()) {
			sum += this.getMaps().get(tfbs);
		}
		this.setNumMappings(sum);
	}
	
	public static void toContingencyMatrix(BindingSite tfbs, 
			GroupMappingWrapper query, GroupMappingWrapper baseline) {
		// TODO given a binding site, get its counts in both the query and
		// baseline dataset.
//		System.out.println("building CM for " + tfbs);
	}

	/**
	 * @return the maps
	 */
	public Map<BindingSite, Integer> getMaps() {
		return maps;
	}

	/**
	 * @param maps the maps to set
	 */
	private void setMaps(Map<BindingSite, Integer> maps) {
		this.maps = maps;
	}

	/**
	 * @return the numMappings
	 */
	public int getNumMappings() {
		return numMappings;
	}

	/**
	 * @param numMappings the numMappings to set
	 */
	private void setNumMappings(int numMappings) {
		this.numMappings = numMappings;
	}

}
