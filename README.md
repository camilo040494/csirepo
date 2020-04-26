´´´
docker run --name some-postgis -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgis/postgis
docker run -it --link postgis:postgres --rm postgres \
    sh -c 'exec psql -h "$POSTGRES_PORT_5432_TCP_ADDR" -p "$POSTGRES_PORT_5432_TCP_PORT" -U postgres'
´´´
