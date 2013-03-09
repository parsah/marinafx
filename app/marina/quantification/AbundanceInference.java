package marina.quantification;


public class AbundanceInference {
	private GroupPairContraster contraster;
	
	public AbundanceInference(GroupPairContraster contraster) {
		this.setContraster(contraster);
	}

	/**
	 * @return the contraster
	 */
	private GroupPairContraster getContraster() {
		return contraster;
	}

	/**
	 * @param contraster the contraster to set
	 */
	private void setContraster(GroupPairContraster contraster) {
		this.contraster = contraster;
	}

}
