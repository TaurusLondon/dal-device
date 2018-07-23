package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * StatusInfo
 */
@Data
public class StatusInfo {
	private String status = null;

	private List<String> errorCodes = new ArrayList<String>();
}
