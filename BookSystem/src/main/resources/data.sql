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
('user','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','モリタタダシ','090-8773-8322','USER');

INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user2','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','イマガワアツコ','050-6561-6215','USER');

INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user3','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','サトウトオル','090-1226-9812','USER');

INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user4','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','シマダトモコ','080-1338-3594','USER');

INSERT INTO users
(username,password,displayname,tellnumber,authority)
VALUES
('user5','$2a$10$A7MbabscmadFQD26O3SmM.ryrtLhY3sI9vgZKB3x6UjoKeLwuc17e','キムラヨシヒロ','070-5334-1565','USER');


--ダミー予約データここから---------------------------------------------------------------------

--過去の予約検索用、非表示になっていることを見せる用
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '16:00:00', 4, '窓際の席を希望',2);
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '14:00:00', 4, '特になし。',3);

INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-10-01', '18:00:00', 4, '結婚記念日のサプライズディナーを考えています。花束の持ち込みをしたいのですが、席に事前に準備していただくことは可能でしょうか？また、デザートプレートに“Happy Anniversary”とメッセージをお願いしたいです。',4);


INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-08', '16:00:00', 4, '特になし。',2);

INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-08', '19:00:00', 4, '',3);

--予約時間で並び替えられていることを見せるためのデータ
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-08', '17:00:00', 2, 'ナッツ、乳製品アレルギーの方が1名います。',4);

--別日の予約
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-09', '19:00:00', 3, 'ソファー席でお願いします',2);

INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-09', '18:00:00', 2, '',3);

INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-09', '17:00:00', 5, '子供用の椅子をお願いします',4);

--すでに49人予約が入っている状態
INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-09', '23:00:00', 24, '会社の歓送迎会での利用を予定しています。24名ほどの団体ですが、飲み放題プランの延長は可能でしょうか？賑やかになるかもしれないので、他のお客様にご迷惑がかからないように席の配置などご配慮いただけると助かります。',5);

INSERT INTO  books(bookdate,booktime,bookcount,memo,user_id)
VALUES
('2024-11-09', '23:00:00', 25, '職場の宴会で25名予約です。コース料理と飲み放題をお願いしたいです。',6);

--ダミー予約データここまで-----------------------------------------------------------------------


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
VALUES ('COUPON_TYPE1', 2, '2024-08-04', '2024-11-04');
INSERT INTO coupon (coupon_type, user_id, issue_date, expiration_date)
VALUES ('COUPON_TYPE3', 2, '2024-08-06', '2024-11-06');
INSERT INTO coupon (coupon_type, user_id, issue_date, expiration_date)
VALUES ('COUPON_TYPE2', 2, '2024-08-06', '2024-11-06');


-- ★テスト用機能ここまで
