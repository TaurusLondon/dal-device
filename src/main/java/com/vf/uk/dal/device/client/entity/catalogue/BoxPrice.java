
package com.vf.uk.dal.device.client.entity.catalogue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author sahil.monga
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BoxPrice {

	protected Double priceNet;
	protected Double priceGross;
	protected Double priceVAT;

}
