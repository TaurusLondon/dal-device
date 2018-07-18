package com.vf.uk.dal.utility.solr.entity;

/**
 * 
 * 
 *
 */
public class OneOffPrice {
	private String gross;

	private String net;

	private String vat;

	/**
	 * 
	 * @param gross
	 */
	public void setGross(String gross) {
		this.gross = gross;
	}

	/**
	 * 
	 * @return
	 */
	public String getGross() {
		return this.gross;
	}

	/**
	 * 
	 * @param net
	 */
	public void setNet(String net) {
		this.net = net;
	}

	/**
	 * 
	 * @return
	 */
	public String getNet() {
		return this.net;
	}

	/**
	 * 
	 * @param vat
	 */
	public void setVat(String vat) {
		this.vat = vat;
	}

	/**
	 * 
	 * @return
	 */
	public String getVat() {
		return this.vat;
	}
}