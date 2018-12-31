package com.vf.uk.dal.device.client.entity.catalogue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Specification
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specification {

	/** name */
	private String name = null;

	/** value */
	private String value = null;

	/** priority */
	private Long priority = null;

	/** comparable */
	private Boolean comparable = null;

	/** isKey */
	private Boolean isKey = null;

	/** valueType */
	private String valueType = null;

	/** valueUOM */
	private String valueUOM = null;

	/** description */
	private String description = null;

	/** footNote */
	private String footNote = null;

	/** hideInList */
	private Boolean hideInList = null;
}
