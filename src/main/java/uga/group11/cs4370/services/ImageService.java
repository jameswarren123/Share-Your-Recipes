package uga.group11.cs4370.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;

@Service
@SessionScope
public class ImageService {

    private final DataSource dataSource;

    @Autowired
    public ImageService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean storeImage(MultipartFile file) throws SQLException {
        final String sql = "INSERT INTO image (image_path) VALUES (?)";

        String imagePath = "/uploads" + file.getOriginalFilename();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imagePath);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public int setImage_id(MultipartFile file) throws SQLException {
        final String sql = "SELECT image_id FROM image WHERE image_path = ?";

        String imagePath = "/uploads" + file.getOriginalFilename();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imagePath);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("image_id");
                } else {
                    throw new SQLException("No image found with path: " + imagePath);
                }
            }
        }
    }

    public String getUserImage() throws SQLException {
        final String sql = "SELECT image_path FROM image i, user u " +
                "WHERE i.image_id = u.image_id " +
                "AND u.image_id = (SELECT image_id FROM user WHERE user_id = ?)";
        
        int user_id = 5;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user_id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("image_path");
                } else {
                    throw new SQLException("No image found");
                }
            }
        }
    }
}