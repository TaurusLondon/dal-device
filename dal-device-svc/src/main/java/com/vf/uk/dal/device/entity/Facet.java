package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Facet
 */
@Data
public class Facet {
	private String equipmentMake = null;

	private List<Make> makeList = new ArrayList<>();

}
