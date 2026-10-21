# Local setup

The project targets the running **MySQL Server 8.4** instance.

The Java application reads database settings from environment variables rather than storing credentials in source code:

- `SHOPSPHERE_DB_URL` — `jdbc:mysql://localhost:3306/shopsphere`
- `SHOPSPHERE_DB_USER` — your local MySQL account
- `SHOPSPHERE_DB_PASSWORD` — that account's password

Create/import the schema in `../database/shopsphere.sql` before starting the app. Do not commit real credentials.
