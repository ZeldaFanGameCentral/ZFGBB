package com.zfgc.zfgbb.model.cms;

import java.util.List;

public record WikiConfig(String siteName, List<String> namespaces, List<NavSection> nav) {

	public record NavSection(String title, List<NavItem> items) {
	}

	public record NavItem(String label, String to) {
	}
}
