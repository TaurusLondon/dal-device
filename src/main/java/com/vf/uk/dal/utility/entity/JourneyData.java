package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * JourneyData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-13T09:29:52.184Z")
@Data
public class JourneyData {
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("state")
	private String state = null;

	@JsonProperty("customerPartyID")
	private String customerPartyID = null;

	@JsonProperty("ssoCreatedDate")
	private String ssoCreatedDate = null;

	@JsonProperty("ownedCustomerPartyIDs")
	private String ownedCustomerPartyIDs = null;

	@JsonProperty("offerID")
	private String offerID = null;

	@JsonProperty("offerCommunicationID")
	private String offerCommunicationID = null;

	@JsonProperty("upgradeEligibilityType")
	private String upgradeEligibilityType = null;

	@JsonProperty("commitmentEndDate")
	private String commitmentEndDate = null;

	@JsonProperty("earlyUpgradeFeeNet")
	private String earlyUpgradeFeeNet = null;

	@JsonProperty("earlyUpgradeFeeGross")
	private String earlyUpgradeFeeGross = null;

	@JsonProperty("accountRoles")
	private List<String> accountRoles = new ArrayList<String>();
}
