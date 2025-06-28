
# Структура проекта com.example.newsaggregator

```
com.example.newsaggregator/
├── core/
│   ├── di/
│   │   ├── NetworkModule.kt
│   │   ├── DatabaseModule.kt
│   │   └── ActionModule.kt
│   ├── navigation/
│   │   └── NavigationPrimitives.kt
│   └── database/
│       └── AppDatabase.kt
├── features/
│   ├── newsfeed/
│   │   ├── data/
│   │   │   ├── local/
│   │   │   │   ├── dao/
│   │   │   │   │   └── NewsFeedDao.kt
│   │   │   │   └── entity/
│   │   │   │       └── NewsFeedEntity.kt
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   │   └── NewsFeedApi.kt
│   │   │   │   └── dto/
│   │   │   │       ├── NewsFeedResponseDto.kt
│   │   │   │       └── ArticleDto.kt
│   │   │   └── action/
│   │   │       ├── FetchHeadlinesActionImpl.kt
│   │   │       └── SearchNewsActionImpl.kt
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   └── NewsFeedItem.kt
│   │   │   ├── action/
│   │   │   │   ├── FetchHeadlinesAction.kt
│   │   │   │   └── SearchNewsAction.kt
│   │   │   └── interactor/
│   │   │       └── NewsFeedInteractor.kt
│   │   └── presentation/
│   │       ├── screen/
│   │       │   ├── NewsFeedScreen.kt
│   │       │   └── state/
│   │       │       └── NewsFeedState.kt
│   │       ├── component/
│   │       │   ├── NewsFeedCardCell.kt
│   │       │   ├── NewsFeedLoadingCell.kt
│   │       │   └── NewsFeedErrorCell.kt
│   │       ├── viewmodel/
│   │       │   └── NewsFeedViewModel.kt
│   │       └── router/
│   │           └── NewsFeedRouter.kt
│   └── favorites/
│       ├── data/
│       │   ├── local/
│       │   │   ├── dao/
│       │   │   │   └── FavoritesDao.kt
│       │   │   └── entity/
│       │   │       └── FavoriteEntity.kt
│       │   └── action/
│       │       ├── SaveFavoriteActionImpl.kt
│       │       └── LoadFavoritesActionImpl.kt
│       ├── domain/
│       │   ├── model/
│       │   │   └── FavoriteItem.kt
│       │   ├── action/
│       │   │   ├── SaveFavoriteAction.kt
│       │   │   └── LoadFavoritesAction.kt
│       │   └── interactor/
│       │       └── FavoritesInteractor.kt
│       └── presentation/
│           ├── screen/
│           │   ├── FavoritesScreen.kt
│           │   └── state/
│           │       └── FavoritesState.kt
│           ├── component/
│           │   └── FavoriteItemCell.kt
│           ├── viewmodel/
│           │   └── FavoritesViewModel.kt
│           └── router/
│               └── FavoritesRouter.kt
├── resources/
│   ├── Images.kt
│   └── Strings.kt
└── App.kt
```

## Подробное описание структуры

### 1. Core Module

**DI (Dagger/Hilt)**
- `di/NetworkModule.kt` - Настройка Retrofit, OkHttp и API сервисов
- `di/DatabaseModule.kt` - Настройка Room Database и DAO
- `di/ActionModule.kt` - Биндинги интерфейсов Action к их реализациям

**Навигация**
- `navigation/NavigationPrimitives.kt` - Базовые маршруты и граф навигации

**База данных**
- `database/AppDatabase.kt` - Главный класс базы данных Room

### 2. Feature Modules

#### feature: newsfeed

**Data Layer**
- `data/local/`
    - `dao/NewsFeedDao.kt` - Интерфейс Room для локального кэша
    - `entity/NewsFeedEntity.kt` - Сущности БД
- `data/remote/`
    - `api/NewsFeedApi.kt` - Retrofit интерфейсы
    - `dto/` - Сетевые модели:
        - `NewsFeedResponseDto.kt`
        - `ArticleDto.kt`
- `data/action/` - Реализации Action:
    - `FetchHeadlinesActionImpl.kt`
    - `SearchNewsActionImpl.kt`

**Domain Layer**
- `domain/model/NewsFeedItem.kt` - Доменная модель новости
- `domain/action/` - Интерфейсы действий:
    - `FetchHeadlinesAction.kt`
    - `SearchNewsAction.kt`
- `domain/interactor/NewsFeedInteractor.kt` - Бизнес-логика работы с лентой новостей

**Presentation Layer**
- `presentation/screen/`
    - `NewsFeedScreen.kt` - Основной экран ленты
    - `state/NewsFeedState.kt` - Состояния UI экрана
- `presentation/component/` - UI компоненты:
    - `NewsFeedCardCell.kt` - Карточка новости
    - `NewsFeedLoadingCell.kt` - Индикатор загрузки
    - `NewsFeedErrorCell.kt` - Блок ошибок
- `presentation/viewmodel/NewsFeedViewModel.kt` - VM для экрана
- `presentation/router/NewsFeedRouter.kt` - Навигация фичи

#### feature: favorites

**Data Layer**
- `data/local/`
    - `dao/FavoritesDao.kt` - Доступ к избранному
    - `entity/FavoriteEntity.kt` - Сущности БД
- `data/action/` - Реализации:
    - `SaveFavoriteActionImpl.kt`
    - `LoadFavoritesActionImpl.kt`

**Domain Layer**
- `domain/model/FavoriteItem.kt` - Доменная модель избранного
- `domain/action/` - Интерфейсы:
    - `SaveFavoriteAction.kt`
    - `LoadFavoritesAction.kt`
- `domain/interactor/FavoritesInteractor.kt` - Логика работы с избранным

**Presentation Layer**
- `presentation/screen/`
    - `FavoritesScreen.kt` - Экран избранного
    - `state/FavoritesState.kt` - UI состояния
- `presentation/component/FavoriteItemCell.kt` - Элемент списка
- `presentation/viewmodel/FavoritesViewModel.kt` - VM для экрана
- `presentation/router/FavoritesRouter.kt` - Навигация

### 3. Resources Module

**Ресурсы приложения**
- `resources/Images.kt` - Централизованное хранение изображений:
  ```kotlin
  object Images {
      val NewsPlaceholder = painterResource(R.drawable.news_placeholder)
      val FavoriteFilled = painterResource(R.drawable.ic_favorite_filled)
  }
  ```