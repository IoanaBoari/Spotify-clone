# Boari Ioana-Ruxandra 322CD

# Proiect GlobalWaves - Spotify Clone

<div align="center"><img src="https://tenor.com/view/listening-to-music-spongebob-gif-8009182.gif" width="300px"></div>

#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa1](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa1)

## Skel Structure

* src/
  * checker/ -> checker files

  * fileio/ -> contains classes used to read data from the json files
    * ActionInput -> reads and represents user action data from JSON files
    * EpisodeInput -> manages podcast episode data from JSON files
    * Filters -> handles filter data from JSON files for refining searches
    * LibraryInput -> manages input related to the program's library from JSON files
    * PodcastInput -> reads and represents podcast data from JSON files
    * SongInput -> handles input data related to songs from JSON files
    * UserInput ->reads and represents user data from JSON files

  * globalwaves/ -> contains program implementation

    * actionoutput/ -> package for generating action's output
      * BackwardOutput ->  handles the output for the "Backward" action
      * ForwardOutput -> handles the output for the "Forward" action
      * LoadOutput -> handles the output for the "Load" action
      * NextOutput ->  handles the output for the "Next" action
      * PlayPauseOutput ->  handles the output for the "Play/Pause" action
      * PrevOutput ->  handles the output for the "Prev" action
      * RepeatOutput -> handles the output for the "Repeat" action
      * SearchOutput ->  handles the output for the "Search" action
      * SelectOutput -> handles the output for the "Select" action
      * ShuffleOutput -> handles the output for the "Shuffle" action
      * StatusOutput -> handles the output for the "Status" action

    * admin/ -> this package contains the implementation for admin actions regarding the database

      * delete/ -> this package contains the implementation for delete and remove cases
        * DeleteAlbum -> deletes an album from the database if it is possible
        * DeleteNormalArtistHost -> deletes an user from the database if it is possible
        * DeletePodcast -> deletes a podcast from the database if it is possible
        * DeleteUser -> deletes an user from the database if it is possible
      * end/ -> this package containes the implementation for ending the program
        * EndProgram -> ends the program, showing the revenues for all listened artists
        * EndProgramResults -> calculates the song and merchendise revenues and generates the most profitable song
      * AddUser -> adds an user to the database if it is possible
      * CheckOffline -> checks if an user is online or offline
      * UpdateStats -> updates the stats for current user or all the stats
      
    * commands/ -> this package contains the final implementations for each command, including the verification of lists used to manage the program's logic
      * Command -> interface used for Command Pattern 
      * DoAddRemoveInPlaylist -> handles the "Add/Remove in Playlist" action
      * DoBackward -> handles the "Backward" action
      * DoCreatePlaylist -> handles the "Create Playlist" action
      * DoFollow -> handles the "Follow" action
      * DoForward -> handles the "Forward" action
      * DoLike ->  handles the "Like" action
      * DoLoad ->  handles the "Load" action
      * DoNext -> handles the "Next" action
      * DoPlayPause -> handles the "Play/Pause" action
      * DoPrev ->  handles the "Previous" action
      * DoRepeat -> handles the "Repeat" action
      * DoSearch ->  handles the "Search" action
      * DoSelect -> handles the "Select" action
      * DoShowPlaylists -> handles the "Show Playlists" action
      * DoShowPreferredSongs -> handles the "Show Preferred Songs" action
      * DoShuffle -> handles the "Shuffle" action
      * DoStatus -> handles the "Status" action
      * DoSwitchVisibility ->  handles the "Switch Visibility" action
      * Invoker -> invoker for implementing Command Pattern

    * generalstats/ -> this package contains general statistics for songs and playlists
      * GetAllUsers -> displays all the users in the database
      * GetOnlineUsers -> displays the normal users that are online
      * GetTop5Albums -> displays the top 5 albums from the database with the most likes
      * GetTop5Artists -> displays the top 5 artists from the database with the most likes
      * GetTop5Playlists ->  displays the top 5 playlists with the most followers
      * GetTop5Songs ->  displays the top 5 songs from the library with the most likes

    * monetization/ -> this package contains the monetization mechanism for globalwaves app
      * AdBreak -> an ad break is added after the current song and the monetization for artists is calculated
      * BuyPremium -> a normal user becomes a premium account
      * CancelPremium -> a normal user cancel the premium account

    * notification/ -> this package contains the notification mechanism for globalwaves app
      * GetNotifications -> displays the notifications for current user
      * Notification -> structure used to store notifications
      * Subscribe -> a nomral user can subscribe to an artist or host to get notifications 
      * UserSubscriptions -> structure used to store all the subscriptions for users

    * pagination/ -> this package contains implementations for commands that interact with pages
      * ChangePage -> changes the page for the current user with the page specified in the input
      * NextPage -> changes the page for the current user with the next page in the page hierarchy
      * PrevoiusPage -> changes the page for the current user with the prevoius page in the page hierarchy
      * PrintCurrentPage -> prints the currentPage information for the current user following a certain format

    * player/ -> this package contains implementations for commands that interact with what is loaded in the music player
      * Backward -> rewinds the podcast episode by 90 seconds
      * Forward -> fast-forwards the podcast episode by 90 seconds
      * Like ->  likes the current loaded song
      * LikedSongs -> class that stores a user and a list of songs they have liked
      * Load -> performs the loading of the selected source
      * LoadResults ->  this class contains information about what is currently loaded and statistics for each user
      * Next -> moves to the next song/episode of the podcast based on the state of the repeat
      * PlayPause ->  changes the state from play to pause or vice versa for the loaded source
      * PodcastLoaded ->  class that retains information about where a user left off in a specific podcast
      * Prev -> moves to the previous song/episode of the podcast or to the begining of the current loaded source based on the state of the repeat
      * Repeat -> changes the repeat state of the source in the music player
      * Shuffle ->  class that changes the shuffle state. If shuffle is activated, the order of songs in the playlist is changed.
      * Stats ->  displays statistics for the loaded source

    * playlist/ -> this package contains implementations for commands that interact exclusively with playlists
      * AddRemoveInPlaylist -> add or remove a song from a specified playlist owned by the current user
      * CreatePlaylist -> create a playlist with a specified name, if it does not allready exists
      * Follow -> follow or unfollow a public playlist that is not owned by the current user
      * FollowedPlaylists -> class that stores a user and a list of playlists they have followed
      * Playlist -> contains the structure of a playlist
      * PlaylistsOwner -> contains a user and their playlists
      * ShowPlaylists -> shows all the songs from all the playlists owned by the current user
      * ShowResult -> class used to display the playlists
      * SortPlaylists -> class used to sort the playlists 
      * SwitchVisibility -> class used to switch the visibility for a playlist owned by the current user

    * recommendation/ -> this package contains the recommendation mechanism for globalwaves app
      * FansPlaylist -> generates fansPlaylist
      * LoadRecommendations -> loads the last recommendation made for current user if it exists
      * RandomPlaylist -> generates randomPlaylist
      * RandomSong -> generates randomSong
      * Recommendations -> structure used to store recommendations
      * UpdateRecommendations -> updates the recommendations for current user
      * UserPages -> structure used to store the page hierarchy

    * searchbar/ -> this package contains implementations for search and select functionalities
      * SearchAlbum -> search album by filters
      * SearchArtist -> search artist by filters
      * SearchHost -> search host by filters
      * SearchPlaylist -> search playlist by filters
      * SearchPodcast -> search podcast by filters
      * SearchResults -> class used to save the search results
      * SearchSong -> search song by filters
      * Select -> select a source from the search results
      * SelectResults -> class used to save the select result

    * user/ -> this package contains commands that can be done just for specific users
      * artist/ -> this package contains actions that can be done just for an artist user
        * merch/ ->this package contains actions that can be done with merch
          * AddMerch -> Command for artist users to add merch
          * BuyMerch -> Command for user to buy merch from an artist
          * Merch -> contains the structure of merch
          * OwnedMerch -> structure used to store merch for normal users
          * SeeMerch -> Command for user to see the merch he ownes
 
        * AddAlbum -> Command for artist users to add an album
        * AddEvent -> Command for artist users to add an event
        * Album -> contains the structure of an album
        * Artist -> contains the structure of an artist
        * ArtistWrapped -> implements wrapped for artist
        * Event -> contains the structure of an event
        * RemoveAlbum ->Command for artist users to delete an album 
        * RemoveEvent -> Command for artist users to delete an event

      * host/ -> this package contains actions that can be done just for a host user
        * AddAnnouncement -> Command for host users to add an announcement
        * AddPodcast -> Command for host users to add a podcast
        * Announcement -> contains the structure of an announcement
        * Host -> contains the structure of a host
        * HostWrapped -> implements wrapped for host
        * RemoveAnnouncement -> Command for host users to delete an announcement
        * RemovePodcast -> Command for host users to delete a podcast

      * normalUser/ -> this package contains actions that can be done just for a normal user
        * NormalWrapped -> implements wrapped for normal user
        * SwitchConnectionStatus -> class used for toggling the online/offline
        status of a normal user in the music library.
        It checks for the user's existence and updates the status accordingly.
    * userstats/ -> this package contains statistics for each user
      * Listener -> structure used to define listener object
      * ShowAlbumResult -> class used to display the albums
      * ShowAlbums -> shows all the songs from all the albums owned by the current user
      * ShowPodcastResult -> class used to display the podcasts
      * ShowPodcasts -> shows all the episodes from all the podcasts owned by the current user
      * ShowPreferredSongs -> generates output to display the preferred songs of a user
      * Wrapped -> calculates statistics for normal user, artist, host
      * WrappedFactory -> utilitary class used to implement Factory Pattern

    * Database -> implemented as a Singleton, the database contains all users and audio files
    * Menu -> the program's menu
  * main/
      * Main -> the Main class runs the checker on your implementation. Add the entry point to your implementation in it. Run Main to test your implementation from the IDE or from command line.
      * Test -> run the main method from Test class with the name of the input file from the command line and the result will be written
        to the out.txt file. Thus, you can compare this result with ref.
* input/ -> contains the tests and library in JSON format
* ref/ -> contains all reference output for the tests in JSON format

## Design Patterns
  I used Singleton Pattern for database 
because i want to have just one database to work with-> src/globalwaves/Database.java
  I used Command Pattern for commands 
because my program implements different commands and this pattern helps me choose the correct command-> src/globalwaves/commands/Command
and src/globalwaves/commands/Invoker
  I used Builder Pattern for Listener structure constructor -> src/globalwaves/userstats/Listener
  I used Factory Pattern for choosing the correct Wrapped type -> src/globalwaves/userstats/WrappedFactory


<div align="center"><img src="https://tenor.com/view/homework-time-gif-24854817.gif" width="500px"></div>
