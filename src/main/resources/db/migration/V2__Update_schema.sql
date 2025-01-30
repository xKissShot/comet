ALTER TABLE messages
ADD message_type NVARCHAR(50) NULL;

ALTER TABLE messages
ADD CONSTRAINT DF_Message_Type DEFAULT 'TEXT' FOR message_type;
