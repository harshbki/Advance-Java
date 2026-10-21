# RTU Advanced Java topic mapping

| RTU topic | ShopSphere demonstration |
|---|---|
| Swing / Swing components | `swing-admin/src/view/` desktop login and admin dashboard |
| MVC | Servlet → Service → DAO web layers; Swing controllers, views and DAO |
| Applets | Historical explanation at `web/src/main/webapp/syllabus/applets.jsp` |
| JDBC | Web and desktop DAO classes |
| JDBC driver | MySQL Connector/J dependency in Maven modules |
| `java.net`, client/server | `network/client/InventoryClient.java`, `network/server/InventoryServer.java` |
| Socket commands | `GET_PRODUCT`, `GET_STOCK`, `SEARCH_PRODUCT` |
| Serialization | Serializable network `Product`, sent by the socket server |
| RMI / registry | `rmi/remote/InventoryService.java`, RMI server and client |
| Naming / JNDI | `/syllabus/jndi` looks up `java:comp/env/jdbc/ShopSphere` |
| URL / URLConnection | `network/client/UrlConnectionDemo.java` |
| Internationalization | `/syllabus/i18n`, `messages_en.properties`, `messages_hi.properties` |
| J2EE / N-tier | WAR application on Tomcat 11; architecture diagrams in `architecture.md` |
| HTTP, request/response | Servlet controllers handle web forms and routes |
| Web container | Apache Tomcat 11 target |
| Servlet lifecycle | Controllers implement `init`, `doGet`, `doPost`, `destroy` hooks as needed |
| ServletConfig / ServletContext | `/syllabus/servlet-config`, `web.xml`, and `AppListener` |
| Sessions | Login state held in `HttpSession` |
| Cookies | `recentlyViewed` product IDs cookie (HttpOnly, non-sensitive) |
| Events / listeners | `AppListener` and `SessionListener` |
| Filters | `AuthenticationFilter`, `AdminFilter` |
| JSP / EL | Main pages render servlet request attributes with EL |
| Traditional JSP elements | `/syllabus/jsp-elements.jsp` |
| JSTL Core / Functions | Main JSPs and `/syllabus/jstl-core.jsp` |
| JSP fragments | `webapp/fragments/` |
| Tag files / reusable tags | `WEB-INF/tags/productCard.tag`, `rating.tag` |
| JSTL XML | `/syllabus/jstl-xml.jsp` and `products.xml` |
| JSTL SQL | `/syllabus/jstl-sql.jsp` (requires Tomcat JNDI datasource configuration) |
