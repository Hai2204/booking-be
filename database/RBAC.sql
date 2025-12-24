CREATE TABLE permission_group (
    code_id     VARCHAR(50) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    application VARCHAR(50)  NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE feature (
    code_id    VARCHAR(50) PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    url        VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE group_feature (
    group_id   VARCHAR(50) NOT NULL,
    feature_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_group_feature PRIMARY KEY (group_id, feature_id),
    CONSTRAINT fk_gf_group
        FOREIGN KEY (group_id)
        REFERENCES permission_group (code_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_gf_feature
        FOREIGN KEY (feature_id)
        REFERENCES feature (code_id)
        ON DELETE CASCADE
);
CREATE TABLE account_permission_group (
    account_id VARCHAR(50) NOT NULL,
    group_id   VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_account_group PRIMARY KEY (account_id, group_id),
    CONSTRAINT fk_apg_group
        FOREIGN KEY (group_id)
        REFERENCES permission_group (code_id)
        ON DELETE CASCADE
);
CREATE INDEX idx_gf_group ON group_feature (group_id);
CREATE INDEX idx_gf_feature ON group_feature (feature_id);

CREATE INDEX idx_apg_account ON account_permission_group (account_id);
CREATE INDEX idx_apg_group ON account_permission_group (group_id);

INSERT INTO permission_group (code_id, name, application) VALUES
('ADMIN', 'Administrator', 'ROLE_ADMIN');

INSERT INTO feature (code_id, name, url) VALUES
('ADMIN_USER',    'User Management',    '/admin/users-management.html'),
('ADMIN_ROOM',    'Room Management',    '/admin/rooms.html'),
('ADMIN_BOOKING', 'Booking Management', '/admin/bookings.html'),
('ADMIN_PARTNER', 'Partner Management', '/admin/partners.html'),
('ADMIN_BLOG',    'Blog Management',    '/admin/blogs.html'),
('ADMIN_REPORT',  'Report Management',  '/admin/reports.html');

INSERT INTO group_feature (group_id, feature_id) VALUES
('ADMIN', 'ADMIN_USER'),
('ADMIN', 'ADMIN_ROOM'),
('ADMIN', 'ADMIN_BOOKING'),
('ADMIN', 'ADMIN_PARTNER'),
('ADMIN', 'ADMIN_BLOG'),
('ADMIN', 'ADMIN_REPORT');

INSERT INTO account_permission_group (account_id, group_id) VALUES
('admin', 'ADMIN'),
('hainv', 'ADMIN');

SELECT f.code_id, f.name, f.url
FROM account_permission_group apg
JOIN group_feature gf ON apg.group_id = gf.group_id
JOIN feature f ON gf.feature_id = f.code_id
WHERE apg.account_id = 'admin';
