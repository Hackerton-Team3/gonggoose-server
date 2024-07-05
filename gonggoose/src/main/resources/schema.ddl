CREATE TABLE `user` (
                        `user_id`	BIGINT	auto_increment PRIMARY KEY,
                        `kakao_id`	BIGINT	NOT NULL,
                        `nickname`	Varchar(20)	NOT NULL,
                        `image_url`	TEXT	NULL,
                        `school_name`	Varchar(50)	NOT NULL,
                        `school_email`	Varchar(50)	NOT NULL,
                        `token`	Varchar(255)	NULL,
                        `create_at`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                        `update_at`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `bulletin` (
                            `bulletin_id`	BIGINT	auto_increment PRIMARY KEY,
                            `create_at`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                            `update_at`	TIMESTAMP	NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            `title`	Varchar(50)	NOT NULL,
                            `content`	TEXT	NOT NULL,
                            `price`	BIGINT	NOT NULL,
                            `max_user_number`	BIGINT	NOT NULL,
                            `deadline`	TIMESTAMP	NOT NULL,
                            `status`	Varchar(20)	NOT NULL	DEFAULT 'on_progress',
                            `writer_id`	BIGINT	NOT NULL
);

CREATE TABLE `bulletin_image` (
                                  `bulletin_image_id`	BIGINT	auto_increment PRIMARY KEY,
                                  `image_url`	TEXT	NOT NULL,
                                  `bulletin_id`	BIGINT	NOT NULL
);

CREATE TABLE `user_bulletin` (
                                 `user_bulletin_id`	BIGINT	auto_increment PRIMARY KEY,
                                 `create_at`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                                 `user_right`	Varchar(20)	NOT NULL,
                                 `user_id`	BIGINT	NOT NULL,
                                 `bulletin_id`	BIGINT	NOT NULL
);