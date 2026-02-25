# Mango Challenge

Aplicacion Android que muestra un catalogo de productos obtenidos de la [Fake Store API](https://fakestoreapi.com/), permite gestionar favoritos con persistencia local y visualizar un perfil de usuario.

## Capturas de pantalla

| Productos | Favoritos | Perfil |
|----------|-----------|--------|
| <img src="https://github.com/user-attachments/assets/24126428-8046-4dd2-aa6c-e31c209de0ed" width="298"/> | <img src="https://github.com/user-attachments/assets/8b83c4e8-dbe1-484b-b8ac-40d107841983" width="298"/> | <img src="https://github.com/user-attachments/assets/2869b154-bfa2-49c2-8546-85b0f4afef08" width="298"/> |

## Arquitectura

El proyecto sigue **Clean Architecture** con separacion en capas y un enfoque **multi-modulo**:

```
View (Compose) -> ViewModel -> UseCase -> Repository -> DataSource (Remote / Local)
```

### Modulos

| Modulo | Responsabilidad |
|--------|----------------|
| `:app` | Navegacion, Hilt setup, MainActivity |
| `:core:domain` | Modelos de dominio, interfaces de repositorio, casos de uso |
| `:core:data` | DataSources (remoto + local), implementaciones de repositorio, DTOs, mappers, modulos DI |
| `:core:ui` | Composables compartidos (ProductCard, ProductListContent, EmptyState, StateWrapper, UiState) |
| `:core:testing` | Utilidades de test compartidas (MainDispatcherRule, TestFixtures) |
| `:feature:products` | Pantalla y ViewModel de listado de productos |
| `:feature:favorites` | Pantalla y ViewModel de favoritos |
| `:feature:profile` | Pantalla y ViewModel de perfil de usuario |

### Flujo de datos

```
FakeStoreApi (Retrofit)
        |
   RemoteDataSource
        |
   ProductRepository  <--- merges --->  LocalDataSource (Room)
        |
     UseCase
        |
    ViewModel
        |
  Compose Screen
```

- **Productos**: Se obtienen de la API remota y se cruzan con los IDs de favoritos almacenados en Room para mostrar el estado correcto.
- **Favoritos**: Se almacenan como entidades completas en Room. La lista se observa de forma reactiva con `Flow`.
- **Perfil**: Datos del usuario desde la API + contador reactivo de favoritos desde Room.

## Stack tecnologico

| Categoria | Tecnologia | Version |
|-----------|------------|---------|
| Lenguaje | Kotlin | 2.0.21 |
| UI | Jetpack Compose + Material 3 | BOM 2024.09.00 |
| Navegacion | Navigation Compose | 2.8.5 |
| DI | Hilt (con KSP) | 2.56 |
| Base de datos | Room | 2.6.1 |
| Networking | Retrofit + OkHttp | 2.11.0 / 4.12.0 |
| Serializacion | Gson | 2.11.0 |
| Imagenes | Coil | 2.7.0 |
| Async | Coroutines + Flow | 1.9.0 |
| Testing | JUnit 4, MockK, Turbine, Espresso | - |

## Configuracion del proyecto

| Propiedad | Valor |
|-----------|-------|
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |
| Compile SDK | 36 |
| JVM Target | 11 |

## Funcionalidades

- **Listado de productos**: Carga productos de la Fake Store API con imagen, titulo, precio y valoracion.
- **Busqueda de productos**: Barra de busqueda en tiempo real que filtra por nombre o categoria.
- **Pull-to-refresh**: Gesto de arrastrar para refrescar la lista de productos sin perder el estado actual.
- **Gestion de favoritos**: Toggle de favorito desde cualquier tarjeta de producto. Persistencia local con Room.
- **Pantalla de favoritos**: Lista reactiva de productos favoritos con estado vacio.
- **Perfil de usuario**: Muestra datos del usuario (nombre, email, telefono, direccion) y un contador reactivo de favoritos.
- **Navegacion inferior**: Tres tabs (Productos, Favoritos, Perfil) con guardado/restauracion de estado.
- **Manejo de estados**: Loading, Error (con retry) y Success en todas las pantallas.
- **Soporte dark mode**: Temas Material 3 con colores dinamicos (Android 12+).

## Testing

### Tests unitarios

| Archivo | Cobertura |
|---------|-----------|
| `ProductsViewModelTest` | Carga inicial, manejo de errores, toggle de favoritos |
| `FavoritesViewModelTest` | Emision de Flow, toggle de favoritos |
| `ProfileViewModelTest` | Carga de perfil, manejo de errores, contador reactivo |
| `ProductRepositoryImplTest` | Merge de productos con favoritos, toggle add/remove, conteo |

### Tests instrumentados

| Archivo | Cobertura |
|---------|-----------|
| `FavoriteProductDaoTest` | CRUD completo de Room DAO (insert, delete, query, count) |
| `NavigationTest` | Navegacion inferior entre las tres tabs |

### Ejecutar tests

```bash
# Tests unitarios
./gradlew test

# Tests instrumentados (requiere emulador/dispositivo)
./gradlew connectedAndroidTest

# Build completo
./gradlew assembleDebug
```

## Estructura del proyecto

```
mangochallenge/
├── app/
│   └── src/main/java/.../
│       ├── MangoApplication.kt          # @HiltAndroidApp
│       ├── MainActivity.kt              # @AndroidEntryPoint, Scaffold + BottomNav
│       └── navigation/
│           ├── Screen.kt                # Sealed class con rutas e iconos
│           └── MangoNavGraph.kt         # NavHost con composable routes
├── core/
│   ├── domain/
│   │   └── src/main/java/.../
│   │       ├── model/
│   │       │   ├── Product.kt           # Product + Rating
│   │       │   └── User.kt             # User + UserName + Address
│   │       ├── repository/
│   │       │   ├── ProductRepository.kt
│   │       │   └── UserRepository.kt
│   │       └── usecase/
│   │           ├── GetProductsUseCase.kt
│   │           ├── GetFavoritesUseCase.kt
│   │           ├── ToggleFavoriteUseCase.kt
│   │           ├── GetFavoriteCountUseCase.kt
│   │           └── GetUserProfileUseCase.kt
│   ├── data/
│   │   └── src/main/java/.../
│   │       ├── remote/
│   │       │   ├── api/FakeStoreApi.kt
│   │       │   ├── dto/ProductDto.kt, UserDto.kt
│   │       │   └── datasource/RemoteDataSource[Impl].kt
│   │       ├── local/
│   │       │   ├── MangoDatabase.kt
│   │       │   ├── entity/FavoriteProductEntity.kt
│   │       │   ├── dao/FavoriteProductDao.kt
│   │       │   └── datasource/LocalDataSource[Impl].kt
│   │       ├── mapper/ProductMapper.kt, UserMapper.kt
│   │       ├── repository/ProductRepositoryImpl.kt, UserRepositoryImpl.kt
│   │       └── di/
│   │           ├── NetworkModule.kt
│   │           ├── DatabaseModule.kt
│   │           ├── DataSourceModule.kt
│   │           └── RepositoryModule.kt
│   ├── ui/
│   │   └── src/main/java/.../
│   │       ├── UiState.kt               # Sealed interface Loading/Success/Error
│   │       ├── StateWrapper.kt          # Composable de manejo de estados
│   │       ├── ProductCard.kt           # Tarjeta de producto reutilizable
│   │       ├── ProductListContent.kt    # LazyColumn de productos reutilizable
│   │       └── EmptyState.kt           # Estado vacio reutilizable
│   └── testing/
│       └── src/main/java/.../
│           ├── MainDispatcherRule.kt    # JUnit Rule para coroutines en tests
│           └── TestFixtures.kt          # Factories de datos de test (testProduct, testUser)
└── feature/
    ├── products/
    │   └── ProductsViewModel.kt, ProductsScreen.kt
    ├── favorites/
    │   └── FavoritesViewModel.kt, FavoritesScreen.kt
    └── profile/
        └── ProfileUiState.kt, ProfileViewModel.kt, ProfileScreen.kt
```

## API

La aplicacion consume la [Fake Store API](https://fakestoreapi.com/):

| Endpoint | Metodo | Descripcion |
|----------|--------|-------------|
| `/products` | GET | Obtiene todos los productos |
| `/users/{id}` | GET | Obtiene un usuario por ID (hardcoded: 1) |

## Posibles mejoras

- **Paginacion**: Implementar paginacion con Paging 3 para la lista de productos.
- **Cache de red**: Agregar una capa de cache con Room o OkHttp cache para el listado de productos, reduciendo llamadas a la API.
- **Filtros avanzados**: Filtrado por precio, valoracion o combinaciones multiples.
- **Detalle de producto**: Pantalla de detalle con descripcion completa, galeria de imagenes y productos relacionados.
- **Autenticacion real**: Reemplazar el usuario hardcodeado por un flujo de login/registro.
- **Offline-first**: Estrategia completa offline-first sincronizando productos remotos con la base de datos local.
- **Migraciones de base de datos**: Implementar migraciones de Room para actualizaciones futuras del esquema.
- **CI/CD**: Configurar GitHub Actions para build automatico, lint y ejecucion de tests.
- **Modularizacion de UI tests**: Mover tests instrumentados a sus modulos de feature correspondientes.
- **Accesibilidad**: Mejorar soporte de accesibilidad con contentDescription mas descriptivos y soporte de TalkBack.
- **Analytics**: Integracion con Firebase Analytics para tracking de eventos de usuario.
- **Manejo de errores granular**: Distinguir entre errores de red, timeout, servidor, etc. con mensajes especificos.
- **Animaciones**: Transiciones entre pantallas y animacion del toggle de favorito.
