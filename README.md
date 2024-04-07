This is a skeleton of Spring Boot application which should be used as a start point to create a working one.
The goal of this task is to create simple web application which allows users to create TODOs. In addition, the
application should expose public REST API in order to allow to manipulate data programmatically.

Below you may find a proposition of the DB model:

![DB model](DBModel.png)

To complete the exercises please implement all missing classes and functonalites in order to be able to store and
retrieve information about tasks and their categories.
Once you are ready, please send it to me (ie link to your git repository) before our interview.

# Todo Management REST API

## Description

This project is a RESTful API built with Spring Boot to manage todo tasks.
It provides endpoints to perform CRUD operations on todo items and categories, allowing users to create, read, update,
and delete tasks and categories.

## Features

- **Todo Tasks Management**:
    - **Create**: Allows users to create new todo tasks.
    - **Read**: Enables users to retrieve all tasks or a specific task by ID.
    - **Update**: Allows users to update existing tasks.
    - **Delete**: Enables users to delete tasks by ID.
- **Task Categories Management**:
    - **Create**: Allows users to create new task categories.
    - **Read**: Enables users to retrieve all categories or a specific category by ID.
    - **Update**: Allows users to update existing categories.
    - **Delete**: Enables users to delete categories by ID.

## Technologies Used

- **Spring Boot**: Framework for building the RESTful API.
- **Spring Data JPA**: For persistence with the database.
- **Spring Web**: For building RESTful web services.
- **H2 Database**: In-memory database for development purposes.
- **Gradle**: Dependency management and build tools.

## Setup Instructions

1. **Clone the Repository**:
    ```
    git clone https://github.com/your-username/todo-api.git
    ```

2. **Navigate to Project Directory**:
    ```
    cd todo-api
    ```

3. **Set up H2 Database**:
    - H2 database is configured to run in-memory by default for development purposes.
    - No further setup is required. Database schema will be created automatically.

4. **Build the Application**:
    ```
    ./gradlew build
    ```

5. **Run the Application**:
    ```
    java -jar build/libs/todo-api.jar
    ```

6. **Access API Endpoints**:
    - Once the application is running, you can access the API endpoints for tasks at `http://localhost:8080/api/tasks`
      and for categories at `http://localhost:8080/api/categories`.

## API Documentation

### Endpoints

#### Task Endpoints

- `GET /api/tasks`: Get all todo tasks.
- `GET /api/tasks/{id}`: Get a todo task by ID.
- `POST /api/tasks`: Create a new todo task.
- `PUT /api/tasks/{id}`: Update an existing todo task.
- `PATCH /api/tasks/{id}`: Partially update an existing todo task.
- `DELETE /api/tasks/{id}`: Delete a todo task by ID.

#### Task Category Endpoints

- `GET /api/categories`: Get all task categories.
- `GET /api/categories/{id}`: Get a task category by ID.
- `POST /api/categories`: Create a new task category.
- `PUT /api/categories/{id}`: Update an existing task category.
- `PATCH /api/categories/{id}`: Partially update an existing task category.
- `DELETE /api/categories/{id}`: Delete a task category by ID.

### Object Details

#### Task

Represents a todo task with the following attributes:

- **id**: The unique identifier of the task.
- **name**: The name or title of the task.
- **description**: The detailed description of the task.
- **deadline**: The deadline or due date for the task.
- **categoryId**: The ID of the category to which the task belongs.

```json
{
  "id": 1,
  "name": "Task 1",
  "description": "Description of Task 1",
  "deadline": "2024-04-10T12:00:00",
  "categoryId": 1
}
```

#### Task Category

Represents a category for organizing todo tasks with the following attributes:

- **id**: The unique identifier of the category.
- **name**: The name or title of the category.
- **description**: The detailed description of the category.
- **tasks**: A list of tasks associated with the category.

```json
{
  "id": 1,
  "name": "Category 1",
  "description": "Description of Category 1",
  "tasks": [
    {
      "id": 1,
      "name": "Task 1",
      "description": "Description of Task 1",
      "deadline": "2024-04-10T12:00:00",
      "categoryId": 1
    },
    {
      "id": 2,
      "name": "Task 2",
      "description": "Description of Task 2",
      "deadline": "2024-04-12T12:00:00",
      "categoryId": 1
    }
  ]
}
```

### Curl Examples

Here are some curl examples to demonstrate how to interact with the API:

- **Create a Task**:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"name":"Task 1", "description":"Description of Task 1", "deadline":"2024-04-10T12:00:00", "categoryId":1}' http://localhost:8080/api/tasks
    ```

- **Retrieve All Tasks**:
    ```bash
    curl http://localhost:8080/api/tasks
    ```

- **Retrieve a Task by ID**:
    ```bash
    curl http://localhost:8080/api/tasks/{task_id}
    ```

- **Update a Task**:
    ```bash
    curl -X PUT -H "Content-Type: application/json" -d '{"name":"Updated Task 1", "description":"Updated Description of Task 1", "deadline":"2024-04-12T12:00:00", "categoryId":1}' http://localhost:8080/api/tasks/{task_id}
    ```

- **Delete a Task**:
    ```bash
    curl -X DELETE http://localhost:8080/api/tasks/{task_id}
    ```

- **Create a Test Category**:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"name":"TestCategory", "description":"Description of Test Category"}' http://localhost:8080/api/categories
    ```

- **Retrieve All Test Categories**:
    ```bash
    curl http://localhost:8080/api/categories
    ```

- **Retrieve a Test Category by ID**:
    ```bash
    curl http://localhost:8080/api/categories/{category_id}
    ```

- **Update a Test Category**:
    ```bash
    curl -X PUT -H "Content-Type: application/json" -d '{"name":"UpdatedTestCategory", "description":"Updated Description of Test Category"}' http://localhost:8080/api/categories/{category_id}
    ```

- **Delete a Test Category**:
    ```bash
    curl -X DELETE http://localhost:8080/api/categories/{category_id}
    ```