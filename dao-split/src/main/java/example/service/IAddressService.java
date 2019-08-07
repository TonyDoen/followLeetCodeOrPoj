package example.service;

import java.util.List;

import example.bean.TbReceiverAddress;
import split.transaction.annotation.Transactional;

/**
 */
public interface IAddressService {
	
	/**
	 * add address
	 * @param address
	 * @return
	 */
	public Integer save(TbReceiverAddress address);
	
	/**
	 * update exist address;
	 * @param address
	 * @throws Exception 
	 */
	public void update(TbReceiverAddress address) throws Exception;
	
	/**
	 * if not exist insert new one, if exist update it 
	 * @param address
	 * @return
	 * @throws Exception 
	 */
	public Integer saveOrUpdate(TbReceiverAddress address) throws Exception;
	
	/**
	 * delete by id
	 * @param Id
	 * @throws Exception 
	 */
	public void delete(Integer id) throws Exception;
		
	/**
	 * get by id
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public TbReceiverAddress getById(Integer id) throws Exception;
}
