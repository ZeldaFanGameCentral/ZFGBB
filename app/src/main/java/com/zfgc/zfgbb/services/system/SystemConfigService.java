package com.zfgc.zfgbb.services.system;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.dao.meta.SystemConfigDao;

@Service
@Transactional
@RequiredArgsConstructor
public class SystemConfigService {

	public static final class Keys {
		public static final String INSTALLED = "installed";
		public static final String SITE_NAME = "site_name";
		public static final String INSTALLED_AT = "installed_at";
		public static final String INSTALLED_BY_USER_ID = "installed_by_user_id";
		public static final String CMS_DISCUSSION_BOARD_ID = "cms_discussion_board_id";
		public static final String RECYCLE_BOARD_ID = "recycle_board_id";
		public static final String CONTENT_GENERATION = "content_generation";
		public static final String AUTHORING_DEFAULT_CONTENT_FORMAT = "authoring_default_content_format";

		private Keys() {
		}
	}

	private record CachedSiteName(long invalidations, Optional<String> siteName) {
	}

	private final SystemConfigDao systemConfigDao;

	private final AtomicReference<CachedSiteName> cachedSiteName =
			new AtomicReference<>(new CachedSiteName(0, Optional.empty()));

	@Transactional(readOnly = true)
	public boolean isInstalled() {
		return Optional.ofNullable(get(Keys.INSTALLED))
				.map(Boolean::parseBoolean)
				.orElse(false);
	}

	@Transactional(readOnly = true)
	public ContentFormat authoringDefaultContentFormat() {
		return ContentFormat.parse(readConfigValue(Keys.AUTHORING_DEFAULT_CONTENT_FORMAT))
				.orElse(ContentFormat.BBCODE);
	}

	public void setAuthoringDefaultContentFormat(ContentFormat authoringDefault) {
		set(Keys.AUTHORING_DEFAULT_CONTENT_FORMAT, authoringDefault.name());
	}

	@Transactional(readOnly = true)
	public String get(String key) {
		if (Keys.SITE_NAME.equals(key))
			return siteName();
		return readConfigValue(key);
	}

	private String siteName() {
		CachedSiteName cached = cachedSiteName.get();
		if (cached.siteName().isPresent())
			return cached.siteName().get();
		String stored = readConfigValue(Keys.SITE_NAME);
		if (stored != null)
			cachedSiteName.compareAndSet(cached,
					new CachedSiteName(cached.invalidations(), Optional.of(stored)));
		return stored;
	}

	private String readConfigValue(String key) {
		return systemConfigDao.find(key)
				.map(SystemConfigDbo::getConfigValue)
				.orElse(null);
	}

	public void set(String key, String value) {
		SystemConfigDbo existing = systemConfigDao.find(key).orElse(null);
		if (existing != null) {
			existing.setConfigValue(value);
			systemConfigDao.update(existing);
		} else {
			SystemConfigDbo inserted = new SystemConfigDbo();
			inserted.setConfigKey(key);
			inserted.setConfigValue(value);
			systemConfigDao.insert(inserted);
		}

		if (Keys.SITE_NAME.equals(key))
			invalidateSiteNameOnceCommitted();
	}

	public void unset(String key) {
		systemConfigDao.delete(key);

		if (Keys.SITE_NAME.equals(key))
			invalidateSiteNameOnceCommitted();
	}

	private void invalidateSiteNameOnceCommitted() {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
				@Override
				public void afterCompletion(int status) {
					invalidateSiteName();
				}
			});
		} else {
			invalidateSiteName();
		}
	}

	private void invalidateSiteName() {
		cachedSiteName.updateAndGet(stale ->
				new CachedSiteName(stale.invalidations() + 1, Optional.empty()));
	}

	public record CmsConfig(String discussionBoardId) {
	}

	@Transactional(readOnly = true)
	public CmsConfig getCmsConfig() {
		return new CmsConfig(get(Keys.CMS_DISCUSSION_BOARD_ID));
	}

	public CmsConfig setCmsConfig(CmsConfig config) {
		set(Keys.CMS_DISCUSSION_BOARD_ID, config.discussionBoardId());
		return getCmsConfig();
	}
}
