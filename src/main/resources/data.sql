-- id는 넣지 않는다. (직접 넣으면 IDENTITY 시퀀스가 안 올라가서 API로 새로 만들 때 PK가 겹친다)
-- 넣은 순서대로 id가 1부터 붙는다.

-- 유저 3명 (id 1~3)
INSERT INTO users (nickname, created_at, updated_at) VALUES ('woni_love', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO users (nickname, created_at, updated_at) VALUES ('liv_forever', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO users (nickname, created_at, updated_at) VALUES ('scene_collector', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- TODO: B의 Photocard, C의 TradeRequest 엔티티가 머지되면 아래 주석을 푼다.
--       테이블·컬럼 이름은 예상값이라 실제 엔티티에 맞춰 고쳐야 한다.

-- 포카 10장 (id 1~10)
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('WONI', 'Re:Scene', '상태 좋음, 슬리브 보관', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('LIV', 'Re:Scene', '모서리 살짝 눌림', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('MINAMI', 'Scenedrome', '미개봉급', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('MAY', 'Scenedrome', '상태 좋음', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('ZENA', 'Scenedrome', '뒷면 기스 있음', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('WONI', 'Lxvelation', '상태 좋음', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('LIV', 'Lxvelation', '탑로더 보관', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('MINAMI', 'Re:Scene', '상태 보통', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('MAY', 'Lxvelation', '미개봉급', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- INSERT INTO photocard (member, album, memo, owner_id, created_at, updated_at) VALUES ('ZENA', 'Re:Scene', '상태 좋음', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 교환 요청 2건
-- 1) 유저1이 자기 WONI(1)를 주고 유저2의 MAY(4)를 원함 → 유저2가 받은 요청
-- INSERT INTO trade_request (status, requester_id, offered_card_id, target_card_id, created_at, updated_at) VALUES ('REQUESTED', 1, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 2) 유저3이 자기 LIV(7)를 주고 유저1의 MINAMI(3)를 원함 → 유저1이 받은 요청
-- INSERT INTO trade_request (status, requester_id, offered_card_id, target_card_id, created_at, updated_at) VALUES ('REQUESTED', 3, 7, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
