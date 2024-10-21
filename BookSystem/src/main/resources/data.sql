--★テスト用機能ここから(本番環境移行時に削除して下さい)
--userテーブルへのデータ登録
--ダミー顧客データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user','12345678','顧客','00000000000','USER');

--ダミー管理者データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('admin','12345678','管理者1','00000000000','ADMIN');

--ダミー予約データ
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '14:00:00', 4, '特になし。',1);

--ダミークーポンデータ
INSERT INTO coupon(coupon_type,user_id)
VALUES
('COUPON_TYPE1',1);

--★テスト用機能ここまで