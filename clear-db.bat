@echo off
setlocal

set POSTGRES_CONTAINER=postgres
set DB_USER=user

for %%d in (attendance_analyser simple_lms) do (
    echo Clearing %%d...
    docker exec %POSTGRES_CONTAINER% psql -U %DB_USER% -d %%d -q -c "DO $$ DECLARE t text; BEGIN SELECT string_agg(quote_ident(tablename), ', ') INTO t FROM pg_tables WHERE schemaname = 'public' AND tablename NOT IN ('databasechangelog', 'databasechangeloglock'); IF t IS NOT NULL THEN EXECUTE 'TRUNCATE ' || t || ' RESTART IDENTITY CASCADE'; END IF; END $$;"
    if errorlevel 1 exit /b 1
)

echo Done. All tables truncated, sequences reset.