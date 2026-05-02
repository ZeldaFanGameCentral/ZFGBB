package com.zfgc.zfgbb.mbg;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SmfTablePrefixPlugin extends PluginAdapter {

	private static final String BIND_NAME = "smfTablePrefix";
	private static final String OGNL_LOOKUP =
			"@com.zfgc.zfgbb.migrator.jobs.JobContextHolder@getTablePrefix()";

	private String introspectedPrefix;
	private String placeholder;

	@Override
	public boolean validate(@NonNull List<String> warnings) {
		introspectedPrefix = properties.getProperty("introspectedPrefix");
		if (introspectedPrefix == null || introspectedPrefix.isBlank()) {
			warnings.add("SmfTablePrefixPlugin: required 'introspectedPrefix' property is missing");
			return false;
		}
		placeholder = "${" + BIND_NAME + "}";
		return true;
	}

	@Override
	public boolean sqlMapDocumentGenerated(@NonNull Document document, @NonNull IntrospectedTable introspectedTable) {
		XmlElement root = document.getRootElement();
		for (VisitableElement child : root.getElements()) {
			if (!(child instanceof XmlElement el)) {
				continue;
			}
			String name = el.getName();
			switch (name) {
				case "select", "insert", "update", "delete" -> {
					rewriteText(el);
					injectBind(el);
				}
				case "sql" -> rewriteText(el);
				default -> { }
			}
		}
		return true;
	}

	private void rewriteText(XmlElement el) {
		List<VisitableElement> children = el.getElements();
		for (int i = 0; i < children.size(); i++) {
			VisitableElement child = children.get(i);
			if (child instanceof TextElement te) {
				String content = te.content();
				if (content.contains(introspectedPrefix)) {
					children.set(i, new TextElement(content.replace(introspectedPrefix, placeholder)));
				}
			} else if (child instanceof XmlElement xe) {
				rewriteText(xe);
			}
		}
	}

	private void injectBind(XmlElement el) {
		XmlElement bind = new XmlElement("bind");
		bind.addAttribute(new Attribute("name", BIND_NAME));
		bind.addAttribute(new Attribute("value", OGNL_LOOKUP));
		el.addElement(0, bind);
	}
}
