--★テスト用機能ここから(本番移行時は記述を削除してください)

--各テーブル・ENUM型が存在したら削除
DROP TABLE IF EXISTS playtime;
DROP TABLE IF EXISTS coupon;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS role;
DROP TYPE IF EXISTS coupon_type;


--ENUM型の権限
CREATE TYPE role AS ENUM('ADMIN','USER');

--userテーブルの作成
CREATE TABLE users (

	--ユーザーID：主キー
	id SERIAL PRIMARY KEY,
	
	--ユーザー名：NULL不許可
	username VARCHAR(255) NOT NULL,
	
	--パスワード：NULL不許可
	password VARCHAR(255) NOT NULL,
	
	--氏名：NULL不許可
	displayname VARCHAR(255) NOT NULL,
	
	--電話番号：NULL不許可
	tellnumber VARCHAR(255) NOT NULL,
	
	--権限：初期設定はUSER
	authority role DEFAULT 'USER'
);

--bookテーブルの作成
CREATE TABLE books (

	--予約ID：主キー
	id SERIAL PRIMARY KEY,
	
	--予約日：NULL不許可
	bookdate DATE NOT NULL,
	
	--予約時間：NULL不許可
	booktime TIME NOT NULL,
	
	--来店人数：NULL不許可
	bookcount INTEGER NOT NULL,
	
	--特記事項
	memo VARCHAR(255),
	
	--userオブジェクト：外部キー
	user_id INTEGER REFERENCES users(id)
);

--ENUM型のクーポンタイプ
CREATE TYPE coupon_type AS ENUM('COUPON_TYPE1','COUPON_TYPE2','COUPON_TYPE3','COUPON_TYPE4');

--couponテーブル作成
CREATE TABLE coupon(

	--クーポンID：主キー
	id SERIAL PRIMARY KEY,
	
	--クーポンタイプ：NULL不許可
	coupon_type VARCHAR(50) not null,
	
	--クーポンの取得日：NULL不許可、初期設定は今日
	issue_date DATE DEFAULT CURRENT_DATE NOT NULL,
	
	--有効期限：NULL不許可
	expiration_date DATE NOT NULL,
	
	--Userオブジェクト：外部キー
	user_id INTEGER REFERENCES users(id)
);
	
--ルーレットの回数制限設定用テーブル
CREATE TABLE playtime(

	--回数ID：主キー
	id SERIAL PRIMARY KEY REFERENCES users(id),
	
	--前回のプレイ日：NULL不許可
	lastplay DATE NOT NULL,
	
	--プレイ回数：NULL不許可
	playcount INTEGER NOT NULL
);
--★テスト用機能ここまで
