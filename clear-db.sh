#!/usr/bin/env bash
set -euo pipefail

POSTGRES_CONTAINER="postgres"
DB_USER="user"
DATABASES=("attendance_analyser" "simple_lms")

TRUNCATE_SQL=$(cat <<'SQL'
DO $$
DECLARE t text;
BEGIN
  SELECT string_agg(quote_ident(tablename), ', ') INTO t
  FROM pg_tables
  WHERE schemaname = 'public'
    AND tablename NOT IN ('databasechangelog', 'databasechangeloglock');
  IF t IS NOT NULL THEN
    EXECUTE 'TRUNCATE ' || t || ' RESTART IDENTITY CASCADE';
  END IF;
END
$$;
SQL
)

for db in "${DATABASES[@]}"; do
  echo "Clearing $db..."
  docker exec "$POSTGRES_CONTAINER" psql -U "$DB_USER" -d "$db" -q -c "$TRUNCATE_SQL"
done

echo "Done. All tables truncated, sequences reset."
