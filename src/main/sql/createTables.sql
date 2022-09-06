USE freeuni;

CREATE TABLE reviews(
    id VARCHAR(160) PRIMARY KEY,
    review_text VARCHAR(1000),
    album_id VARCHAR(160) NOT NULL, /* add reference */
    username VARCHAR(80) NOT NULL, /* add reference */
    stars INT NOT NULL,
    prestige INT NOT NULL,
    upload_date DATE
);

CREATE TABLE voting_data(
    username VARCHAR(80) NOT NULL, /* add reference */
    review_id VARCHAR(160) NOT NULL,
    vote INT NOT NULL,
                        /* -1: downvote, 1: upvote, 0: neither */
    FOREIGN KEY (review_id) REFERENCES reviews(id)
);