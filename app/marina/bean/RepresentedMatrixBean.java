package marina.bean;

import java.io.Serializable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import marina.bindingsite.BindingSite;


/**
 * The goal of this class is to serve as a data-model solely for 
 * visualization purposes. In doing so, the logic as-part of a contingency
 * matrix is therefore separated and not inherited by this bean. As a result,
 * the data-model can solely be used to encapsulate states of the matrix
 * without being concerned as-to how its computation was performed.
 * @author Parsa Hosseini
 * */
public class RepresentedMatrixBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SimpleObjectProperty<BindingSite> tfbs = new SimpleObjectProperty<BindingSite>();
	private SimpleDoubleProperty laplace = new SimpleDoubleProperty();
	private SimpleDoubleProperty confidence = new SimpleDoubleProperty();
	private SimpleDoubleProperty lift = new SimpleDoubleProperty();
	private SimpleDoubleProperty cosine = new SimpleDoubleProperty();
	private SimpleDoubleProperty jaccard = new SimpleDoubleProperty();
	private SimpleDoubleProperty kappa = new SimpleDoubleProperty();
	private SimpleDoubleProperty phi = new SimpleDoubleProperty();
	private SimpleDoubleProperty pvalue = new SimpleDoubleProperty();
	private SimpleDoubleProperty numQuery = new SimpleDoubleProperty();
	private SimpleDoubleProperty numBaseline = new SimpleDoubleProperty();

	public RepresentedMatrixBean() {
		this.setConfidence(0);
		this.setCosine(0);
		this.setJaccard(0);
		this.setKappa(0);
		this.setLaplace(0);
		this.setLift(0);
		this.setNumBaseline(0);
		this.setNumQuery(0);
		this.setPhi(0);
		this.setPvalue(0);
	}

	/**
	 * @return the laplace
	 */
	public double getLaplace() {
		return laplace.get();
	}
	/**
	 * @param arg the laplace to set
	 */
	public void setLaplace(double arg) {
		this.laplace.set(arg);
	}
	/**
	 * @return the confidence
	 */
	public double getConfidence() {
		return confidence.get();
	}
	/**
	 * @param arg the confidence to set
	 */
	public void setConfidence(double arg) {
		this.confidence.set(arg);
	}
	/**
	 * @return the lift
	 */
	public double getLift() {
		return lift.get();
	}
	/**
	 * @param arg the lift to set
	 */
	public void setLift(double arg) {
		this.lift.set(arg);
	}
	/**
	 * @return the cosine
	 */
	public double getCosine() {
		return cosine.get();
	}
	/**
	 * @param arg the cosine to set
	 */
	public void setCosine(double arg) {
		this.cosine.set(arg);
	}
	/**
	 * @return the jaccard
	 */
	public double getJaccard() {
		return jaccard.get();
	}
	/**
	 * @param arg the jaccard to set
	 */
	public void setJaccard(double arg) {
		this.jaccard.set(arg);
	}
	/**
	 * @return the kappa
	 */
	public double getKappa() {
		return kappa.get();
	}
	/**
	 * @param arg the kappa to set
	 */
	public void setKappa(double arg) {
		this.kappa.set(arg);
	}
	/**
	 * @return the phi
	 */
	public double getPhi() {
		return phi.get();
	}
	/**
	 * @param arg the phi to set
	 */
	public void setPhi(double arg) {
		this.phi.set(arg);
	}
	/**
	 * @return the pvalue
	 */
	public double getPvalue() {
		return pvalue.get();
	}
	/**
	 * @param arg the pvalue to set
	 */
	public void setPvalue(double arg) {
		this.pvalue.set(arg);
	}
	/**
	 * @return the numQuery
	 */
	public double getNumQuery() {
		return numQuery.get();
	}
	/**
	 * @param arg the numQuery to set
	 */
	public void setNumQuery(double arg) {
		this.numQuery.set(arg);
	}
	/**
	 * @return the numBaseline
	 */
	public double getNumBaseline() {
		return numBaseline.get();
	}
	/**
	 * @param arg the numBaseline to set
	 */
	public void setNumBaseline(double arg) {
		this.numBaseline.set(arg);
	}

	/**
	 * @return the bindingSite
	 */
	public BindingSite getBindingSite() {
		return tfbs.get();
	}

	/**
	 * @param arg the bindingSite to set
	 */
	public void setBindingSite(BindingSite arg) {
		this.tfbs.set(arg);	
	}

}
