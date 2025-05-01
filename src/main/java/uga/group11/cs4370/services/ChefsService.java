package uga.group11.cs4370.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Blob;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import uga.group11.cs4370.models.Recipe;
import uga.group11.cs4370.models.User;

@Service
@SessionScope
public class ChefsService {
    private final DataSource dataSource;

    @Autowired
    public ChefsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createRecipe(String title, String directions, int estim_time, String imgPath) throws SQLException {
        final String postSql = "insert into recipe (title,directions,estim_time,imgPath) values (?,?,?,?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement sqlStmt = conn.prepareStatement(postSql)) {
            sqlStmt.setString(1, title);
            sqlStmt.setString(2, directions);
            sqlStmt.setInt(3, estim_time);
            sqlStmt.setString(4, imgPath);

            int rowsAffected = sqlStmt.executeUpdate();
            return rowsAffected > 0;
        }

    }

    public List<Recipe> getCreatedRecipes() throws SQLException {
        final String sql = "select * from recipe";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String directions = rs.getString("directions");
                    String title = rs.getString("title");
                    String estim_time = rs.getString("estim_time");
                    String imgPath = rs.getString("imgPath");

                    System.out.println(imgPath);

                    recipes.add(new Recipe(null, title, directions, imgPath, estim_time, null));
                }
            }

        }

        return recipes;

    }

    public List<User> getUsers() throws SQLException {
        final String sql = "select * from user";
        List<User> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String user_id = rs.getString("user_id");
                    String username = rs.getString("username");
                    String profileImagePath = rs.getString("profile_picture");

                    users.add(new User(user_id, username, profileImagePath));
                }
            }

        }

        return users;

    }

}
