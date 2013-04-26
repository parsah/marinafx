package bindingsite;

import group.DNASequence;

import java.io.IOException;


/**
 * A LinearDNAMotif is a linear string of DNA characters. This 
 * data-structure is one of two models for which a binding site can be
 * represented. Each object of this type references a transcription factor (TF)
 * family and a TF gene.
 * @author Parsa Hosseini
 * */
public class LinearDNAMotif extends DNASequence implements BindingSite {
	private String family;
	private String gene;

	/**
	 * Create a LinearDNAMotif object.
	 * @param family TF family object.
	 * @param gene TF gene name.
	 * @param sequence binding site sequence
	 * */
	public LinearDNAMotif(String ... attribs) {
		this.setFamily(attribs[0]);
		this.setGene(attribs[1]);
		this.setSequence(attribs[2]);
	}

	@Override
	public boolean sanityCheck() throws IOException{
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(obj instanceof LinearDNAMotif) { // equality based on gene name
			LinearDNAMotif other = (LinearDNAMotif)obj;
			return this.getGene().equals(other.getGene());
		}
		return false;
	}
	
	@Override
	public int hashCode() { // Gene name is unique.
		return this.getGene().hashCode();
	}

	/**
	 * @return the family
	 */
	public String getFamily() {
		return family;
	}

	/**
	 * @param family the family to set
	 */
	public void setFamily(String family) {
		this.family = family;
	}

	/**
	 * @return the gene
	 */
	public String getGene() {
		return gene;
	}

	/**
	 * @param gene the gene to set
	 */
	public void setGene(String gene) {
		this.gene = gene;
	}
	
	@Override
	public String toString() {
		return this.getGene();
	}

}
