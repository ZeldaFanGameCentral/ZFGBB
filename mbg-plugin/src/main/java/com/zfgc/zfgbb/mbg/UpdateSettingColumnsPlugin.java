package com.zfgc.zfgbb.mbg;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.runtime.mybatis3.MyBatis3FormattingUtilities;

public class UpdateSettingColumnsPlugin extends PluginAdapter {

	static final String METHOD_NAME = "updateByExampleSettingColumns";

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean sqlMapDocumentGenerated(@NonNull Document document, @NonNull IntrospectedTable introspectedTable) {
		if (!introspectedTable.hasPrimaryKeyColumns())
			return true;

		XmlElement update = new XmlElement("update");
		update.addAttribute(new Attribute("id", METHOD_NAME));
		update.addAttribute(new Attribute("parameterType", "map"));
		commentGenerator.addComment(update);

		update.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

		XmlElement set = new XmlElement("set");
		for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
			if (column.isGeneratedAlways())
				continue;
			XmlElement assignIfNamed = new XmlElement("if");
			assignIfNamed.addAttribute(new Attribute("test",
					"columns.contains('" + column.getActualColumnName() + "')"));
			assignIfNamed.addElement(new TextElement(
					MyBatis3FormattingUtilities.getEscapedColumnName(column) + " = "
							+ MyBatis3FormattingUtilities.getParameterClause(column, "row.") + ","));
			set.addElement(assignIfNamed);
		}
		update.addElement(set);

		XmlElement whereExamplePresent = new XmlElement("if");
		whereExamplePresent.addAttribute(new Attribute("test", "example != null"));
		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid",
				introspectedTable.getMyBatis3SqlMapNamespace() + ".Update_By_Example_Where_Clause"));
		whereExamplePresent.addElement(include);
		update.addElement(whereExamplePresent);

		document.getRootElement().addElement(update);
		return true;
	}
}
