# FotoFusion

FotoFusion is a Java-based application designed to manage and manipulate photo collections. It provides functionalities such as setting source and destination paths, generating collections, loading photo EXIF data, adding and removing tag branches, and copying photos.

## Features

- **Set Source and Destination Paths**: The application allows you to set the source and destination paths for your photo collections.

- **Generate Collections**: The application can generate a collection of photos from the source path.

- **Load Photo EXIF Data**: The application can load EXIF data from the photos in the collection.

- **Add and Remove Tag Branches**: The application allows you to add and remove tag branches to/from all photos in the collection.

- **Copy Photos**: The application can copy photos from the source path to the destination path, creating necessary directories if they do not exist.

## Recent Updates

- Added functionality to create necessary directories if they do not exist when copying photos.

- Improved error handling and logging for file and directory operations.

- Enhanced the `FolderTreeGenerator` class to check if a directory exists before attempting to create it.

## Getting Started

To run the FotoFusion Engine, you need to have Java installed on your machine. You can then clone this repository and run the `FotoFusionTest` class to start the application.

## Future Work

- Implement a user interface for easier interaction with the application.

- Add more advanced photo manipulation features.

- Improve the efficiency of the collection generation process.

## Contributing

Contributions are welcome. Please fork this repository and create a pull request if you have something you want to add or improve.

## License

This project is licensed under the terms of the MIT license.