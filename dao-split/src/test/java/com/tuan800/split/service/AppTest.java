package com.tuan800.split.service;

import example.bean.TbReceiverAddress;
import example.service.IAddressService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import split.support.ServiceLoader;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws Exception
	 */
	public void testInsert() throws Exception {
		assertTrue(true);
		IAddressService addressService = (IAddressService) ServiceLoader.getSingleTon(IAddressService.class);
		TbReceiverAddress address = new TbReceiverAddress();
		address.setAddress("address insert::"+System.currentTimeMillis());
		address.setCityId(100);
		address.setCityName("bei jing");
		address.setCountyId(100);
		address.setCountyName("chao yang");
		address.setEmail("zhe800@zhe800.com");
		Long begin = System.currentTimeMillis();
		addressService.save(address);
		System.out.println(System.currentTimeMillis()-begin);
	}
	
	public void testUpdate() throws Exception{
		assertTrue(true);
		IAddressService addressService = (IAddressService) ServiceLoader.getSingleTon(IAddressService.class);
		TbReceiverAddress address = addressService.getById(153807);
		address.setAddress("address update::"+System.currentTimeMillis());
		addressService.update(address);
	}
	
	public void testDelete() throws Exception{
		assertTrue(true);
		IAddressService addressService = (IAddressService) ServiceLoader.getSingleTon(IAddressService.class);
		addressService.delete(153807);
	}

}
