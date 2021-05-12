# Technical descritption:
[MVVM pattern](https://developer.android.com/jetpack/guide#overview) used for app architecture. Following the most important principle of SoC (separation of concerns)
Coroutines in charge for data communication between architetual parts and immplemented in following way:
 - Data communication between UI and ViewModel with LiveData in main thert. 
 - Communication between ViewModel and Repository with Flow in IO thred and collected with LiveData

# Features:
*	Loads data from WEB
  - REST API
*	Data cache in local disc 
  - ROOM DAO and SQL, Post Detail fetched from relational
*	List of posts in recyclceview
*	Movie details
*	Animation between fragments transition

# Screens
![screen]()
![screen]()

# Libraries:
*	Kotlin Flow
*	Kotlin LiveData
*	Kotlin Coroutines
*	Hilt
*	Room SQL
*	Retrofit
*	Glide
*	DiffUtil
*	Navigation component
