package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.*;
import org.freeuni.musicforum.filter.Filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLUserDAO implements UserDAO {

    private final Connection con;

    public SQLUserDAO() {
        con = DataSource.INSTANCE.getConnection();
    }

    @Override
    public void add(User user) {

        try{
            PreparedStatement addStatement = con.prepareStatement("INSERT INTO users"+
                    "(username, password, first_name, last_name, status, badge, gender, profile_picture, birth_date) "+
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            addStatement.setString(1, user.getUsername());
            addStatement.setString(2, user.getPassword().hashed());
            addStatement.setString(3, user.getFirstName());
            addStatement.setString(4, user.getLastName());
            addStatement.setInt(5, 0);
            addStatement.setInt(6, user.getBadge().getBadgeIntValue());
            addStatement.setInt(7, user.getGenderIntValue());
            addStatement.setString(8, user.getProfileImageBase64());
            addStatement.setDate(9, new java.sql.Date(user.getBirthDate().getTime()));
            addStatement.setInt(10, user.getPassword().getPasswordSize());
            addStatement.executeQuery();
            addStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<User> getByUsername(String username) {
        try{
            PreparedStatement getStatement = con.prepareStatement("SELECT username FROM users WHERE username = ?;");
            getStatement.setString(1, username);
            ResultSet resultSet = getStatement.executeQuery();
            if(!resultSet.next()){
                Optional<User> userOptional = Optional.of(makeUser(resultSet));
                resultSet.close();
                getStatement.close();
                return userOptional;
            }
            resultSet.close();
            getStatement.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean doesExist(String username) { return !getByUsername(username).isEmpty();
    }

    @Override
    public boolean correctCredentials(String username, String passwordHash) {
        try{
            PreparedStatement getStatement = con.prepareStatement("SELECT* from users " +
                    "WHERE username=? AND password=? AND status=?;");
            getStatement.setString(1, username);
            getStatement.setString(2, passwordHash);
            getStatement.setInt(3, 1);
            ResultSet rs = getStatement.executeQuery();
            if(!rs.next()){
                return false;
            }
            getStatement.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void updateBadgeAccordingTo(String username, int prestige) {
        try {

            PreparedStatement getStatement = con.prepareStatement("SELECT b.badge FROM users u " +
                    "JOIN badges b on u.badge = b.id " +
                    "WHERE username = ?;");
            getStatement.setString(1, username);
            ResultSet rs = getStatement.executeQuery();

            if(!rs.next()) {
                Badge b = new Badge(Badge.BadgeEnum.valueOf(rs.getString(1)));
                b.modifyAccordingTo(prestige);

                PreparedStatement updateStatement = con.prepareStatement("UPDATE users SET badge = ?;");
                updateStatement.setInt(1,b.getBadgeIntValue());
                updateStatement.executeUpdate();
                updateStatement.close();
            }
            rs.close();
            getStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String username) {
        try {
            PreparedStatement getStatement = con.prepareStatement("DELETE FROM users WHERE username = ?;");
            getStatement.setString(1, username);
            getStatement.executeUpdate();
            getStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void banUser(String username) {
        try{
            if(doesExist(username)){
                PreparedStatement updateStatement = con.prepareStatement("UPDATE users SET status = ? "+
                        "WHERE username = ?;");
                updateStatement.setInt(1, 0);
                updateStatement.setString(2, username);
                updateStatement.executeUpdate();
                updateStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getUsersByFriendshipStatus(String username, FriendshipStatus fs) {
        List<String> usernames = new ArrayList<>();

        try{
            PreparedStatement getStatement = con.prepareStatement("SELECT fd.second_username FROM friendship_data fd "+
                    "JOIN statuses s on fd.friendship_status = s.id "+
                    "WHERE fd.first_username = ? AND s.status = ?");
            getStatement.setString(1, username);
            getStatement.setString(2, fs.toString());
            ResultSet rs = getStatement.executeQuery();
            while(rs.next()){
                usernames.add(rs.getString(1));
            }
            getStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }
    @Override
    public boolean updateFriendshipStatus(String fromUsername, String toUsername, FriendshipStatus fs) {
        try{
            PreparedStatement updateStatement = con.prepareStatement("UPDATE friendship_data SET friendship_status = ? "+
                    "WHERE first_username = ? AND second_username = ?;");
            updateStatement.setString(1, fs.toString());
            updateStatement.setString(2, fromUsername);
            updateStatement.setString(3, toUsername);
            int num = updateStatement.executeUpdate();
            updateStatement.close();
            return num!=0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteFriendshipStatus(String fromUsername, String toUsername) {
        try{
            PreparedStatement updateStatement = con.prepareStatement("DELETE  FROM friendship_data " +
                    "WHERE first_username = ? AND second_username = ?;");
            updateStatement.setString(1, fromUsername);
            updateStatement.setString(2, toUsername);
            int num = updateStatement.executeUpdate();
            updateStatement.close();
            return num!=0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public FriendshipStatus getFriendshipStatus(String fromUsername, String toUsername) {
        try{
            PreparedStatement getStatement = con.prepareStatement("SELECT s.status FROM friendship_data fd JOIN statuses s ON " +
                    "fd.friendship_status = s.id " +
                    "WHERE first_username = ? AND second_username = ?;");
            getStatement.setString(1, fromUsername);
            getStatement.setString(2, toUsername);
            ResultSet rs = getStatement.executeQuery();

            if (rs.next()) {
                FriendshipStatus fs = FriendshipStatus.valueOf(rs.getString(1));
                rs.close();
                getStatement.close();
                return fs;
            }
            getStatement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getFiltered(Filter f){
        List<User> users = new ArrayList<>();

        try{
            PreparedStatement getStatement = con.prepareStatement("SELECT* FROM users;");
            ResultSet rs = getStatement.executeQuery();

            while(rs.next()){
                User curr = makeUser(rs);
                if(f.doFilter(new SearchRequest(curr))){
                    users.add(curr);
                }
            }
            getStatement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    private User makeUser(ResultSet rs){
        try{
            User user = new User(rs.getString(3), rs.getString(4), rs.getDate(9),
                    getGenderFromInt(rs.getInt(7)), rs.getString(1),
                    new Password(rs.getString(2), rs.getInt(10)),
                    getBadgeFromInt(rs.getInt(6)));

            return user;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setProfileImageBase64(String username, String base64String) {

        try{
            PreparedStatement updateStatement = con.prepareStatement("UPDATE users SET profile_picture = ? "+
                    "WHERE username = ?;");
            updateStatement.setString(1, base64String);
            updateStatement.setString(2, username);
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Gender getGenderFromInt(int number){
        try{
            PreparedStatement getStatement = con.prepareStatement("SELECT gender FROM genders WHERE id=?");
            getStatement.setInt(1, number);
            ResultSet rs = getStatement.executeQuery();

            if(!rs.next()){
                throw new SQLException("no matches found in gender table");
            }

            return Gender.valueOf(rs.getString(1));

        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Badge getBadgeFromInt(int number){
        try{
            PreparedStatement getStatement = con.prepareStatement("SELECT badge FROM badges WHERE id=?");
            getStatement.setInt(1, number);
            ResultSet rs = getStatement.executeQuery();

            if(!rs.next()){
                throw new SQLException("no matches found in badge table");
            }

            return new Badge(Badge.BadgeEnum.valueOf(rs.getString(1)));

        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
