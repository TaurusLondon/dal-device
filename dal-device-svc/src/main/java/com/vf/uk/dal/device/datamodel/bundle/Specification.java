package com.vf.uk.dal.device.datamodel.bundle;

import lombok.Data;

@Data
public class Specification {

	private String name;

	private String value;

	private Long priority;

	private boolean comparable;

	private boolean isKey;

	private String valueType;

	private String valueUOM;

	private String footNote;

	private String description;

	private boolean hideInList;

	public Specification() {
		super();
	}
}
