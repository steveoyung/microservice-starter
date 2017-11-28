# 使用方法
- # zuul-db-starter
---
1.  pom.xml引用
```

   		<dependency>  
  		  <groupId>com.yonyou.cloud</groupId>
  		  <artifactId>microservice-zuul-db-starter</artifactId>
  		  <version>0.0.1</version>
		</dependency>
```
2.  启动类注解

```

@EnableZuulProxyStore
@ComponentScan(basePackages = {"com.yonyou.cloud"})
```
3.  application.properties

```
zuul
  store:
    mysql:
      enabled: true
  db:
    name: test
    url: jdbc:mysql://10.180.8.205:3306/ag_admin?useUnicode=true&characterEncoding=UTF8
    username: root
    password: rcs
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    basepackage: com.yonyou.cloud.zuul.db.mapper
    xmlLocation: classpath:mapper/**/*.xml
```


- # groovy-filter-starter
---
1.  pom.xml引用
```
		<dependency>
			<groupId>com.yonyou.cloud</groupId>
			<artifactId>microservice-filter-starter</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```
2.  启动类注解

```
@EnableGroovyFilter
```
3.  application.properties

```
groovy:
  filter:
    path: D:\\Del\\groovy\\
    interval: 600
  db:
    name: test
    url: jdbc:mysql://10.180.8.205:3306/ag_admin?useUnicode=true&characterEncoding=UTF8
    username: root
    password: rcs
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    basepackage: com.yonyou.microservice.filter.mapper
    xmlLocation: classpath:mapper/**/*.xml
```