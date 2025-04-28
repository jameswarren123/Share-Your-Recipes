package uga.group11.cs4370.models;

public class User {
    private final String user_id;
    private final String username;
    private final String profileImagePath;
    static final String DEFAULT_PROFILE_IMAGE_PATH = "https://example.com/default_profile_image.png";

    public User(String user_id, String username, String profileImagePath) {
        this.user_id = user_id;
        this.username = username;
        this.profileImagePath = profileImagePath;
    }

    public User(String user_id, String username) {
        this.user_id = user_id;
        this.username = username;
        this.profileImagePath = DEFAULT_PROFILE_IMAGE_PATH;
    }

    public String getUserId() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

}
