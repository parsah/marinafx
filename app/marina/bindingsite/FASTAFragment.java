package marina.bindingsite;

import marina.group.FASTASequence;

public class FASTAFragment extends FASTASequence {
	private FASTASequence parent; // FASTASequence object this comes from.
	private int location; // relative index on parent (zero-indexed).
	
	public FASTAFragment(FASTASequence parent, String header, 
			String sub, int location) {
		super(header, sub);
		this.setParent(parent);
		this.setLocation(location);
	}

	/**
	 * @return the parent
	 */
	public FASTASequence getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	private void setParent(FASTASequence parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return this.getSequence();
	}

	/**
	 * @return the location
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	private void setLocation(int location) {
		this.location = location;
	}

}
