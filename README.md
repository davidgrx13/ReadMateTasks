==========================================
 ReadmateTasks - Aplicación de Tareas (basada en ReadMate)
==========================================

ReadmateTasks es una aplicación para la gestión de tareas, desarrollada en Kotlin con Jetpack Compose,
siguiendo el patrón MVVM y utilizando Firebase para autenticación y almacenamiento de datos.

------------------------------------------
 FUNCIONALIDADES
------------------------------------------
- Registro e inicio de sesión con Firebase Authentication  
- Creación, edición y eliminación de tareas  
- Marcar tareas como completadas  
- Adjuntar imágenes a las tareas  
- Persistencia de datos en Firestore  
- Diseño con Jetpack Compose  
- Animaciones con Lottie  

------------------------------------------
 TECNOLOGÍAS UTILIZADAS
------------------------------------------
- Kotlin
- Jetpack Compose
- Firebase Authentication
- Firestore
- Model-View-ViewModel
- Lottie - Animaciones avanzadas  
- Coil - Carga de imágenes optimizada  

------------------------------------------
ESTRUCTURA DEL PROYECTO
------------------------------------------
📦 ReadmateTasks  
 ┣ 📂 data                 -> Modelos y repositorios de datos  
 ┣ 📂 ui                     -> Pantallas, navegación y temas  
 ┣ 📂 viewmodel  	 -> Lógica de la UI con MVVM  
 ┣ 📂 utils                 -> Utilidades generales  
 ┣ 📜 README.txt   -> Documentación

------------------------------------------
 CONFIGURACIÓN Y EJECUCIÓN
------------------------------------------
1️⃣ Clonar el repositorio  
   git clone https://github.com/tuusuario/ReadmateTasks.git  

2️⃣ Configurar Firebase  
   - Crear un nuevo proyecto en Firebase Console  
   - Agregar una app Android y descargar `google-services.json` en `app/`  
   - Habilitar Firebase Authentication y Firestore Database  

3️⃣ Abrir el proyecto en Android Studio  
   - Usar Android Studio Flamingo o superior  
   - Sincronizar dependencias con Gradle  

4️⃣ Ejecutar la aplicación  
   - Seleccionar un emulador o dispositivo físico  
   - Presionar "Run" ▶️  

------------------------------------------
 AUTOR
------------------------------------------
Nombre: David García  
GitHub: davidgrx13
