INSERT INTO exchangerate.spread
VALUES
    ('GBP', 0),
    ('JPY', 3.25),
	('HKD', 3.25),
	('KRW', 3.25),
	('MYR', 4.5),
	('INR', 4.5),
	('MXN', 4.5),
	('RUB', 6),
	('CNY', 6),
	('ZAR', 6) ON CONFLICT (currency) DO NOTHING;