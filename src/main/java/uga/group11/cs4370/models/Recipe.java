package uga.group11.cs4370.models;

public class Recipe {
    private final String recipe_id;
    // private final String chef_id;
    private final String title;
    private final String directions;
    private final String image_path;
    private final int estim_time;
    private final String rating;
    private String meal_type;
    private String cuisine_type;
    private int view_count;
    private boolean is_favorite;

    public Recipe(String recipe_id, String title, String directions, String image_path, int estim_time, String rating, String meal_type, String cuisine_type, int view_count, boolean is_favorite) {
        this.recipe_id = recipe_id;
        // this.chef_id = chef_id;
        this.title = title;
        this.directions = directions;
        this.image_path = image_path;
        this.estim_time = estim_time;
        this.rating = rating;
        this.meal_type = meal_type;
        this.cuisine_type = cuisine_type;
        this.view_count = view_count;
        this.is_favorite = is_favorite;
    }

    public Recipe(String recipe_id, String title, String directions, String image_path, int estim_time, String rating) {

        this.recipe_id = recipe_id;
        // this.chef_id = chef_id;
        this.title = title;
        this.directions = directions;
        this.image_path = image_path;
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
        return image_path;
    }

    public int getEstimTime() {
        return estim_time;
    }

    public String getRating() {
        return rating;
    }

    public String getMealType() {
        return meal_type;
    }

    public String getCuisineType() {
        return cuisine_type;
    }

    public int getViewCount() {
        return view_count;
    }

    public boolean isFavorite() {
        return is_favorite;
    }
}
