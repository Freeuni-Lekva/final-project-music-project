package org.freeuni.musicforum.listeners;

import org.freeuni.musicforum.Activity.Activity;
import org.freeuni.musicforum.dao.DataSource;
import org.freeuni.musicforum.fileProcessor.FileProcessor;
import org.freeuni.musicforum.service.ServiceFactory;

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
        context.setAttribute("albumService", ServiceFactory.getAlbumService());
        context.setAttribute("reviewService", ServiceFactory.getReviewService());
        context.setAttribute("userService", ServiceFactory.getUserService());
        context.setAttribute("imagePrefix", FileProcessor.IMAGE_HTML_PREFIX_BASE64);
        context.setAttribute("audioPrefix", FileProcessor.AUDIO_HTML_PREFIX_BASE64);
        context.setAttribute("activity", Activity.getActivityLog());
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
