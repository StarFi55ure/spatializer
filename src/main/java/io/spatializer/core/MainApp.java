package io.spatializer.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"io.spatializer.core.controllers"})
public class MainApp
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!!!" );

        SpringApplication.run(MainApp.class);
    }
}