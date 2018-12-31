package com.vf.uk.dal.device.client.entity.catalogue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ItemAttribute
 * @author manoj.bera
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAttribute {

	/** key */
	private String key;

	/** value */
	private String value;

	/** type */
	private String type;

	/** valueUOM */
	private String valueUOM;
}
