FROM postgres:16.1 as postgres
ENV POSTGRES_DB odedb
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD 12345678
COPY src/main/resources/init_schema.sql /docker-entrypoint-initdb.d/






