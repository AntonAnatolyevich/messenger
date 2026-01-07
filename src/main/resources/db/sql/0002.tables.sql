CREATE TABLE message_attachments (
    id UUID PRIMARY KEY,
    message_id UUID NOT NULL,
    file_url text NOT NULL,
    CONSTRAINT fk_message FOREIGN KEY (message_id) REFERENCES message (id) ON DELETE CASCADE
);

CREATE TABLE user_attachments (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    file_url text NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);