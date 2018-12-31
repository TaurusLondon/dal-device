package com.vf.uk.dal.device.client.entity.catalogue;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Group
 * @author manoj.bera
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

	/** groupName */
	private String groupName;

	/** priority */
	private Long priority;

	/** comparable */
	private String comparable;

	/** specifications */
	private List<Specification> specifications;

	/** type */
	private String type;
}
