package com.vf.uk.dal.device.utils;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.vf.uk.dal.common.beans.Environment;
import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.logger.LogHelper;

/**
 * Connection details to cohernece
 * **/
public class CohAddressProvider implements com.tangosol.net.AddressProvider {
	@Autowired
	Environment props;

	private ArrayList<InetSocketAddress> addressList = null;

	private int index = 0;

	public CohAddressProvider() {

		LogHelper.info(this, "Check me --> CohAddressProvider() --> Entered");
		addressList = new ArrayList<>(5);
		String coherenceIP = ConfigHelper.getString(Constants.COHERENCE_IP, Constants.DEFAULT_COHERENCE_IP);
		int coherencePort = ConfigHelper.getInt(Constants.COHERENCE_PORT, Constants.DEFAULT_COHERENCE_PORT );
		LogHelper.info(this,"coherenceIP::"+coherenceIP +" and "+"coherencePort::"+coherencePort);
		addressList.add(new InetSocketAddress(coherenceIP, coherencePort));
	}

	@Override
	public void accept() {
		LogHelper.info(this,"Check me --> connection accepted");
	}

	@Override
	public void reject(Throwable throwable) {
		LogHelper.info(this,"Check me --> connection rejected --> " + throwable.getMessage());
	}

	@Override
	public InetSocketAddress getNextAddress() {
		LogHelper.info(this,"Check me --> getNextAddress() --> Entered");
		if (index >= addressList.size()) {
			index = 0;
			// IMPORTANT: null should be returned to avoid infinite loop
			return null;
		}

		InetSocketAddress addr = addressList.get(index);
		LogHelper.info(this,"Check me --> getNextAddress() --> " + addr.toString());
		index++;

		return addr;
	}
}