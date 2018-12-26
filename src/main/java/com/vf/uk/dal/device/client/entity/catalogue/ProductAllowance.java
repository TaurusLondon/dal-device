package com.vf.uk.dal.device.client.entity.catalogue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProductAllowance
 * @author manoj.bera
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAllowance {

	/** type */
	private String type;

	/** uom */
	private String uom;

	/** value */
	private String value;
}
