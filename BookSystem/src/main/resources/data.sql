--★テスト用機能ここから(本番環境移行時に削除して下さい)
--userテーブルへのデータ登録
--ダミー顧客データ
INSERT INTO "user"
(username,password,displayname,tellnumber,authority)
VALUES
('user1','abcd1234','顧客1','00000000000','USER');

--ダミー管理者データ
INSERT INTO "user"
(username,password,displayname,tellnumber,authority)
VALUES
('admin1','abcd1234','管理者1','00000000000','ADMIN');

--ダミー予約データ
INSERT INTO  "book"(username,date,time,count,memo)
VALUES
('user1', '2024-10-01', '14:00:00', 4, '特になし。');

--★テスト用機能ここまで