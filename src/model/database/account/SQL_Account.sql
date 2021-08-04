CREATE TABLE account
(
	account_id           		SERIAL,
    account_username 			VARCHAR(64) UNIQUE NOT NULL,
    account_email 				VARCHAR(64) NOT NULL,
    account_password 			VARCHAR(64) NOT NULL,
    account_type		 		VARCHAR(64) NOT NULL,
    account_name				VARCHAR(64) NOT NULL,
    account_surname				VARCHAR(64) NOT NULL,
    PRIMARY KEY (account_id)
)