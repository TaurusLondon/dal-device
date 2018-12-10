package com.vf.uk.dal.device.dao;

import java.util.List;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.solr.DevicePreCalculatedData;
import com.vf.uk.dal.device.model.solr.Media;
import com.vf.uk.dal.device.model.solr.OfferAppliedPriceDetails;
/**
 * 
 * Device Tile Cache Dao
 *
 */
public interface DeviceTileCacheDAO {

	/**
	 * Insert cache device to db.
	 *
	 * @return the cache device tile response
	 */
	public CacheDeviceTileResponse insertCacheDeviceToDb();

	/**
	 * Update cache device to db.
	 *
	 * @param jobId
	 *            the job id
	 * @param jobStatus
	 *            the job status
	 */
	public void updateCacheDeviceToDb(String jobId, String jobStatus);

	/**
	 * Gets the cache device job status.
	 *
	 * @param jobId
	 *            the job id
	 * @return the cache device job status
	 * @throws ApplicationException
	 *             the application exception
	 */
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId);

	/**
	 * rollBackTransaction
	 * @return 
	 */
	public void rollBackTransaction();

	/**
	 * endTransaction
	 * @return void
	 */
	public void endTransaction();

	/**
	 * beginTransaction
	 * @return void
	 */
	public void beginTransaction();

	/**
	 * 
	 * @param mediaList
	 * @param deviceId
	 * @return DeviceMediaData
	 */
	public int saveDeviceMediaData(List<Media> mediaList, String deviceId);

	/**
	 * 
	 * @param listProductGroupForDeviceListing
	 * @return DeviceListPreCalcData
	 */
	public int saveDeviceListPreCalcData(List<DevicePreCalculatedData> listProductGroupForDeviceListing);

	/**
	 * 
	 * @param offerAppliedPricesList
	 * @return DeviceListILSCalcData
	 */
	public int saveDeviceListILSCalcData(List<OfferAppliedPriceDetails> offerAppliedPricesList);
}
