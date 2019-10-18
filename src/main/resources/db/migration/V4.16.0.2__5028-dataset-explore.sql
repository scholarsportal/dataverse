ALTER TABLE externaltool ADD COLUMN scope VARCHAR(255);
UPDATE externaltool SET scope = 'FILE';
ALTER TABLE externaltool ALTER COLUMN scope SET NOT NULL;
