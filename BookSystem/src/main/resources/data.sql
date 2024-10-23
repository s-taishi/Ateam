--★テスト用機能ここから(本番環境移行時に削除して下さい)
--userテーブルへのデータ登録
--ダミー管理者データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('admin','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','管理者1','00000000000','ADMIN');
--ダミー顧客データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','顧客','00000000000','USER');


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


--★テスト用機能ここまで