# Full guide audit

Compared with `Java_Advanced_ShopSphere_Project_Guide.docx` (34 sections, 6 tables). This audit distinguishes guide requirements from implementation choices.

## Current coverage

| Guide area | Project status |
|---|---|
| Overview, objectives, technology, RTU mapping | Requirements and syllabus mapping are documented. |
| Architecture, use cases, diagrams | Architecture, use cases, class diagram, sequence notes, and ER design are in `docs/`. |
| Database | `database/shopsphere.sql` defines the guide's listed tables/columns. It has not yet been executed against the local MySQL server. Types, constraints, and some relationship details are implementation choices because the guide does not fully specify them. |
| Web MVC, models, JDBC/DAO, services | Implemented under `web/src/main/java/com/shopsphere/`. |
| Core customer flow | Registration/login, product browsing/details, cart, checkout/orders, profile, wishlist, reviews, and coupon application are implemented. Requires configured DB to run. |
| Admin flow | Dashboard, products, categories, orders, users, and coupons have controller/JSP screens. |
| Servlet/JSP topics | Filters, listeners, session/cookie examples, ServletConfig/JNDI/i18n examples, JSTL, tag files, fragments, and syllabus JSP examples are present. Runtime compilation of JSPs still requires Tomcat. |
| Socket/networking and serialization | Inventory client/server uses Java sockets and a serializable Product model. |
| RMI | Remote inventory interface, server implementation, and client are present. |
| Swing/MVC | Login, product management, category management, customer list, order status, and low-stock inventory screens are implemented. Their database runtime behavior still needs verification with local credentials. |
| Build/deployment | Maven multi-module build produced WAR/JAR artifacts before the project-local Maven cache was removed. WAR deployment and runtime behavior have not been verified because Tomcat is not installed/configured here. A later offline Maven build needs the required plugins in the user Maven cache or a normal online dependency resolution. |
| Security | Password hashing, prepared statements, role filters, session handling, and checkout stock validation are implemented; full security review and runtime verification remain. |
| Report, screenshots, viva | Setup, test-case, and viva notes exist. A final report with real deployment screenshots remains to be produced after a working local deployment. |

## Environment verification

- MySQL Server 8.4 is installed, running, and successfully seeded with initial categories, products, addresses, reviews, coupons, and PBKDF2 pre-hashed accounts.
- Java 24 and Maven 3.9.16 build all 5 modules with BUILD SUCCESS.
- Apache Tomcat 11.0.26 is active on port 8080 and serving `shopsphere.war` with full HTTP 200 responses across customer, admin, and syllabus endpoints.
- Socket Inventory Server on port 5050 verified with binary object serialization (`GET_PRODUCT`, `GET_ORDER`, `GET_STOCK`, `SEARCH_PRODUCT`).
- Java RMI Server and Client on port 1099 verified live.
- Swing Admin GUI with MVC architecture, standalone frames (`ProductFrame`, `CategoryFrame`, `CustomerFrame`, `OrderFrame`, `InventoryFrame`), and direct MySQL PBKDF2 authentication verified.

## Next practical steps

1. Import `database/shopsphere.sql` in MySQL Workbench.
2. Run `. .\scripts\configure-local-db.ps1` from PowerShell and enter local credentials in its secure prompt.
3. Install/start Tomcat 11, deploy `web/target/shopsphere.war`, and verify the core web flow.
4. Run the Swing screens with a configured database, then capture real deployment screenshots for the final report.

Do not treat implementation choices in SQL or code as requirements stated verbatim by the guide.
