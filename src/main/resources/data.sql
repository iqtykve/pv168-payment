CREATE TABLE accounts (ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, birthName VARCHAR(20), givenName VARCHAR(30), accountNumber VARCHAR(20), sumAmmount NUMERIC(8,3), wasDeleted BOOLEAN);