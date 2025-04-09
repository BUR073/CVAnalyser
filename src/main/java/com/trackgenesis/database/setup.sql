-- SID: 2408078

-- Setup file for the SQL database

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS CVAnalyser;

-- Select the database to operate on
USE CVAnalyser;

-- Create the Users table
CREATE TABLE Users (
                       UserId INT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing ID (Not implemented)
                       username VARCHAR(255) NOT NULL UNIQUE, -- Not null and unique
                       password VARCHAR(255) NOT NULL -- Not null
);