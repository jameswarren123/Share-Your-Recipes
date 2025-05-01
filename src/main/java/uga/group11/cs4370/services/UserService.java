package uga.group11.cs4370.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import uga.group11.cs4370.models.User;


@Service
@SessionScope
public class UserService {
    
    private final DataSource dataSource;
    private final BCryptPasswordEncoder passwordEncoder;
    private User loggedInUser = null;

    @Autowired
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean authenticate(String username, String password) throws SQLException {
        // Note the ? mark in the query. It is a place holder that we will later replace.
        final String sql = "select * from user where username = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Following line replaces the first place holder with username.
            System.out.println("Username: " + username);
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Traverse the result rows one at a time.
                // Note: This specific while loop will only run at most once 
                // since username is unique.
                while (rs.next()) {
                    // Note: rs.get.. functions access attributes of the current row.
                    String storedPasswordHash = rs.getString("password");
                    boolean isPassMatch = passwordEncoder.matches(password, storedPasswordHash);
                    System.out.println(isPassMatch);
                    // Note: 
                    if (isPassMatch) {
                        String userId = rs.getString("user_id");
                        String profile_picture = rs.getString("profile_picture");

                        // Initialize and retain the logged in user.
                        loggedInUser = new User(userId, username, profile_picture);
                    }

                    return isPassMatch;
                }
            }
        }
        return false;
    }


    /**
     * Logs out the user.
     */
    public void unAuthenticate() {
        loggedInUser = null;
    }

    /**
     * Checks if a user is currently authenticated.
     */
    public boolean isAuthenticated() {
        return loggedInUser != null;
    }

    /**
     * Retrieves the currently logged-in user.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }


    public boolean registerUser(String password, String username)
            throws SQLException {
        // Note the ? marks in the SQL statement. They are placeholders like mentioned above.
        final String registerSql = "insert into user (password, username) values (?, ?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement registerStmt = conn.prepareStatement(registerSql)) {
            // Following lines replace the placeholders 1-4 with values.
            registerStmt.setString(1, passwordEncoder.encode(password));
            registerStmt.setString(2, username);

            // Execute the statement and check if rows are affected.
            int rowsAffected = registerStmt.executeUpdate();
            return rowsAffected > 0;
            
            
        }
    }
}
