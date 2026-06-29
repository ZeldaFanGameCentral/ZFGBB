package com.zfgc.zfgbb.services.cms;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.wiki.WikiTitle;

@Service
public class WikiNamespaceRegistry {
	private final JdbcTemplate jdbc;

	public WikiNamespaceRegistry(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public WikiTitle resolve(String path) {
		int colon = path == null ? -1 : path.indexOf(':');
		if (colon > 0 && colon < path.length() - 1) {
			String prefix = path.substring(0, colon).trim();
			List<Namespace> namespaces = jdbc.query(
					"select distinct n.name, n.case_mode from zfgbb.wiki_namespace n left join zfgbb.wiki_namespace_alias a "
					+ "on a.namespace_name=n.name where lower(n.name)=lower(?) or lower(a.alias)=lower(?)",
					(rs, row) -> new Namespace(rs.getString(1), WikiTitle.CaseMode.valueOf(rs.getString(2))), prefix, prefix);
			if (namespaces.size() > 1)
				throw new IllegalStateException("Ambiguous registered wiki namespace '" + prefix + "'");
			if (namespaces.size() == 1)
				return WikiTitle.of(namespaces.get(0).name(), path.substring(colon + 1), namespaces.get(0).caseMode());
		}
		if (colon < 0) {
			List<String> modes = jdbc.queryForList(
					"select case_mode from zfgbb.wiki_namespace where lower(name) = 'main'", String.class);
			if (modes.size() == 1)
				return WikiTitle.of("MAIN", path, WikiTitle.CaseMode.valueOf(modes.get(0)));
		}
		return WikiTitle.parse(path);
	}

	public WikiTitle.CaseMode caseMode(String namespace) {
		List<String> modes = jdbc.queryForList(
				"select case_mode from zfgbb.wiki_namespace where lower(name)=lower(?)", String.class, namespace);
		return modes.size() == 1 ? WikiTitle.CaseMode.valueOf(modes.get(0)) : WikiTitle.CaseMode.FIRST_LETTER;
	}

	public boolean isSyntheticSystemPage(Integer wikiPageId, String code) {
		return Boolean.TRUE.equals(jdbc.queryForObject("select exists(select 1 from zfgbb.wiki_system_template_page "
				+ "where wiki_page_id = ? and code = ?)", Boolean.class, wikiPageId, code));
	}

	private record Namespace(String name, WikiTitle.CaseMode caseMode) {}
}
