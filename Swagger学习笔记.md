##### API接口：

前端渲染、后端分离的状态。

前后端分离：

Vue + SpringBoot

1. 后端时代：前段志勇管理静态页面；HTML==>后端。模板引擎JSP==>后端是主力

前后端分离时代：

- 后端：后端控制层，服务层，数据访问层【后端团队】
- 前端：前段控制层，视图层【前端团队】
  - 伪造后端数据，json。已经存在了，不需要后端，前端工程已久能够跑起来
- 前后端如何交互？===>API
- 前后端相对独立，松耦合
- 前后端甚至可以部署在不同的服务器上



产生一个问题:

- 前后端集成联调，前端人员和后端人员无法做到，及时协商，尽早解决，最终导致问题集中爆发；

解决方案：

- 首先制定schema【计划的提纲】，实时更新最新API，降低集成的风险
- 早些年：制定word计划文档；

前端后端文艺联系变成了API接口。API文档变成了前后端开发人员联系的纽带，愈来愈重要。

#### Swagger：

按照他的规范去定义借口及几口相关信息->通过其衍生出来的一些列项目和工具，

1. 生成各种格式的接口文档，
2. 多种语言的客户端和服务器端代码，
3. 在线接口调试页面等



1. 编写一个hello工程

```java
package com.example.practice1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
```



2. 配置Swagger===>Config

```java
@Configuration
@EnableSwagger2	//开启Swagger2
public class SwaggerConfig{
	//配置了Swagger的Docket的bean示例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
        	.select()
            //RequestHandlerSelectors, 配置要扫描接口的方式
            //basePackage：制定要扫描的包
            //any():扫描全部
            //none():不扫描
            //withClassAnnotation:扫描类上的注解
            //withMethodAnnotation:扫描方法上的注解
            .apis(requestHandlerSelectors.withMethodAnnotation(GetMapping.class))
            .build();
    }
    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("秦江","https://blog.kuangstudy.com/", "24736743@qq.com");
        return new ApiInfo(
            			"狂神的SwaggerAPI文档",
                          "及时再小的帆船也能远航",
                          "v1.0",
                          "https://blog.kuangstudy.com/",
                          DEFAULT CONTACT,
                          "Apache 2.0",
                          "http://www.apache.org/licenses/LICENSE-2.0",
                          new ArrayList());
    }
    
}
```

3. 测试运行

![QQ截图20210715093721](C:\Users\yoyo_lee\Desktop\图片\QQ截图20210715093721.png)

配置是否启用Swagger

```java
	//配置了Swagger的Docket的bean示例
	@Bean
	public Docket docket(Environment environment){
        
        //设置要现实的Swagger环境
        Proifiles profiles = Profiles.of("dev","test");
        //通过environment来返回是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);
        System.out.println(flag);
        
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .enable(false)//enable是否启动Swagger，如果是False，则Swagger不能在浏览器中访问
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.kuang.swagger.controller"))
            //.paths(PathSelectors.ant("/kuang/**"))
            .build();
    }
```

我只希望我的swagger在生产环境中使用，在发布的时候不使用？

- 判断是不是生产环境 flag = false
- 注入enable(flag)

application-dev.properties

```
spring.profiles.active-dev
```

application-pro.properties

```
server.port=8081
```



#### 配置Swagger

Swagger的bean示例Docket：







特性：

swagger：

功能丰富：多种注解，自动生成接口文档界面，支持在界面测试API接口功能

及时更新：

整合简单：添加pom依赖和简单配置，内嵌于应用中就可以发布API接口文档界面，不需要独立部署独立服务。

1、添加pom依赖

```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.9.2</version>
</dependency>
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.9.2</version>
</dependency>
```

2、将swagger-ui中的界面配置至spring-boot环境

3、配置API文档页基本信息

```java
package com.example.swagger.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com"))//扫描com路径下的api文档
            .paths(PathSelectors.any())//路径判断
            .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
            .title("Swagger开发规范")//标题
            .description("Swagger开发描述")
            .termsOfServiceUrl("http://baidu.com")//(不可见)条款地址
            .version("1.0.0")//版本号
            .build();
    }
    
}
```

4、API文档编写示例

```java
package com.example.swagger.controller;
import com.example.swagger.bean.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@Api(value = "Demo2-API", description="测试Demo2")
@RestController
@RequestMapp("/demo2")
public class Demo2Controller{
    static Map<String, User> users = Collections.synchronizedMap(new HashMap<String,User>());
    //1.post
    @ApiOperation(value="创建对象",notes="根据User对象创建用户")
    @RequestMapping(value="",method=RequestMethod.POST)
    public String postUser(User user, @RequestParam(defaultValue="false") boolean flag,String version){
        System.out.println("添加用户"+user.toString());
        return "ok";
    }
    //2.get
    @ApiOperation(value="获取列表",notes="返回List<?>类型信息的JSON")
    @RequestMapping(value={""},method=RequestMethod.GET)
    public List<User> getUserList(){
        List<User> users = new ArrayList<User>();
        for(int q=1;q<10;q++){
            User user = new User();
            user.setBaseId(q);
            users.add(user);
        }
        return users;
    }
}
```



#### 新的开发模式：

更新Swagger描述文件->

1. 自动生成接口文档和客户端服务器端代码
2. 调用端、服务器端代码以及接口文档的一致性



#### Springfox：

1. Spring（java服务端框架）将Sagger规范纳入自身标准->Spring-swagger->Springfox
2. 引入Springfox，扫描相关代码，生成该描述文件，进一步生成与代码一致的接口文档和客户端代码。



#### Swagger Codegen：

1. 将描述文件生成html格式和cwiki形式的接口文档
2. 同时生成多种语言的服务端和酷虎端的代码
3. 支持通过jar包，docker，node等方式在**本地化执行生**成
4. Swagger Editor中**在线生成**



#### Swagger UI：

1. 可视化的UI界面展示描述文件
2. 接口的调用方、测试方、项目经理等都在以该页面中队相关接口进行查阅和做一些简单的接口请求。
3. 支持在线导入描述文件和本地部署UI项目



#### Swagger Editor：

1. 类似于markendown编辑器的编辑Swagger描述文件的编辑器
2. 支持实时预览描述文件的更新效果
3. 提供在线和本地不熟两种方式



#### Swagger Inspector：

1. 和postman差不多，对接口进行测试的在线版的postman
2. 比在Swagger UI里面做接口请求返回更多的信息
3. 保存请求的实际请求参数等数据



#### Swagger  Hub：

1. 集成上面所有项目各个功能
2. 项目和版本为单位，将描述文件上传到Swagger Hub中
3. 在Swagger Hub中可以完成上面项目的所有工作，需要注册账号，分为免费版和付费版



#### Springfox Swagger：

1. Spring基于Swagger规范，可以将基于SpringMVC和Spring Boot项目的项目代码，自动生成JSON格式的描述文件。
2. 本身不是属于Swagger官网提供的。



#### Spring框架的Swagger流程应用：

结合现有的工具和功能，设计一个流程（保证一个项目从开始开发到后面持续迭代的时候，以最小的代价维护代码、接口文档以及Swagger描述文件）

##### 项目开始阶段：

接口文档都是服务端来编写的，服务端开发可以视情况决定直接编写服务端调用代码，还是Swagger描述文件。

1. 项目启动阶段，已经打好了后台框架，可以直接编写服务端被调用层的代码（controller及其入参出参对象）->Springfox-swagger生成swagger json描述文件
2. 羡慕启动阶段没有相关后台框架，而前端对接口文档追的紧，建议先编写swagger描述文件，通过该描述文件申城接口代码。后续后台框架打好了，也可以生成相关的服务端代码。



##### 项目迭代阶段：

后续后台人员，无需关注Swagger描述文件和接口文档，有需求变更导致接口变化，直接写代码就好了。

拔掉用蹭的代码做个修改，然后生成新的描述文件和接口文档后，给到前段即可。



##### 流程：

![img](https://upload-images.jianshu.io/upload_images/813533-1879ce94558a8553.png?imageMogr2/auto-orient/strip|imageView2/2/format/webp)

![img](https://upload-images.jianshu.io/upload_images/813533-ffc262914e89f87d.png?imageMogr2/auto-orient/strip|imageView2/2/format/webp)

#### 给Mock系统的正常请求及相应全流程数据

1. 适当的在代码中加入Swagger的注解，可以让接口文档描述信息更加详细
2. 每个出入参数的是示例值都配上，前段就可以直接在接口文档中拿到模拟数据

```java
#####Controller代码
@Override
    @ApiOperation(value = "post请求调用示例", notes = "invokePost说明", httpMethod = "POST")
    public FFResponseModel<DemoOutputDto> invokePost(@ApiParam(name="传入对象",value="传入json格式",required=true) @RequestBody @Valid DemoDto input) {
        log.info("/testPost is called. input=" + input.toString());
        return new FFResponseModel(Errcode.SUCCESS_CODE, Errcode.SUCCESS_MSG);
    }
```

```java
#####接口请求入参对象   
@Data
@ApiModel(value="演示类",description="请求参数类" )
public class DemoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "defaultStr",example="mockStrValue")
    private String strDemo;

    @NotNull
    @ApiModelProperty(example="1234343523",required = true)
    private Long longNum;

    @NotNull
    @ApiModelProperty(example="111111.111")
    private Double doubleNum;

    @NotNull
    @ApiModelProperty(example="2018-12-04T13:46:56.711Z")
    private Date date;
    
}
```

```java
#####接口请求出参公共类
@ApiModel(value="基础返回类",description="基础返回类")
public class FFResponseModel<T> implements Serializable {

    private static final long serialVersionUID = -2215304260629038881L;
    // 状态码
    @ApiModelProperty(example="成功")
    private String code;
    // 业务提示语
    @ApiModelProperty(example="000000")
    private String msg;
    // 数据对象
    private T data;

...
}
```

```java
#####接口请求出参实际数据对象
@Data
public class DemoOutputDto {

    private String res;

    @NotNull
    @ApiModelProperty(value = "defaultOutputStr",example="mockOutputStrValue")
    private String outputStrDemo;

    @NotNull
    @ApiModelProperty(example="6666666",required = true)
    private Long outputLongNum;

    @NotNull
    @ApiModelProperty(example="88888.888")
    private Double outputDoubleNum;

    @NotNull
    @ApiModelProperty(example="2018-12-12T11:11:11.111Z")
    private Date outputDate;
    
}
```

#### 总结：

使用Swagger，把相关信息存储在定义的描述文件里（yml或json格式）

在通过维护这个描述文件去更新接口文档，以及生成个端代码

Springfox-swagger，可以通过扫描代码去生成这个文件，连描述文件都不需要再去维护

所有的讯息都存在代码里面了（代码及接口文档，接口文档及代码）

```

```

