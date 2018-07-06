#Query for getting the user by email and password
SELECT u.user_id, u.is_admin, u.email, u.password, u.first_name, u.last_name, u.money 
FROM users AS u 
WHERE u.email = 'peconipetko@gmail.com' AND u.password = 'petko123';

#Query for registering a user
INSERT INTO users(email, password, first_name, last_name)
VALUES('test@test.com','test123','Test','ForTest');

#Query for seraching the songs by their name
SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating, s.genre_id, s.resource_path, s.price
FROM songs AS s
WHERE s.name LIKE '%un%'
ORDER BY s.name ASC;

#Query for searching the songs by their singer
SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating, s.genre_id, s.resource_path, s.price
FROM songs AS s
WHERE s.singer LIKE '%er%'
ORDER BY s.name ASC;

#Query for searching the songs by their genre
SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating, s.genre_id, s.resource_path, s.price
FROM songs AS s JOIN genres AS g
ON s.genre_id = g.genre_id
WHERE g.value = 'Pop music'
ORDER BY s.name ASC;

#Query for getting comments of the song by their id
SELECT shc.comment_id, shc.user_id, shc.song_id, shc.content, shc.date_time 
FROM song_has_comments AS shc
WHERE shc.song_id = 2
ORDER BY shc.date_time ASC;

#Query for getting songs of a current user
SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating, s.genre_id, s.resource_path, s.price
FROM songs AS s
JOIN user_has_songs AS uhs
ON uhs.song_id = s.song_id
WHERE uhs.user_id = 2;

#Query for adding song in the cart
INSERT INTO user_has_songs(song_id, user_id)
VALUES(1,2);

#Query for rate a song 
INSERT INTO song_has_raters(song_id, user_id, rating)
VALUES(1, 1, 5) ON DUPLICATE KEY UPDATE rating = 5;

#Query for adding comment to a song
INSERT INTO song_has_comments(user_id, song_id, content, date_time)
VALUES(1, 1, 'Best music ever', now());

#Query for adding a song 
INSERT INTO songs(name, singer, album, published_date, genre_id, resource_path, price) 
VALUES('TestName','TestSinger','TestAlbum','2018-09-01',3,'uri_path', 5.50);

#Query for updating a song
UPDATE songs SET 
name = 'Vse na men2',
singer = 'Dara',
album = 'TAlbum',
published_date = '2018-09-02',
genre_id = 2,
resource_path = 'uri_path',
price = 5.50
WHERE song_id = 9;
