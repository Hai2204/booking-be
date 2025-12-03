-- ========================================================
-- DATABASE AUTHENTICATION & AUTHORIZATION
-- ========================================================

-- =========================
-- TABLE: users
-- =========================
CREATE TABLE IF NOT EXISTS users (
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(100) UNIQUE NOT NULL,
    password        VARCHAR(255) NOT NULL,
    full_name       VARCHAR(255),
    is_active       SMALLINT DEFAULT 1 CHECK (is_active IN (0,1)),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE users IS 'Danh sách tài khoản người dùng hệ thống';
COMMENT ON COLUMN users.is_active IS '1 = hoạt động, 0 = vô hiệu hóa';


-- =========================
-- TABLE: roles
-- =========================
CREATE TABLE IF NOT EXISTS roles (
    id              BIGSERIAL PRIMARY KEY,
    role_code       VARCHAR(100) UNIQUE NOT NULL,
    role_name       VARCHAR(255) NOT NULL
);

COMMENT ON TABLE roles IS 'Danh sách vai trò (role) trong hệ thống, ví dụ: ROLE_ADMIN, ROLE_USER';


-- =========================
-- TABLE: user_roles
-- =========================
CREATE TABLE IF NOT EXISTS user_roles (
    user_id         BIGINT NOT NULL,
    role_id         BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

COMMENT ON TABLE user_roles IS 'Bảng trung gian nhiều-nhiều giữa user và role';


-- =========================
-- TABLE: permissions
-- =========================
CREATE TABLE IF NOT EXISTS permissions (
    id              BIGSERIAL PRIMARY KEY,
    url_pattern     VARCHAR(255) NOT NULL,
    http_method     VARCHAR(10) DEFAULT '*' CHECK (http_method IN ('GET','POST','PUT','DELETE','PATCH','*')),
    description     VARCHAR(255)
);

COMMENT ON TABLE permissions IS 'Danh sách endpoint hoặc URL pattern có thể phân quyền';
COMMENT ON COLUMN permissions.http_method IS 'Phương thức HTTP được phép, * = tất cả';


-- =========================
-- TABLE: role_permissions
-- =========================
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id         BIGINT NOT NULL,
    permission_id   BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

COMMENT ON TABLE role_permissions IS 'Phân quyền: role nào được phép truy cập endpoint nào';


-- =========================
-- SEED DATA: mặc định hệ thống
-- =========================
-- ROLE ADMIN
INSERT INTO roles (role_code, role_name)
VALUES ('ROLE_ADMIN', 'Administrator');

-- ROLE USER
INSERT INTO roles (role_code, role_name)
VALUES ('ROLE_USER', 'Normal User');

-- ADMIN USER
INSERT INTO users (username, password, full_name, is_active)
VALUES ('admin', '{bcrypt}$2a$10$123456789012345678901uQJdMLOJ9asdasdasdasdasdasdasd', 'System Administrator', 1);

-- GÁN ROLE ADMIN CHO USER ADMIN
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.role_code = 'ROLE_ADMIN';

-- PERMISSIONS MẪU
INSERT INTO permissions (url_pattern, http_method, description) VALUES
('/api/admin/**', '*', 'Admin-only access'),
('/api/user/profile', 'GET', 'Xem thông tin cá nhân'),
('/api/user/update', 'POST', 'Cập nhật thông tin cá nhân');

-- GÁN QUYỀN CHO ROLE
-- ROLE_ADMIN có toàn quyền
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE r.role_code = 'ROLE_ADMIN';

-- ROLE_USER chỉ được phép truy cập 2 API
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.url_pattern IN ('/api/user/profile', '/api/user/update')
WHERE r.role_code = 'ROLE_USER';

-- =========================
-- DONE
-- =========================
