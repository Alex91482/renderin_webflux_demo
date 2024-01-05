# spring cloud + project reactor + r2dbc + java2d
### Технологии: 
spring cloud, project reactor, r2dbc, java2d
### Предпочтительно СУБД: 
postgresql

### Задание:
1) Реализовать основу микросесервисной архитектуры - gateway, discovery, config
2) Реализовать микросервис рендеринга данных с одним get контроллером со следующими параметрами:
- width ширина изображения
- height высота изображения
- bbox: минимальная и максимальная координата прямоугольной области, в которой находятся объекты, которые нужно отобразить (обычно в координатах 3857)
3) Использование ProjectReactor и spring cloud ОБЯЗАТЕЛЬНО
4) Плюсом будет браузерный веб клиент на основе openlayer или leafleat

### Пояснение:
В БД 1 таблица. Объекты - идентификатор, геометрия, цветоопределяющая характеристика
```sql
CREATE TABLE entity (
id serial NOT NULL,
geom geometry(linestring, 4326) not null,
color text not null,
CONSTRAINT cabels_pkey PRIMARY KEY (id)
);
CREATE TABLE line (
id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
id_osm bigint,
geom geometry(linestring, 4326) not null,
color text not null
);
```
```sql
insert into line_test (id_osm, geom, color) values (22724737,'LINESTRING(30.3072072 59.9488782, 30.3091483 59.9481586)','0,0,225');
insert into line_test (id_osm, geom, color) values (23553389,'LINESTRING(30.3226257 59.9502601, 30.3222525 59.9505655, 30.3222277 59.9505580)','0,0,225');
insert into line_test (id_osm, geom, color) values (88391191,'LINESTRING(30.3534598 59.9429722, 30.3534641 59.9429958, 30.3534680 59.9430230)','255,0,0');
insert into line_test (id_osm, geom, color) values (88391191,'LINESTRING(30.3524817 59.9436478, 30.3533716 59.9436409, 30.3533703 59.9435723, 30.3533681 59.9434564)','255,0,0');



select ST_Intersects(
'SRID=4326;LINESTRING(30.3072072 59.9488782, 30.3091483 59.9481586)'::geography ,
ST_MakeEnvelope(30.30843, 59.94766, 30.32527, 59.95250, 4326)
);

select *
from line_test lt
where ST_Intersects(
lt.geom ,
ST_MakeEnvelope(30.30843, 59.94766, 30.32527, 59.95250, 4326)
);
```
## Описание
Для геометрии используется структура и данные из сервиса OpenStreetMap https://www.openstreetmap.org/ 
- node - точка
- way - линия
- relation - это логическое объединение точек, линий и других отношений в единый объект