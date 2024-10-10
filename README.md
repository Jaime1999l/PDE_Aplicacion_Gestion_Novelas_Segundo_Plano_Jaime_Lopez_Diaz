LINK: https://github.com/Jaime1999l/PDE_Aplicacion_Gestion_Novelas_Segundo_Plano_Jaime_Lopez_Diaz.git

# Gestión de Novelas en Segundo Plano

Este proyecto es una aplicación para la gestión de novelas, que permite a los usuarios agregar, ver y gestionar novelas y sus reseñas. También incorpora la funcionalidad de marcar novelas como favoritas, todo a través de una arquitectura basada en Android con el uso de Firebase para la persistencia de datos.

### Clases y Funcionalidades

#### 1. `PantallaPrincipalActivity` (MainActivity)
   - **Descripción**: Actividad principal que muestra la lista de novelas y permite la navegación entre diferentes funcionalidades.
   - **Componentes**:
     - `DrawerLayout`: Menú lateral para la navegación.
     - `FirebaseFirestore`: Conexión con Firebase para la carga de datos.
     - `ExecutorService`: Ejecuta tareas en segundo plano para actualizar la lista de novelas.
   - **Métodos Principales**:
     - `openDrawer()`: Abre el menú lateral.
     - `setupNavigation()`: Configura las acciones del menú de navegación.
     - `loadNovels()`: Carga la lista de novelas desde Firebase.
     - `updateNovelList(List<Novel>)`: Actualiza la lista de novelas en pantalla evitando duplicados.
     - `startPolling()`: Realiza una actualización periódica de la lista de novelas.
     - `onDestroy()`: Detiene el servicio en segundo plano al destruir la actividad.

#### 2. `ReviewActivity`
   - **Descripción**: Actividad que muestra todas las reseñas de las novelas.
   - **Componentes**:
     - `ReviewViewModel`: Proporciona acceso a las reseñas almacenadas en Firebase.
     - `LinearLayout`: Contiene la vista de las reseñas.
   - **Métodos Principales**:
     - `loadAllReviews()`: Observa los cambios en la lista de reseñas y actualiza la interfaz.
     - `displayReviews(List<Review>)`: Muestra cada reseña en pantalla con opciones para ver más detalles de la novela.
     - `loadNovelDetails(String novelId)`: Carga los detalles de una novela específica.

#### 3. `AddReviewActivity`
   - **Descripción**: Permite a los usuarios agregar una nueva reseña para una novela.
   - **Componentes**:
     - `EditText`: Campos para el nombre del revisor, comentario y puntuación.
     - `Button`: Botón para agregar la reseña.
   - **Métodos Principales**:
     - `addReview()`: Valida y guarda una nueva reseña en Firebase.

#### 4. `FavoritesActivity`
   - **Descripción**: Muestra la lista de novelas marcadas como favoritas por el usuario.
   - **Componentes**:
     - `FavoritesViewModel`: Obtiene las novelas favoritas desde Firebase.
     - `LinearLayout`: Muestra la lista de novelas favoritas.
   - **Métodos Principales**:
     - `displayFavoriteNovels(List<Novel>)`: Muestra cada novela favorita en la vista.

#### 5. `AddEditNovelActivity`
   - **Descripción**: Permite agregar una nueva novela o editar una existente.
   - **Componentes**:
     - `EditText`: Campos para el título, autor, año y sinopsis de la novela.
     - `ImageView`: Muestra la portada de la novela.
     - `ActivityResultLauncher`: Permite seleccionar una imagen desde la galería.
   - **Métodos Principales**:
     - `saveNovel()`: Guarda o actualiza una novela en Firebase.
     - `loadNovelDetails(int novelId)`: Carga los detalles de una novela existente para su edición.

#### 6. `ReviewViewModel`
   - **Descripción**: Proporciona la lógica de negocio para la gestión de reseñas.
   - **Métodos Principales**:
     - `getAllReviews()`: Obtiene todas las reseñas desde Firebase.
     - `getNovelById(String novelId)`: Obtiene una novela específica mediante su ID.
     - `addReview(Review review)`: Agrega una nueva reseña a la base de datos.

#### 7. `NovelViewModel`
   - **Descripción**: Administra la lógica de negocio relacionada con la obtención de novelas.
   - **Métodos Principales**:
     - `getAllNovels()`: Retorna todas las novelas desde Firebase.
     - `getFavoriteNovels()`: Obtiene las novelas marcadas como favoritas.
     - `loadNovels()`: Carga las novelas desde la base de datos de Firebase.

#### 8. `FavoritesViewModel`
   - **Descripción**: Administra la obtención de las novelas favoritas.
   - **Métodos Principales**:
     - `loadFavoriteNovels()`: Carga las novelas favoritas desde Firebase.

#### 9. `ReviewAdapter` y `NovelAdapter`
   - **Descripción**: Adaptadores para mostrar las listas de reseñas y novelas en `RecyclerView`.
   - **Componentes**:
     - `ReviewAdapter`: Muestra cada reseña en un `RecyclerView`.
     - `NovelAdapter`: Muestra cada novela y permite interactuar con las opciones de favorito y generar reseñas.

### Modelos de Datos

#### 1. `Novel`
   - **Atributos**:
     - `String id`: Identificador de la novela.
     - `String title`: Título de la novela.
     - `String author`: Autor de la novela.
     - `int year`: Año de publicación.
     - `String synopsis`: Sinopsis de la novela.
     - `String imageUri`: URI de la imagen de la portada.
     - `boolean favorite`: Indica si la novela es favorita.
   - **Descripción**: Representa la información básica de una novela.

#### 2. `Review`
   - **Atributos**:
     - `String id`: Identificador de la reseña.
     - `String novelId`: ID de la novela asociada.
     - `String reviewer`: Nombre del revisor.
     - `String comment`: Comentario de la reseña.
     - `int rating`: Puntuación de la reseña.
     - `String novelName`: Nombre de la novela reseñada.
   - **Descripción**: Representa la información de una reseña para una novela específica.

## Arquitectura
La aplicación sigue el patrón de arquitectura MVVM (Model-View-ViewModel), lo que facilita la separación de la lógica de negocio de la interfaz de usuario, mejorando la mantenibilidad y escalabilidad del código.
Uso de hilos para tareas en segundo plano.
Los datos se almacenan en Firebase Firestore, lo que permite sincronización en tiempo real y almacenamiento en la nube.

Jaime López Díaz.
