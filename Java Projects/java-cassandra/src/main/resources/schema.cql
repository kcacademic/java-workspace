CREATE TYPE IF NOT EXISTS address (
city text,
postcode text
);

CREATE TABLE IF NOT EXISTS customersWithAddress (
id uuid PRIMARY KEY,
firstName text,
lastName text,
address FROZEN<address>
);

INSERT INTO customersWithAddress (id, firstName, lastName, address) 
values (now(), 'Ashish', 'Roy', { city : 'New Delhi', postcode : '110011'});


CREATE TABLE IF NOT EXISTS customersWithAddress (
id uuid PRIMARY KEY,
firstName text,
lastName text,
address set<FROZEN<address>>
);

INSERT INTO customersWithAddress (id, firstName, lastName, address) 
values (now(), 'Ashish', 'Roy', {{ city : 'New Delhi', postcode : '110011'}});

UPDATE customersWithAddress 
SET address = address + {{ city : 'Mumbai', postcode : '410011'}} WHERE id = <UUID>;

DROP TYPE IF EXISTS address;

DROP TABLE IF EXISTS customersWithAddress;