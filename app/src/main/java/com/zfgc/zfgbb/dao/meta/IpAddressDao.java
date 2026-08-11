package com.zfgc.zfgbb.dao.meta;

import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.forum.MessageHistoryDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;

@Repository
public class IpAddressDao extends IdentityDao<IpAddressDbo, IpAddressDboExample> {

	private final MessageHistoryDao messageHistoryDao;

	public IpAddressDao(IpAddressDboMapper mapper,
			MessageHistoryDao messageHistoryDao) {
		super(mapper);
		this.messageHistoryDao = messageHistoryDao;
	}

	public int deleteUnreferencedIpAddresses(List<Integer> ipAddressIds) {
		Set<Integer> stillReferenced = Set.copyOf(messageHistoryDao.findReferencedIpAddressIdsAmong(ipAddressIds));
		List<Integer> unreferenced = ipAddressIds.stream().filter(id -> !stillReferenced.contains(id)).toList();
		if (unreferenced.isEmpty())
			return 0;
		IpAddressDboExample byIds = new IpAddressDboExample();
		byIds.createCriteria().andIpAddressIdIn(unreferenced);
		return deleteWhere(byIds);
	}
}
