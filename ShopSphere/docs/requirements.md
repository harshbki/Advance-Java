# Requirements and screen plan

## Goal
Build ShopSphere, an e-commerce management system for the RTU Advanced Java practical project.

## Roles
- Customer: register, sign in, browse/search products, maintain a cart and wishlist, check out, and view orders.
- Admin: manage products, categories, inventory, customers, coupons, and orders.

## Core customer screens
Home, registration, login, product listing/search, product details, cart, checkout, order confirmation, order history/details, profile, and wishlist.

## Core admin screens
Admin login, dashboard, product management, category management, customer list, order management, coupon management, and low-stock view.

## Core flow to implement first
Registration/login -> product browsing -> add/update/remove cart items -> checkout -> order placement -> order history.

## Architecture
JSP/HTML -> Servlet controllers -> Service layer -> DAO layer -> JDBC -> MySQL. Swing, Socket, and RMI are separate syllabus demonstration modules.

## Security requirements
Use PreparedStatement; hash stored passwords; validate input on the server; keep authentication/cart state in HttpSession; protect customer/admin routes with filters; calculate totals on the server; never put passwords in cookies.

## UI direction
Responsive shopping navbar with search, product cards with price/discount/rating, cart count, mobile-friendly forms, clear order-status badges, and an admin dashboard.

## Decisions for implementation
- Configure MySQL credentials locally; never commit secrets.
- Seed sample products and categories for demonstrations.
- Use a mock payment method for checkout.
