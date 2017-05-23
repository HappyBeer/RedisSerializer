#jmh 使用：
##安装：help-->Eclipse MarketPlace --> 搜索 m2e-apt 安装
````
window-->Preferences-->Maven-->Annotation Processing-->Automatically configure JDT APT
````
##依赖 
```` java 
<jmh.version>1.19</jmh.version>
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>${jmh.version}</version>
</dependency>
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-generator-annprocess</artifactId>
    <version>${jmh.version}</version>
    <scope>provided</scope>
</dependency>
````

http://www.importnew.com/12548.html



