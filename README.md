# 🎓 Advanced Java Laboratory & Project

Welcome to the **Advanced Java Programming** practical laboratory repository. This repository contains all academic lab programs organized with clear, meaningful folder names, along with the complete Java project (**ShopSphere**).

---

## 📁 Repository Contents

| Folder Name | Topic / Description | Key Files Included |
| :--- | :--- | :--- |
| 📁 [`Student-Registration-Form-Swing`](./Student-Registration-Form-Swing) | Student Registration Form using Java Swing GUI | `StudentRegistration.java` |
| 📁 [`Basic-WebApps-Tomcat`](./Basic-WebApps-Tomcat) | Basic Web Application for Apache Tomcat | `index.html` |
| 📁 [`JdbcProject`](./JdbcProject) | Java Database Connectivity (JDBC) with MySQL | `src/`, `lib/mysql-connector-j.jar` |
| 📁 [`Server-and-Client-Socket`](./Server-and-Client-Socket) | TCP Socket Programming (Client & Server) | `Server.java`, `Client.java` |
| 📁 [`RMI`](./RMI) | Remote Method Invocation (Java RMI) | `MyInterface.java`, `MyRemote.java`, `Server.java`, `Client.java` |
| 📁 [`MVC-Calculator`](./MVC-Calculator) | Swing Calculator following MVC Architecture | `CalculatorModel.java`, `CalculatorView.java`, `CalculatorController.java`, `Main.java` |
| 📁 [`Servlet`](./Servlet) | Java Servlet Architecture (GET, POST, Session, Filter, Listener) | `HelloServlet.java`, `LoginServlet.java`, `AuthFilter.java`, `pom.xml` |
| 📁 [`HTML-to-JSP`](./HTML-to-JSP) | Converting HTML Form to JSP with Expression Language (EL) | `student.jsp`, `result.jsp`, `HelloServlet.java` |
| 📁 [`ShopSphere`](./ShopSphere) | **Major Java Project**: Complete E-Commerce Application | Full Project (`web/`, `swing-admin/`, `database/`, `network/`, `rmi/`) |

---

## 🛠️ Overview of Programs

### 1. Student-Registration-Form-Swing
* **Description:** Interactive GUI registration form designed with Java Swing.
* **Features:** Name, Roll Number, Gender radio buttons (`ButtonGroup`), Branch textfield, Terms checkbox, Submit/Reset buttons with validations.

### 2. Basic-WebApps-Tomcat
* **Description:** Basic HTML web application deployed on Apache Tomcat 11.
* **URL:** `http://localhost:8080/mywebapps/index.html`

### 3. JdbcProject
* **Description:** Connects Java with MySQL using JDBC driver (`mysql-connector-j`).
* **Features:** Creates connection with `DriverManager`, executes SQL queries with `PreparedStatement`, and retrieves results via `ResultSet`.

### 4. Server-and-Client-Socket
* **Description:** Client-Server networking application using TCP sockets over port 5000 (`java.net`).
* **Flow:** `Server.java` listens on port 5000, `Client.java` connects and exchanges messages (`"Hello Server"` ➔ `"Hello Client"`).

### 5. RMI
* **Description:** Remote Method Invocation allowing client program to invoke methods on a remote server object.
* **Files:** Remote Interface (`MyInterface`), Implementation (`MyRemote`), `Server` (binds to RMI registry), and `Client` (looks up and executes remote `add()` method).

### 6. MVC-Calculator
* **Description:** Desktop arithmetic calculator strictly separating concerns into Model, View, and Controller.
* **Components:** 
  * `Model`: Performs arithmetic operations (`+`, `-`, `*`, `/`).
  * `View`: Swing GUI window (`JFrame`, inputs, buttons).
  * `Controller`: Handles button clicks (`ActionListener`) and coordinates between View and Model.

### 7. Servlet
* **Description:** Web server controllers handling HTTP requests using Jakarta Servlet API.
* **Topics:** GET/POST handling, Session management (`HttpSession`), Authentication Filters (`AuthFilter`), and Lifecycle Listeners.

### 8. HTML-to-JSP
* **Description:** Conversion of HTML form into JavaServer Pages (JSP).
* **Flow:** `student.jsp` takes input ➔ `HelloServlet` sets request attributes ➔ `result.jsp` displays data using Expression Language (`${name}`, `${email}`, `${course}`, `${age}`).

---

## 🚀 Major Project: ShopSphere
* **Description:** Enterprise Java application with a web-based e-commerce platform and a desktop Swing-based admin management portal.
* **Architecture:** Multi-tier Java architecture with relational database connectivity, distributed services, and networking.

---

## 👨‍💻 Author & Submission Details
* **Student Name:** Harshit Ghiya
* **GitHub Profile:** [@harshbki](https://github.com/harshbki)
* **Google Form Link:** [Submit Repository](https://forms.gle/QPc4Ua1qK3FwrqTg9)
