USE hanalink;

DROP TABLE IF EXISTS `meeting_account`;
DROP TABLE IF EXISTS `chatting`;
DROP TABLE IF EXISTS `transaction`;
DROP TABLE IF EXISTS `alarm`;
DROP TABLE IF EXISTS `plan`;
DROP TABLE IF EXISTS `team_member`;
DROP TABLE IF EXISTS `team`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `member`;
DROP TABLE IF EXISTS `survey`;
DROP TABLE IF EXISTS `survey_response`;
DROP TABLE IF EXISTS `si_gun_gu`;
DROP TABLE IF EXISTS `si_gun`;
DROP TABLE IF EXISTS `base`;

CREATE TABLE `base` (
    `created_at` VARCHAR(255) NULL,
    `updated_at` VARCHAR(255) NULL
);

CREATE TABLE `si_gun` (
    `si_gun_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `si_gun` VARCHAR(255) NOT NULL
);

CREATE TABLE `si_gun_gu` (
    `si_gun_gu_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `si_gun_gu` VARCHAR(255) NOT NULL,
    `si_gun_id` BIGINT NOT NULL,
    FOREIGN KEY (`si_gun_id`) REFERENCES `si_gun`(`si_gun_id`)
);

CREATE TABLE `survey_response` (
    `survey_response_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `total_score` FLOAT NOT NULL,
    `survey_cnt` INT NOT NULL
);

CREATE TABLE `member` (
    `member_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(255) NOT NULL,
    `gender` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `fcm_token` VARCHAR(255) NOT NULL,
    `si_gun_gu_id` BIGINT NOT NULL,
    `profile` VARCHAR(2000) NOT NULL,
    FOREIGN KEY (`si_gun_gu_id`) REFERENCES `si_gun_gu`(`si_gun_gu_id`)
);

CREATE TABLE `team` (
    `team_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `team_name` VARCHAR(255) NOT NULL,
    `team_desc` VARCHAR(2000) NOT NULL,
    `category` VARCHAR(255) NOT NULL,
    `capacity` INT NOT NULL,
    `score` FLOAT NOT NULL,
    `thumb_nail` VARCHAR(2000) NULL,
    `banner` VARCHAR(2000) NULL,
    `survey_response_id` BIGINT NOT NULL,
    `si_gun_gu_id` BIGINT NOT NULL,
    FOREIGN KEY (`survey_response_id`) REFERENCES `survey_response`(`survey_response_id`),
    FOREIGN KEY (`si_gun_gu_id`) REFERENCES `si_gun_gu`(`si_gun_gu_id`)
);

CREATE TABLE `account` (
    `account_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_name` VARCHAR(255) NOT NULL,
    `account_number` VARCHAR(255) NOT NULL,
    `balance` BIGINT NOT NULL,
    `bank` VARCHAR(255) NOT NULL,
    `member_id` BIGINT NOT NULL,
    FOREIGN KEY (`member_id`) REFERENCES `member`(`member_id`)
);

CREATE TABLE `team_member` (
    `team_member_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nickname` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) NOT NULL,
    `hello` VARCHAR(255) NOT NULL,
    `team_id` BIGINT NOT NULL,
    `member_id` BIGINT NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `team`(`team_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`member_id`)
);

CREATE TABLE `plan` (
    `plan_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `plan_name` VARCHAR(255) NOT NULL,
    `plan_date` DATETIME NOT NULL,
    `place` VARCHAR(255) NOT NULL,
    `cost` BIGINT NOT NULL,
    `plan_img` VARCHAR(2000) NULL,
    `team_id` BIGINT NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `team`(`team_id`)
);

CREATE TABLE `alarm` (
    `alarm_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `body` VARCHAR(255) NOT NULL,
    `member_id` BIGINT NOT NULL,
    FOREIGN KEY (`member_id`) REFERENCES `member`(`member_id`)
);

CREATE TABLE `transaction` (
    `trans_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `amount` BIGINT NOT NULL,
    `trans_to` VARCHAR(255) NOT NULL,
    `trans_from` VARCHAR(255) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
    `account_to_id` BIGINT NULL,
    `account_from_id` BIGINT NOT NULL,
    FOREIGN KEY (`account_to_id`) REFERENCES `account_to`(`account_to_id`),
    FOREIGN KEY (`account_from_id`) REFERENCES `account`(`account_id`)
);

CREATE TABLE `chatting` (
    `chat_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` VARCHAR(255) NOT NULL,
    `team_id` BIGINT NOT NULL,
    `team_member_id` BIGINT NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `team`(`team_id`),
    FOREIGN KEY (`team_member_id`) REFERENCES `team_member`(`team_member_id`)
);

CREATE TABLE `survey` (
    `survey_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `survey_score` FLOAT NOT NULL,
    `team_id` BIGINT NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `team`(`team_id`)
);

CREATE TABLE `meeting_account` (
    `meeting_account_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `meeting_account_number` VARCHAR(255) NOT NULL,
    `team_id` BIGINT NOT NULL,
    `account_id` BIGINT NOT NULL,
    FOREIGN KEY (`team_id`) REFERENCES `team`(`team_id`),
    FOREIGN KEY (`account_id`) REFERENCES `account`(`account_id`)
);


CREATE TABLE `account_to` (
    `account_to_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id` BIGINT	NOT NULL
);