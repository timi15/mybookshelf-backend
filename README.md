# 📚 My Bookshelf

The system allows users to manage their personal book collection, monitor their reading progress, and organize books
into different lists such as Loved and To Read. Users can create, update, and delete book reviews.

In addition, the application features a dashboard that provides an aggregated overview of reading activity. The
dashboard displays annual reading statistics, the number of books read and loved, top genres, top-rated books, the most
recently read book, and detailed genre-based insights, helping users better understand and analyze their reading habits.

## 🛠 Technology Stack

- **Backend:** Java – Spring ecosystem
    - Spring Boot
    - Spring Core
    - Spring MVC
    - Spring Validation
    - Spring Cloud GCP
    - Lombok
    - MapStruct
- **Database:** Google Cloud Firestore
- **Firebase integration:** Firebase Admin SDK
- **Data access:** FirestoreReactiveRepository (used in a blocking manner)
- **Testing:** JUnit, Mockito
- **Build & Quality:** Maven, Checkstyle
- **Deployment:** Docker, CI/CD via Northflank
- **Version control:** Git, GitHub

## 📌 Endpoints

**Base path: `/v1/mybookshelf`**

## 📘 Books

| Method | Endpoint | Description                    |
|--------|----------|--------------------------------|
| GET    | `/books` | Retrieves all available books. |

## 📝 Reviews

| Method | Endpoint              | Description                                              |
|--------|-----------------------|----------------------------------------------------------|
| GET    | `/book-reviews`       | Retrieves all book reviews created by the user.          |
| GET    | `/book-reviews/:isbn` | Retrieves the review associated with the given ISBN.     |
| POST   | `/book-reviews`       | Creates and saves a new book review.                     |
| PUT    | `/book-reviews/:isbn` | Updates an existing review identified by the given ISBN. |
| DELETE | `/book-reviews/:isbn` | Deletes the review associated with the given ISBN.       |

## ❤️ Loved List

| Method | Endpoint       | Description                                        |
|--------|----------------|----------------------------------------------------|
| GET    | `/loved`       | Retrieves all books marked as loved by the user.   |
| POST   | `/loved`       | Adds a book to the loved list.                     |
| DELETE | `/loved/:isbn` | Removes a book from the loved list using its ISBN. |

## 📖 To Read List

| Method | Endpoint         | Description                                           |
|--------|------------------|-------------------------------------------------------|
| GET    | `/to-read`       | Retrieves all books added to the user's to-read list. |
| POST   | `/to-read`       | Adds a book to the to-read list.                      |
| DELETE | `/to-read/:isbn` | Removes a book from the to-read list using its ISBN.  |

## 📊 Dashboard

| Method | Endpoint           | Description                                                                                                                                                                                             |
|--------|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET    | `/dashboard/:year` | Returns dashboard data for the given year, including: number of books read, number of books loved, annual reading statistics, top 3 genres, top 5 books, last read book, and detailed genre statistics. |

## 🧪 Running Tests

To run all backend tests locally, execute the following command from the project root:

```bash
mvn test
```

For a clean build with tests and code quality checks:

```bash
mvn clean verify
```

## 🚀 Deployment

The application is deployed on **Northflank** and publicly accessible at:

https://p01--my-bookshelf-service--phgz78clghl.code.run

**Deployment characteristics:**

- Containerized using Docker
- Automatic CI/CD environment
- Stateless REST service

## 🔗 Related Repositories

The frontend source code is available here: [my-bookshelf-frontend](https://github.com/timi15/mybookshelf)

## 📄 License

This project is licensed under the **MIT License**.