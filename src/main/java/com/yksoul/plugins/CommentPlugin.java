package com.yksoul.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 * 生成java文件中文注释
 *
 * @author yk
 * @version 1.0 Date: 2018-01-26
 */
public class CommentPlugin extends PluginAdapter {

    private static String author = "yksoul";
    private static String version = "V1.0";

    private static final String UNDERLINE = "_";


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 实体类属性添加注释
     *
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        StringBuffer sb = new StringBuffer();
        sb.append("/**");
        sb.append("\n\t * ");
        sb.append(StringUtility.stringHasValue(introspectedColumn.getRemarks()) ? introspectedColumn.getRemarks() : field.getName());
        sb.append("\n\t */");
        field.addJavaDocLine(sb.toString());
        return true;
    }

    /**
     * 添加实体类注释
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine(addClassComment(getEntityName(introspectedTable) + "实体类"));
        return true;
    }

    /**
     * 添加接口注释
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addJavaDocLine(addClassComment(getEntityName(introspectedTable) + "Mapper接口"));
        return true;
    }


    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine(addClassComment("providerGenerated"));
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String a = this.properties.getProperty("author");
        String v = this.properties.getProperty("version");
        if (StringUtility.stringHasValue(a)) {
            author = a;
        }
        if (StringUtility.stringHasValue(v)) {
            version = v;
        }
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Properties properties = new Properties();
        properties.setProperty("suppressAllComments", "true");
        properties.setProperty("suppressDate", "true");
        commentGenerator.addConfigurationProperties(properties);
    }

    private String addClassComment(String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("/**\n * ");
        sb.append(content);
        sb.append("\n * \n * ");
        sb.append("@author " + author);
        sb.append("\n * ");
        sb.append("@date " + LocalDate.now());
        sb.append("\n * ");
        sb.append("@version " + version);
        sb.append("\n */");
        return sb.toString();
    }

    /**
     * 获取实体类名称
     *
     * @param introspectedTable
     * @return
     */
    private String getEntityName(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        return entityType.getShortName();
    }
}
