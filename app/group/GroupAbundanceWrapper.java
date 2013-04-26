package group;

import java.util.Map;

import bindingsite.BindingSite;


/**
 * A GroupAbundanceWrapper encapsulates various behaviors and states as-to
 * how many binding sites mapped to a specific group, and how many times a
 * specific binding site is found within a particular group. Having such
 * behaviors and states within a class enables the ability to efficiently
 * contrast abundance between a query and baseline group.
 * @author Parsa Hosseini
 * */
public class GroupAbundanceWrapper {
	private Map<BindingSite, Integer> maps;
	private int numMappings; // total number of TFBss mapping to group
	
	public GroupAbundanceWrapper(Map<BindingSite, Integer> maps) {
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
