-- liquibase formatted sql
-- changeset dbostan:1

create table notification_task(
id bigserial primary key,
task_text varchar,
chat_id bigint,
task_clock timestamp
)