# MultiPortListener Java Project

## Overview
The **MultiPortListener** project is a Java-based application designed to listen on multiple ports and process incoming data using dedicated classes for each port. The project demonstrates the use of multi-threading to handle simultaneous connections on different ports efficiently.

## Features
- **Multi-threaded Listening**: Separate threads listen on specified ports, ensuring efficient handling of connections.
- **Port-Specific Data Processing**: Each port uses a unique processor class to handle incoming data.
- **Threaded Client Communication**: Client connections are handled in independent threads for scalability.

## Project Structure
### Classes
1. **`MultiPortListener`**: The main class that initializes and starts threads for each port.
2. **`PortListener`**: A runnable class responsible for listening on a specific port and accepting client connections.
3. **`ClientHandler`**: A runnable class that manages communication with individual clients connected to a port.
4. **`Processor` (Interface)**: Defines the `process(String data)` method for data handling.
5. **`SubscriberProcessor`**: Implements `Processor` for processing data on port 5001.
6. **`DistributionProcessor`**: Implements `Processor` for processing data on port 6001.
7. **`ConfigurationProcessor`**: Implements `Processor` for processing data on port 7001.
8. **`CapacityProcessor`**: Implements `Processor` for processing data on port 8001.

(*) Whole `Processor` implementations performs on `String` types. You are responsible for updating `Strings` with `protobuf` objects 

### Workflow
1. **Initialization**:
   - The `main` method starts a `PortListener` thread for each port (5001 and 5002).
2. **Listening**:
   - Each `PortListener` creates a `ServerSocket` to listen for incoming connections.
3. **Client Handling**:
   - When a client connects, a `ClientHandler` thread is created to manage communication.
4. **Data Processing**:
   - The `ClientHandler` receives data and delegates processing to the corresponding `Processor`.

## How to Run
1. Open as a project and choose `pom.xml` then run the application in your IDE:

2. Send messages to each port and observe how they are processed.

## Example Output
### Port 5001
```sh
Listening on port: 5001
Connection established on port: 5001
Port 5001 received: Hello
Processing data on port 5001: Hello
Data processed: Hello
```

### Port 6001
```sh
Listening on port: 6001
Connection established on port: 6001
Port 5002 received: World
Processing data on port 6001: World
Data processed: World
```

### Port X001...

## Customization
- **Add New Ports**:
  - You can create a new `Processor` class (e.g., `XYZProcessor`).
  - Update the `main` method to include the new port and processor.
- **Modify Data Processing**:
  - Implement specific logic in the `process` method of each `Processor` class.

## Dependencies
This project uses only core Java libraries and does not require external dependencies.

