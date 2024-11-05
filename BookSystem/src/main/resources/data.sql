--★テスト用機能ここから(本番環境移行時に削除して下さい)
--userテーブルへのデータ登録
--ダミー管理者データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('admin','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','カンリシャ','000-0000-0000','ADMIN');
--ダミー顧客データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','コキャク','000-0000-0000','USER');

INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user2','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','コキャク2','000-0000-0000','USER');


INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '16:00:00', 4, '特になし。',1);
--ダミー予約データ
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '14:00:00', 4, '特になし。',1);
--ダミー予約データ
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '15:00:00', 4, 'aqwsedrftgyhujikolp;@:[ ]qawsedrftgyhujikolpaqwsedrftgyhujikolp;aqwsedrftgyhujik]',1);
--ダミー予約データ
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '16:00:00', 4, '特になし。',1);

-- クーポン情報のダミーデータ
INSERT INTO coupon (coupon_type, user_id, issue_date, expiration_date)
VALUES ('COUPON_TYPE1', 2, '2024-11-05', '2025-02-05');

INSERT INTO coupon (coupon_type, user_id, issue_date, expiration_date)
VALUES ('COUPON_TYPE2', 2, '2024-11-05', '2025-02-05');

INSERT INTO coupon (coupon_type, user_id, issue_date, expiration_date)
VALUES ('COUPON_TYPE3', 2, '2024-11-05', '2025-02-05');

INSERT INTO coupon (coupon_type, user_id, issue_date, expiration_date)
VALUES ('COUPON_TYPE4', 2, '2024-11-05', '2025-02-05');

--期限切れクーポン
INSERT INTO coupon (coupon_type, user_id, issue_date, expiration_date)
VALUES ('COUPON_TYPE1', 2, '2024-09-05', '2024-11-05');



-- ★テスト用機能ここまで
