CREATE TABLE todos (
  id BIGINT PRIMARY KEY,
  description VARCHAR(200) NOT NULL,
  owner VARCHAR(200) NOT NULL,
  finished BOOLEAN NOT NULL
);