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
public class SearchService {
    
    private final DataSource dataSource;
    private final UserService userService;

    @Autowired
    public SearchService(DataSource dataSource, UserService userService) {
        this.dataSource = dataSource;
        this.userService = userService;
    }

    public List<Recipe> searchRecipes(
        boolean favoritesOnly,
        boolean followingOnly,
        Integer lessThanTime,
        Float overRating,
        String mealType,
        String cuisineType,
        String orderRecipe,
        Integer limitResults,
        String user_id) throws SQLException {

        List<Recipe> recipes = new ArrayList<>();

        if(orderRecipe.compareTo("Highest Rated") == 0) {
            orderRecipe = "rating DESC";
        } else if(orderRecipe.compareTo("Lowest Rated") == 0) {
            orderRecipe = "rating ASC";
        } else if(orderRecipe.compareTo("Most Viewed") == 0) {
            orderRecipe = "view_count DESC";
        } else if(orderRecipe.compareTo("Least Viewed") == 0) {
            orderRecipe = "view_count ASC";
        } else if(orderRecipe.compareTo("Most Recent") == 0) {
            orderRecipe = "rec_id DESC";
        } else if(orderRecipe.compareTo("Least Recent") == 0) {
            orderRecipe = "rec_id ASC";
        }

        final String sql = "select * from recipe where user_id = ?;";
        final String sql = "SELECT r.rec_id, r.title, AVG(rt.rating) AS avg_rating FROM recipes 
        r JOIN rating rt ON r.rec_id = rt.rec_id GROUP BY r.rec_id, r.title ORDER BY avg_rating 
        DESC LIMIT 5;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if(favoritesOnly){
                        if(this.favoriteRec())
                    }
                    Recipe recipe = new Recipe();
                    recipe.setId(rs.getString("rec_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setDirections(rs.getString("directions"));
                    recipe.setEstimTime(rs.getInt("estim_time"));
                    recipe.setImagePath(rs.getString("image_path"));
                    recipe.setMealType(rs.getString("meal_type"));
                    recipe.setCuisineType(rs.getString("cuisine_type"));
                    recipe.setViewCount(rs.getInt("view_count"));
                    recipes.add(recipe);
                }
            }
        }


        return recipes;
    }
    
    public List<User> searchChefs(
        boolean favoritesOnly,
        boolean followingOnly,
        Integer lessThanTime,
        Float overRating,
        String mealType,
        String cuisineType,
        String orderChef,
        Integer limitResults,
        String user_id) throws SQLException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE username LIKE ? OR email LIKE ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                // Set other properties as needed
                users.add(user);
            }
        }

        return users;
    }
}
