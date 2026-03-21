-- Clear tables
DELETE FROM preparation_plan;
DELETE FROM users;

-- Reset auto increment
ALTER TABLE users AUTO_INCREMENT = 1;

-- Insert users
INSERT INTO users (name, email, created_at) VALUES
('Aastha', 'aastha@email.com', NOW()),
('Typcsy', 'typcsy@email.com', NOW());

-- Insert preparation plans
INSERT INTO preparation_plan
(created_at, description, duration_days, end_date, start_date, status, title, updated_at, user_id)
VALUES
(NOW(),'Prepare physically for Narmada Parikrama',30,'2026-05-30','2026-05-01','PLANNED','Physical Preparation',NOW(),1),

(NOW(),'Meditation and spiritual readiness',15,'2026-04-15','2026-04-01','IN_PROGRESS','Spiritual Preparation',NOW(),1),

(NOW(),'Buy required items and plan logistics',10,'2026-03-20','2026-03-10','COMPLETED','Logistics Preparation',NOW(),2);