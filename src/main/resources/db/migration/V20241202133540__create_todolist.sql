CREATE TABLE todo
(
    id   INT AUTO_INCREMENT NOT NULL,
    text VARCHAR(255)       NULL,
    done BOOLEAN            NULL,
    CONSTRAINT pk_todos PRIMARY KEY (id)
);