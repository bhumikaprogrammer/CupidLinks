package com.cupidlinks.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// this listener runs when the app starts up and shuts down
// we use it to store a site-wide visit counter in the application scope
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        // store visit counter in application scope - shared across all users and sessions
        context.setAttribute("visitCount", 0);
        System.out.println("CupidLinks application started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("CupidLinks application stopped.");
    }
}