**Тест-план проекта Pickman**

**Содержание**

1. [Введение](#1)
2. [Объект тестирования](#2)
3. [Риски](#3)
4. [Аспекты тестирования](#4)
5. [Подходы к тестированию](#5)
6. [Представление результатов](#6)
7. [Выводы](#7)

<a name="1"></a>

**1. Введение**

Данный файл содержит тест план приложения Pickman. Основной целью является проверка приложения на соответствие требованиям SRS.

<a name="2"></a>

**2. Объект тестирования**

Объект тестирования - приложение Pickman. Данная игра предлагает пользователю поиграть за главного героя и пройти 3 различных уровня, убивая монстров и собирая монеты. Приложение предназначено для работы на ОС Windows. Для написания приложения был использован язык программирования Java, фреймворк - JavaFX.

Приложение обязано обладать определенными атрибутами качества:

1. Функциональность:

- функциональная полнота: приложение обязано выполнять все заявленные функции в соответствии с SRS;
- функциональная целесообразность: отсутствуют незаявленные функции, которые бы мешали приложению выполнять первоначально поставленные задачи.

2. Удобство использования:

- Производительность: распределение ресурсов операционной системы;

- Понятность: интуитивный интерфейс.

3. Наличие качественной документации.

<a name="3"></a>

**3.** **Риски**

К рискам можно отнести следующие пункты:

- Возможность некорректной загрузки приложения.

<a name="4"></a>

**4. Аспекты тестирования:**

К аспектам тестирования относится реализация основных функций приложения:

- Проверка движения вправо;

- Проверка движения влево;

- Проверка движения вверх;

- Проверка движения вниз;

- Проверка выстрела огненным шаром;

- Проверка запуска игры;

- Проверка изменения громкости звука;

- Проверка изменения громкости музыки;

- Проверка выхода из игры;

- Проверка начала новой игры;

- Проверка просмотра таблицы рекордов;

- Проверка завершения игры.

<a name="5"></a>

**5. Подходы к тестированию**

Каждый аспект тестирования был произведен с помощью системного тестирования.

Системное тестирование - это тестирование программы в целом.

Каждый тест производится вручную.

<a name="6"></a>

**6. Представление результатов**

| ID   | Case Description                    | Scenario/Steps                          | Expected Result                                          |
| :--- | ----------------------------------- | --------------------------------------- | -------------------------------------------------------- |
| 1    | Проверка движения вправо            | нажать клавишу "D"                      | Главный герой движется вправо                            |
| 2    | Проверка движения влево             | нажать клавишу "A"                      | Главный герой движется влево                             |
| 3    | Проверка движения вверх             | нажать клавишу "W"                      | Главный герой движется вверх                             |
| 4    | Проверка движения вниз              | нажать клавишу "S"                      | Главный герой движется вниз                              |
| 5    | Проверка выстрела огненным шаром    | нажать клавишу "пробел"                 | Огненный шар был выпущен по ходу движения главного героя |
| 6    | Проверка запуска игры               | Запустить приложение                    | Приложение запущено                                      |
| 7    | Проверка изменения громкости звука  | Сдвиг ползунка громкости звука вправо   | Громкость звуков увеличена                               |
| 8    | Проверка изменения громкости музыки | Сдвиг ползунка громкости музыки вправо  | Громкость музыки увеличена                               |
| 9    | Проверка выхода из игры             | Нажатие на кнопку "выход"               | Завершение игры                                          |
| 10   | Проверка начала новой игры          | Нажатие на кнопку "Новая игра"          | Запущен первый уровень игры                              |
| 11   | Проверка просмотра таблицы рекордов | Ввод логина и нажатие на кнопку "далее" | Открытие таблицы рекордов                                |
| 12   | Проверка завершения игры.           | Нажатие на кнопку "Меню"                | Выход в меню                                             |

<a name="7"></a>

**7. Выводы**

Данный тестовый план позволяет протестировать основной функционал приложения. Успешное прохождение всех тестов может свидетельствовать о том, что приложение стабильно работает и соответствует всем заявленным требования.