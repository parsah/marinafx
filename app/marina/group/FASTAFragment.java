package marina.group;


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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(obj instanceof FASTAFragment) { // equality based on header
			FASTAFragment other = (FASTAFragment)obj;
			return (this.getHeader().equals(other.getHeader()) &&
					this.getSequence().equals(other.getSequence()));
		}
		return false;
	}

	@Override
	public int hashCode() { // FASTA header is set to be unique
		return this.getHeader().hashCode();
	}

}
