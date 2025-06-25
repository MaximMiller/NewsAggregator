
# NewsAggregator

Приложение для просмотра новостей из различных источников с использованием NewsAPI.

## Описание

NewsAggregator позволяет пользователям:

- Просматривать новости по категориям
- Искать новости по ключевым словам
- Сохранять избранные статьи для быстрого доступа


## Технологии

- Jetpack Compose — UI
- Retrofit — работа с NewsAPI
- Room — локальное хранение избранного
- Coroutines — асинхронные операции
- Dagger/Hilt — dependency injection
- Paging 3 — пагинация новостей
- Coil — загрузка изображений

## Установка и запуск

1. Клонируйте репозиторий:

```bash
git clone https://github.com/yourusername/newsapp.git
cd newsapp
```
2. Получите API ключ на NewsAPI.org и добавьте его в файл local.properties:
 ```
   NEWS_API_KEY="ваш_ключ_здесь"
 ```
3. Соберите и запустите проект в Android Studio.

## План разработки
Подробный план разработки приложения находится в файле DevelopmentPlan.md.


