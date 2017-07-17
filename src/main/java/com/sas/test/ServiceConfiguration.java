package com.sas.test;
/*
 * copyright(c) 2017 SAS Institute, Cary NC 27513
 * Created on Mar 20, 2017
 *
 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ServiceConfiguration
{

    public static void main(String[] args)
    {
        SpringApplication.run(ServiceConfiguration.class, args);
    }

}
