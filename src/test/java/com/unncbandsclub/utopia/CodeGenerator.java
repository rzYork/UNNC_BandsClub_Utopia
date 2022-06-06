package com.unncbandsclub.utopia;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.unncbandsclub.utopia.vo.TokenVo;
import com.unncbandsclub.utopia.utlis.TokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 王岩
 * @version v 1.0
 * @date 2020/12/3
 */

public class CodeGenerator {
    @Test
    public void run() {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //有的电脑系统，如果代码发现生成有问题，projectPath直接写成项目名称
        //gc.setOutputDir("edu_eduservice" + "/src/main/java");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Ruizhe Huang");
        gc.setOpen(false); //生成后是否打开资源管理器

        gc.setFileOverride(false); //重新生成时文件是否覆盖
        //IUserServcie
        gc.setServiceName("%sService");    //去掉Service接口的首字母I
        gc.setIdType(IdType.AUTO); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式
        mpg.setGlobalConfig(gc);
        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/utopia");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("utopia");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("user"); //模块名
        pc.setParent("com.unncbandsclub.utopia");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);
        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("avatar");//表名称
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        mpg.setStrategy(strategy);
        // 6、执行
        mpg.execute();
    }

    @Test
    public void generateUuid(){
        while(true){
            System.out.println(UUID.randomUUID().toString());
        }
    }

    @Test
    public void testJWTUtils(){

        List<Integer> arrayList=new ArrayList<>();
        arrayList.add(2001);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setLoginTime(new Date().getTime());
        tokenVo.setUsername("username");
        tokenVo.setPassword("pwd123");
        tokenVo.setRoleList(arrayList);

        String token = TokenUtil.createToken(tokenVo);
        System.out.println(token);

        new Runnable() {

            int i=0;
            @Override
            public void run() {
                while(true) {
                    Claims key = TokenUtil.parseToken(token);
                    try {
                        Thread.sleep(5000);
                        i+=5;

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(key==null){
                        System.out.println("Invalid Token!");
                        break;
                    }
                    System.out.println(i + " second later: " + key.toString());

                }
            }
        }.run();;

    }
}
