package com.quality.gen;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import javax.xml.stream.events.StartDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    //更新阿里云的数据库密码
    private static String URL="jdbc:mysql://39.105.201.114:3306/quality?characterEncoding=utf8&useSSL=true";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //String projectPath = "D:\\workspace\\idea_workspace\\qc_workspace\\QualityCheck\\quality";
        String projectPath = "D:\\IDEA\\QualityCheck\\quality";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("yerui");
        //允许重写
        gc.setFileOverride(true);
        //不弹出新窗口打开输出目录
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        //数据库密码，阿里云数据库密码
        dsc.setPassword("7731ea5491bc");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("请输入模块"));
        pc.setParent("com.quality");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //设置表名的下划下和驼峰标识
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //设置字段的下划线和驼峰标识
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //设置父类的实体
        // strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        //设置lombok模式
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(false);
        strategy.setSuperEntityClass("com.quality.domain.BaseEntity");
        strategy.setSuperControllerClass("com.quality.controller.BaseController");
        //设置表名
        strategy.setInclude(scanner("请输入表名，多个用逗号分开"));
        //自定义基础的entity类的字段，如id
        strategy.setSuperEntityColumns("id","crtTime","updTime");
        //驼峰转链接符
        strategy.setControllerMappingHyphenStyle(true);
        //设置字段的注解
        strategy.entityTableFieldAnnotationEnable(true);
       /* strategy.setTablePrefix(pc.getModuleName() + "_");*/
        mpg.setStrategy(strategy);
        //设置成vm引擎
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        //设置自定义的模板引擎
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/template/controller.java.vm");
        mpg.setTemplate(tc);
        mpg.execute();
    }

}
