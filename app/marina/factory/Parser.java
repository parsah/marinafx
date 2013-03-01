package marina.factory;

public interface Parser {
	
	/**
	 * Be-it DNA motifs or TFBSs, these models must be uniquely parsed
	 * since each is represented slightly different from one another.
	 * 
	 * */
	public void parse();
	
	/**
	 * Some TFBSs do not pass minimum-length cutoffs. Those that do are kept
	 * however those which do not must be filtered-out.
	 * @param minLen Minimum length threshold.
	 * */
	public void filter(int minLen);

}
