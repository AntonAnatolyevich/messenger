CREATE TABLE users (
    id uuid PRIMARY KEY,
    first_name text,
    second_name text,
    user_name text UNIQUE,
    password text,
    description text,
    created_at timestamp
);

CREATE TABLE message (
    id uuid PRIMARY KEY,
    content text,
    created_at timestamp,
    sender_id uuid,
    recipient_id uuid,
    CONSTRAINT fk_user_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_user_recipient FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);
