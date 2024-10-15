--★テスト用機能ここから(本番移行時は記述を削除してください)
--各テーブル・ENUM型が存在したら削除
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS role;
--★テスト用機能ここまで

--権限用のENUM型
CREATE TYPE role AS ENUM('ADMIN','USER');

--userテーブルの作成
CREATE TABLE users (
	id serial primary key,
	--user(ユーザー名)カラム；主キー
	username VARCHAR(255) not null,
	
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
CREATE TABLE books (
	--ID(予約ID)カラム：主キー
	id SERIAL PRIMARY KEY,
	
	--username(ユーザー名)：外部キー
	username VARCHAR(255) NOT NULL,
	--date(予約日)カラム：NULL不許可
	bookdate DATE NOT NULL,
	
	--time(予約時間)カラム：NULL不許可
	booktime TIME NOT NULL,
	
	--count(来店人数)カラム：NULL不許可
	bookcount INTEGER NOT NULL,
	
	--memo(特記事項)カラム
	memo VARCHAR(255),
	
	user_id integer references users(id)
);

