# **Отчёт по итогам автоматизации**

## 📊 Планирование vs Фактические результаты

| Запланировано | Реализовано |
|---------------|-------------|
| **Проведение исследовательского тестирования** | Успешно выполнено тестирование веб-сервиса "Путешествие дня" |
| **Настройка SUT с подключением к БД** | Реализован удобный запуск системы с быстрым подключением к требуемой БД |
| **Разработка плана автоматизации** | Составлен детальный план на 52 тестовых сценария |
| **Создание тестового фреймворка** | Разработан фреймворк с Page Objects и helpers для генерации случайных тестовых данных |
| **Автоматизация тестирования** | Автоматизированы все 52 сценария |
| **Документирование результатов** | Подготовлен итоговый отчёт и создано (в разработке)  баг-репортов |
| **Использование запланированных инструментов** | Все инструменты использованы согласно плану |

## **Выявленные риски и их последствия**
**1. Проблемы с локаторами элементов**

Отсутствие уникальных CSS-селекторов значительно усложнило процесс написания автотестов и потребовало разработки сложных стратегий поиска элементов.

**2. Сложности с тестовым окружением**

Настройка поддержки двух СУБД (MySQL и PostgreSQL) потребовала дополнительных временных затрат и создания гибкой системы конфигурации.

**3. Недостаток документации**

Отсутствие четких требований и спецификаций вынудило:
- Принимать решения на основе экспертной оценки
- Разрабатывать тестовые сценарии, опираясь на логику и аналогичные системы
- Вводить дополнительные проверки для неочевидных случаев

**4. Проблемы с инфраструктурой**

Нестабильная работа Docker Desktop приводила к:
- Незапланированным простоям
- Необходимости перезапуска тестовых окружений
- Увеличению общего времени выполнения тестовых прогонов

## **⏱ Временные затраты**

| Этап                     | План (часов) | Факт (часов) | Отклонение | Причины отклонения         |
|--------------------------|-------------:|-------------:|:----------:|----------------------------|
| Анализ системы      | 10           | 12           | +2         | Недостаточная документация |
| Разработка тестов    | 20           | 20           | 0          | -                          |
| Настройка окружения  | 10           | 15           | +5         | Проблемы с подключением БД |
| Подготовка отчёта    | 5            | 5            | 0          | -                          |
| **Итого**               | **45**       | **52**       | **+24**     | -                          |

## **Общий вывод**
Работа по автоматизации тестирования показала высокую эффективность и позволила:

- Сократить время на повторное тестирование основных сценариев.
- Получить наглядную отчетность по результатам выполнения тестов.
