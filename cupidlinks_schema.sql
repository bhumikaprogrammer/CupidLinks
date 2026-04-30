CREATE DATABASE cupidlinks_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE cupidlinks_db;


CREATE TABLE users (
    user_id       INT          NOT NULL AUTO_INCREMENT,
    email         VARCHAR(150) NOT NULL,
    phone         VARCHAR(15),
    password_hash VARCHAR(255) NOT NULL,
    role          ENUM('user', 'admin') NOT NULL DEFAULT 'user',
    status        ENUM('pending', 'approved', 'suspended', 'deleted') NOT NULL DEFAULT 'approved',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE KEY uq_email (email),
    UNIQUE KEY uq_phone (phone)
);


CREATE TABLE profiles (
    profile_id        INT          NOT NULL AUTO_INCREMENT,
    user_id           INT          NOT NULL,
    full_name         VARCHAR(100) NOT NULL,
    date_of_birth     DATE         NOT NULL,
    gender            ENUM('male', 'female', 'non-binary', 'prefer_not_to_say') NOT NULL,
    bio               TEXT,
    location          VARCHAR(150),
    dating_preference ENUM('casual', 'serious', 'friendship', 'open') NOT NULL DEFAULT 'open',
    profile_photo     VARCHAR(255),
    nepali_raasi      ENUM('Mesh','Brish','Mithun','Karkat','Singh','Kanya','Tula','Brishchik','Dhanu','Makar','Kumbha','Meen'),
    visibility        ENUM('public', 'matches_only', 'hidden') NOT NULL DEFAULT 'public',
    updated_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (profile_id),
    UNIQUE KEY uq_profile_user (user_id),
    CONSTRAINT fk_profile_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);



CREATE TABLE user_interests (
    interest_id  INT         NOT NULL AUTO_INCREMENT,
    user_id      INT         NOT NULL,
    interest_tag VARCHAR(80) NOT NULL,
    PRIMARY KEY (interest_id),
    UNIQUE KEY uq_user_interest (user_id, interest_tag),
    CONSTRAINT fk_interest_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);



CREATE TABLE interests (
    like_id     INT  NOT NULL AUTO_INCREMENT,
    sender_id   INT  NOT NULL,
    receiver_id INT  NOT NULL,
    status      ENUM('pending', 'accepted', 'declined') NOT NULL DEFAULT 'pending',
    sent_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (like_id),
    UNIQUE KEY uq_like_pair (sender_id, receiver_id),
    CONSTRAINT chk_no_self_like
        CHECK (sender_id <> receiver_id),
    CONSTRAINT fk_like_sender
        FOREIGN KEY (sender_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_like_receiver
        FOREIGN KEY (receiver_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);



CREATE TABLE matches (
    match_id   INT  NOT NULL AUTO_INCREMENT,
    user1_id   INT  NOT NULL,
    user2_id   INT  NOT NULL,
    matched_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (match_id),
    UNIQUE KEY uq_match_pair (user1_id, user2_id),
    CONSTRAINT chk_match_order
        CHECK (user1_id < user2_id),
    CONSTRAINT fk_match_user1
        FOREIGN KEY (user1_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_match_user2
        FOREIGN KEY (user2_id) REFERENCES users(user_id)
        ON DELETE CASCADE

);


CREATE TABLE connections (
    connection_id     INT  NOT NULL AUTO_INCREMENT,
    user_id           INT  NOT NULL,
    connected_user_id INT  NOT NULL,
    status            ENUM('sent', 'confirmed', 'blocked') NOT NULL DEFAULT 'sent',
    created_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (connection_id),
    UNIQUE KEY uq_connection_pair (user_id, connected_user_id),
    CONSTRAINT fk_conn_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_conn_target
        FOREIGN KEY (connected_user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);


CREATE TABLE favourites (
    favourite_id  INT  NOT NULL AUTO_INCREMENT,
    user_id       INT  NOT NULL,
    saved_user_id INT  NOT NULL,
    saved_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (favourite_id),
    UNIQUE KEY uq_favourite_pair (user_id, saved_user_id),
    CONSTRAINT fk_fav_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_fav_saved
        FOREIGN KEY (saved_user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);


CREATE TABLE reports (
    report_id        INT  NOT NULL AUTO_INCREMENT,
    reporter_id      INT  NOT NULL,
    reported_user_id INT  NOT NULL,
    reason           TEXT NOT NULL,
    status           ENUM('open', 'reviewed', 'resolved', 'dismissed') NOT NULL DEFAULT 'open',
    reported_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (report_id),
    CONSTRAINT fk_report_reporter
        FOREIGN KEY (reporter_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);



CREATE TABLE messages (
    message_id INT  NOT NULL AUTO_INCREMENT,
    match_id   INT  NOT NULL,
    sender_id  INT  NOT NULL,
    content    TEXT NOT NULL,
    sent_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (message_id),
    CONSTRAINT fk_msg_match
        FOREIGN KEY (match_id) REFERENCES matches(match_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_msg_sender
        FOREIGN KEY (sender_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);


CREATE TABLE feedback (
    feedback_id  INT     NOT NULL AUTO_INCREMENT,
    user_id      INT     NOT NULL,
    rating       TINYINT NOT NULL,
    comment      TEXT,
    submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (feedback_id),
    CONSTRAINT chk_rating_range
        CHECK (rating BETWEEN 1 AND 5),
    CONSTRAINT fk_feedback_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);


CREATE TABLE support_tickets (
    ticket_id    INT          NOT NULL AUTO_INCREMENT,
    user_id      INT          NOT NULL,
    subject      VARCHAR(200) NOT NULL,
    message      TEXT         NOT NULL,
    status       ENUM('open', 'in_progress', 'resolved') NOT NULL DEFAULT 'open',
    submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ticket_id),
    CONSTRAINT fk_ticket_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);


CREATE INDEX idx_profiles_location  ON profiles(location);
CREATE INDEX idx_profiles_gender    ON profiles(gender);
CREATE INDEX idx_profiles_dob       ON profiles(date_of_birth);
CREATE INDEX idx_profiles_raasi     ON profiles(nepali_raasi);
CREATE INDEX idx_interests_receiver ON interests(receiver_id, status);
CREATE INDEX idx_matches_user1      ON matches(user1_id);
CREATE INDEX idx_matches_user2      ON matches(user2_id);
CREATE INDEX idx_messages_match     ON messages(match_id, sent_at);
CREATE INDEX idx_reports_status     ON reports(status);
CREATE INDEX idx_tickets_status     ON support_tickets(status);


INSERT INTO users (email, phone, password_hash, role, status)
VALUES (
    'admin@cupidlinks.com',
    '9800000000',
    '$2a$12$eBE262NItBy0qGdj1P/ZSO4nvYsffyzeC/io095c0MwBKwz55Aud2',
    'admin',
    'approved'
);

ALTER TABLE profiles ADD COLUMN clan VARCHAR(100) NULL AFTER nepali_raasi;
