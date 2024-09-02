# Social-Media-Platform-backend

This project is a social media platform built using Spring Boot, Hibernate, and MySQL. It includes features such as user management, status updates, reactions, comments, and friendship management. Security is implemented using JWT (JSON Web Tokens).

## Table of Contents

1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Project Structure](#project-structure)
4. [Getting Started](#getting-started)
5. [API Documentation](#api-documentation)
6. [Contributing](#contributing)
7. [License](#license)

## Features

- **User Management:** Create, update, and retrieve user information.
- **Status Updates:** Post and retrieve status updates.
- **Reactions:** Add and manage reactions (likes, etc.) to status updates.
- **Comments:** Post and manage comments on status updates.
- **Friendship Management:** Send, accept, and manage friend requests.
- **Follow/Unfollow:** Follow and unfollow users.
- **Security:** Authentication and authorization using JWT.

## Technologies Used

- **Backend Framework:** Spring Boot
- **Persistence:** Hibernate ORM
- **Database:** MySQL
- **Security:** JWT (JSON Web Tokens)
- **Utilities:** Various utility classes for handling file uploads and data mappings

## Project Structure

- **Entity Classes:** 
  - `User`, `Upload`, `StatusUpdate`, `ReactionType`, `Reaction`, `Comment`

- **DTO Classes:** 
  - `UserDTO`, `StatusUpdateDTO`, `SimpleUserDTO`, `ReactionDTO`, `FriendDTO`, `FollowingDTO`, `FollowerDTO`, `AuthenticationResponse`

- **Services:** 
  - `UserService`, `UploadingService`, `StatusUpdateService`, `ReactionService`, `FriendService`, `FriendRequestsService`, `FollowService`, `CommentService`

- **Security Components:**
  - `JwtUtil`, `JwtRequestFilter`, `SecurityConfig`

- **Utility Classes:**
  - `UploadUtils`, `UserMapper`

- **Controllers:** 
  - `UserController`, `UploadController`, `StatusController`, `ReactionController`, `FriendRequestController`, `FriendController`, `FollowController`, `CommentController`

## Getting Started

### Prerequisites

- Java 11 or higher
- MySQL Database
- Maven

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/your-username/social-media-platform.git
   cd social-media-platform

2. **Configure Database:**
   Update the application.properties file with your MySQL database configuration:

   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password

3. **Build the Project:**

   ```bash
   mvn clean install

4. **Run the Application:**

   ```bash
   mvn spring-boot:run

### API Documentation
For detailed API documentation, please refer to the Swagger UI or check the API documentation provided in the project.

## Contributing
We welcome contributions! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (git checkout -b feature/your-feature).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature/your-feature).
5. Create a new Pull Request.
