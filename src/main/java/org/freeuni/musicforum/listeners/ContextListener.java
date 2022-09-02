package org.freeuni.musicforum.listeners;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.dao.DataSource;
import org.freeuni.musicforum.dao.InMemoryAlbumDAO;
import org.freeuni.musicforum.service.AlbumService;
import org.freeuni.musicforum.service.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AlbumService albumService = ServiceFactory.getAlbumService();
        sce.getServletContext().setAttribute("albumService", albumService);
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
