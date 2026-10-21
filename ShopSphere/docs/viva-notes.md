# SHOPSPHERE: VIVA DEMONSTRATION & ORAL EXAMINATION MASTER GUIDE
**Target Audience**: Students preparing for RTU Advanced Java Practical Viva / External Examination  
**Project**: ShopSphere E-Commerce Management System  

---

## 1. QUICK DEMONSTRATION SCRIPT FOR EXAMINERS (5-MINUTE WALKTHROUGH)

When the external examiner asks you to demonstrate your project, follow this exact sequence:

1. **Deploy & Show Web Application on Apache Tomcat 11**:
   - Open browser at `http://localhost:8080/shopsphere/`.
   - Show the clean landing page, brand, and navigation.
   - Point out that Tomcat 11 uses Jakarta EE 10 with `jakarta.servlet.*` packages.
2. **Demonstrate Customer Workflow**:
   - Register a new customer (`/register`) & show duplicate email validation.
   - Sign in (`/login`) & show session creation.
   - Browse products (`/products`), search keyword "Headphones", filter by category, sort by price.
   - Add product to cart (`/cart`), change quantity, observe server-side line total calculation.
   - Proceed to checkout (`/checkout`), select address, apply coupon `WELCOME10`, and place order.
   - View order confirmation (`/order-success.jsp`) and order history tracking (`/orders`).
3. **Demonstrate Security & Authorization Filters**:
   - Open a private/incognito window, try navigating to `/cart` &rarr; observe automatic redirect to `/login` by `AuthenticationFilter`.
   - Log in as a customer, try opening `/admin/dashboard` &rarr; observe **HTTP 403 Forbidden** returned by `AdminFilter`.
4. **Demonstrate Desktop Swing Administration Client**:
   - Run `com.shopsphere.swing.view.ShopSphereAdmin`.
   - Log in with `admin@shopsphere.com` / `Admin@123`.
   - Show the tabbed interface: Products (with Add/Edit/Delete), Categories, Customers, Orders (with status changer), and Inventory (low stock <= 5).
5. **Demonstrate TCP Socket Networking & Serialization**:
   - Start `com.shopsphere.network.server.InventoryServer` (port 5050).
   - In terminal, run `InventoryClient GET_PRODUCT 1` &rarr; explain binary `ObjectOutputStream`/`ObjectInputStream` serialization.
   - Run `InventoryClient GET_ORDER 1` &rarr; show Order object serialization.
   - Run `InventoryClient GET_STOCK 1` &rarr; show textual protocol.
6. **Demonstrate Java RMI**:
   - Start `com.shopsphere.rmi.server.InventoryServer` (port 1099).
   - In terminal, run `InventoryClient 1` &rarr; explain RMI Registry, remote stub, and remote method execution across JVM boundaries.
7. **Show Syllabus Specific Pages**:
   - `/syllabus/servlet-config` &rarr; ServletConfig init-params & ServletContext attributes.
   - `/syllabus/jsp-elements.jsp` &rarr; Declarations, scriptlets, expressions, comments.
   - `/syllabus/jstl-core.jsp` &rarr; JSTL Core tags & JSTL functions (`fn:split`).
   - `/syllabus/jstl-xml.jsp` &rarr; JSTL XML parsing of `products.xml`.
   - `/syllabus/i18n?lang=hi` &rarr; Hindi Devanagari ResourceBundle & currency formatting.

---

## 2. FREQUENTLY ASKED VIVA QUESTIONS & MODEL ANSWERS

### Q1: What is the difference between J2EE / Java EE and Jakarta EE? Which does this project use?
**Answer**:
> "In 2017, Oracle transferred Java EE to the Eclipse Foundation. Because Oracle retained rights to the 'Java' trademark, all package namespaces were renamed from `javax.*` to `jakarta.*` starting with Jakarta EE 9.
> ShopSphere runs on Apache Tomcat 11, which implements the **Jakarta EE 10** specification (Servlet 6.1, JSP 3.1). All our Java source files import `jakarta.servlet.*`, `jakarta.servlet.http.*`, and use Jakarta JSTL taglib URIs (`jakarta.tags.core`)."

### Q2: Explain the N-Tier / MVC architecture used in ShopSphere.
**Answer**:
> "ShopSphere uses a 4-tier enterprise architecture:
> 1. **Presentation Tier**: JSP pages, Expression Language (EL `${...}`), JSTL tags, and custom Tag Files (`productCard.tag`, `rating.tag`). Views do not contain SQL or business logic.
> 2. **Controller Tier**: Jakarta Servlets (`LoginServlet`, `CheckoutServlet`, `ProductServlet`) map HTTP requests, handle session management, and delegate to services.
> 3. **Service Tier (Business Logic)**: Java services (`AuthService`, `ProductService`, `CheckoutService`) enforce business rules, such as password hashing and stock validation.
> 4. **Data Access Tier (DAO)**: 13 DAO classes using pure JDBC `PreparedStatement` to query MySQL 8.4.
> This separation ensures high maintainability and allows our desktop Swing and networking modules to reuse the database persistence layer."

### Q3: How is SQL Injection prevented throughout the project?
**Answer**:
> "SQL Injection is prevented by using parameterized `PreparedStatement` across 100% of our database queries in all modules. User inputs are never concatenated directly into SQL strings. When placeholders (`?`) are bound using `ps.setString()`, `ps.setInt()`, or `ps.setBigDecimal()`, the MySQL driver automatically escapes and treats the values strictly as literal data, neutralising SQL injection attempts."

### Q4: How is password security implemented?
**Answer**:
> "We implement PBKDF2 with HMAC-SHA256 (`PBKDF2WithHmacSHA256`) using 210,000 iterations and a 16-byte random salt generated by `SecureRandom`. Passwords are never stored in plaintext. Password verification uses `MessageDigest.isEqual()` to ensure constant-time comparison, eliminating timing attacks. In addition, passwords in `LoginServlet` and `AdminBootstrap` are handled as `char[]` arrays and overwritten with `\0` in `finally` blocks to minimize memory exposure."

### Q5: How do Servlets manage user state and sessions?
**Answer**:
> "HTTP is a stateless protocol. ShopSphere tracks user state using `HttpSession`:
> - Upon successful login, `request.changeSessionId()` is invoked to prevent Session Fixation attacks.
> - The authenticated `User` object and role are saved into the session (`session.setAttribute(\"user\", user)`).
> - On logout, `session.invalidate()` destroys the session.
> - `AuthenticationFilter` checks `session.getAttribute(\"user\")` on every protected request.
> - Non-sensitive state, such as recently viewed product IDs, is stored in a client-side `HttpOnly` cookie."

### Q6: What is the Servlet Lifecycle? Where is it demonstrated?
**Answer**:
> "The Servlet lifecycle consists of four phases:
> 1. **Loading and Instantiation**: Tomcat loads the servlet class and instantiates it.
> 2. **Initialization (`init(ServletConfig)`)**: Called once when the servlet is created.
> 3. **Request Handling (`service()`, `doGet()`, `doPost()`)**: Invoked concurrently for each incoming client request.
> 4. **Destruction (`destroy()`)**: Called once when Tomcat unloads or stops the application.
> This is demonstrated in `ServletConfigDemoServlet.java` where we override `init()`, `doGet()`, and `destroy()`."

### Q7: Explain Java RMI and how it works in your project.
**Answer**:
> "Remote Method Invocation (RMI) enables a Java program on one JVM to invoke methods on an object in another JVM.
> 1. **Remote Interface**: `InventoryService` extends `java.rmi.Remote` with methods declaring `throws RemoteException`.
> 2. **Implementation**: `InventoryServiceImpl` implements the interface and executes JDBC database queries.
> 3. **RMI Registry**: `InventoryServer` creates or binds to the RMI registry on port 1099 using `LocateRegistry.createRegistry(1099)` and exports the stub via `Naming.rebind(\"rmi://localhost/InventoryService\", stub)`.
> 4. **Client**: `InventoryClient` looks up the remote stub via `Naming.lookup()` and calls methods like `service.getProduct(id)` as if it were a local object."

### Q8: What is Object Serialization and why is it needed in the network module?
**Answer**:
> "Serialization is the process of converting an in-memory Java object into a byte stream for storage or network transmission. Deserialization restores the object in another JVM.
> In our `network` module, `Product` and `Order` implement `java.io.Serializable` with a `serialVersionUID`. When `InventoryClient` requests `GET_PRODUCT 1` or `GET_ORDER 1`, `InventoryServer` writes the object to `ObjectOutputStream`. The client reads it back with `ObjectInputStream.readObject()`, preserving all object fields and types."

### Q9: How are transactions handled during checkout?
**Answer**:
> "Checkout touches multiple tables: `orders`, `order_items`, `products`, `coupon_usage`, `payments`, and `cart`.
> In `CheckoutService.java`, we invoke `connection.setAutoCommit(false)`. If any step fails (e.g., stock is insufficient or a coupon is invalid), `connection.rollback()` is executed to ensure atomic ACID compliance. When all steps succeed, `connection.commit()` commits the changes simultaneously."

### Q10: How does Java Internationalization (i18n) work in ShopSphere?
**Answer**:
> "We use `java.util.ResourceBundle` and `java.util.Locale`.
> Two resource bundles exist: `messages_en.properties` (English) and `messages_hi.properties` (Hindi in Devanagari script).
> In `InternationalizationServlet.java`, when a user accesses `?lang=hi`, the servlet loads `ResourceBundle.getBundle(\"messages\", new Locale(\"hi\", \"IN\"))`, formats currency using `NumberFormat.getCurrencyInstance(locale)`, formats dates using `DateTimeFormatter`, and forwards the localized strings to `webapp/syllabus/i18n.jsp`."

---

## 3. RTU SYLLABUS CHECKLIST FOR VIVA

- [x] **Swing**: Desktop Admin application with `JFrame`, `JPanel`, `JTable`, `JTabbedPane`, `JOptionPane`
- [x] **MVC Architecture**: Web (JSP-Servlet-Service-DAO) and Swing MVC
- [x] **JDBC & Driver**: MySQL Connector/J 9.3.0, `PreparedStatement`, `ResultSet`, connection configuration
- [x] **Client-Server (java.net)**: `ServerSocket` and `Socket` inventory server on port 5050
- [x] **Serialization**: `ObjectOutputStream` / `ObjectInputStream` transferring `Product` and `Order` objects
- [x] **RMI & RMI Registry**: `InventoryService`, `InventoryServer` (port 1099), `InventoryClient`
- [x] **Naming / JNDI**: `InitialContext.lookup(\"java:comp/env/jdbc/ShopSphere\")`
- [x] **URL / URLConnection**: `UrlConnectionDemo.java` demonstrating protocol stream reading
- [x] **Internationalization**: Locale switching, ResourceBundle, formatted prices & dates
- [x] **J2EE / N-Tier Architecture**: Presentation &rarr; Controller &rarr; Service &rarr; DAO &rarr; MySQL
- [x] **Web Container & Servlets**: Apache Tomcat 11, `init/doGet/doPost/destroy` lifecycle
- [x] **Sessions & Cookies**: `HttpSession`, session fixation protection, `HttpOnly` cookies
- [x] **Filters & Listeners**: `AuthenticationFilter`, `AdminFilter`, `AppListener`, `SessionListener`
- [x] **JSP Elements**: Scriptlets, declarations, expressions, comments in `jsp-elements.jsp`
- [x] **Expression Language (EL)**: `${product.name}`, `${sessionScope.user.name}`
- [x] **Tag Files & Components**: `productCard.tag`, `rating.tag`
- [x] **JSP Fragments**: `header.jsp`, `navbar.jsp`, `footer.jsp`
- [x] **JSTL Tags**: `<c:forEach>`, `<c:if>`, `<c:choose>`, JSTL Functions (`fn:split`), JSTL XML, JSTL SQL
