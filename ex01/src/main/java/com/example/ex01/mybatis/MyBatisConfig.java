package com.example.ex01.mybatis;

// ApplicationContext가 이 클래스를 "설정"으로 인식하도록 한다

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

// ORM으로서 MyBatis를 사용할 것이기에 그에 관련된 설정을 여기에 작성한다
@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {
    // ApplicationContext 객체를 직접 주입받아서 사용할 것임
    private final ApplicationContext applicationContext;

    // ConfigurationProperties 어노테이션의 prefix 뒤에 있는 설정들을 전부 가져와서
    // hikariConfig의 매개변수로 사용해라. 라는 뜻이다.
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Bean // 메소드 단위로 Bean을 개발자가 컨테이너에 직접 등록하기 위해 쓰는 어노테이션.
    // 보통 Configuration 클래스 내부에서 메서드 단위로 사용되는 어노테이션임.
    public HikariConfig hikariConfig(){ // HikariConfig 객체 생성
        // 메소드 안에서는 당연히 new를 사용하여 객체화해야 한다.
        return new HikariConfig();
    }

    // 위에서 만든 HikariConfig 객체를 이용해 DataSource 객체를 생성한다.
    @Bean
    public DataSource dataSource(){ // DataSource 객체 생성
        return new HikariDataSource(hikariConfig()); 
    }

    // 위에서 만든 DataSource 객체를 이용해 SqlSessionFactory 객체를 만든다
    // 여기서 외부 파일에 대한 설정을 해야 한다 (매퍼 파일, Config 파일 등)
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException{
        // SqlSessionFactoryBean 객체를 만든다.
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); // 팩토리 객체를 먼저 만들고,
        sqlSessionFactoryBean.setDataSource(dataSource()); // sqlSessionFactory의 DataSource를 설정해준다.

        // 이제 매퍼의 경로, Config의 경로에 대해 이 SqlSessionFactory 객체에게 알려줘야 한다.
        // /src/main/resources 까지가 일종의 ClassPath로 등록이 되어 있기 때문에, 이것을 이용하자
        // applicationContext.getResource() 메소드로 클래스패스 기준으로 리소스를 찾는다.
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:/mapper/*.xml")); // 매퍼 경로 지정 (주의: 갖고 올 파일이 여러개면 getResource가 아닌 getResources 메소드를 사용해야 한다)

        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/config.xml")); // Config 파일 경로 지정

        try {
            // 위에서 만든 SqlSessionFactoryBean 객체를 이용하여 SqlSessionFactory 객체 생성
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            // DBMS에서는 언더바(스네이크) 표기법으로 컬럼명을 작성하는데, 자바에서는 카멜 표기법으로 필드명을 작성하므로,
            // setMapUnderscoreToCamelCase 설정을 true로 함으로써 자동으로 매핑을 가능하게 한다.
            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);

            return sqlSessionFactory; // sqlSessionFactory 반환
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
}











