CREATE DATABASE IF NOT EXISTS shopsphere
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE shopsphere;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(190) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    mobile VARCHAR(25),
    role ENUM('CUSTOMER', 'ADMIN') NOT NULL DEFAULT 'CUSTOMER',
    status ENUM('ACTIVE', 'DISABLED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS addresses (
    address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    address_line VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pincode VARCHAR(20) NOT NULL,
    address_type ENUM('HOME', 'WORK', 'OTHER') NOT NULL DEFAULT 'HOME',
    CONSTRAINT fk_addresses_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL UNIQUE,
    description TEXT,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE'
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    name VARCHAR(180) NOT NULL,
    brand VARCHAR(120),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    discount DECIMAL(5,2) NOT NULL DEFAULT 0,
    stock INT NOT NULL DEFAULT 0,
    image_url VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_products_price CHECK (price >= 0),
    CONSTRAINT chk_products_discount CHECK (discount >= 0 AND discount <= 100),
    CONSTRAINT chk_products_stock CHECK (stock >= 0),
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(category_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_products_category_status (category_id, status),
    INDEX idx_products_name (name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cart (
    cart_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    CONSTRAINT chk_cart_quantity CHECK (quantity > 0),
    CONSTRAINT uq_cart_user_product UNIQUE (user_id, product_id),
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cart_product FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS wishlist (
    wishlist_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_wishlist_user_product UNIQUE (user_id, product_id),
    CONSTRAINT fk_wishlist_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    discount DECIMAL(10,2) NOT NULL DEFAULT 0,
    payment_method VARCHAR(40) NOT NULL DEFAULT 'MOCK',
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    order_status ENUM('PLACED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL DEFAULT 'PLACED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_orders_amount CHECK (total_amount >= 0),
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES addresses(address_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_orders_user_created (user_id, created_at)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS order_items (
    order_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT chk_order_item_quantity CHECK (quantity > 0),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS payments (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL UNIQUE,
    payment_method VARCHAR(40) NOT NULL,
    transaction_reference VARCHAR(120),
    amount DECIMAL(10,2) NOT NULL,
    payment_status ENUM('PENDING', 'SUCCESS', 'FAILED', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_payments_amount CHECK (amount >= 0),
    CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reviews (
    review_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    rating TINYINT NOT NULL,
    review_text TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_reviews_rating CHECK (rating BETWEEN 1 AND 5),
    CONSTRAINT uq_reviews_user_product UNIQUE (user_id, product_id),
    CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_reviews_product FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS coupons (
    coupon_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(60) NOT NULL UNIQUE,
    discount_type ENUM('PERCENT', 'FIXED') NOT NULL,
    discount_value DECIMAL(10,2) NOT NULL,
    minimum_order DECIMAL(10,2) NOT NULL DEFAULT 0,
    maximum_discount DECIMAL(10,2),
    expiry_date DATETIME,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT chk_coupons_value CHECK (discount_value > 0),
    CONSTRAINT chk_coupons_minimum CHECK (minimum_order >= 0),
    CONSTRAINT chk_coupons_maximum CHECK (maximum_discount IS NULL OR maximum_discount >= 0)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS coupon_usage (
    usage_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    coupon_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL UNIQUE,
    used_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_coupon_usage_coupon FOREIGN KEY (coupon_id) REFERENCES coupons(coupon_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_coupon_usage_user FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_coupon_usage_order FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =====================================================================
-- INITIAL SEED DATA (RTU Advanced Java ShopSphere Academic Demonstration)
-- =====================================================================

-- 1. Default Users (Pre-hashed passwords using PBKDF2WithHmacSHA256)
-- Admin account: admin@shopsphere.com / Admin@123
-- Customer account: customer@shopsphere.com / Customer@123
INSERT IGNORE INTO users (user_id, name, email, password, mobile, role, status) VALUES
(1, 'Administrator', 'admin@shopsphere.com', 'pbkdf2-sha256$210000$zxufe4Z2nmz5aX/9b3PD5w==$R0GCB1+ORClVSHAzzZVB6mB/toRVXjoMCezASgUqqgU=', '9876543210', 'ADMIN', 'ACTIVE'),
(2, 'Priya Sharma', 'customer@shopsphere.com', 'pbkdf2-sha256$210000$sr1IVoYeX02U5r4Jy7dpFw==$t2c7cmYii3b+UvNaBHWN+9bhpRxZQnhw9WN9dQApar0=', '9876543211', 'CUSTOMER', 'ACTIVE');

-- 2. Customer Saved Delivery Addresses
INSERT IGNORE INTO addresses (address_id, user_id, address_line, city, state, pincode, address_type) VALUES
(1, 2, 'Flat 402, Sunshine Residency, MI Road', 'Jaipur', 'Rajasthan', '302001', 'HOME'),
(2, 2, 'Tech Park Phase 1, Sitapura Industrial Area', 'Jaipur', 'Rajasthan', '302022', 'WORK');

-- 3. Product Categories
INSERT IGNORE INTO categories (category_id, name, description, status) VALUES
(1, 'Electronics', 'Smartphones, audio gear, computing accessories, and gadgets', 'ACTIVE'),
(2, 'Fashion', 'Men and women apparel, daily wear, and footwear', 'ACTIVE'),
(3, 'Home & Kitchen', 'Home decor, desk accessories, and kitchen essentials', 'ACTIVE'),
(4, 'Books & Stationery', 'Academic textbooks, technical guides, notebooks, and pens', 'ACTIVE');

-- 4. Sample Products
INSERT IGNORE INTO products (product_id, category_id, name, brand, description, price, discount, stock, image_url, status) VALUES
(1, 1, 'Wireless Headphones', 'SoundMax', 'High-definition wireless headphones with active noise cancellation and 30-hour battery life.', 1999.00, 10.00, 24, 'images/products/p1.webp', 'ACTIVE'),
(2, 1, 'Smart Fitness Watch', 'PulseTech', 'Track steps, heart rate, sleep quality, and workouts with a crisp OLED display and 7-day battery.', 3499.00, 15.00, 18, 'images/products/p2.webp', 'ACTIVE'),
(3, 1, 'Mechanical Gaming Keyboard', 'KeyCrafter', 'Tactile mechanical switches, customizable RGB backlighting, and detachable USB-C braided cable.', 2899.00, 5.00, 12, 'images/products/p3.png', 'ACTIVE'),
(4, 2, 'Classic Cotton T-Shirt', 'UrbanStyle', '100% combed organic cotton regular-fit crewneck t-shirt. Breathable and comfortable for all-day wear.', 599.00, 20.00, 40, 'images/products/p4.png', 'ACTIVE'),
(5, 2, 'Slim Fit Denim Jeans', 'DenimCo', 'Classic 5-pocket stretch denim with durable stitching and modern tapered slim cut.', 1499.00, 15.00, 20, 'images/products/p5.png', 'ACTIVE'),
(6, 2, 'Breathable Running Shoes', 'AeroStep', 'Lightweight cushioning with breathable mesh upper and slip-resistant rubber outsole.', 2199.00, 10.00, 15, 'images/products/p6.webp', 'ACTIVE'),
(7, 3, 'Minimalist LED Desk Lamp', 'LumiHome', 'Touch-controlled dimmable desk lamp with multiple color temperature modes and flexible gooseneck.', 850.00, 0.00, 30, 'images/products/p7.webp', 'ACTIVE'),
(8, 3, 'Stainless Steel Water Bottle', 'HydroSteel', 'Vacuum-insulated double-wall 750ml flask keeping beverages cold for 24h or hot for 12h.', 450.00, 5.00, 50, 'images/products/p8.webp', 'ACTIVE'),
(9, 4, 'Java Advanced Programming Guide', 'TechPress', 'Comprehensive guide covering Servlets, JSP, JDBC, JSTL, Swing, Sockets, and RMI.', 750.00, 10.00, 25, 'images/products/p9.jpg', 'ACTIVE'),
(10, 4, 'Hardcover Dotted Journal', 'NoteCraft', 'A5 160 GSM bleed-resistant dotted pages with ribbon bookmark and elastic closure band.', 320.00, 0.00, 35, 'images/products/p10.jpg', 'ACTIVE'),
(11, 1, '4K Ultra HD Action Camera', 'GoPro', 'Waterproof compact action camera featuring HyperSmooth video stabilization, front and rear touchscreens, and voice control.', 12499.00, 15.00, 8, 'images/products/p11.png', 'ACTIVE'),
(12, 1, 'True Wireless Stereo Earbuds', 'SoundMax', 'Compact wireless earbuds with deep bass drivers, IPX5 sweat resistance, touch controls, and 28-hour charging case.', 1499.00, 20.00, 35, 'images/products/p12.webp', 'ACTIVE'),
(13, 2, 'Vintage Chronograph Leather Watch', 'Fossil', 'Classic analogue watch with stainless steel bezel, genuine brown leather strap, water resistance, and stopwatch dials.', 5499.00, 18.00, 14, 'images/products/p13.webp', 'ACTIVE'),
(14, 2, 'Classic Aviator Sunglasses UV400', 'Ray-Ban', 'Iconic metal-frame polarized sunglasses offering complete 100% UV400 eye protection and scratch-resistant lenses.', 3299.00, 10.00, 22, 'images/products/p14.webp', 'ACTIVE'),
(15, 2, 'Pure Oxford Cotton Casual Shirt', 'Arrow', 'Tailored pure cotton button-down casual shirt designed for comfort, breathable weave, and all-day office elegance.', 1299.00, 25.00, 30, 'images/products/p15.webp', 'ACTIVE'),
(16, 3, 'Ergonomic Mesh High-Back Chair', 'Green Soul', 'Adjustable lumbar support with breathable mesh backrest, pneumatic height adjustment, and multi-lock recline mechanism.', 6999.00, 12.00, 10, 'images/products/p16.webp', 'ACTIVE'),
(17, 3, 'Handcrafted Ceramic Coffee Mug Set', 'ClayCraft', 'Pair of handcrafted ceramic mugs with smooth matte glaze, comfortable grip handles, microwave and dishwasher safe.', 499.00, 5.00, 40, 'images/products/p17.jpg', 'ACTIVE'),
(18, 4, 'Clean Code: Handbook of Agile Craft', 'Robert C. Martin', 'Renowned software craftsmanship guide on writing readable, maintainable, testable, and robust enterprise software.', 899.00, 10.00, 18, 'images/products/p18.jpg', 'ACTIVE');

-- 5. Promotional Coupons
INSERT IGNORE INTO coupons (coupon_id, code, discount_type, discount_value, minimum_order, maximum_discount, expiry_date, status) VALUES
(1, 'WELCOME10', 'PERCENT', 10.00, 500.00, 200.00, '2030-12-31 23:59:59', 'ACTIVE'),
(2, 'FLAT100', 'FIXED', 100.00, 999.00, 100.00, '2030-12-31 23:59:59', 'ACTIVE'),
(3, 'FESTIVE20', 'PERCENT', 20.00, 1500.00, 500.00, '2030-12-31 23:59:59', 'ACTIVE');

-- 6. Sample Product Reviews
INSERT IGNORE INTO reviews (review_id, user_id, product_id, rating, review_text) VALUES
(1, 2, 1, 5, 'Exceptional audio clarity and super comfortable earcups. Definitely worth the price!'),
(2, 2, 2, 4, 'Great fitness tracker, battery easily lasts a whole week with regular use.');
