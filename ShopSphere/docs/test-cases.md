# ShopSphere test cases

Run these after importing `database/shopsphere.sql`, setting the three `SHOPSPHERE_DB_*` environment variables, and deploying `web/target/shopsphere.war` to Tomcat 11.

| ID | Action | Expected result |
|---|---|---|
| TC-01 | Register with a new email and an 8+ character password | Customer account is created; password is stored as a PBKDF2 hash. |
| TC-02 | Register again with the same email | Registration shows a duplicate-email error. |
| TC-03 | Log in as customer | A session is created and Products opens. |
| TC-04 | Open `/cart` without a session | Browser redirects to Login. |
| TC-05 | Search, filter, and sort products | Only matching active products are shown in the requested order. |
| TC-06 | Add, update, and remove a cart item | Cart changes only for the signed-in user and never exceeds stock. |
| TC-07 | Create an address and check out | Order, order items, mock payment, and stock update are committed together. |
| TC-08 | Apply an invalid or expired coupon | Checkout rejects it without placing an order. |
| TC-09 | Log in as a customer and open `/admin/dashboard` | Server returns Forbidden. |
| TC-10 | Log in as admin and update an order status | New status appears in customer order history. |
| TC-11 | Run InventoryServer and InventoryClient | `GET_PRODUCT`, `GET_STOCK`, and `SEARCH_PRODUCT` return inventory data. |
| TC-12 | Run the RMI inventory server then its client | Client obtains the remote product/stock result. |
| TC-13 | Run Swing Admin | Login, products, categories, customers, orders, and low-stock inventory screens load from MySQL. |
