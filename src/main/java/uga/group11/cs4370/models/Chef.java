package uga.group11.cs4370.models;

import java.util.List;

public class Chef extends User {
    /*
     * Chefs should extend the User class.
     * Chefs have a list of their created recipes.
     */

    private final List<Recipe> recipes;

    public Chef(String user_id, String username, int image_id, String image_path, List<Recipe> recipes) {
        super(user_id, username, image_id, image_path);
        this.recipes = recipes;
    }

    public Chef(String user_id, String username, int image_id, List<Recipe> recipes) {
        super(user_id, username, image_id);
        this.recipes = recipes;
    }

    public Chef(String user_id, String username, List<Recipe> recipes) {
        super(user_id, username);
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
