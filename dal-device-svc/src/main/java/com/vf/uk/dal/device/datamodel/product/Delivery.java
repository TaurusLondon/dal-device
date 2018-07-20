package com.vf.uk.dal.device.datamodel.product;

import lombok.Data;

/**
 * 
 * @author manoj.bera
 *  Delivery
 *
 */
@Data
public class Delivery {

	private String classification;

	private String partner;

	private String soaDeliveryMethod;

	private boolean weekend;

}
