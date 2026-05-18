package com.cupidlinks.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application listener that runs when CupidLinks starts and stops.
 * It initializes shared application-scope values such as the site visit counter.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    /**
     * Initializes application-wide attributes when the web application starts.
     *
     * @param event servlet context event provided by the container
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        // store visit counter in application scope - shared across all users and sessions
        context.setAttribute("visitCount", 0);
        System.out.println("CupidLinks application started.");
    }

    /**
     * Runs cleanup or shutdown logging when the web application stops.
     *
     * @param event servlet context event provided by the container
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("CupidLinks application stopped.");
    }
}
