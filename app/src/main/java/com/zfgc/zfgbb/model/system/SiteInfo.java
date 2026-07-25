package com.zfgc.zfgbb.model.system;

import java.util.List;
import java.util.Optional;

public record SiteInfo(String siteName, boolean registrationEnabled, String defaultContentFormat,
		List<String> contentFormats, Optional<String> buildVersion) {
}
