# ShopSphere — Application Screenshots & UI Walkthrough Guide

This document catalogs all screens and views of the **ShopSphere** E-Commerce platform across the Web Customer Portal, Admin Console, Swing Desktop Client, Network Socket Terminal, and RTU Syllabus Demonstrations.

---

## 1. Web Customer Application

### 1.1 Home Page (`/`)
- **URL**: `http://localhost:8080/shopsphere/`
- **Description**: Features hero banner highlighting current shopping campaigns, promotional discount cards, quick category filters (Electronics, Fashion, Home & Kitchen, Books), and top featured products.
- **Key Components**: Top navigation bar with live cart item counter, category pills, product grid cards using `<tags:productCard>`, and site footer.

### 1.2 Customer & Admin Login (`/login`)
- **URL**: `http://localhost:8080/shopsphere/login`
- **Test Credentials**:
  - Customer: `john.doe@example.com` / `Customer@123`
  - Admin: `admin@shopsphere.com` / `Admin@123`
- **Features**: Dual-mode authentication, server-side PBKDF2 password validation, automatic redirection based on role (`/products` for customers, `/admin/dashboard` for admins).

### 1.3 User Registration (`/register`)
- **URL**: `http://localhost:8080/shopsphere/register`
- **Features**: Responsive registration form with server-side duplicate email detection, mobile number format check, and automated SHA-256 salt generation.

### 1.4 Product Catalog & Search (`/products`)
- **URL**: `http://localhost:8080/shopsphere/products`
- **Features**: Real-time keyword search, category filtering dropdown, price/name sorting (`price_asc`, `price_desc`, `name_asc`), and stock availability badges.

### 1.5 Product Details & Reviews (`/product-details?id=1`)
- **URL**: `http://localhost:8080/shopsphere/product-details?id=1`
- **Features**: High-resolution image preview, discount pricing badge, stock quantity counter, recently viewed products carousel (via `recentlyViewed` HTTP Cookie), customer ratings `<tags:rating>`, and review submission form.

### 1.6 Shopping Cart (`/cart`)
- **URL**: `http://localhost:8080/shopsphere/cart`
- **Features**: Per-item quantity increment/decrement, server-side stock validation, real-time subtotal calculation, remove item confirmation, and proceed to checkout CTA.

### 1.7 Checkout & Mock Payment (`/checkout`)
- **URL**: `http://localhost:8080/shopsphere/checkout`
- **Features**: Delivery address selection or new address creation, promo coupon validation (`WELCOME10`, `FESTIVE20`, `FLAT50`), server-side order calculation, payment gateway simulation (Credit Card / UPI / COD), and transactional order creation.

### 1.8 Order Confirmation (`/orders`)
- **URL**: `http://localhost:8080/shopsphere/orders`
- **Features**: Chronological order history table with order IDs, timestamps, total amounts, payment status badges, and "View Details" links.

### 1.9 Order Details & Tracking (`/order-details?id=1`)
- **URL**: `http://localhost:8080/shopsphere/order-details?id=1`
- **Features**: Itemized invoice listing purchased products with individual quantities and prices, delivery address block, and progressive order tracking timeline (`PLACED` -> `PROCESSING` -> `SHIPPED` -> `DELIVERED`).

### 1.10 User Profile (`/profile`)
- **URL**: `http://localhost:8080/shopsphere/profile`
- **Features**: Displays user registration metadata, contact information, role badge, and saved shipping addresses.

### 1.11 Wishlist (`/wishlist`)
- **URL**: `http://localhost:8080/shopsphere/wishlist`
- **Features**: Saved items for future purchase with direct "Move to Cart" and "Remove" actions.

---

## 2. Web Admin Module (`/admin/*`)

### 2.1 Admin Dashboard (`/admin/dashboard`)
- **URL**: `http://localhost:8080/shopsphere/admin/dashboard`
- **Features**: Statistical summary cards showing Total Customers, Total Products, Total Orders, Total Revenue, and critical Low-Stock Alert table.

### 2.2 Product Management (`/admin/products`)
- **URL**: `http://localhost:8080/shopsphere/admin/products`
- **Features**: Comprehensive tabular view of all catalog items, Add New Product modal/form, Edit Product dialog, and Delete/Deactivate operations.

### 2.3 Category Management (`/admin/categories`)
- **URL**: `http://localhost:8080/shopsphere/admin/categories`
- **Features**: Category listing with product counts, Add Category form, and status toggle.

### 2.4 Order Management (`/admin/orders`)
- **URL**: `http://localhost:8080/shopsphere/admin/orders`
- **Features**: Master order list with customer details, order amounts, and inline dropdown to update status (`PLACED`, `PROCESSING`, `SHIPPED`, `DELIVERED`, `CANCELLED`).

### 2.5 Customer User List (`/admin/users`)
- **URL**: `http://localhost:8080/shopsphere/admin/users`
- **Features**: Registered user directory with roles, registration dates, and account status controls.

---

## 3. Swing Desktop Admin Application

### 3.1 Login Frame (`LoginFrame`)
- **Component**: `com.shopsphere.swing.view.LoginFrame`
- **Features**: `JFrame` with System Look-and-Feel, `JTextField`, `JPasswordField`, `JButton`, and `JOptionPane` error dialogs. Authenticates directly against MySQL database using PBKDF2 hash verification.

### 3.2 Desktop Management Dashboard (`AdminDashboard`)
- **Component**: `com.shopsphere.swing.view.AdminDashboard`
- **Features**: `JTabbedPane` containing:
  - **Product Panel**: `JTable` inside `JScrollPane` with Add, Edit, and Delete action buttons and pagination.
  - **Category Panel**: Category management table with Add and Edit controls.
  - **Customer Panel**: Read-only listing of registered customers.
  - **Order Panel**: Order fulfillment table with status modifier dialogs.
  - **Inventory Panel**: Real-time stock audit highlighting low-inventory items in red.

---

## 4. Java Networking & Distributed Modules

### 4.1 Socket Inventory Server & Client (`network`)
- **Server**: `com.shopsphere.network.server.InventoryServer` (Port 9090)
- **Client**: `com.shopsphere.network.client.InventoryClient`
- **Commands**:
  - `GET_STOCK <product_id>` -> Returns integer stock count.
  - `GET_PRODUCT <product_id>` -> Returns formatted product details.
  - `GET_ORDER <order_id>` -> Transmits serialized `com.shopsphere.network.model.Order` object over `ObjectOutputStream` / `ObjectInputStream`.
  - `SEARCH_PRODUCT <keyword>` -> Returns newline-delimited matches.

### 4.2 RMI Remote Inventory Registry (`rmi`)
- **Registry & Server**: `com.shopsphere.rmi.server.InventoryServer` (Port 1099, service `InventoryService`)
- **Remote Interface**: `com.shopsphere.rmi.remote.InventoryService`
- **Client**: `com.shopsphere.rmi.client.InventoryClient`
- **Remote Methods**:
  - `getProduct(int id)`
  - `getStock(int productId)`
  - `updateStock(int productId, int quantity)`

---

## 5. RTU Syllabus Feature Demonstration Pages

| Endpoint | Topic | Expected Screen Output |
|---|---|---|
| `/syllabus/servlet-config` | `ServletConfig` & `ServletContext` | Displays context parameter `appName=ShopSphere` and servlet init-param `demoValue`. |
| `/syllabus/jndi` | JNDI DataSource Lookup | Queries `java:comp/env/jdbc/ShopSphere` or returns graceful evaluator setup instructions. |
| `/syllabus/i18n?lang=hi` | Internationalization (`Locale` & `ResourceBundle`) | Displays Hindi greeting `शॉपस्फ़ेयर में आपका स्वागत है`, local date, and currency `₹1,299.50`. |
| `/syllabus/jsp-elements.jsp` | Traditional JSP Syntax | Demonstrates declaration `<%! %>`, scriptlet `<% %>`, expression `<%= %>`, and comment `<%-- %>`. |
| `/syllabus/jstl-core.jsp` | JSTL Core Tag Library | Iterates mock product list using `<c:forEach>`, `<c:if>`, and `<c:choose>`. |
| `/syllabus/jstl-sql.jsp` | JSTL SQL Tag Library | Demonstrates declarative SQL tag concepts with safety fallback. |
| `/syllabus/jstl-xml.jsp` | JSTL XML Tag Library | Parses sample XML product feed using `<x:parse>`, `<x:forEach>`, and `<x:out>`. |
| `/syllabus/applets.jsp` | Java Applets Historical Overview | Architectural discussion of legacy Applets vs. modern Jakarta web containers. |
