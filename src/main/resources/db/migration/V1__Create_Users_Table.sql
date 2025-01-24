CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    role NVARCHAR(50) NOT NULL,
    is_deleted BIT DEFAULT 0,
    deleted_at DATETIME NULL
);

CREATE TABLE channels (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    is_deleted BIT DEFAULT 0,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE messages (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    channel_id BIGINT NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    timestamp DATETIME NOT NULL, -- Add this column to store message creation time
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (channel_id) REFERENCES channels(id)
);

CREATE TABLE user_friends (
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (friend_id) REFERENCES users(id)
);
