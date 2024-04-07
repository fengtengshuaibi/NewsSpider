package com.intelligent.marking.intelligentmarking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(value = "com.intelligent.marking.intelligentmarking")
@MapperScan(value = {"com.intelligent.marking.intelligentmarking.mapper","com.intelligent.marking.intelligentmarking.dao"})
public class IntelligentMarkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligentMarkingApplication.class, args);
		System.out.println("启动完成...");
	}

}
