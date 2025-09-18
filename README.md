# Task Manager App

A modern, professional Task Manager Android application built with **Clean Architecture**, **MVVM pattern**, and **Material Design 3**.

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

- **Domain Layer**: Contains business logic, entities, use cases, and repository interfaces
- **Data Layer**: Implements data sources, repositories, and database operations
- **Presentation Layer**: Contains UI components, ViewModels, and user interactions

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: Clean Architecture + MVVM
- **UI**: XML with Material Design 3
- **Database**: Room (SQLite)
- **Dependency Injection**: Hilt
- **Navigation**: Navigation Component
- **Reactive Programming**: StateFlow, Flow
- **Async Operations**: Coroutines

## 📱 Features

### Core Features
- ✅ **Add new tasks** with title, description, priority, and due date
- ✅ **Edit existing tasks** with full CRUD operations
- ✅ **Delete tasks** with confirmation
- ✅ **Mark tasks as completed/pending** with toggle functionality
- ✅ **Filter tasks** by status (All, Completed, Pending)
- ✅ **Search tasks** by title with real-time filtering

### Runtime UI Features
- ✅ **Toggle view type** between List and Grid views
- ✅ **Dynamic priority color coding**:
  - 🔴 High Priority → Red
  - 🟡 Medium Priority → Yellow/Orange
  - 🟢 Low Priority → Green
- ✅ **Material Design 3** with modern UI components
- ✅ **Responsive design** with proper spacing and typography

## 🎨 UI Design

The app features a clean, modern interface inspired by Material Design 3:

- **Task List Screen**: 
  - Search bar with real-time filtering
  - Filter chips for task status
  - View toggle button (List/Grid)
  - Floating Action Button for adding tasks
  - Task cards with priority indicators

- **Task Detail Screen**:
  - Form-based input with validation
  - Date picker for due dates
  - Priority dropdown selection
  - Completion toggle switch
  - Save/Delete action buttons

## 📁 Project Structure

```
app/src/main/java/com/mahmood/taskmanager/
├── data/
│   ├── local/
│   │   ├── TaskDao.kt
│   │   └── TaskDatabase.kt
│   └── repository/
│       └── TaskRepositoryImpl.kt
├── di/
│   └── DatabaseModule.kt
├── domain/
│   ├── entity/
│   │   └── Task.kt
│   ├── repository/
│   │   └── TaskRepository.kt
│   └── usecase/
│       ├── AddTaskUseCase.kt
│       ├── DeleteTaskUseCase.kt
│       ├── FilterTasksUseCase.kt
│       ├── GetAllTasksUseCase.kt
│       ├── SearchTasksUseCase.kt
│       └── UpdateTaskUseCase.kt
├── presentation/
│   ├── ui/
│   │   ├── tasklist/
│   │   │   ├── TaskAdapter.kt
│   │   │   ├── TaskListFragment.kt
│   │   │   └── TaskListViewModel.kt
│   │   └── taskdetail/
│   │       ├── TaskDetailFragment.kt
│   │       └── TaskDetailViewModel.kt
│   └── MainActivity.kt
└── TaskManagerApplication.kt
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd TaskManager
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the TaskManager folder

3. **Sync Project**
   - Android Studio will automatically sync the project
   - Wait for Gradle sync to complete

4. **Build and Run**
   - Click the "Run" button or press `Shift + F10`
   - Select your device or emulator
   - The app will install and launch

### Building from Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

## 🧪 Testing

The project includes comprehensive testing setup:

- **Unit Tests**: Test business logic and use cases
- **Integration Tests**: Test database operations
- **UI Tests**: Test user interactions and navigation

Run tests:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📊 Database Schema

The app uses Room database with the following schema:

```sql
CREATE TABLE tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT,
    priority TEXT NOT NULL,
    isCompleted INTEGER NOT NULL DEFAULT 0,
    createdAt INTEGER NOT NULL
);
```

## 🎯 Key Features Implementation

### 1. Clean Architecture
- **Domain Layer**: Pure Kotlin business logic
- **Data Layer**: Room database with repository pattern
- **Presentation Layer**: MVVM with ViewModels and Fragments

### 2. MVVM Pattern
- **ViewModels**: Handle UI logic and state management
- **LiveData/StateFlow**: Reactive data binding
- **Fragments**: UI components with lifecycle awareness

### 3. Material Design 3
- **Material Components**: Cards, Chips, FABs, TextFields
- **Color Scheme**: Dynamic theming with priority colors
- **Typography**: Consistent text styles and sizing
- **Spacing**: Proper margins and padding

### 4. Room Database
- **Entity**: Task data model with annotations
- **DAO**: Data access methods with Flow support
- **Database**: Singleton database instance with migrations

### 5. Dependency Injection
- **Hilt**: Modern DI framework
- **Modules**: Database and repository modules
- **Scopes**: Proper lifecycle management

## 🔧 Customization

### Adding New Features
1. Create new use cases in `domain/usecase/`
2. Add repository methods in `domain/repository/`
3. Implement in data layer
4. Update ViewModels and UI

### Modifying UI
1. Update layouts in `res/layout/`
2. Modify colors in `res/values/colors.xml`
3. Update themes in `res/values/themes.xml`

### Database Changes
1. Update entity classes
2. Modify DAO methods
3. Increment database version
4. Add migration scripts


The app includes:
- Modern task list with search and filtering
- Clean task detail form with validation
- Priority-based color coding
- Responsive grid and list views
- Material Design 3 components

## 👨‍💻 Author

**Mahmood** - Android Developer

## 🙏 Acknowledgments

- Material Design 3 guidelines
- Android Architecture Components
- Clean Architecture principles by Robert C. Martin
- Kotlin Coroutines and Flow
- Room database library

---

