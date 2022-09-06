package org.freeuni.musicforum.listeners;

import org.freeuni.musicforum.dao.DataSource;
import org.freeuni.musicforum.file.processor.FileProcessor;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        context.setAttribute("audioPrefix", FileProcessor.AUDIO_HTML_PREFIX_BASE64);
        // add activity log for feed
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DataSource.INSTANCE.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
