package com.yksoul.plugins;

import com.yksoul.exceptions.MapperException;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

/**
 * @author yk
 * @version 1.0 Date: 2018-01-26
 */
public class TkMapperPlugin extends FalseMethodPlugin {

    private Set<String> mappers;

    /**
     * 接收参数
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String m = this.properties.getProperty("mappers");
        if (!StringUtility.stringHasValue(m)) {
            throw new MapperException("MappersPlugin插件缺少必要的mappers属性!");
        } else {
            this.mappers = new HashSet<>(Arrays.asList(m.split(",")));
        }
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        this.mappers.forEach(m -> {
            interfaze.addImportedType(new FullyQualifiedJavaType(m));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(m + "<" + entityType.getShortName() + ">"));
        });
        interfaze.addImportedType(entityType);
        interfaze.addAnnotation("@org.apache.ibatis.annotations.Mapper");
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("javax.persistence.*");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        topLevelClass.addAnnotation("@Table(name = \"`" + tableName + "`\")");
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
        boolean res = columns.stream().anyMatch(c -> c == introspectedColumn);
        if (res) {
            field.addAnnotation("@Id");
            field.addAnnotation("@GeneratedValue(generator = \"JDBC\" )");
        } else {
            field.addAnnotation("@Column(name = \"`" + introspectedColumn.getActualColumnName() + "`\")");
        }
        return true;
    }
}
