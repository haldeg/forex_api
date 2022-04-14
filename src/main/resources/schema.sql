CREATE TABLE IF NOT EXISTS exchangerate.spread
(
    currency char(3) NOT NULL primary key,
    spread_percentage numeric NOT NULL
);

CREATE TABLE IF NOT EXISTS exchangerate.forex_rate
(
    id serial primary key,
    rate_date date NOT NULL,
    currency char(3) NOT NULL,
    rate_value numeric NOT NULL,
    counter integer NOT NULL DEFAULT 0,
    CONSTRAINT rate_unique UNIQUE (rate_date, currency)
);
