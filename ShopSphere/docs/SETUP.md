# Local setup (MySQL 8.4 and Tomcat 11)

## 1. Import the database

Open MySQL Workbench, connect to your local MySQL Server 8.4 instance, and open/run `database/shopsphere.sql`. The script creates the `shopsphere` schema and its tables.

## 2. Configure local credentials

In PowerShell, from the project root, dot-source the prompt script:

```powershell
. .\scripts\configure-local-db.ps1
```

It asks for the MySQL account and password without writing the password into a project file. The variables last only for that PowerShell session. Open a new PowerShell and repeat this step when needed.

## 3. Build

```powershell
mvn -DskipTests package
```

The build creates the web WAR at `web/target/shopsphere.war` and JARs for Swing, Socket, and RMI. The guide requires Apache Tomcat 11; copy the WAR into Tomcat's `webapps` folder and start Tomcat. Open `http://localhost:8080/shopsphere/`.

## 4. Create the first admin account

Run `com.shopsphere.util.AdminBootstrap` from the IDE using the `web` module runtime classpath, after setting database environment variables. It prompts for the admin password privately and stores a PBKDF2 hash in the guide's `users.password` column. Customer registration creates customer accounts only.

## 5. Advanced Java demos

- Swing: run `com.shopsphere.swing.view.ShopSphereAdmin` from `swing-admin`.
- Socket: run `com.shopsphere.network.server.InventoryServer`, then run `InventoryClient` with `GET_PRODUCT 1`, `GET_STOCK 1`, or `SEARCH_PRODUCT lamp`.
- RMI: run `com.shopsphere.rmi.server.InventoryServer`, then `com.shopsphere.rmi.client.InventoryClient 1`.
- URL/URLConnection: run `com.shopsphere.network.client.UrlConnectionDemo https://example.com`.

All modules use `SHOPSPHERE_DB_URL`, `SHOPSPHERE_DB_USER`, and `SHOPSPHERE_DB_PASSWORD`. The JNDI syllabus examples additionally require a `jdbc/ShopSphere` datasource configured in Tomcat.
