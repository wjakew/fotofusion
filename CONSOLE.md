## Console UI Guide
## Available Commands
### Commands for setting up the program
- `exit`<br>
    Exits the console and the program. Program saves the current log in the working directory.
- `clear`<br>
    Clears the console.
- `reset`<br>
    Clears the current source, destination, and photo collection.
- `showhistory`<br>
    Shows the history of the commands typed in the console.
- `help`<br>
    Shows the available commands in the console.
- `gui`<br>
    Starts GUI.
### Commands for setting the engine
- `setsource <source>`<br>
    Sets the source of the engine. The source can be a file or a directory.
- `setdestination <destination>`<br>
    Sets the destination of the engine. The destination can be a file or a directory.
- `runcollection`<br>
    Runs the photo grab from the current source.
- `runexifloader`<br>
    Runs the exif loader for the current photo collection.
- `listbranches`<br>
    Lists the branches (common tags) of the current photo collection.
- `addbranch <branch>`<br>
    Adds a branch (common tag) to the current photo collection.
- `removebranch <branch>`<br>
    Removes a branch (common tag) from the current photo collection.
- `runcopy`<br>
    Runs the copy engine for the current photo collection.
- `showstats`<br>
    Shows the statistics of the current photo collection.
- `save <filename>`<br>
    Saves the current engine configuration in the working directory.
## Example Usage
<br>1.`setsource /home/user/photos`<br>
    Sets the source of the engine to `/home/user/photos`.
<br>2.`setdestination /home/user/destination`<br>
    Sets the destination of the engine to `/home/user/destination`.
<br>3.`runcollection`<br>
    Runs the photo grab from the current source.
<br>4.`runexifloader`<br>
    Runs the exif loader for the current photo collection.
<br>5.`listbranches`<br>
    Lists the branches (common tags) of the current photo collection.
<br>6.`addbranch vacation`<br>
    Adds a branch (common tag) to the current photo collection.
<br>7.`removebranch vacation`<br>
    Removes a branch (common tag) from the current photo collection.
<br>8.`runcopy`<br>
    Runs the copy engine for the current photo collection.