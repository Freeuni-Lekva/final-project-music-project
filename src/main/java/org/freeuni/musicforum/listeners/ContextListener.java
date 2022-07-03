package org.freeuni.musicforum.listeners;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.dao.DataSource;
import org.freeuni.musicforum.dao.InMemoryAlbumDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AlbumDAO albumDao = new InMemoryAlbumDAO(); //change to sql
        sce.getServletContext().setAttribute("albumDAO", albumDao);

        sce.getServletContext().setAttribute("base64SrcPrefix", "data:image/*;base64,");
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
