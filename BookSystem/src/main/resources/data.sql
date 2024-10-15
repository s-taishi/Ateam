--★テスト用機能ここから(本番環境移行時に削除して下さい)
--userテーブルへのデータ登録
--ダミー顧客データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user','a','顧客','00000000000','USER');

--ダミー管理者データ
INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('admin','1','管理者1','00000000000','ADMIN');

--ダミー予約データ
INSERT INTO  books(username,bookdate,booktime,bookcount,memo)
VALUES
('user', '2024-10-01', '14:00:00', 4, '特になし。');

--★テスト用機能ここまで