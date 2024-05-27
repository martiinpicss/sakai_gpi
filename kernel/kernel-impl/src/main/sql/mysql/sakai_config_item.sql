-- ---------------------------------------------------------------------------
-- SAKAI_CONFIG_ITEM - KNL-1063 - MYSQL
-- ---------------------------------------------------------------------------

CREATE TABLE SAKAI_CONFIG_ITEM (
	ID				BIGINT(20) NOT NULL AUTO_INCREMENT,
	NODE			VARCHAR(255),
	NAME			VARCHAR(255) NOT NULL,
	VALUE			LONGTEXT,  
	RAW_VALUE		LONGTEXT,
	TYPE			VARCHAR(255) NOT NULL,
	DEFAULT_VALUE	LONGTEXT,
	DESCRIPTION		LONGTEXT,
	SOURCE			VARCHAR(255) DEFAULT NULL,
	DEFAULTED		BIT(1) NOT NULL,
	REGISTERED		BIT(1) NOT NULL,
	SECURED			BIT(1) NOT NULL,
	DYNAMIC			BIT(1) NOT NULL,
	CREATED			DATETIME NOT NULL,
	MODIFIED		DATETIME NOT NULL,
	POLL_ON			DATETIME DEFAULT NULL,
	PRIMARY KEY (ID)
);

CREATE INDEX SCI_NODE_IDX ON SAKAI_CONFIG_ITEM (NODE ASC);
CREATE INDEX SCI_NAME_IDX ON SAKAI_CONFIG_ITEM (NAME ASC);
