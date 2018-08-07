package com.vf.uk.dal.device.controller;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.datamodel.handsetonlinemodel.HandsetOnlineModel;
import com.vf.uk.dal.device.datamodel.handsetonlinemodel.HandsetOnlineModelList;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.ProductGroupModelMap;
import com.vf.uk.dal.device.svc.DeviceEntityService;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.validator.Validator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DeviceEntityController {
	@Autowired
	DeviceEntityService deviceEntiryService;

	/**
	 * Handles requests for getDeviceTile Service with input as
	 * GROUP_NAME,GROUP_TYPE in URL as query. performance improved by @author
	 * manoj.bera
	 * 
	 * @param ex
	 * @return ErrorResponse
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public com.vf.uk.dal.common.exception.ErrorResponse handleMissingParams(
			MissingServletRequestParameterException ex) {

		return new com.vf.uk.dal.common.exception.ErrorResponse(400, "DEVICE_INVALID_INPUT",
				"Missing mandatory parameter " + ex.getParameterName());

	}

	/**
	 * Handles requests to return the Commercial Products from Product Catalog.
	 * It takes either Product Id(s) or product Name(s) as input. This is an
	 * Entity API which will return Commercial Products without applying any
	 * business logic.
	 * 
	 * @param productIds
	 * @param productNames
	 * 
	 * @return List of CommercialProducts
	 */
	@ApiOperation(value = "The service gets the details of the commercial Product in the response.", notes = "The service gets the details of the commercial Product in the response.", response = CommercialProduct.class, tags = {
			"GetCommercialProductByProductNameOrProductId" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = CommercialProduct.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/product", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public List<CommercialProduct> getCommercialProduct(
			@ApiParam(value = "Device Id for getting commercial product to displayed. possible value can be comma separated device Id like 093353,080004") @RequestParam(value = "productId", required = false) String productId,
			@ApiParam(value = "Product Name for getting commercial product to displayed. possible value can be comma separated product names are like Fibre Activation Fee,ATA Device") @RequestParam(value = "productName", required = false) String productName) {
		List<CommercialProduct> commProductDetails;

		if (StringUtils.isBlank(productId) && StringUtils.isBlank(productName)) {
			LogHelper.error(this,
					"Query parameter(s) passed in the request is invalid " + ExceptionMessages.INVALID_QUERY_PARAMS);
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
		} else if (StringUtils.isNotBlank(productName)) {
			LogHelper.info(this,
					"Get the list of Product Details for the Product Name passed as request params: " + productName);
			commProductDetails = deviceEntiryService.getCommercialProductDetails(productName);
		} else {
			LogHelper.info(this,
					"Get the list of Product Details for the Product Name passed as request params: " + productName);
			commProductDetails = deviceEntiryService.getCommercialProductDetails(productId);
		}

		return commProductDetails;
	}

	/**
	 * Handles requests to return the Product Groups from Product Catalog by
	 * taking groupType as input. This is an Entity API which will return
	 * Commercial ProductGroups without applying any business logic.
	 * 
	 * @param groupType
	 * @return List of ProductGroups
	 */
	@ApiOperation(value = "The service gets the details of the Product group in the response.", notes = "The service gets the details of the Product group in the response.", response = Group.class, tags = {
			"GetProductGroupByGroupType" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Group.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/productGroup", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public List<Group> getProductGroupByGroupType(
			@NotNull @ApiParam(value = "Product group Type for getting product group to displayed. possible value can be like DEVICE_PAYM or DEVICE_PAYG") @RequestParam(value = "groupType", required = true) String groupType) {
		List<Group> groupDetails;

		if (StringUtils.isNotBlank(groupType)) {
			LogHelper.info(this,
					"Get the list of Product Details for the Product Id passed as request params: " + groupType);
			groupDetails = deviceEntiryService.getProductGroupByType(groupType);
		} else {
			LogHelper.error(this,
					" Query parameter(s) passed in the request is invalid" + ExceptionMessages.INVALID_QUERY_PARAMS);
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
		}
		return groupDetails;
	}

	/**
	 * Handles requests to return the Map of Product Group Model from Product
	 * Catalog. It takes Product Id(s) as input. This is an Entity API which
	 * will return Map of Product Group Model with applying small business
	 * logic.
	 * 
	 * @param productIds
	 * 
	 * @return Map of Product Group Model
	 */
	@ApiOperation(value = "Handles requests to return the Map of Product Group Model from Product Catalog. It takes Product Id(s) as input.", notes = "This is an Entity API which will return Map of Product Group Model with applying small business logic.", response = ProductGroupModelMap.class, tags = {
			"ProductGroupModelMap" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ProductGroupModelMap.class),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/device/getDeliveryMethod/getProductGroupModel", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON })
	public ProductGroupModelMap getProductGroupModel(
			@NotNull @ApiParam(value = "Device Id for getting product Group to displayed. possible value can be comma separated device Id like 093353,080004") @RequestParam(value = "productId", required = true) String productId) {
		ProductGroupModelMap productGroupModelDetails;

		if (StringUtils.isBlank(productId)) {
			LogHelper.error(this,
					"Query parameter(s) passed in the request is invalid" + ExceptionMessages.INVALID_QUERY_PARAMS);
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
		} else {
			LogHelper.info(this,
					"Get the list of Product Details for the Product Id (s) passed as request params: " + productId);
			productGroupModelDetails = deviceEntiryService.getMapOfProductModelForGetDeliveryMethod(productId);
		}

		return productGroupModelDetails;
	}
	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param make
	 * @param model
	 * @param groupType
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @param color
	 * @param operatingSystem
	 * @param capacity
	 * @param mustHaveFeatures
	 * @return
	 */
	@ApiOperation(value = "The service gets the details of the handset Online Model in the response.", notes = "The service gets the details of the Handset Online Model in the response.", response = HandsetOnlineModel.class, tags = {
			"GetHandsetOnlineModelByDeviceTileMakeAndModelDeviceDetailsDeviceList" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = HandsetOnlineModelList.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 404, message = "Not found", response = com.vf.uk.dal.device.entity.Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = com.vf.uk.dal.device.entity.Error.class) })
	@RequestMapping(value = "/productCatalog/device", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON })
	public HandsetOnlineModelList getHandsetOnlineModel(
			@ApiParam(value = "Device Id of the device tile being requested", required = false)@RequestParam(value="deviceId", required = false) String deviceId,
			@ApiParam(value = "Journey Type", required = false)@RequestParam(value="journey Type", required = false) String journeyType,
			@ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"apple\".", required = false) @RequestParam(value = "make", required = false) String make,
			@ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"iphone7\".", required = false) @RequestParam(value = "model", required = false) String model,
			@ApiParam(value = "Values on which the attributes should be filtered upon. Possible values are \"DEVICE_PAYM\" or \"DEVICE_PAYG\" or \"DATA_DEVICE_PAYM\".", required = false) @RequestParam(value = "groupType", required = false) String groupType,
			@ApiParam(value = "Values of attributes based on which solr will provide the sorted response, like Most Popular(Priority),Rating, New Releases, Brand(a-z)(z-a) but need to pass EquipmentMake to api, MonthlyPrice(lo-hi)(hi-lo)(Need to pass RecurringCharge).", required = false) @RequestParam(value = "sort", required = false) String sort,
			@ApiParam(value = "Page Number") @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@ApiParam(value = "Page Size") @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "Filter by Colour of the device as in specification groups. Please note the value of this filter should be passed in double quotes. such as colour is \"Black\",\"Gold\"") @RequestParam(value = "color", required = false) String color,
			@ApiParam(value = "Filter by OS of the device as in specification groups. Please note the value of this filter should be passed in double quotes. such as operatingSystem is \"iOS 10\",\"iOS 9\"") @RequestParam(value = "operatingSystem", required = false) String operatingSystem,
			@ApiParam(value = "Filter by capacity of the device as in specification groups. Please note the value of this filter should be passed in double quotes. such as capacity is \"32 GB\",\"8 GB\"") @RequestParam(value = "capacity", required = false) String capacity,
			@ApiParam(value = "One or more of the following token separated by comma. \"physicalKeyboard\", \"greatCamera\", \"goodBattery\", \"bigScreen\", \"4GEnabled\", 'lightWeight\"") @RequestParam(value = "mustHaveFeatures", required = false) String mustHaveFeatures) {
		HandsetOnlineModelList handsetOnlineModel;

		if (Validator.validateNullValuesFOrHandsetOnlineModel(deviceId, journeyType, make, model, groupType, sort, pageNumber, pageSize, color, operatingSystem, capacity, mustHaveFeatures)) {
			LogHelper.error(this,
					"Query parameter(s) passed in the request is invalid" + ExceptionMessages.INVALID_QUERY_PARAMS);
			throw new ApplicationException(ExceptionMessages.INVALID_QUERY_PARAMS);
		} else  {
			LogHelper.info(this,
					"Get the list of Online Handset Model ");
			handsetOnlineModel = deviceEntiryService.getHandsetOnlineModelDetails(deviceId,journeyType,make,model,groupType,
					sort,pageNumber,pageSize,color,operatingSystem,capacity,mustHaveFeatures);
		}
		return handsetOnlineModel;
	}

}
