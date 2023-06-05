package cn.wx.biz.test.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.yilian.woketech.allison1875.config.EnumConfig;
import com.yilian.woketech.allison1875.config.WoketechConfigConstant;
import com.yilian.woketech.allison1875.module.WoketechPersistenceGeneratorModule;

/**
 * 持久层生成工具
 *
 * @author weixin 2021/3/25 4:41 下午
 */
public class PersistenceGeneratorBoot {

    public static void main(String[] args) {
        PersistenceGeneratorConfig config = WoketechConfigConstant.persistenceGeneratorConfig;
        config.setJdbcUrl("jdbc:mysql://localhost:3306");
        config.setUserName("root");
        config.setPassword("12345678");
        config.setAuthor("weixin");
        config.setSchema("majiang");
        config.setTables(Lists.newArrayList("card","user"));
        config.setMapperXmlDirectoryPath("src/main/resources/mapper");
        config.setMapperPackage("cn.wx.elephant.biz.mapper");
        config.setEntityPackage("cn.wx.elephant.biz.bean.entity");
        config.setSuperEntityQualifier("cn.wx.elephant.core.bean.EntityAncestor");
        config.setEnableGenerateDesign(false);
        config.setDesignPackage("cn.wx.elephant.biz.bean.querydesign");
        config.setDisableBatchInsert(false);
        config.setDisableBatchUpdate(false);
        config.setDisableInsertOrUpdate(false);
//        config.setDeletedSql("valid_flag = false");
//        config.setNotDeletedSql("valid_flag = true");
        EnumConfig enumConfig = new EnumConfig();
        enumConfig.setEnumPackage("cn.wx.elephant.biz.bean.enums");

        Allison1875.allison1875(PersistenceGeneratorBoot.class, new WoketechPersistenceGeneratorModule(config,enumConfig));
    }


}