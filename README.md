# Banking Savings Account API

A Java Spring Boot application fulfilling the requirements outlined in [Requirements.md](Requirements.md).

Created using:

* Spring Initializer.
* IntelliJ IDEA Community Edition.
* Claude (free version - not Claude code).
* Microsoft Windows 11.
* Eclipse Java 25.
* PostgreSQL 18.

## Requirements

* Java 25
* Postgres database. TODO: Use testcontainers instead.

## Compiling

```
.\gradlew clean build
```

## Running

```
.\gradlew bootrun
```

Note: Application automatically creates schema on start up and drops it on shutdown.
i.e. You won't see the tables in PgAdmin after you stop the application.
TODO: Use Flyway scripts in production.

## Tests

Run

```
cls ; ./gradlew cleanTest test --info
```

## Manual Testing

See [PowerShell curl scripts](./scripts).