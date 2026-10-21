# 🎓 Advanced Java Laboratory & Project

Welcome to the **Advanced Java Programming** practical laboratory repository. This repository contains all academic lab programs organized with clear, meaningful folder names, along with the complete enterprise Java project (**ShopSphere**).

---

## 📌 Repository Overview & Contents

This repository is organized to showcase practical implementations across core enterprise Java technologies:

| Folder Name | Topic / Description | Key Technologies & Files |
| :--- | :--- | :--- |
| 📁 [`Student-Registration-Form-Swing`](./Student-Registration-Form-Swing) | GUI Student Registration Form | Java Swing (`JFrame`, `JTextField`, `JRadioButton`, `ButtonGroup`, `JCheckBox`) |
| 📁 [`Basic-WebApps-Tomcat`](./Basic-WebApps-Tomcat) | Basic Web Application Deployment | HTML5, Apache Tomcat Webapps (`index.html`) |
| 📁 [`JdbcProject`](./JdbcProject) | Database Connectivity with MySQL | JDBC API, `DriverManager`, `PreparedStatement`, `mysql-connector-j` |
| 📁 [`Server-and-Client-Socket`](./Server-and-Client-Socket) | TCP Socket Network Programming | `java.net.Socket`, `ServerSocket`, Port 5000 (`Server.java`, `Client.java`) |
| 📁 [`RMI`](./RMI) | Remote Method Invocation | `java.rmi.*`, Remote Interface, `UnicastRemoteObject`, `rmiregistry` |
| 📁 [`MVC-Calculator`](./MVC-Calculator) | Swing Calculator following MVC Architecture | Model-View-Controller, `ActionListener`, `CalculatorModel.java`, `CalculatorView.java` |
| 📁 [`Servlet`](./Servlet) | Java Servlet Architecture & Lifecycle | Jakarta Servlet API, `HttpServlet`, Sessions (`HttpSession`), `AuthFilter`, Listeners |
| 📁 [`HTML-to-JSP`](./HTML-to-JSP) | HTML Form to JSP with Expression Language | JSP Directives, Expression Language (`${name}`, `${email}`), MVC Dispatcher |
| 📁 [`ShopSphere`](./ShopSphere) | **Major Java Project**: E-Commerce Platform | Multi-tier Architecture (`web/`, `swing-admin/`, `database/`, `network/`, `rmi/`) |

---

## 🚀 Major Project: ShopSphere

📁 **Directory:** [`ShopSphere`](./ShopSphere)  
**ShopSphere** is a complete, multi-tiered Java enterprise application designed for modern e-commerce and retail management.

### Project Architecture & Modules:
* **`web/`**: Full customer shopping storefront and administrative web application built using **Jakarta Servlets**, **JSP**, **JSTL**, and **Custom Tags**.
* **`swing-admin/`**: Desktop GUI management dashboard built using **Java Swing** for real-time inventory and catalog management.
* **`database/`**: Relational MySQL schema (`shopsphere.sql`) defining products, users, categories, orders, carts, and reviews.
* **`network/`**: TCP Client-Server communication module for remote status checking and server pinging.
* **`rmi/`**: Remote Method Invocation services for remote inventory and price lookup across JVMs.
* **`docs/`**: Complete system documentation, ER diagrams, use cases, architecture diagrams, and test suites.

---

## 🛠️ Detailed Lab Experiment Guide

### 1. Student-Registration-Form-Swing
* **Aim:** Design an interactive student registration window using Java Swing with comprehensive input validation.
* **Key Components:** `JFrame`, `JLabel`, `JTextField`, `JRadioButton` with `ButtonGroup` for gender, `JCheckBox` for terms agreement, and validation alerts using `JOptionPane`.
* **Execution:**
  ```bash
  cd Student-Registration-Form-Swing
  javac StudentRegistration.java
  java StudentRegistration
  ```

---

### 2. Basic-WebApps-Tomcat
* **Aim:** Deploy and host a standard web application on Apache Tomcat container.
* **Structure:** Deployed under Tomcat's `webapps/` folder with proper HTML structure and UTF-8 encoding.
* **Access URL:** `http://localhost:8080/mywebapps/index.html`

---

### 3. JdbcProject
* **Aim:** Connect Java applications to a relational MySQL database to perform CRUD operations.
* **Workflow:**
  1. Load MySQL Driver: `Class.forName("com.mysql.cj.jdbc.Driver")`
  2. Create Connection: `DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "password")`
  3. Execute Query: `PreparedStatement ps = con.prepareStatement("insert into student values(?,?,?)")`
  4. Iterate Results: `while(rs.next()) { ... }`

---

### 4. Server-and-Client-Socket
* **Aim:** Implement bidirectional network communication using TCP sockets.
* **Workflow:**
  * `Server.java`: Initializes `ServerSocket(5000)`, waits on `accept()`, receives message, and returns acknowledgment.
  * `Client.java`: Connects via `Socket("localhost", 5000)`, sends `"Hello Server"`, and prints server reply.
* **Execution:**
  ```bash
  # Terminal 1:
  java Server
  # Terminal 2:
  java Client
  ```

---

### 5. RMI (Remote Method Invocation)
* **Aim:** Enable a Java client running in one JVM to invoke methods of a remote object running in another JVM.
* **Architecture:**
  * Remote Interface: `MyInterface.java` extending `java.rmi.Remote`
  * Remote Implementation: `MyRemote.java` extending `UnicastRemoteObject`
  * Server: Binds service via `Naming.rebind("rmi://localhost/Add", obj)`
  * Client: Finds service via `Naming.lookup("rmi://localhost/Add")` and invokes remote calculation.

---

### 6. MVC-Calculator
* **Aim:** Build an arithmetic calculator strictly adhering to Model-View-Controller (MVC) architecture.
* **Separation of Concerns:**
  * **Model (`CalculatorModel.java`):** Contains arithmetic calculation methods (`add`, `subtract`, `multiply`, `divide`). Free from any GUI code.
  * **View (`CalculatorView.java`):** Pure Swing GUI presentation (`JFrame`, buttons, labels, non-editable output).
  * **Controller (`CalculatorController.java`):** Implements `ActionListener`, coordinates user clicks, retrieves data from View, invokes Model, and sets output on View.

---

### 7. Servlet Architecture
* **Aim:** Master server-side web controllers, lifecycle handling, session tracking, and request filters.
* **Topics Covered:**
  * Lifecycle: `init()`, `service()`, and `destroy()` in `LifeCycleServlet.java`.
  * Session Management: `HttpSession` for login authentication (`LoginServlet.java`) and logout invalidation (`LogoutServlet.java`).
  * Interceptors: `AuthFilter.java` and `MyFilter.java` for route protection and access logging.
  * Listeners: `AppListener.java` and `SessionListener.java` for application event monitoring.

---

### 8. HTML-to-JSP Conversion
* **Aim:** Transition from static HTML presentation and Servlet `PrintWriter` rendering to clean MVC architecture using JSP and Expression Language.
* **Workflow:**
  * Form: `student.jsp` with `<%@ page contentType="text/html;charset=UTF-8" %>` page directive.
  * Controller: `HelloServlet.java` extracts parameters via `request.getParameter()` and sets attributes using `request.setAttribute()`.
  * View: `result.jsp` renders data dynamically via Expression Language (`${name}`, `${email}`, `${course}`, `${age}`).

---

## ⚙️ Technical Environment

* **Java Development Kit:** JDK 17 / JDK 21+
* **Build Tool:** Apache Maven 3.9+
* **Application Server:** Apache Tomcat 11.x
* **Database:** MySQL Server 8.x
* **Editor / IDE:** VS Code / Eclipse / IntelliJ IDEA

---

## 👨‍💻 Author

* **Student Name:** Harshit Ghiya
* **GitHub Profile:** [@harshbki](https://github.com/harshbki)
* **Course:** Advanced Java Laboratory
