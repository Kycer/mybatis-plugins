package com.yksoul.plugins;

import com.yksoul.exceptions.MapperException;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

/**
 * @author yk
 * @version 1.0 Date: 2018-01-25
 */
public class LombokPlugin extends PluginAdapter {

    private Set<String> annotations;
    private static final Integer MAX_ANNOTATION = 3;

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 生成实体类
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addAnnotation(topLevelClass);
        return true;
    }


    /**
     * 返回false,取消生成getter方法
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 返回false,取消生成setter方法
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 接收参数
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String annotation = this.properties.getProperty("annotations");
        if (!StringUtility.stringHasValue(annotation)) {
            throw new MapperException("LombokPlugin插件缺少必要的annotations属性!");
        } else {
            this.annotations = new HashSet<>(Arrays.asList(annotation.split(",")));
        }
    }

    /**
     * 添加注解
     *
     * @param topLevelClass
     */
    private void addAnnotation(TopLevelClass topLevelClass) {
        if (this.annotations.size() > MAX_ANNOTATION) {
            topLevelClass.addImportedType("lombok.*");
            this.annotations.forEach(s -> {
                topLevelClass.addAnnotation("@" + s);
            });
        } else {
            this.annotations.forEach(s -> {
                topLevelClass.addImportedType("lombok." + s);
                topLevelClass.addAnnotation("@" + s);
            });
        }
    }
}
