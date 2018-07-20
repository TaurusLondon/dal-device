package com.vf.uk.dal.device.datamodel.bundle;

import java.util.List;

import lombok.Data;
@Data
public class Group {

	private String groupName;

	private Long priority;

	private boolean comparable;

	private List<Specification> specifications;

	public Group() {
		super();
	}
}
