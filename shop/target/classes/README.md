# Video Store Project

## Overview
This project is a simple video store management system. It allows users to add, remove, and check out videos, and manage inventory. The application uses a Graphical User Interface (GUI) for user interaction.

## Features
- Add new videos to the store
- Remove videos from the store
- Check out videos
- Check in videos
- Query for videos
- Display the inventory of videos in the store
- Undo and redo previous actions

## How to Run
To run the application, execute the `VideoStoreGUI.java` file. This will open a new window with various buttons to interact with the application.

You could also run the `VideoStoreTextUI.java` file for a text based UI.

## Code Structure
The main classes and interfaces in the project are:

- `VideoStoreGUI`: This is the main class that sets up the GUI and handles user interaction.
- `VideoStore`: This class handles the logic for managing the video store.
- `Video`: This interface represents a video in the store.
- `Record`: This interface represents a record of a video in the store, including the video and the number of copies in the store.
- `Inventory`: This interface manages the inventory of videos in the store.
- `Data`: This class provides static methods for creating `Video` and `Record` objects.
- `Command`: This interface represents a command that can be executed on the video store. It has methods for executing and undoing the command. It is extended by `RerunnableCommand` and `UndoableCommand`.
- `CommandHistory`: This class manages the history of commands executed on the video store.
- `Composite`: This class represents a composite command that can execute multiple commands at once.

## Future Improvements
- Implement a search feature to find videos by title, director, or year.
- Add a feature to manage customer accounts, including tracking checked out videos.
- Improve the GUI to provide more information about the current state of the store, such as the total number of videos and the most popular videos.

## Contributors
- Murat Altindag