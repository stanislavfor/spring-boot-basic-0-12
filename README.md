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
    - Получение всех задач: http://localhost:8080/tasks
    - Получение задачи по идентификатору: `http://localhost:8080/tasks/{id}`
    - Создание задачи: `POST http://localhost:8080/tasks`
    - Обновление задачи: `PUT http://localhost:8080/tasks/{id}`
    - Удаление задачи: `DELETE http://localhost:8080/tasks/{id}`
    - Получение задач по приоритету: `http://localhost:8080/tasks/priority/{priority}`
    - Получение задач по статусу выполнения: `http://localhost:8080/tasks/completed/{completed}`

- Подписчики (Subscribers):
    - Получение всех подписчиков: http://localhost:8080/subscribers
    - Получение подписчика по идентификатору: `http://localhost:8080/subscribers/{id}`
    - Создание подписчика: `POST http://localhost:8080/subscribers`
    - Обновление подписчика: `PUT http://localhost:8080/subscribers/{id}`
    - Удаление подписчика: `DELETE http://localhost:8080/subscribers/{id}`

### Доступ к базе данных H2

Можно получить доступ к консоли базы данных H2 по следующему адресу:

- Консоль H2: http://localhost:8080/h2-console

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

```
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

```
    INSERT INTO task (title, description, due_date, priority, completed)
    VALUES ('Task 1', 'Description for Task 1', '2025-05-25', 'High', FALSE);
```

- Вставка записей в таблицу `Subscriber` <br>

   После создания таблиц, вставить записи в таблицу `Subscriber` с помощью SQL-запроса для вставки записи:

```
    INSERT INTO subscriber (full_name, email_address)
    VALUES ('Subscriber 1', 'subscriber1@example.com');
```

- Связывание записей в таблице `subscriber_task` <br>

   После вставки записей в таблицы `Task` и `Subscriber`, нужно связать их в таблице `subscriber_task` с помощью SQL-запроса для связывания записей:

```
    INSERT INTO subscriber_task (subscriber_id, task_id)
    VALUES (1, 1);
```

или, например <br>

```
    INSERT INTO subscriber_task (subscriber_id, task_id)
    VALUES (1, 2);
```



### Примеры запросов в консоли (Console) инструмента разработчика в браузере

- Создание задачи:
  ```sh
  curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{
      "title": "Test Task",
      "description": "Test Description",
      "dueDate": "2025-05-01",
      "priority": "High",
      "completed": false
  }'
  ```

- Создание подписчика:
  ```sh
  curl -X POST http://localhost:8080/subscribers -H "Content-Type: application/json" -d '{
      "fullName": "Subscriber 1",
      "emailAddress": "subscriber1@example.com"
  }'
  ```

Для добавления данных в базу данных для сущностей `Task`, `Subscriber` и связи между ними в таблице `subscriber_task`, <br> 
можно использовать различные методы, например, терминал, консоль H2 или браузер.

### Добавление данных в базе данных

#### Добавление данных через терминал

##### Добавление новой задачи

1. Открыть терминал.
2. Ввести команду для добавления новой задачи:

   ```sh
   curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{
       "title": "Task 1",
       "description": "Description for Task 1",
       "dueDate": "2025-05-25",
       "priority": "High",
       "completed": false
   }'
   ```

##### Добавление нового подписчика

1. Открыть терминал.
2. Ввести команду для добавления нового подписчика:

   ```sh
   curl -X POST http://localhost:8080/subscribers -H "Content-Type: application/json" -d '{
       "fullName": "Subscriber 1",
       "emailAddress": "subscriber1@example.com"
   }'
   ```

##### Добавление связи между задачей и подписчиком

1. Открыть терминал.
2. Ввести команду для добавления связи между задачей и подписчиком:

   ```sh
   curl -X POST http://localhost:8080/subscribers/1/tasks/1 -H "Content-Type: application/json"
   ```

   , где `1` - это идентификатор подписчика, а `1` - это идентификатор задачи.


#### Добавление данных через консоль H2

##### Добавление новой задачи

1. Открыть консоль H2 по адресу `http://localhost:8080/h2-console`.
2. Подключиться к базе данных, используя следующие параметры:
  - **JDBC URL**: `jdbc:h2:file:./taskdb`
  - **User Name**: `sa`
  - **Password**: (оставить пустым)
3. Ввести SQL-запрос для добавления новой задачи:
   ```
   INSERT INTO task (title, description, due_date, priority, completed)
   VALUES ('Task 1', 'Description for Task 1', '2025-05-25', 'High', FALSE);
   ```
4. Нажать кнопку "Run" для выполнения запроса.


##### Добавление нового подписчика

1. Открыть консоль H2 по адресу `http://localhost:8080/h2-console`.
2. Подключиться к базе данных, используя следующие параметры:
  - **JDBC URL**: `jdbc:h2:file:./taskdb`
  - **User Name**: `sa`
  - **Password**: (оставить пустым)
3. Ввести SQL-запрос для добавления нового подписчика:
   ```
   INSERT INTO subscriber (full_name, email_address)
   VALUES ('Subscriber 1', 'subscriber1@example.com');
   ```
4. Нажать кнопку "Run" для выполнения запроса.


##### Добавление связи между задачей и подписчиком

1. Открыть консоль H2 по адресу `http://localhost:8080/h2-console`.
2. Подключиться к базе данных, используя следующие параметры:
  - **JDBC URL**: `jdbc:h2:file:./taskdb`
  - **User Name**: `sa`
  - **Password**: (оставить пустым)
3. Ввести SQL-запрос для добавления связи между задачей и подписчиком:
   ```
   INSERT INTO subscriber_task (subscriber_id, task_id)
   VALUES (1, 1);
   ```
4. Нажать кнопку "Run" для выполнения запроса.


#### Добавление данных через браузер

##### Добавление новой задачи

1. Открыть браузер.
2. Ввести URL в адресной строке:
   ```plaintext
   http://localhost:8080/tasks
   ```
3. Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки POST-запроса:
  - Открыть вкладку "Console".
  - Ввести код JavaScript для отправки POST-запроса:
    ```javascript
    fetch('http://localhost:8080/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title: 'Task 1',
            description: 'Description for Task 1',
            dueDate: '2025-05-25',
            priority: 'High',
            completed: false
        })
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
    ```
4. Нажать клавишу `Enter` для выполнения кода.


##### Добавление нового подписчика

1. Открыть браузер.
2. Ввести URL в адресной строке:
   ```plaintext
   http://localhost:8080/subscribers
   ```
3. Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки POST-запроса:
  - Открыть вкладку "Console".
  - Ввести код JavaScript для отправки POST-запроса:
    ```javascript
    fetch('http://localhost:8080/subscribers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            fullName: 'Subscriber 1',
            emailAddress: 'subscriber1@example.com'
        })
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
    ```
4. Нажать клавишу `Enter` для выполнения кода.


##### Добавление связи между задачей и подписчиком

1. Открыть браузер.
2. Ввести URL в адресной строке:
   ```plaintext
   http://localhost:8080/subscribers/1/tasks/1
   ```
3. Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки POST-запроса:
  - Открыть вкладку "Console".
  - Ввести код JavaScript для отправки POST-запроса:
    ```javascript
    fetch('http://localhost:8080/subscribers/1/tasks/1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
    ```
4. Нажать клавишу `Enter` для выполнения кода.


#### Пример запроса с использованием Postman


##### Запрос для добавления новой задачи

1. Открыть Postman.
2. Создать новый запрос.
3. Установить метод запроса на `POST`.
4. Ввести URL `http://localhost:8080/tasks`.
5. Установить заголовок `Content-Type` на `application/json`.
6. Ввести тело запроса в формате JSON:
   ```json
   {
       "title": "Task 1",
       "description": "Description for Task 1",
       "dueDate": "2025-05-25",
       "priority": "High",
       "completed": false
   }
   ```
7. Отправить запрос.


##### Запрос для добавления нового подписчика

1. Открыть Postman.
2. Создать новый запрос.
3. Установить метод запроса на `POST`.
4. Ввести URL `http://localhost:8080/subscribers`.
5. Установить заголовок `Content-Type` на `application/json`.
6. Ввести тело запроса в формате JSON:
   ```json
   {
       "fullName": "Subscriber 1",
       "emailAddress": "subscriber1@example.com"
   }
   ```
7. Отправить запрос.


##### Запрос для добавления связи между задачей и подписчиком

1. Открыть Postman.
2. Создать новый запрос.
3. Установить метод запроса на `POST`.
4. Ввести URL `http://localhost:8080/subscribers/1/tasks/1`.
5. Установить заголовок `Content-Type` на `application/json`.
6. Отправить запрос.


#### Пример запроса с использованием Python и библиотеки `requests`

Можно отправлять запросы с использованием Python и библиотеки `requests`:

##### Запрос для добавления новой задачи

```
import requests
import json

url = 'http://localhost:8080/tasks'
headers = {'Content-Type': 'application/json'}
data = {
    'title': 'Task 1',
    'description': 'Description for Task 1',
    'dueDate': '2025-05-25',
    'priority': 'High',
    'completed': False
}

response = requests.post(url, headers=headers, data=json.dumps(data))
print(response.json())
```

##### Запрос для добавления нового подписчика

```
import requests
import json

url = 'http://localhost:8080/subscribers'
headers = {'Content-Type': 'application/json'}
data = {
    'fullName': 'Subscriber 1',
    'emailAddress': 'subscriber1@example.com'
}

response = requests.post(url, headers=headers, data=json.dumps(data))
print(response.json())
```

##### Запрос для добавления связи между задачей и подписчиком

```
import requests

url = 'http://localhost:8080/subscribers/1/tasks/1'
headers = {'Content-Type': 'application/json'}

response = requests.post(url, headers=headers)
print(response.json())
```



### Редактирование данных в базе данных

#### Редактирование задачи

- Через терминал:

   Открыть терминал и ввести команду для редактирования задачи:

   ```sh
   curl -X PUT http://localhost:8080/tasks/1 -H "Content-Type: application/json" -d '{
       "title": "Updated Task",
       "description": "Updated Description for Task",
       "dueDate": "2023-12-31",
       "priority": "High",
       "completed": true
   }'
   ```

   , где `1` - это идентификатор задачи, которую нужно редактировать.

- Через консоль H2:

   Открыть консоль H2 по адресу `http://localhost:8080/h2-console` и выполнить SQL-запрос:

   ```
   UPDATE task
   SET title = 'Updated Task', description = 'Updated Description for Task', due_date = '2023-12-31', priority = 'High', completed = TRUE
   WHERE id = 1;
   ```

- Через браузер:

   Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки PUT-запроса:

   ```javascript
   fetch('http://localhost:8080/tasks/1', {
       method: 'PUT',
       headers: {
           'Content-Type': 'application/json'
       },
       body: JSON.stringify({
           title: 'Updated Task',
           description: 'Updated Description for Task',
           dueDate: '2023-12-31',
           priority: 'High',
           completed: true
       })
   })
   .then(response => response.json())
   .then(data => console.log(data))
   .catch(error => console.error('Error:', error));
   ```

#### Редактирование подписчика

- Через терминал:

   Открыть терминал и ввести команду для редактирования подписчика:

   ```sh
   curl -X PUT http://localhost:8080/subscribers/1 -H "Content-Type: application/json" -d '{
       "fullName": "Updated Subscriber",
       "emailAddress": "updatedsubscriber@example.com"
   }'
   ```

   , где `1` - это идентификатор подписчика, которого нужно редактировать.

- Через консоль H2:

   Открыть консоль H2 по адресу `http://localhost:8080/h2-console` и выполнить SQL-запрос:

   ```
   UPDATE subscriber
   SET full_name = 'Updated Subscriber', email_address = 'updatedsubscriber@example.com'
   WHERE id = 1;
   ```

- Через браузер:

   Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки PUT-запроса:

   ```javascript
   fetch('http://localhost:8080/subscribers/1', {
       method: 'PUT',
       headers: {
           'Content-Type': 'application/json'
       },
       body: JSON.stringify({
           fullName: 'Updated Subscriber',
           emailAddress: 'updatedsubscriber@example.com'
       })
   })
   .then(response => response.json())
   .then(data => console.log(data))
   .catch(error => console.error('Error:', error));
   ```

### Удаление данных в базе данных

#### Удаление задачи

- Через терминал

   Открыть терминал и ввести команду для удаления задачи:

   ```sh
   curl -X DELETE http://localhost:8080/tasks/1
   ```

   , где `1` - это идентификатор задачи, которую нужно удалить.

- Через консоль H2:

   Открыть консоль H2 по адресу `http://localhost:8080/h2-console` и выполнить SQL-запрос:

   ```
   DELETE FROM task WHERE id = 1;
   ```

- Через браузер:

   Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки DELETE-запроса:

   ```javascript
   fetch('http://localhost:8080/tasks/1', {
       method: 'DELETE'
   })
   .then(response => response.json())
   .then(data => console.log(data))
   .catch(error => console.error('Error:', error));
   ```

#### Удаление подписчика

- Через терминал6

   Открыть терминал и ввести команду для удаления подписчика:

   ```sh
   curl -X DELETE http://localhost:8080/subscribers/1
   ```

   , где `1` - это идентификатор подписчика, которого нужно удалить.

- Через консоль H2:

   Открыть консоль H2 по адресу `http://localhost:8080/h2-console` и выполнить SQL-запрос:

   ```
   DELETE FROM subscriber WHERE id = 1;
   ```

- Через браузер:

   Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки DELETE-запроса:

   ```javascript
   fetch('http://localhost:8080/subscribers/1', {
       method: 'DELETE'
   })
   .then(response => response.json())
   .then(data => console.log(data))
   .catch(error => console.error('Error:', error));
   ```

#### Удаление связи в `subscriber_task`

- Через терминал:

   Открыть терминал и ввести команду для удаления связи между задачей и подписчиком:

   ```sh
   curl -X DELETE http://localhost:8080/subscribers/1/tasks/1
   ```

   , где `1` - это идентификатор подписчика, а `1` - это идентификатор задачи.

- Через консоль H2:

   Открыть консоль H2 по адресу `http://localhost:8080/h2-console` и выполнить SQL-запрос:

   ```
   DELETE FROM subscriber_task WHERE subscriber_id = 1 AND task_id = 1;
   ```

- Через браузер

   Использовать инструмент разработчика браузера (например, Chrome DevTools) для отправки DELETE-запроса:

   ```javascript
   fetch('http://localhost:8080/subscribers/1/tasks/1', {
       method: 'DELETE'
   })
   .then(response => response.json())
   .then(data => console.log(data))
   .catch(error => console.error('Error:', error));
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