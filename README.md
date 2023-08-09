
# MovieTek

Welcome to the Android project developed with Kotlin and Android Jetpack.
## Features

The app has a Login activity which will perform a validation when trying to log in, in turn a customized button was implemented to make the login more interactive, showing the status of the request. The implemented logic simulates a server since it takes a few seconds to make the request and then just enter if the credentials are correct, otherwise the user will be notified that the credentials entered are wrong.

Already in the main activity a list of movie elements will be displayed, this list and its logic is implemented with Paging, a library that is part of Android Jetpack. The main benefits for the application are being able to handle paged resources efficiently, using caching, compatibility with coroutines and streams, among others. This simplifies data source management by allowing you to use network data when possible and using local data when not. This, together with its integration with room, further simplify this data management, thus achieving compliance with the single source of truth architecture.

Finally, a fragment of details is also presented to be able to visualize more details of the selected movie. The transitions between the screens have animations to make navigation more interactive.
# Architecture

The project architecture is based on MVVM (Model-View-ViewModel), with the goal of keeping logic away from presentation layers and ensuring a clear separation of responsibilities. It is prioritized that most of the presentation related tasks are done in the repositories, and other more specific tasks are handled in the use cases. This is achieved by following SOLID principles, where each main class focuses on a specific logic of a certain component.

The application uses coroutines to perform asynchronous tasks efficiently, avoiding blocking the main thread and keeping the user interface fluid.

The project adheres to SOLID principles, ensuring that higher-level implementations do not depend on lower-level ones, thus improving code scalability and maintainability.

For UI and reactivity management, the use of Flows and StateFlow is prioritized, rather than LiveData, leveraging the capabilities of coroutines to deliver a fluid and reactive user experience.
## Libraries

- Splash Screen: Esta libreria recomendada por google simplifica la implementacion de la vista que se muestra al abrir la aplicacion
- Kotlin-Serialization-Json: Esta libreria facilita la serializacion de objetos, ademas de estar optimizada simplifica el codigo
- Proto DataStore: Con esta libreria podemos almacenar datos ligeros al igual que preferences DataStore, con el beneficio de la seguridad de tipo
- Paging: Libreria que permite manejar listas con paginacion, junto a su integracion con Room
- Dagger-Hilt: Util para la simplificacion de inyeccion de dependencias
- Coil: libreria para mostrar imagenes desde url
- Timber: Utilidad para simplificacion de logs