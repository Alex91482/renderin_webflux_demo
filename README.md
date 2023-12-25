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
CREATE TABLE line (
id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
id_osm bigint,
geom geometry(linestring, 4326) not null,
color text not null
);
```
## Описание
Для геометрии используется структура и данные из сервиса OpenStreetMap https://www.openstreetmap.org/ 
- node - точка
- way - линия
- relation - это логическое объединение точек, линий и других отношений в единый объект