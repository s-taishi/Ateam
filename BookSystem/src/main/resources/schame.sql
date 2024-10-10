--★テスト用機能ここから(本番移行時は記述を削除してください)
--各テーブル・ENUM型が存在したら削除
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS "book";
DROP TYPE IF EXISTS role;
--★テスト用機能ここまで

--権限用のENUM型
CREATE TYPE role AS ENUM('ADMIN','USER');

--userテーブルの作成
CREATE TABLE "user" (
	--user(ユーザー名)カラム；主キー
	username VARCHAR(255) PRIMARY KEY,
	
	--password(パスワード)カラム：NULL不許可
	password VARCHAR(255) NOT NULL,
	
	--displayname(氏名)カラム：NULL不許可
	displayname VARCHAR(255) NOT NULL,
	
	--tellnumber(電話番号)カラム：NULL不許可
	tellnumber VARCHAR(255) NOT NULL,
	
	--authority(権限)カラム：NULL不許可
	authority role NOT NULL
);

--bookテーブルの作成
CREATE TABLE "book" (
	--ID(予約ID)カラム：主キー
	id SERIAL PRIMARY KEY,
	
	--username(ユーザー名)：外部キー
	username VARCHAR(255) NOT NULL,
	--date(予約日)カラム：NULL不許可
	date DATE NOT NULL,
	
	--time(予約時間)カラム：NULL不許可
	time TIME NOT NULL,
	
	--count(来店人数)カラム：NULL不許可
	count INTEGER NOT NULL,
	
	--memo(特記事項)カラム
	memo VARCHAR(255),
	
	
	--外部キー制約
	FOREIGN KEY (username) REFERENCES "user"(username)
);

