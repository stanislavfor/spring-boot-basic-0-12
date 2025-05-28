![](assets/_logo_spring_framwork_.png)
# Фреймворк Spring (семинары)
# Урок 12. Паттерны проектирония и GoF паттерны в Spring приложении
## Описание
- Использование паттернов проектирования при создании Spring приложения (например, Singleton, Factory, Decorator).
- Анализ примеров использования паттернов проектирования в существующем Spring приложении.
- Рефакторинг кода с использованием соответствующих паттернов проектирования.

<br><hr>
## Домашнее задание
Задание: <br>
1) На базе первого примера разобранного на семинаре, добавить в один из проектов разработанных ранее spring Integration. Сохранять запросы от пользователя в файл.
2) Добавить в проект один из паттернов разобранных на лекции.
<br><hr>
## Решение задания

### Список требуемых зависимостей для реализации проекта
1. spring-boot-starter-web (Spring Web)
2. spring-boot-starter-data-jpa (Spring Data JPA)
3. h2 (H2 Database)
4. spring-boot-starter-mail
5. spring-boot-starter-test
6. junit-jupiter-api
7. mockito-core
8. lombok
9. spring-boot-starter-validation
10. spring-boot-starter-thymeleaf

Проект Spring Boot приложения для управления задачами (Task Management) со структурой MVC (Model-View-Controller) имеет следующие элементы кода:

##### Model (Модель)

Модель представляет данные и бизнес-логику приложения: сущности и сервисы.

- Сущности:
  - `Task`: Сущность для представления задач.
  - `Subscriber`: Сущность для представления подписчиков.

- Репозитории:
  - `TaskRepository`: Интерфейс для взаимодействия с базой данных для сущности `Task`.
  - `SubscriberRepository`: Интерфейс для взаимодействия с базой данных для сущности `Subscriber`.

- Сервисы:
  - `TaskService`: Сервис для управления задачами.
  - `SubscriberService`: Сервис для управления подписчиками.
  - `EmailService`: Сервис для отправки уведомлений по электронной почте.

##### View (Представление)

Представление отвечает за отображение данных пользователю.


##### Controller (Контроллер)

В проекте используются REST API контроллеры, которые возвращают данные в формате JSON. <br>
Контроллер обрабатывает входящие HTTP-запросы, взаимодействует с моделью и возвращает ответы пользователю. <br>
- Контроллеры:
  - `TaskController`: Контроллер для обработки HTTP-запросов, связанных с задачами.
  - `SubscriberController`: Контроллер для обработки HTTP-запросов, связанных с подписчиками.
   


### Подключение базы данных H2

Указать конфигурации H2 в файле application.properties:
```
    spring.datasource.url=jdbc:h2:file:./taskdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
```

Проверить конфигурации H2 в pom.xml с указанием зависимости для H2:

```
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
  </dependency>
```


### Сборка проекта

Собрать проект с помощью Maven:

```sh
mvn clean install
```
### Запуск приложения

Запустить приложение с помощью Maven:

```sh
mvn spring-boot:run
```
или можно запустить приложение из IDE, используя класс `TaskManagementApplication`.

### Доступ к API

После запуска приложения можно получить доступ к API через следующие адреса в браузере:

- Задачи (Tasks):
    - Получение всех задач: `http://localhost:8080/tasks`
    - Получение задачи по идентификатору: `http://localhost:8080/tasks/{id}`
    - Создание задачи: `POST http://localhost:8080/tasks`
    - Обновление задачи: `PUT http://localhost:8080/tasks/{id}`
    - Удаление задачи: `DELETE http://localhost:8080/tasks/{id}`
    - Получение задач по приоритету: `http://localhost:8080/tasks/priority/{priority}`
    - Получение задач по статусу выполнения: `http://localhost:8080/tasks/completed/{completed}`

- Подписчики (Subscribers):
    - Получение всех подписчиков: `http://localhost:8080/subscribers`
    - Получение подписчика по идентификатору: `http://localhost:8080/subscribers/{id}`
    - Создание подписчика: `POST http://localhost:8080/subscribers`
    - Обновление подписчика: `PUT http://localhost:8080/subscribers/{id}`
    - Удаление подписчика: `DELETE http://localhost:8080/subscribers/{id}`

### Доступ к базе данных H2

Можно получить доступ к консоли базы данных H2 по следующему адресу:

- Консоль H2: `http://localhost:8080/h2-console`

Для подключения использовать следующие параметры:
- JDBC URL: `jdbc:h2:file:./taskdb`
- User Name: `sa`
- Password: (оставить пустым)

### SQL-запросы

- Создание таблицы `Task`:
  - Таблица `Task` содержит поля `id`, `title`, `description`, `due_date`, `priority` и `completed`.

- Создание таблицы `Subscriber`:
  - Таблица `Subscriber` содержит поля `id`, `full_name` и `email_address`.

- Создание таблицы `subscriber_task`:
  - Таблица `subscriber_task` содержит поля `subscriber_id` и `task_id`, которые являются внешними ключами, ссылающимися на таблицы `Subscriber` и `Task`.

- Вставка записи в таблицу `Task`:
  - Вставка записи с заголовком `Task 1`, описанием `Description for Task 1`, датой выполнения `2025-05-25`, приоритетом `High` и статусом выполнения `FALSE`.

- Вставка записи в таблицу `Subscriber`:
  - Вставка записи с именем `Subscriber 1` и адресом электронной почты `subscriber1@example.com`.

- Связывание записей в таблице `subscriber_task`:
  - Связывание записи с `subscriber_id` равным 1 и `task_id` равным 1.

После подключения к базе данных, требуется создать таблицы для сущностей `Task` и `Subscriber`. <br> 

- SQL-запросы для создания таблиц:

```sql
   CREATE TABLE IF NOT EXISTS task (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       description VARCHAR(255),
       due_date DATE,
       priority VARCHAR(50),
       completed BOOLEAN
   );

   CREATE TABLE IF NOT EXISTS subscriber (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       full_name VARCHAR(255) NOT NULL,
       email_address VARCHAR(255) NOT NULL
   );

   CREATE TABLE IF NOT EXISTS subscriber_task (
       subscriber_id BIGINT,
       task_id BIGINT,
       PRIMARY KEY (subscriber_id, task_id),
       FOREIGN KEY (subscriber_id) REFERENCES subscriber(id),
       FOREIGN KEY (task_id) REFERENCES task(id)
   );
```

- Вставка записей в таблицу `Task` <br>

   После создания таблиц, вставить записи в таблицу `Task` с помощью SQL-запроса для вставки записи:

```sql
    INSERT INTO task (title, description, due_date, priority, completed)
    VALUES ('Task 1', 'Description for Task 1', '2025-05-25', 'High', FALSE);
```

- Вставка записей в таблицу `Subscriber` <br>

   После создания таблиц, вставить записи в таблицу `Subscriber` с помощью SQL-запроса для вставки записи:

```sql
    INSERT INTO subscriber (full_name, email_address)
    VALUES ('Subscriber 1', 'subscriber1@example.com');
```

- Связывание записей в таблице `subscriber_task` <br>

   После вставки записей в таблицы `Task` и `Subscriber`, нужно связать их в таблице `subscriber_task` с помощью SQL-запроса для связывания записей:

```sql
    INSERT INTO subscriber_task (subscriber_id, task_id)
    VALUES (1, 1);
```



### Примеры запросов

- Создание задачи:
  ```sh
  curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{
      "title": "Test Task",
      "description": "Test Description",
      "dueDate": "2023-12-31",
      "priority": "High",
      "completed": false
  }'
  ```

- Создание подписчика:
  ```sh
  curl -X POST http://localhost:8080/subscribers -H "Content-Type: application/json" -d '{
      "fullName": "John Doe",
      "emailAddress": "john.doe@example.com"
  }'
  ```

### Остановка приложения

Для остановки приложения можно использовать комбинацию клавиш `Ctrl + C` в терминале, где оно запущено.

<br><hr>
## Дополнительная информация

#### Настройка базы данных

Проект использует базу данных H2, которая запускается в памяти. Дополнительных настроек не требуется.

### Настройка отправки электронных писем

Для отправки электронных писем необходимо настроить SMTP-сервер. <br>
Открыть файл `src/main/resources/application.properties` и настроить параметры SMTP:

```properties
  spring.mail.host=smtp.gmail.com
  spring.mail.port=587
  spring.mail.username=your-email@gmail.com
  spring.mail.password=your-password
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.starttls.enable=true
```

, где `your-email@gmail.com` и `your-password` учетные данные используемого адреса e-mail.


<br><br><br><br>
<hr><hr><hr><hr>

![](assets/_logo_spring_boot_.png)