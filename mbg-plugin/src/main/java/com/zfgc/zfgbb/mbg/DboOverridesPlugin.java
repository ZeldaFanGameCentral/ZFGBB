package com.zfgc.zfgbb.mbg;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class DboOverridesPlugin extends PluginAdapter {

	private static final FullyQualifiedJavaType INTEGER = new FullyQualifiedJavaType("java.lang.Integer");
	private static final FullyQualifiedJavaType LOCAL_DATE_TIME = new FullyQualifiedJavaType("java.time.LocalDateTime");

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addMethod(buildPkIdOverride(introspectedTable));
		topLevelClass.addImportedType(LOCAL_DATE_TIME);
		topLevelClass.addMethod(buildTimestampOverride(introspectedTable, "created_ts", "getCreatedTime", "createdTs"));
		topLevelClass.addMethod(buildTimestampOverride(introspectedTable, "updated_ts", "getUpdatedTime", "updatedTs"));
		return true;
	}

	private Method buildPkIdOverride(IntrospectedTable table) {
		Method m = new Method("getPkId");
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setReturnType(INTEGER);
		m.addAnnotation("@Override");

		List<IntrospectedColumn> pks = table.getPrimaryKeyColumns();
		if (pks.isEmpty()) {
			m.addBodyLine("return null;");
		} else {
			IntrospectedColumn pk = pks.get(0);
			String pkJavaType = pk.getFullyQualifiedJavaType().getFullyQualifiedName();
			// Single-column Integer PK is the convention. Tables with a non-Integer
			// PK (e.g. system_config keyed by text) get null here -- they don't go
			// through AbstractDao.save anyway.
			if ("java.lang.Integer".equals(pkJavaType)) {
				m.addBodyLine("return " + pk.getJavaProperty() + ";");
			} else {
				m.addBodyLine("return null;");
			}
		}
		return m;
	}

	private Method buildTimestampOverride(IntrospectedTable table, String columnName, String methodName,
			String fieldName) {
		Method m = new Method(methodName);
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setReturnType(LOCAL_DATE_TIME);
		m.addAnnotation("@Override");

		boolean hasColumn = table.getAllColumns().stream()
				.anyMatch(c -> columnName.equalsIgnoreCase(c.getActualColumnName()));
		if (hasColumn) {
			m.addBodyLine("return " + fieldName + ";");
		} else {
			m.addBodyLine("return null;");
		}
		return m;
	}
}
