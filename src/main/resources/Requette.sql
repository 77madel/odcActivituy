SELECT genre, COUNT(*) FROM participant GROUP BY genre;
UPDATE participant SET genre = 'Homme' WHERE LOWER(genre) = 'homme';
UPDATE participant SET genre = 'Femme' WHERE LOWER(genre) = 'femme';
DELETE FROM participant WHERE genre IS NULL OR genre = '';

