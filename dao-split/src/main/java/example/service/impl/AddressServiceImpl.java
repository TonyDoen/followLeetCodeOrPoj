package example.service.impl;

import example.bean.TbReceiverAddress;
import example.dao.IAddressDAO;
import example.service.IAddressService;
import split.annotation.HolisticController;
import split.support.DAOLoader;

public class AddressServiceImpl implements IAddressService {
	
	private IAddressDAO addressDAO = (IAddressDAO) DAOLoader.getSingleTon(IAddressDAO.class);
	
	@Override
	@HolisticController
	public Integer save(TbReceiverAddress address) {
		try{
			this.addressDAO.insert(address);
			Integer intId = address.getId();
			return intId;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@HolisticController
	public TbReceiverAddress getById(Integer id) throws Exception {
		TbReceiverAddress address = new TbReceiverAddress();
		address.setId(id);
		address = this.addressDAO.selectById(address);
		return address;
	}

	@Override
	@HolisticController
	public void update(TbReceiverAddress address) throws Exception {
		TbReceiverAddress addressExist = this.addressDAO.selectById(address);
		if(addressExist!=null){
			//copy attributes
//			addressExist.setPostCode(String.valueOf(System.currentTimeMillis()));
//			addressExist.setAddress(address.getAddress());
			this.addressDAO.update(address);
		}
	}

	@Override
	@HolisticController
	public Integer saveOrUpdate(TbReceiverAddress address) throws Exception {
		if(address.getId()==0 || address.getId()==null){
			return null;
		}
		TbReceiverAddress addressTemp = this.addressDAO.selectById(address);
		if(addressTemp!=null){
			//copy attributes
			this.addressDAO.update(address);
			return null;
		}else{
			this.addressDAO.insert(address);
			return address.getId();
		}
	}

	@Override
	@HolisticController
	public void delete(Integer id) throws Exception {
		TbReceiverAddress address = new TbReceiverAddress();
		address.setId(id);
		this.addressDAO.delete(address);
	}
}
