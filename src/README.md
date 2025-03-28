# Digital Library Book Management System

## Project Overview
A Spring Boot-based application for managing a digital library's book catalog, allowing librarians to add, update, search, and remove books.

## Features
- Add new books to the catalog
- View all books
- Search books by ID or Title
- Update book details
- Delete book records
- Validation of book inputs

## Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8+

## Setup and Installation
1. Clone the repository
```bash
git clone https://github.com/SumedhNaidu/LibraryManagmentSystemSpringBoot.git
cd digital-library
```

2. Login to MySQL and create the database:
```sql
mysql -u root -p
CREATE DATABASE digital_library_db;
CREATE USER 'librarian'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON digital_library_db.* TO 'librarian'@'localhost';
FLUSH PRIVILEGES;
```

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

## Security
By default, the application uses basic authentication:

- **Username:** `admin`
- **Password:** `admin123`

You can change these credentials in `application.properties`:

```properties
spring.security.user.name=admin
spring.security.user.password=admin123
```

🔹 **Note:** Do **not** use these credentials in production. Instead, configure a secure authentication method.

## API Endpoints
- `POST /api/books`: Add a new book
- `GET /api/books`: Retrieve all books
- `GET /api/books/{bookId}`: Get a book by ID
- `GET /api/books/search?title=`: Search books by title
- `PUT /api/books/{bookId}`: Update a book
- `DELETE /api/books/{bookId}`: Delete a book

## Testing
Run unit tests:
```bash
mvn test
```

## Challenges and Improvements
- Current implementation uses in-memory storage
- Future improvements could include:
  - Persistent database integration
  - Advanced search capabilities
  - Authentication and authorization
  - Comprehensive error handling

## Contributing
1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

