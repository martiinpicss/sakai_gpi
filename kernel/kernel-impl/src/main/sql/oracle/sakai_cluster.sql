-----------------------------------------------------------------------------
-- SAKAI_CLUSTER
-----------------------------------------------------------------------------

CREATE TABLE SAKAI_CLUSTER
(
	SERVER_ID_INSTANCE VARCHAR2 (64),
	UPDATE_TIME TIMESTAMP WITH LOCAL TIME ZONE,
	STATUS VARCHAR2(8) CHECK (STATUS IN ('STARTING', 'RUNNING', 'CLOSING', 'STOPPING')),
	SERVER_ID VARCHAR(64)
);

ALTER TABLE SAKAI_CLUSTER ADD ( CONSTRAINT "SAKAI_CLUSTER_PK" PRIMARY KEY ("SERVER_ID_INSTANCE") VALIDATE );
