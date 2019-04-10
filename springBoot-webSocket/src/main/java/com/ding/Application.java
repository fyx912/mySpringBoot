package com.ding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class Application implements ServletContextInitializer {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

    @Autowired
    ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","52428800");//50M
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","52428800");//50M
    }


}
