# Implementación de Room en Eikocolors System

## Resumen de la Implementación

Se ha integrado exitosamente **Room Database** en el proyecto Eikocolors System siguiendo los principios de **Clean Architecture**. El proyecto ahora utiliza una estrategia de persistencia local que cachea los datos de Firebase para reducir las solicitudes de red.

## Estructura del Proyecto

### Capa de Datos (`data/`)

#### Entidades de Room (`data/local/model/`)
- **ClientEntity.kt**: Entidad de base de datos para clientes
- **WorkEntity.kt**: Entidad de base de datos para trabajos
- Funciones de mapeo: `toEntity()` y `toDomain()` para convertir entre modelos de dominio y entidades

#### DAOs (`data/local/`)
- **ClientDao.kt**: Operaciones CRUD para clientes
- **WorkDao.kt**: Operaciones CRUD para trabajos
- **AppDatabase.kt**: Clase principal de la base de datos Room

#### Conversores de Tipo (`core/data/local/converter/`)
- **Converters.kt**: Conversores para `LocalDateTime`, `ClientStatus`, `WorkStatus` y `WorkPriority`

#### Fuentes de Datos (`data/datasource/`)
- **LocalDataSource**: Interfaz para operaciones de datos locales
- **LocalDataSourceImpl**: Implementación que usa los DAOs de Room
- **RemoteDataSource**: Interfaz para operaciones de datos remotos
- **RemoteDataSourceImpl**: Implementación que usa Firebase Firestore

#### Repositorio (`data/repository/`)
- **AppRepositoryImpl**: Implementación del repositorio que coordina datos locales y remotos

### Capa de Dominio (`domain/`)

#### Repositorio (`domain/repository/`)
- **AppRepository**: Interfaz del repositorio principal

#### Casos de Uso (`domain/usecase/`)
- **SyncClientsUseCase**: Sincroniza clientes desde Firebase a la base de datos local
- **SyncWorksUseCase**: Sincroniza trabajos desde Firebase a la base de datos local

#### Casos de Uso de Features
Se actualizaron los casos de uso existentes para usar `AppRepository`:
- `GetAllClientsUseCase`
- `SaveClientUseCase`
- `UpdateClientUseCase`
- `DeleteClientUseCase`
- `SearchClientsUseCase` (ahora busca localmente)
- `GetAllWorksUseCase`
- `SaveWorkUseCase`
- `UpdateWorkUseCase`
- `DeleteWorkUseCase`
- `GetOverdueWorksUseCase`

### Inyección de Dependencias (`di/`)

#### Módulos Koin
- **DataModule.kt**: Provee base de datos Room, DAOs, fuentes de datos y repositorio
- **UseCaseModule.kt**: Actualizado para incluir casos de uso de sincronización
- **ViewModelModule.kt**: Sin cambios (usa inyección automática)

### ViewModels

#### ClientsViewModel
- Inyecta `SyncClientsUseCase` para sincronización inicial
- Sincroniza datos de Firebase la primera vez que se carga
- Luego lee datos directamente de Room

#### WorksViewModel
- Inyecta `SyncWorksUseCase` y `SyncClientsUseCase`
- Sincroniza datos de Firebase la primera vez que se carga
- Luego lee datos directamente de Room

## Flujo de Datos

### Lectura
1. La aplicación solicita datos a través de casos de uso
2. El repositorio devuelve un `Flow` de la base de datos local (Room)
3. Los datos se muestran en la UI en tiempo real (observables)

### Sincronización Inicial
1. Al iniciar la app, los ViewModels llaman a `syncClientsUseCase()` y `syncWorksUseCase()`
2. Estos casos de uso obtienen datos de Firebase (una sola vez usando `.first()`)
3. Los datos se guardan en Room usando `insertAll()`
4. La UI se actualiza automáticamente gracias a los `Flow` de Room

### Escritura (Crear/Actualizar/Eliminar)
1. El usuario realiza una acción (crear, actualizar o eliminar)
2. El caso de uso correspondiente llama al repositorio
3. El repositorio:
   - Actualiza Firebase (para mantener sincronización con la nube)
   - Actualiza Room (para reflejar el cambio localmente)
4. La UI se actualiza automáticamente gracias a los `Flow` de Room

## Configuración de la Base de Datos

La base de datos Room se configura en `DataModule.kt`:
- **Nombre**: `eikocolors_database.db`
- **Ubicación**: Directorio home del usuario (`~`)
- **Driver**: `BundledSQLiteDriver` (incluido con Room KMP)
- **Tablas**: `clients` y `works`

## Beneficios de la Implementación

1. **Offline First**: Los datos están disponibles incluso sin conexión
2. **Mejor Rendimiento**: Se reduce drásticamente el número de solicitudes a Firebase
3. **UI Reactiva**: Los cambios en la base de datos se reflejan automáticamente en la UI
4. **Clean Architecture**: Separación clara de responsabilidades
5. **Testabilidad**: Cada capa puede ser testeada independientemente
6. **Mantenibilidad**: Código organizado y fácil de entender

## Próximos Pasos Sugeridos

1. **Implementar estrategia de sincronización periódica**: Sincronizar con Firebase cada X minutos
2. **Manejo de conflictos**: Definir estrategia para conflictos de sincronización
3. **Indicadores de sincronización**: Mostrar al usuario cuándo se están sincronizando datos
4. **Logs y monitoreo**: Agregar logging para depuración
5. **Tests unitarios**: Crear tests para repositorios, casos de uso y ViewModels
6. **Migración de base de datos**: Implementar estrategia de migración para futuras versiones

## Notas Técnicas

- Room genera código en tiempo de compilación usando KSP
- Los esquemas de la base de datos se guardan en `composeApp/schemas/jvm/`
- Los `TypeConverter` son necesarios para tipos complejos como `LocalDateTime` y enums
- Firebase sigue siendo la fuente de verdad para operaciones de escritura

## Comandos Útiles

```bash
# Compilar el proyecto
./gradlew build

# Ejecutar la aplicación
./gradlew run

# Limpiar y compilar
./gradlew clean build
```

## Solución de Problemas

Si encuentras errores de compilación:
1. Ejecuta `./gradlew clean`
2. Sincroniza el proyecto en el IDE (File > Sync Project with Gradle Files)
3. Invalida cachés y reinicia (File > Invalidate Caches / Restart)

---

**Implementación completada exitosamente** ✅

