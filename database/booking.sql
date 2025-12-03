-- =========================
-- XÓA CÁC BẢNG NẾU ĐÃ TỒN TẠI
-- =========================
DROP TABLE IF EXISTS payment CASCADE;
DROP TABLE IF EXISTS booking CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS accommodation CASCADE;
DROP TABLE IF EXISTS partner CASCADE;

-- =========================
-- BẢNG partner
-- =========================
CREATE TABLE IF NOT EXISTS partner (
    partner_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    contact_info VARCHAR(100)
);

-- =========================
-- BẢNG accommodation
-- =========================
CREATE TABLE IF NOT EXISTS accommodation (
    accommodation_id SERIAL PRIMARY KEY,
    partner_id INTEGER REFERENCES partner(partner_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    name VARCHAR(255),
    accommodation_type VARCHAR(20) CHECK (accommodation_type IN ('hotel', 'villa', 'homestay'))  NOT NULL,
    description VARCHAR(255),
    address VARCHAR(255)
);

COMMENT ON COLUMN accommodation.accommodation_type IS 'hotel/villa/homestay';


-- =========================
-- BẢNG room
-- =========================
CREATE TABLE IF NOT EXISTS room (
    id SERIAL PRIMARY KEY,
    accommodation_id INTEGER REFERENCES accommodation(accommodation_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    name VARCHAR(50) NOT NULL,
    type_room VARCHAR(20) DEFAULT '2_person' CHECK (type_room IN ('2_person', '4_person', 'the_whole_house')) NOT NULL,
    price INTEGER,
    active SMALLINT DEFAULT 0
);

COMMENT ON TABLE room IS 'name room';
COMMENT ON COLUMN room.type_room IS '2_person/4_person/the_whole_house';


-- =========================
-- BẢNG customer
-- =========================
CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    national_id VARCHAR(20) NOT NULL,
    age INTEGER NOT NULL,
    phone_number VARCHAR(255),
    email VARCHAR(255)
);


-- =========================
-- BẢNG booking
-- =========================
CREATE TABLE IF NOT EXISTS booking (
    id SERIAL PRIMARY KEY,
    room_id INTEGER REFERENCES room(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    customer_id INTEGER REFERENCES customer(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    time_in TIMESTAMP,
    time_out TIMESTAMP,
    status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'close', 'booked', 'cancel'))  NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	note VARCHAR(255)
);

COMMENT ON COLUMN booking.status IS 'active/close/booked/cancel';


-- =========================
-- BẢNG payment
-- =========================
CREATE TABLE IF NOT EXISTS payment (
    id SERIAL PRIMARY KEY,
    booking_id INTEGER REFERENCES booking(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    amount INTEGER,
    payment_type VARCHAR(10) CHECK (payment_type IN ('deposit', 'rental', 'refund')),
    payment_method VARCHAR(10) DEFAULT 'cash' CHECK (payment_method IN ('cash', 'transfer', 'credit', 'vnpay', 'momo'))  NOT NULL,
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    note VARCHAR(255),
    transaction_code VARCHAR(50) NOT NULL
);

COMMENT ON COLUMN payment.payment_type IS 'deposit/rental/rental/refund';
COMMENT ON COLUMN payment.payment_method IS 'cash/transfer/credit/vnpay/momo';
