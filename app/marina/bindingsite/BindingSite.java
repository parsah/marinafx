package marina.bindingsite;

/**
 * A BindingSite provides the ability to work with both DNA motifs or a PWM.
 * In doing so, both these objects can be classified using one data-structure
 * without the need of having a data-structure for each type of binding site.
 * @author Parsa Hosseini
 * */
public interface BindingSite {
	
	/**
	 * Each BindingSite differs as-to how it checks to verify if it is
	 * of sound composition or not.
	 * @return whether the BindingSite object is valid.
	 * */
	public boolean sanityCheck();

}
