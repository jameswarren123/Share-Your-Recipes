package uga.group11.cs4370.models;

public class Recipe {
    private final String recipe_id;
    // private final String chef_id;
    private final String title;
    private final String directions;
    private final String imagePath;
    private final int estim_time;
    private final String rating;

    public Recipe(String recipe_id, String title, String directions, String imagePath, int estim_time, String rating) {
        this.recipe_id = recipe_id;
        // this.chef_id = chef_id;
        this.title = title;
        this.directions = directions;
        this.imagePath = imagePath;
        this.estim_time = estim_time;
        this.rating = rating;
    }

    public String getRecipeId() {
        return recipe_id;
    }

    /*
     * public String getChefId() {
     * return chef_id;
     * }
     */
    public String getTitle() {
        return title;
    }

    
    public String getDirections() {
       return directions;
    }
     
    public String getImagePath() {
        return imagePath;
    }

    public int getEstimTime() {
        return estim_time;
    }

    public String getRating() {
        return rating;
    }
}
