package com.vf.uk.dal.device.datamodel.handsetonlinemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BundleAndHardwareTuple
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 
 * @author sahil.monga
 *
 */
public class BundleAndHardwareTuple {
	@JsonProperty("hardwareId")
	private String hardwareId = null;

	@JsonProperty("bundleId")
	private String bundleId = null;

}
