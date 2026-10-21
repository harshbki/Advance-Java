package com.shopsphere.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String ACTIVE_SESSIONS = "activeSessions";
    @Override public void sessionCreated(HttpSessionEvent event) {
        AtomicInteger count = (AtomicInteger) event.getSession().getServletContext().getAttribute(ACTIVE_SESSIONS);
        if (count == null) {
            synchronized (event.getSession().getServletContext()) {
                count = (AtomicInteger) event.getSession().getServletContext().getAttribute(ACTIVE_SESSIONS);
                if (count == null) { count = new AtomicInteger(); event.getSession().getServletContext().setAttribute(ACTIVE_SESSIONS, count); }
            }
        }
        count.incrementAndGet();
    }
    @Override public void sessionDestroyed(HttpSessionEvent event) {
        AtomicInteger count = (AtomicInteger) event.getSession().getServletContext().getAttribute(ACTIVE_SESSIONS);
        if (count != null) count.updateAndGet(value -> Math.max(0, value - 1));
    }
}
