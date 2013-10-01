Airport Status
==============
An Android app to help travelers on their way to the airport.

Main screen
-----------
As the app loads, it will detect the user's location and default the view to a local airport.

This behavior will change before the next version, so that the first view is a search interface.

App activities
--------------
Once the airport detail view page is active, there are 3 detail panes with tab navigation between then.

### Status
This pane displays details for the last searched airport. Current weather, delay information provided by FlightStats.com's airport data API, a link to the airport's alerts website, and travel time information are presented. The two buttons displaying the journey time to the airport use the Google Directions API. Clicking those buttons will launch the native Maps app with detailed directions for the requested travel mode.

Airports can also be starred to save them as favorites.

### Saved
This is a convenience list of airports that the user has already favorited.

### Search
This screen contains a simple search bar for a new airport. The search bar auto-suggests completions.  

Screenshots
-----------
![Main screen](/res/screenshots/loading.png "Loading")
![Airport detail](/res/screenshots/status.png "Status")
![Favorites](/res/screenshots/saved.png "Saved Airports")
![Search](/res/screenshots/search.png "Search")
