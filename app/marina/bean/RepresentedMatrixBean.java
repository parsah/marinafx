package marina.bean;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
public class RepresentedMatrixBean {
	private SimpleObjectProperty<BindingSite> site = new SimpleObjectProperty<BindingSite>();
	private SimpleIntegerProperty laplace = new SimpleIntegerProperty();
	private SimpleIntegerProperty confidence = new SimpleIntegerProperty();
	private SimpleIntegerProperty lift = new SimpleIntegerProperty();
	private SimpleIntegerProperty cosine = new SimpleIntegerProperty();
	private SimpleIntegerProperty jaccard = new SimpleIntegerProperty();
	private SimpleIntegerProperty kappa = new SimpleIntegerProperty();
	private SimpleIntegerProperty phi = new SimpleIntegerProperty();
	private SimpleDoubleProperty pvalue = new SimpleDoubleProperty();
	private SimpleDoubleProperty numQuery = new SimpleDoubleProperty();
	private SimpleDoubleProperty numBaseline = new SimpleDoubleProperty();

	public RepresentedMatrixBean() {
		this.site.set(null);
		this.confidence.set(0);
		this.cosine.set(0);
		this.jaccard.set(0);
		this.kappa.set(0);
		this.laplace.set(0);
		this.lift.set(0);
		this.numBaseline.set(0);
		this.numQuery.set(0);
		this.phi.set(0);
		this.pvalue.set(0);
	}

	/**
	 * @return the laplace
	 */
	public int getLaplace() {
		return laplace.get();
	}
	/**
	 * @param arg the laplace to set
	 */
	public void setLaplace(int arg) {
		this.laplace.set(arg);
	}
	/**
	 * @return the confidence
	 */
	public int getConfidence() {
		return confidence.get();
	}
	/**
	 * @param arg the confidence to set
	 */
	public void setConfidence(int arg) {
		this.confidence.set(arg);
	}
	/**
	 * @return the lift
	 */
	public int getLift() {
		return lift.get();
	}
	/**
	 * @param arg the lift to set
	 */
	public void setLift(int arg) {
		this.lift.set(arg);
	}
	/**
	 * @return the cosine
	 */
	public int getCosine() {
		return cosine.get();
	}
	/**
	 * @param arg the cosine to set
	 */
	public void setCosine(int arg) {
		this.cosine.set(arg);
	}
	/**
	 * @return the jaccard
	 */
	public int getJaccard() {
		return jaccard.get();
	}
	/**
	 * @param arg the jaccard to set
	 */
	public void setJaccard(int arg) {
		this.jaccard.set(arg);
	}
	/**
	 * @return the kappa
	 */
	public int getKappa() {
		return kappa.get();
	}
	/**
	 * @param arg the kappa to set
	 */
	public void setKappa(int arg) {
		this.kappa.set(arg);
	}
	/**
	 * @return the phi
	 */
	public int getPhi() {
		return phi.get();
	}
	/**
	 * @param arg the phi to set
	 */
	public void setPhi(int arg) {
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
	public BindingSite getSite() {
		return site.get();
	}

	/**
	 * @param arg the bindingSite to set
	 */
	public void setSite(BindingSite arg) {
		this.site.set(arg);	
	}

}
