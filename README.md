# HoldingsPortfolioApp

**HoldingsPortfolioApp** is a modern Android application built using Kotlin and Jetpack Compose that displays a user's stock holdings portfolio. The app supports both online and offline modes with Room database integration and periodic data syncing using WorkManager.

---

## Features

- Live stock holdings with LTP, quantity, average price, and P&L
- Offline-first architecture (RoomDB + WorkManager)
- Periodic data sync (every 15 mins)
- Clean MVVM + Repository pattern
- Jetpack Compose UI
- Custom P&L bar with expandable details
- Dark/light theme support
- Tab layout for Holdings and future sections
- Network state check and fallback


---

## Offline-First Architecture

- **Room DB** for caching API responses.
- **WorkManager** scheduled every 15 mins to sync fresh data.
- **NetworkUtils** checks connectivity before triggering sync.
- ViewModel observes **cached DB** using `Flow` and updates UI.

---

## Tech Stack

| Layer          | Library / Tool                        |
|----------------|----------------------------------------|
| UI             | Jetpack Compose                        |
| Architecture   | MVVM, Hilt, Repository Pattern         |
| DB             | Room Database                          |
| Network        | Retrofit + Gson                        |
| Background     | WorkManager                            |
| Dependency DI  | Dagger-Hilt                            |
| State Mgmt     | Kotlin Flows, State                    |
| Testing        | JUnit, Mockito, Room Test (planned)    |

---

## Setup Instructions

### Prerequisites

- Android Studio Flamingo+
- Kotlin 1.8+
- Compile SDK: 34+

### Run the App
