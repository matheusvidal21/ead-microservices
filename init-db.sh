#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE "ead-authuser";
    CREATE DATABASE "ead-courses";
    CREATE DATABASE "ead-notifications";
EOSQL
