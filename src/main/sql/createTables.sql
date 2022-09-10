CREATE TABLE genders(
    id           INT PRIMARY KEY,
    gender       VARCHAR(20)
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
    profile_picture  LONGBLOB,
    birth_date       DATE,
    password_length  INT,
    FOREIGN KEY (badge) REFERENCES badges(id),
    FOREIGN KEY (gender) REFERENCES genders(id)
);

CREATE TABLE friendship_data(
    first_username           VARCHAR(80),
    second_username          VARCHAR(80),
    friendship_status        VARCHAR(25),
    FOREIGN KEY (first_username) REFERENCES users(username),
    FOREIGN KEY (second_username) REFERENCES users(username)
);

CREATE TABLE  albums(
    id VARCHAR(160) PRIMARY KEY,
    username VARCHAR(80) NOT NULL,
    album_name VARCHAR(80) NOT NULL,
    artist_name VARCHAR(80) NOT NULL,
    cover_image LONGBLOB NOT NULL,
    upload_date DATE NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE songs(
    album_id VARCHAR(160) NOT NULL,
    name VARCHAR(30) NOT NULL,
    full_name VARCHAR(50) PRIMARY KEY,
    album_name VARCHAR(50) NOT NULL,
    artist_name VARCHAR(50),
    FOREIGN KEY (album_id) REFERENCES albums(id)
);

CREATE TABLE reviews(
    id VARCHAR(160) PRIMARY KEY,
    review_text VARCHAR(1000),
    album_id VARCHAR(160) NOT NULL,
    username VARCHAR(80) NOT NULL,
    stars INT NOT NULL,
    prestige INT NOT NULL,
    upload_date DATE,
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (album_id) REFERENCES albums(id)
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

INSERT INTO users VALUES
    ('guri', 'bc9c0fa5919718ebf8b576970b8a4335244bc81a8519991757f750dfba4700a8',
     'Guri', 'Kropotkin', 1, 0, 0, null, STR_TO_DATE('06/14/2002', '%m/%d/%Y'), 4),
    ('eva', '81a83544cf93c245178cbc1620030f1123f435af867c79d87135983c52ab39d9',
     'Evangelina', 'Nausse', 1, 0, 0, null, STR_TO_DATE('03/13/2002', '%m/%d/%Y'), 4);




