CREATE TABLE genders(
    id           INT PRIMARY KEY,
    gender       VARCHAR(20)
);

CREATE TABLE statuses(
    id          INT PRIMARY KEY,
    status      VARCHAR(20)
);

CREATE TABLE badges(
    id          INT PRIMARY KEY,
    badge       VARCHAR(20)
);


CREATE TABLE users(
    username         VARCHAR(80) PRIMARY KEY,
    password         VARCHAR(150),
    first_name       VARCHAR(80),
    last_name        VARCHAR(80),
    status           INT,  /* 0 - banned, 1-active */
    badge            INT  NOT NULL,
    gender           INT  NOT NULL,
    profile_picture  VARCHAR(20),
    birth_date       DATE,
    password_length  INT,
    FOREIGN KEY (badge) REFERENCES badges(id),
    FOREIGN KEY (gender) REFERENCES genders(id)
);

CREATE TABLE friendship_data(
    first_username           VARCHAR(80),
    second_username          VARCHAR(80),
    friendship_status        INT NOT NULL,
    FOREIGN KEY (first_username) REFERENCES users(username),
    FOREIGN KEY (second_username) REFERENCES users(username),
    FOREIGN KEY (friendship_status) REFERENCES statuses(id)
);

CREATE TABLE reviews(
    id VARCHAR(160) PRIMARY KEY,
    review_text VARCHAR(1000),
    album_id VARCHAR(160) NOT NULL, /* add reference */
    username VARCHAR(80) NOT NULL,
    stars INT NOT NULL,
    prestige INT NOT NULL,
    upload_date DATE,
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE voting_data(
    username VARCHAR(80) NOT NULL,
    review_id VARCHAR(160) NOT NULL,
    vote INT NOT NULL,
    /* -1: downvote, 1: upvote, 0: neither */
    FOREIGN KEY (review_id) REFERENCES reviews(id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO badges VALUES
    (0, 'ADMINISTRATOR'),
    (1, 'NEWCOMER'),
    (2, 'CONTRIBUTOR'),
    (3, 'ENTHUSIAST');

INSERT INTO genders VALUES
    (0, 'MAN'),
    (1, 'WOMAN'),
    (2, 'OTHER');

INSERT INTO statuses VALUES
    (0, 'REQUEST SENT'),
    (1, 'ACCEPT REQUEST'),
    (2, 'FRIENDS');




