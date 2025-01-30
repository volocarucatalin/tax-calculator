#!/bin/sh
set -e

host="$1"
shift
cmd="$@"

echo "Waiting for MySQL to be ready at $host:3306..."

while ! mysqladmin ping -h"$host" --silent; do
    sleep 2
done

>&2 echo "MySQL is up and running – executing command"
exec $cmd