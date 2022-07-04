insert into customer(id, email, name) values (1, 'bob@example.com', 'Bob Stevenson');
insert into customer(id, email, name) values (2, 'mary@example.com', 'Mary Stevenson');
insert into bank_account(id, name) values (1, 'my family bank accoun');
insert into customer_bank_account(owner_id, bank_account_id) values (1, 1);
insert into customer_bank_account(owner_id, bank_account_id) values (2, 1);