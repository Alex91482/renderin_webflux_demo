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

### Порядок запуска приложений:
1) cloud-config
2) server-registry
3) renderin_webflux_demo

### Панель Eureka
Панель будет находится по адресу http://localhost:9002/
![plot](./images/eureka.png)