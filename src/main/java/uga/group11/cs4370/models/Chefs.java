package uga.group11.cs4370.models;

import java.util.List;

public class Chefs extends User {
    /*
     * Chefs should extend the User class.
     * Chefs have a list of their created recipes.
     */

    private final List<Recipe> recipes;

    public Chefs(String user_id, String username, String profileImagePath, List<Recipe> recipes) {
        super(user_id, username, profileImagePath);
        this.recipes = recipes;
    }

    public Chefs(String user_id, String username, List<Recipe> recipes) {
        super(user_id, username);
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
