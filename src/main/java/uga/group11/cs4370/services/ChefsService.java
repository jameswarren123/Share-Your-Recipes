package uga.group11.cs4370.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import uga.group11.cs4370.models.Chef;
import uga.group11.cs4370.models.Recipe;
import uga.group11.cs4370.models.User;

@Service
@SessionScope
public class ChefsService {
    private final DataSource dataSource;
    private final UserService userService;
    private final RecipeService recipeService;

    @Autowired
    public ChefsService(DataSource dataSource, UserService userService, RecipeService recipeService) {
        this.dataSource = dataSource;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    public boolean createRecipe(String userId, String title, String directions, int estim_time, String meal_type, String cuisine_type) throws SQLException {
        final String postSql = "insert into recipe (user_id,title,directions,estim_time,meal_type,cuisine_type,view_count) values (?,?,?,?,?,?,?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement sqlStmt = conn.prepareStatement(postSql)) {
            sqlStmt.setString(1, userId);
            sqlStmt.setString(2, title);
            sqlStmt.setString(3, directions);
            sqlStmt.setInt(4, estim_time);
            sqlStmt.setString(5, meal_type);
            sqlStmt.setString(6, cuisine_type);
            sqlStmt.setInt(7, 0); // view_count is set to 0 by default
            System.out.println("SQL Statement: " + sqlStmt.toString());
            int rowsAffected = sqlStmt.executeUpdate();
            return rowsAffected > 0;
        }

    }

    public List<Recipe> getCreatedRecipes(String userId) throws SQLException {
        final String sql = "select * from recipe where user_Id = ?";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String directions = rs.getString("directions");
                    String title = rs.getString("title");
                    int estim_time = rs.getInt("estim_time");
                    String rec_id = rs.getString("rec_id");
                    String meal_type = rs.getString("meal_type");
                    String cuisine_type = rs.getString("cuisine_type");
                    String image = rs.getString("image");
                    recipes.add(new Recipe(rec_id, title, directions, null, estim_time, "-1"));
                }
            }

        }

        return recipes;

    }

    public List<User> getUsers() throws SQLException {
        final String sql = "select * from user u, image i where u.image_id = i.image_id";
        List<User> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String user_id = rs.getString("user_id");
                    String username = rs.getString("username");
                    int image_id = rs.getInt("image_id");
                    String image_path = rs.getString("image_path");

                    users.add(new User(user_id, username, image_id, image_path));
                }
            }

        }

        return users;
    }

    public List<Chef> getChefs() throws SQLException {
        final String sql = "select * from user u, image i where u.image_id = i.image_id";
        List<Chef> chefs = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String user_id = rs.getString("user_id");
                    String username = rs.getString("username");
                    int image_id = rs.getInt("image_id");
                    String image_path = rs.getString("image_path");
                    List<Recipe> recipes = recipeService.getUserRecipes(user_id);
                    chefs.add(new Chef(user_id, username, image_id, image_path, recipes));
                }
            }

        }

        return chefs;
    }
}
