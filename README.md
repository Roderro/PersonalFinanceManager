# Консольное приложение для управления личными финансами

Это консольное приложение, разработанное для управления личными финансами. Оно позволяет пользователям отслеживать свои
доходы и расходы, устанавливать лимиты по категориям и получать отчеты о своих финансах. Приложение использует базу
данных H2 и Hibernate для хранения и управления данными.

## Структура проекта

Проект разделен на несколько модулей, включая:

* `ioconsole`: Модуль для работы с консольным вводом/выводом, включая абстрактные классы `AbstractPanel` и
  `AbstractMainPanel`.
* `models`: Модуль, содержащий Entity-классы, сопоставленные с таблицами базы данных.
* `repository`: Модуль, содержащий репозиторные классы для взаимодействия с базой данных через Hibernate.
* `security`: Модуль, содержащий классы для обеспечения безопасности, включая аутентификацию и авторизацию.
* `transport`: Модуль, отвечающий за настройку потоков ввода и вывода данных, а также за обработку ввода команд.
* `utils`: Модуль, содержащий вспомогательные утилиты и классы, которые не относятся к основной бизнес-логике.
* `main`: Модуль, содержащий класс `App`, точку входа в приложение.

## Модуль ioconsole: Управление Вводом-Выводом через Консольное Меню

**Назначение:** Модуль `ioconsole` предоставляет механизм для интерактивного взаимодействия с приложением через
консольный интерфейс, организованный в виде иерархического меню. Структура пакетов и классов внутри модуля отражает
структуру меню приложения.

**Организация:**

Модуль `ioconsole` построен по принципу иерархической навигации, где каждый пакет соответствует пункту меню, а вложенные
пакеты и классы — подпунктам меню.

**Ключевые Компоненты:**

1. **`AbstractPanel`:**
    * Базовый абстрактный класс для представления элементов меню (пунктов или подпунктов).
    * Должен иметь статическое поле `TEXT` типа `String`, **переопределенное** в каждом конкретном подклассе. Это поле
      содержит текст, отображаемый в консоли как название пункта меню.
    * Предполагается реализация метода для выполнения определенной логики, ассоциированной с пунктом меню.

2. **`AbstractMainPanel`:**
    * Абстрактный класс для представления главных (корневых) панелей меню.
    * Обеспечивает автоматизированное построение и отображение меню на основе анализа подклассов `AbstractPanel`,
      находящихся в текущем пакете, а также анализа `AbstractMainPanel` из вложенных пакетов.
    * Содержит логику для итеративного вывода пунктов меню и их подпунктов на основе структуры пакетов и классов.

**Автоматическая Генерация Меню:**

Для автоматической генерации меню необходимо соблюдать следующие условия:

* **Наличие `TEXT` в `AbstractPanel`:** Каждый класс, который должен отображаться как пункт или подпункт меню, должен
  иметь статическое поле `TEXT` со строковым представлением этого пункта.

* **Наличие `MainPanel` в пакете:** В каждом пакете, представляющем пункт меню, должен присутствовать класс `MainPanel`,
  **наследуемый** от `AbstractMainPanel`, который обеспечивает автоматический вывод названий подменю.

* **Отключение автоматического включения:**
    * Для **исключения пакета** или класса из автоматической генерации меню необходимо добавить префикс `_` (
      подчеркивание) перед его названием (имени пакета или класса).

**Принцип Работы:**

Модуль `ioconsole` работает путем сканирования и анализа классов внутри модуля. При инициализации модуля `MainPanel` в
корневом пакете запускает процесс отображения меню, который рекурсивно обходит вложенные пакеты и классы, выводя пункты
меню на основе статических полей `TEXT` классов `AbstractPanel`.

**Преимущества:**

* **Автоматизация:** Избавляет от ручной настройки консольного меню, сокращая время разработки.
* **Гибкость:** Позволяет легко добавлять и удалять пункты меню, просто создавая новые классы в соответствующих пакетах.
* **Поддерживаемость:** Структурированная иерархия меню упрощает навигацию и поддержку.
* **Конфигурируемость:** Возможность исключения пакетов и классов из автоматического включения.

**Заключение:**

Модуль `ioconsole` предоставляет мощный и гибкий механизм для создания консольного интерфейса, отражающего структуру
приложения. Его автоматизированный подход упрощает разработку и поддержку, позволяя разработчикам сосредоточиться на
реализации основной логики приложения.

## Зависимости

* Java 22+
* Maven
* Postgres
* Spring
* Hibernate


## Использование

После запуска приложения вы увидите главное меню. Используйте цифры для выбора нужных пунктов меню. Приложение проведет
вас через процесс авторизации и предоставит доступ к функциональности по управлению финансами.

* **Авторизация:** Введите логин и пароль. Если вы новый пользователь, создайте учетную запись, следуя инструкциям на
  экране.
* **Управление финансами:** Используйте меню для добавления доходов и расходов, просмотра категорий бюджета и установки
  лимитов.
* **Просмотр отчетов:** Просматривайте отчеты о своих финансах и бюджете.
* **Настройки:** Используйте меню для изменения логина и пароля.

## Структура меню

Меню "Аутентификация":

1. Вход
2. Регистрация
3. Закрыть приложение

Главное меню:

1. Управление финансами
2. Настройки
3. Просмотр отчетов
4. Выйти из аккаунта
5. Закрыть приложение

Подменю "Управление финансами":

1. Управление транзакциями
2. Управление категориями
3. Назад
4. Закрыть приложение

Подменю "Настройки":

1. Изменить логин
2. Изменить пароль
3. Назад
4. Закрыть приложение

Подменю "Просмотр отчетов":

1. Общие отчеты
2. Отчеты по категориям
3. Отчеты за период
4. Назад
5. Закрыть приложение



