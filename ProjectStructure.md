
# Структура проекта com.example.newsaggregator

```
com.example.newsaggregator/
├── core/
│   ├── di/
│   │   ├── NetworkModule.kt
│   │   ├── DatabaseModule.kt
│   │   ├── ActionModule.kt
│   │   └── CacheModule.kt         
│   ├── navigation/
│   │   └── NavigationPrimitives.kt
│   ├── database/
│   │   └── AppDatabase.kt
│   └── network/
│       ├── exceptions/
│       │   └── ApiException.kt     
│       └── extensions/
│           └── ResponseExtensions.kt
├── features/
│   ├── newsfeed/
│   │   ├── data/
│   │   │   ├── local/
│   │   │   │   ├── dao/
│   │   │   │   │   └── NewsDao.kt
│   │   │   │   └── entity/
│   │   │   │       └── NewsEntity.kt
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   │   └── NewsApi.kt
│   │   │   │   └── dto/
│   │   │   │       ├── ArticleDto.kt
│   │   │   │       ├── NewsResponseDto.kt
│   │   │   │       ├── NewsSourceDto.kt     
│   │   │   │       ├── SourceDto.kt
│   │   │   │       └── SourcesResponseDto.kt
│   │   │   ├── action/             
│   │   │   │   ├── FetchHeadlinesActionImpl.kt
│   │   │   │   ├── GetCachedHeadlinesImpl.kt
│   │   │   │   ├── GetCachedNewsByQueryImpl.kt
│   │   │   │   ├── GetCachedSourcesByFiltersImpl.kt
│   │   │   │   ├── GetSourcesActionImpl.kt
│   │   │   │   ├── SaveNewsToCacheImpl.kt
│   │   │   │   ├── SaveSourcesToCacheImpl.kt
│   │   │   │   └── SearchNewsActionImpl.kt
│   │   │   └── mapper/            
│   │   │       ├── NewsMapper.kt
│   │   │       └── SourceMapper.kt
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── NewsItem.kt
│   │   │   │   ├── SourceItem.kt  
│   │   │   │   └── FeedType.kt     
│   │   │   ├── action/             
│   │   │   │   ├── FetchHeadlinesAction.kt
│   │   │   │   ├── GetCachedHeadlinesAction.kt
│   │   │   │   ├── GetCachedNewsByQueryAction.kt
│   │   │   │   ├── GetCachedSourcesByFiltersAction.kt
│   │   │   │   ├── GetSourcesAction.kt
│   │   │   │   ├── SaveNewsToCacheAction.kt
│   │   │   │   ├── SaveSourcesToCacheAction.kt
│   │   │   │   └── SearchNewsAction.kt
│   │   │   └── usecase/            
│   │   │       ├── GetHeadlinesUseCase.kt
│   │   │       ├── GetSourcesUseCase.kt
│   │   │       └── SearchNewsUseCase.kt
│   │   └── presentation/
│   │       ├── screen/
│   │       │   ├── feed/
│   │       │   │   ├── NewsFeedScreen.kt
│   │       │   │   └── state/
│   │       │   │       └── NewsFeedState.kt
│   │       │   └── details/
│   │       │       ├── NewsDetailsScreen.kt
│   │       │       └── state/
│   │       │           └── NewsDetailsState.kt
│   │       ├── component/
│   │       │   ├── NewsCardCell.kt
│   │       │   ├── LoadingCell.kt
│   │       │   └── ErrorCell.kt
│   │       ├── viewmodel/
│   │       │   ├── NewsFeedViewModel.kt
│   │       │   └── NewsDetailsViewModel.kt
│   │       └── router/
│   │           └── NewsRouter.kt
│   └── favorites/
│       ├── data/
│       │   ├── local/
│       │   │   ├── dao/
│       │   │   │   └── FavoritesDao.kt
│       │   │   └── entity/
│       │   │       └── FavoriteEntity.kt
│       │   ├── mapper/             
│       │   │   └── FavoriteMapper.kt
│       │   └── action/
│       │       ├── SaveFavoriteActionImpl.kt
│       │       ├── LoadFavoritesActionImpl.kt
│       │       ├── RemoveFavoriteActionImpl.kt  
│       │       └── IsFavoriteCheckActionImpl.kt 
│       ├── domain/
│       │   ├── action/
│       │   │   ├── SaveFavoriteAction.kt
│       │   │   ├── LoadFavoritesAction.kt
│       │   │   ├── RemoveFavoriteAction.kt  
│       │   │   └── IsFavoriteCheckAction.kt 
│       │   └── usecase/              
│       │       ├── GetFavoritesUseCase.kt
│       │       └── ToggleFavoriteUseCase.kt
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
- `di/CacheModule.kt` - Управление кэшированием

**Навигация**
- `navigation/NavigationPrimitives.kt` - Базовые маршруты и граф навигации

**База данных**
- `database/AppDatabase.kt` - Главный класс базы данных Room

**Сеть**
- `network/exceptions/ApiException.kt` - Иерархия ошибок
- `network/extensions/ResponseExtensions.kt` - Утилиты

### 2. Feature Modules

#### feature: newsfeed

**Data Layer**
- `data/local/`
    - `dao/NewsDao.kt` - Интерфейс Room для локального кэша
    - `entity/NewsEntity.kt` - Сущности БД
    - `entity/SourceEntity.kt` - Сущности БД
- `data/remote/`
    - `api/NewsApi.kt` - Retrofit интерфейсы
    - `dto/` - Сетевые модели:
        - `NewsFeedResponseDto.kt`
        - `ArticleDto.kt`
        - `SourceDto.kt`
- `data/action/` - Реализации Action:
    - `FetchHeadlinesActionImpl.kt`
    - `SearchNewsActionImpl.kt`
    -  `GetCachedSourcesImpl.kt`

**Domain Layer**
- `domain/model/NewsItem.kt` - Доменная модель новости
- `domain/model/SourceItem.kt` - Доменная модель ресурсов
- `domain/model/FeedType.kt` - Доменная модель типов ленты
- `domain/action/` - Интерфейсы действий:
    - `FetchHeadlinesAction.kt`
    - `SearchNewsAction.kt`
    - `GetSourcesAction.kt`
- `domain/usecase/GetHeadlinesUseCase.kt` - Бизнес-логика работы с лентой новостей
- `domain/usecase/GetSourcesUseCase.kt` - Бизнес-логика работы с ресурсами
- `domain/usecase/SearchNewsUseCase.kt` - Бизнес-логика работы с поиском

**Presentation Layer**
- `presentation/`
   - `screen/`
         - `feed/` - Подраздел для ленты новостей
             - `NewsFeedScreen.kt` - Основной экран ленты
             - `state/NewsFeedState.kt` - Состояния UI экрана ленты
         - `details/` -  Подраздел для деталей
             - `NewsDetailsScreen.kt` - Экран деталей новости
             - `state/NewsDetailsState.kt` - Состояния UI экрана деталей 
- `presentation/component/` - UI компоненты:
  - `NewsCardCell.kt` - Карточка новости
  - `LoadingCell.kt` - Индикатор загрузки
  - `ErrorCell.kt` - Блок ошибок
- `presentation/viewmodel/NewsFeedViewModel.kt` - VM для экрана ленты
- `presentation/viewmodel/NewsDetailsViewModel.kt` - VM для деталей
- `presentation/router/NewsRouter.kt` - Навигация фичи

#### feature: favorites

**Data Layer**
- `data/local/`
    - `dao/FavoritesDao.kt` - Доступ к избранному
    - `entity/FavoriteEntity.kt` - Сущности БД
- `data/action/` - Реализации:
    - `SaveFavoriteActionImpl.kt`
    - `LoadFavoritesActionImpl.kt`

**Domain Layer**
- `domain/action/` - Интерфейсы:
    - `SaveFavoriteAction.kt`
    - `LoadFavoritesAction.kt`
- `domain/usecase/ToggleFavoriteUseCase.kt` - Логика работы с избранным

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