# Notes on Remote Postgres to Heroku

## Add to PATH

export PATH=/Applications/Postgres.app/Contents/Versions/latest/bin:$PATH

## Run PSQL

heroku pg:psql -a jitterted-kidbank

## Insert


## Schema View

SELECT
 COLUMN_NAME, DATA_TYPE
FROM
 information_schema.COLUMNS
WHERE
 TABLE_NAME = 'user_profiles';