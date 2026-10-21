# ShopSphere Database Design & Entity-Relationship (ER) Diagram

This document models the relational schema for ShopSphere based on Section 8 of the *RTU Advanced Java Project Development Guide*.

---

## Entity-Relationship Diagram

```mermaid
erDiagram
    USERS ||--o{ ADDRESSES : "has"
    USERS ||--o{ CART : "maintains"
    USERS ||--o{ WISHLIST : "saves"
    USERS ||--o{ ORDERS : "places"
    USERS ||--o{ REVIEWS : "writes"
    USERS ||--o{ COUPON_USAGE : "redeems"

    CATEGORIES ||--o{ PRODUCTS : "classifies"

    PRODUCTS ||--o{ CART : "added_to"
    PRODUCTS ||--o{ WISHLIST : "stored_in"
    PRODUCTS ||--o{ ORDER_ITEMS : "ordered_in"
    PRODUCTS ||--o{ REVIEWS : "reviewed_in"

    ORDERS ||--|{ ORDER_ITEMS : "contains"
    ORDERS ||--|| PAYMENTS : "associated_with"
    ORDERS ||--o| COUPON_USAGE : "applied_in"
    ADDRESSES ||--o{ ORDERS : "delivered_to"

    COUPONS ||--o{ COUPON_USAGE : "tracks"

    USERS {
        bigint user_id PK
        varchar name
        varchar email UK
        varchar password "PBKDF2 hash"
        varchar mobile
        enum role "CUSTOMER, ADMIN"
        enum status "ACTIVE, DISABLED"
        timestamp created_at
    }

    ADDRESSES {
        bigint address_id PK
        bigint user_id FK
        varchar address_line
        varchar city
        varchar state
        varchar pincode
        enum address_type "HOME, WORK, OTHER"
    }

    CATEGORIES {
        bigint category_id PK
        varchar name UK
        text description
        enum status "ACTIVE, INACTIVE"
    }

    PRODUCTS {
        bigint product_id PK
        bigint category_id FK
        varchar name
        varchar brand
        text description
        decimal price
        decimal discount
        int stock
        varchar image_url
        enum status "ACTIVE, INACTIVE"
        timestamp created_at
    }

    CART {
        bigint cart_id PK
        bigint user_id FK
        bigint product_id FK
        int quantity
    }

    WISHLIST {
        bigint wishlist_id PK
        bigint user_id FK
        bigint product_id FK
        timestamp created_at
    }

    ORDERS {
        bigint order_id PK
        bigint user_id FK
        bigint address_id FK
        decimal total_amount
        decimal discount
        varchar payment_method
        enum payment_status "PENDING, PAID, FAILED, REFUNDED"
        enum order_status "PLACED, PROCESSING, SHIPPED, DELIVERED, CANCELLED"
        timestamp created_at
    }

    ORDER_ITEMS {
        bigint order_item_id PK
        bigint order_id FK
        bigint product_id FK
        int quantity
        decimal price
    }

    PAYMENTS {
        bigint payment_id PK
        bigint order_id FK, UK
        varchar payment_method
        varchar transaction_reference
        decimal amount
        enum payment_status "PENDING, SUCCESS, FAILED, REFUNDED"
        timestamp created_at
    }

    REVIEWS {
        bigint review_id PK
        bigint user_id FK
        bigint product_id FK
        tinyint rating "1 to 5"
        text review_text
        timestamp created_at
    }

    COUPONS {
        bigint coupon_id PK
        varchar code UK
        enum discount_type "PERCENT, FIXED"
        decimal discount_value
        decimal minimum_order
        decimal maximum_discount
        datetime expiry_date
        enum status "ACTIVE, INACTIVE"
    }

    COUPON_USAGE {
        bigint usage_id PK
        bigint coupon_id FK
        bigint user_id FK
        bigint order_id FK, UK
        timestamp used_at
    }
```

---

## Schema Summary and Constraints

| Table | Primary Key | Foreign Keys | Key Integrity Constraints |
|---|---|---|---|
| `users` | `user_id` | - | `email UNIQUE`, password stored as PBKDF2 hash |
| `addresses` | `address_id` | `user_id` &rarr; `users.user_id` (CASCADE) | Delivery destination tied to user |
| `categories` | `category_id` | - | `name UNIQUE` |
| `products` | `product_id` | `category_id` &rarr; `categories.category_id` (RESTRICT) | `CHECK (price >= 0)`, `CHECK (discount BETWEEN 0 AND 100)`, `CHECK (stock >= 0)` |
| `cart` | `cart_id` | `user_id`, `product_id` (CASCADE) | `UNIQUE (user_id, product_id)`, `CHECK (quantity > 0)` |
| `wishlist` | `wishlist_id` | `user_id`, `product_id` (CASCADE) | `UNIQUE (user_id, product_id)` |
| `orders` | `order_id` | `user_id`, `address_id` (RESTRICT) | `CHECK (total_amount >= 0)` |
| `order_items` | `order_item_id` | `order_id` (CASCADE), `product_id` (SET NULL) | `CHECK (quantity > 0)` |
| `payments` | `payment_id` | `order_id` (CASCADE) | `order_id UNIQUE`, `CHECK (amount >= 0)` |
| `reviews` | `review_id` | `user_id`, `product_id` (CASCADE) | `UNIQUE (user_id, product_id)`, `CHECK (rating BETWEEN 1 AND 5)` |
| `coupons` | `coupon_id` | - | `code UNIQUE`, `CHECK (discount_value > 0)`, `CHECK (minimum_order >= 0)` |
| `coupon_usage` | `usage_id` | `coupon_id` (RESTRICT), `user_id` (RESTRICT), `order_id` (CASCADE) | `order_id UNIQUE` (prevents double logging per order) |
