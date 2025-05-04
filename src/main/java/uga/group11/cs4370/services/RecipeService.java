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

import uga.group11.cs4370.models.ExpandedRecipe;
import uga.group11.cs4370.models.Recipe;
import uga.group11.cs4370.models.User;

@Service
@SessionScope
public class RecipeService {

    private final DataSource dataSource;
    private final UserService userService;

    @Autowired
    public RecipeService(DataSource dataSource, UserService userService) {
        this.dataSource = dataSource;
        this.userService = userService;
    }

    public String getRating(String rec_id) throws SQLException {
        final String sql = "select round(avg(rating), 1) as average_rating from ratings where rec_id = ?;";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rec_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    float averageRating = rs.getFloat("average_rating");
                    return String.format("%.1f", averageRating);
                }

            } catch (SQLException e) {
                return "No Rating";
            }
        }
        return null;
    }

    public List<Recipe> getRecipe(String rec_id) throws SQLException {

        List<Recipe> recipes = new ArrayList<>();

       final String sql1 = "UPDATE recipe SET view_count = view_count + 1 WHERE rec_id = ?;";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            pstmt.setString(1, rec_id);

            int rowsAffected = pstmt.executeUpdate();

            // Optional: check if update actually occurred
            if (rowsAffected > 0) {
                System.out.println("View count updated successfully.");
            } else {
                System.out.println("No rows updated. Check rec_id.");
            }
        }


        final String sql = "select * from recipe where rec_id = ?";

        String rating = this.getRating(rec_id);

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rec_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String directions = rs.getString("directions");
                    String image_path = rs.getString("image_path");
                    int estim_time = rs.getInt("estim_time");
                    String meal_type = rs.getString("meal_type");
                    String cuisine_type = rs.getString("cuisine_type");
                    int view_count = rs.getInt("view_count");


                    // Create a new Recipe object and return it
                     recipes.add( new Recipe(rec_id, title, directions, image_path, estim_time, rating,meal_type,cuisine_type,view_count,false));
                }
            }
        }
        return recipes;
    }

    public boolean rateRecipe(int count, String rec_id, User user) throws SQLException {
        final String sql = "insert into ratings (user_id, rec_id, rating) values (?, ?, ?);";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, rec_id);
            pstmt.setInt(3, count);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<ExpandedRecipe> getUserExpRecipes(String userId) throws SQLException {
        List<ExpandedRecipe> recipes = new ArrayList<>();
        final String sql = "select * from recipe where user_id = ?;";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String rec_id = rs.getString("rec_id");
                    String title = rs.getString("title");
                    String directions = rs.getString("directions");
                    String image = rs.getString("image");
                    int estim_time = rs.getInt("estim_time");
                    String rating = this.getRating(rec_id);
                    recipes.add(new ExpandedRecipe(rec_id, title, image, estim_time, rating, directions));
                }
            }
        }

        return recipes;
    }

    public List<Recipe> getUserRecipes(String userId) throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        final String sql = "select * from recipe where user_id = ?;";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String rec_id = rs.getString("rec_id");
                    String title = rs.getString("title");
                    String directions = rs.getString("directions");
                    int estim_time = rs.getInt("estim_time");
                    String rating = this.getRating(rec_id);
                    String meal_type = rs.getString("meal_type");
                    String cuisine_type = rs.getString("cuisine_type");
                    String image_path = rs.getString("image_path");
                    int view_count = rs.getInt("view_count");

                    
                    recipes.add(new Recipe(rec_id, title, directions, image_path, estim_time, rating,meal_type,cuisine_type,view_count,false));
                }
            }
        }

        return recipes;
    }
}
