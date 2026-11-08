INSERT INTO data_sources
    (source_code, source_name, source_type)
VALUES
    ('INTERNAL_APP', 'Internal Order System', 'INTERNAL'),
    ('PAY_GATEWAY', 'Payment Gateway', 'GATEWAY'),
    ('BANK', 'Bank Settlement System', 'BANK');

SELECT * FROM data_sources;

INSERT INTO import_batches
    (data_source_id, file_name, batch_status,
     total_rows, successful_rows, failed_rows)

SELECT id, 'internal_nov.csv', 'COMPLETED', 3, 3, 0
FROM data_sources
WHERE source_code = 'INTERNAL_APP'

UNION ALL

SELECT id, 'gateway_nov.csv', 'COMPLETED', 4, 4, 0
FROM data_sources
WHERE source_code = 'PAY_GATEWAY'

UNION ALL

SELECT id, 'bank_nov.csv', 'COMPLETED', 2, 2, 0
FROM data_sources
WHERE source_code = 'BANK';

SELECT * FROM import_batches;

INSERT INTO source_transactions
    (import_batch_id, source_transaction_ref,
     amount, currency, transaction_type,
     transaction_status, occurred_at)

SELECT b.id, v.txn_ref, v.amount, 'INR',
       v.txn_type, v.txn_status, v.occurred_at::timestamptz
FROM (
    VALUES
    ('internal_nov.csv', 'TXN1001', 1000.00::numeric,
     'PAYMENT', 'SUCCESS', '2026-11-07 09:00:00+05:30'),

    ('internal_nov.csv', 'TXN1002', 2000.00::numeric,
     'PAYMENT', 'SUCCESS', '2026-11-07 09:10:00+05:30'),

    ('internal_nov.csv', 'TXN1003', 500.00::numeric,
     'PAYMENT', 'SUCCESS', '2026-11-07 09:20:00+05:30'),

    ('gateway_nov.csv', 'TXN1001', 1000.00::numeric,
     'PAYMENT', 'SUCCESS', '2026-11-07 09:00:00+05:30'),

    ('gateway_nov.csv', 'TXN1002', 1950.00::numeric,
     'PAYMENT', 'SUCCESS', '2026-11-07 09:10:00+05:30'),

    ('gateway_nov.csv', 'TXN1004', 800.00::numeric,
     'PAYMENT', 'SUCCESS', '2026-11-07 09:30:00+05:30'),

    ('gateway_nov.csv', 'TXN1004', 800.00::numeric,
     'PAYMENT', 'SUCCESS', '2026-11-07 09:30:00+05:30'),

    ('bank_nov.csv', 'TXN1001', 1000.00::numeric,
     'SETTLEMENT', 'SUCCESS', '2026-11-07 10:00:00+05:30'),

    ('bank_nov.csv', 'TXN1002', 1950.00::numeric,
     'SETTLEMENT', 'SUCCESS', '2026-11-07 10:10:00+05:30')
) AS v(
    file_name, txn_ref, amount,
    txn_type, txn_status, occurred_at
)
JOIN import_batches b ON b.file_name = v.file_name;

SELECT
    t.id,
    t.source_transaction_ref,
    d.source_name,
    b.file_name,
    t.amount,
    t.currency,
    t.transaction_type,
    t.transaction_status
FROM source_transactions t
JOIN import_batches b
    ON t.import_batch_id = b.id
JOIN data_sources d
    ON b.data_source_id = d.id
ORDER BY t.id;