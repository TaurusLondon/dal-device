
package com.vf.uk.dal.utility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * Error
 *
 */
@Data
public class Error {

	@JsonProperty("code")
	private String code = null;

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("referenceId")
	private String referenceId = null;

}
