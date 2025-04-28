-- Create the database.
create database if not exists cs4370p4;

-- Use the created database.
use cs4370p4;

-- Create the user table.
CREATE TABLE User (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE Profile (
  username VARCHAR(50) PRIMARY KEY,
  profile_picture VARCHAR(255),
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Recipe (
  rec_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  title VARCHAR(255) NOT NULL,
  directions TEXT,
  image VARCHAR(255),
  estim_time INT,
  FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Subscription (
  subscriber_id INT,
  subscribed_id INT,
  PRIMARY KEY (subscriber_id, subscribed_id),
  FOREIGN KEY (subscriber_id) REFERENCES User(user_id),
  FOREIGN KEY (subscribed_id) REFERENCES User(user_id)
);

CREATE TABLE Favorite (
  user_id INT,
  rec_id INT,
  PRIMARY KEY (user_id, rec_id),
  FOREIGN KEY (user_id) REFERENCES User(user_id),
  FOREIGN KEY (rec_id) REFERENCES Recipe(rec_id)
);

CREATE TABLE Rating (
  rate_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  rec_id INT,
  rating INT CHECK (rating >= 1 AND rating <= 5),
  FOREIGN KEY (user_id) REFERENCES User(user_id),
  FOREIGN KEY (rec_id) REFERENCES Recipe(rec_id)
);
