package com.zfgc.zfgbb.services.system;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dao.system.SystemConfigDao;
import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.dbo.SystemConfigDboExample;

@Service
@Transactional
public class SystemConfigService {

	public static final class Keys {
		public static final String INSTALLED = "installed";
		public static final String SITE_NAME = "site_name";
		public static final String INSTALLED_AT = "installed_at";
		public static final String INSTALLED_BY_USER_ID = "installed_by_user_id";
		public static final String CMS_DISCUSSION_BOARD_ID = "cms_discussion_board_id";
		public static final String RECYCLE_BOARD_ID = "recycle_board_id";

		private Keys() {
		}
	}

	private final SystemConfigDao dao;

	private final AtomicReference<Optional<String>> cachedSiteName = new AtomicReference<>();

	public SystemConfigService(SystemConfigDao dao) {
		this.dao = dao;
	}

	@Transactional(readOnly = true)
	public boolean isInstalled() {
		return Optional.ofNullable(get(Keys.INSTALLED))
				.map(Boolean::parseBoolean)
				.orElse(false);
	}

	@Transactional(readOnly = true)
	public String get(String key) {
		if (Keys.SITE_NAME.equals(key)) {
			Optional<String> cachedValue = cachedSiteName.get();
			if (cachedValue == null) {
				cachedValue = Optional.ofNullable(readConfigValue(key));
				cachedSiteName.set(cachedValue);
			}
			return cachedValue.orElse(null);
		}
		return readConfigValue(key);
	}

	private String readConfigValue(String key) {
		SystemConfigDboExample ex = new SystemConfigDboExample();
		ex.createCriteria().andConfigKeyEqualTo(key);
		return dao.get(ex).stream().findFirst().map(SystemConfigDbo::getConfigValue).orElse(null);
	}

	public void set(String key, String value) {
		OffsetDateTime now = OffsetDateTime.now(java.time.ZoneOffset.UTC);
		SystemConfigDboExample ex = new SystemConfigDboExample();
		ex.createCriteria().andConfigKeyEqualTo(key);
		Optional<SystemConfigDbo> existing = dao.get(ex).stream().findFirst();

		if (existing.isPresent()) {
			SystemConfigDbo dbo = existing.get();
			dbo.setConfigValue(value);
			dbo.setUpdatedTs(now);
			dao.getMapper().updateByPrimaryKey(dbo);
		} else {
			SystemConfigDbo dbo = new SystemConfigDbo();
			dbo.setConfigKey(key);
			dbo.setConfigValue(value);
			dbo.setCreatedTs(now);
			dbo.setUpdatedTs(now);
			dao.getMapper().insert(dbo);
		}

		if (Keys.SITE_NAME.equals(key))
			cachedSiteName.set(null);
	}

	public void unset(String key) {
		SystemConfigDboExample ex = new SystemConfigDboExample();
		ex.createCriteria().andConfigKeyEqualTo(key);
		dao.getMapper().deleteByExample(ex);

		if (Keys.SITE_NAME.equals(key))
			cachedSiteName.set(null);
	}
}
