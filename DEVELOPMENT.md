# Development Guide - Membership App

This guide provides information for developers working on the Membership App project.

## Project Architecture

### Overview
The application follows a layered architecture pattern:

```
┌─────────────────────────────────────┐
│   Presentation Layer (JavaFX UI)    │
│  LoginForm | RegistrationForm |     │
│  Dashboard                          │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│   Business Logic / Application       │
│   State Management                  │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│   Data Access Layer                 │
│   DatabaseManager                   │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│   Apache Derby Database             │
└─────────────────────────────────────┘
```

### Component Description

#### 1. MembershipApp.java (Main Application)
- Entry point for the JavaFX application
- Extends `javafx.application.Application`
- Creates primary stage and initializes LoginForm

```java
public class MembershipApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        new LoginForm(primaryStage);
    }
}
```

#### 2. LoginForm.java (Presentation Layer)
Responsibilities:
- Display login UI with email and phone fields
- Handle login button action
- Navigate to RegistrationForm or Dashboard
- Validate user input

Key Methods:
- Constructor: Sets up the UI layout
- LoginButton.setOnAction(): Authenticates user
- RegisterLink.setOnAction(): Switches to registration

#### 3. RegistrationForm.java (Presentation Layer)
Responsibilities:
- Display comprehensive registration form
- Collect member information
- Perform form validation
- Submit data to database

Fields Collected:
- `first_name`, `last_name`
- `email`, `phone`
- `address1`, `address2`
- `country`, `gender`
- `membership_type`, `sports`
- `reason`, `terms_accepted`

#### 4. Dashboard.java (Presentation Layer)
Responsibilities:
- Display post-login user interface
- Provide access to member features
- Handle user action triggers

Features:
- Send Message
- Make Payment
- Edit Profile

#### 5. DatabaseManager.java (Data Access Layer)
Responsibilities:
- Establish database connections
- Execute SQL queries
- Handle connection pooling
- Error handling and logging

Key Methods:
```java
// Get database connection
public static Connection getConnection()

// Validate user credentials during login
public static boolean validateLogin(String email, String phone)

// Insert new member record
public static void insertMember(String fn, String ln, String email, ...)
```

## Development Setup

### IDE Setup (NetBeans)
1. Open Project Properties (Alt + Enter)
2. Configure Build
3. Add JavaFX libraries under Libraries tab
4. Set Apache Derby JDBC driver

### Database Connection Parameters
```
Driver: org.apache.derby.jdbc.ClientDriver
URL: jdbc:derby://localhost:1527/MembershipDB
User: app
Password: app
```

## Code Structure Guidelines

### Naming Conventions
- **Packages**: `membershipapp` (lowercase)
- **Classes**: `LoginForm` (PascalCase)
- **Methods**: `validateLogin()` (camelCase)
- **Constants**: `WINDOW_WIDTH` (UPPER_CASE)
- **Variables**: `emailField` (camelCase)

### Directory Structure
```
src/membershipapp/
├── *.java files (one class per file)
```

### Import Organization
```java
// Standard Java
import java.sql.*;

// JavaFX
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
```

## Database Schema

### Members Table
```sql
CREATE TABLE members (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address1 VARCHAR(100),
    address2 VARCHAR(100),
    country VARCHAR(50),
    gender VARCHAR(20),
    membership_type VARCHAR(50),
    sports VARCHAR(100),
    reason VARCHAR(255),
    terms_accepted BOOLEAN
);
```

### Common SQL Operations

#### Insert New Member
```sql
INSERT INTO members (first_name, last_name, email, phone, address1, address2, country, gender, membership_type, sports, reason, terms_accepted)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
```

#### Find Member
```sql
SELECT * FROM members WHERE email = ? AND phone = ?
```

#### Get All Members
```sql
SELECT * FROM members ORDER BY id DESC
```

#### Update Member
```sql
UPDATE members SET first_name = ?, last_name = ? WHERE id = ?
```

## Common Development Tasks

### Add a New Feature

1. **Identify the Layer**:
   - UI changes → Modify corresponding Form class
   - Database changes → Modify DatabaseManager
   - Business logic → Create new utility class

2. **Example: Add Edit Profile Feature**
   ```java
   // 1. Create EditProfileForm.java
   public class EditProfileForm {
       public EditProfileForm(Stage stage, String userEmail) {
           // Create UI form
       }
   }
   
   // 2. Add database method in DatabaseManager.java
   public static void updateMember(int id, String firstName, ...) {
       // SQL UPDATE statement
   }
   
   // 3. Link from Dashboard
   editProfileBtn.setOnAction(e -> new EditProfileForm(stage, userEmail));
   ```

### Add Database Field

1. Alter the table:
   ```sql
   ALTER TABLE members ADD COLUMN new_field VARCHAR(100)
   ```

2. Update DatabaseManager.insertMember():
   ```java
   ps.setString(13, newFieldValue);
   ```

3. Update RegistrationForm.java to collect the field

### Handle Database Errors

```java
try (Connection conn = getConnection()) {
    if (conn == null) {
        System.err.println("Database connection failed");
        return;
    }
    // Execute queries
} catch (SQLException e) {
    e.printStackTrace();
    // Show error dialog
    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
}
```

## Build and Deployment

### Building
```bash
# Clean build
ant clean build

# Create executable JAR
ant jar

# Run from JAR
java -jar dist/MembershipApp.jar
```

### Build Configuration (build.xml)
- Compiles source files
- Creates JAR archive
- Bundles JavaFX runtime (if configured)
- Generates executable

## Testing

### Manual Testing Checklist
- [ ] Registration form accepts valid data
- [ ] Registration rejects invalid data
- [ ] Login works with registered credentials
- [ ] Login fails with invalid credentials
- [ ] Dashboard displays after successful login
- [ ] Database contains registered users
- [ ] Error messages display clearly

### Database Testing
```sql
-- Connect to database
CONNECT 'jdbc:derby://localhost:1527/MembershipDB' USER 'app' PASSWORD 'app';

-- View registered members
SELECT * FROM members;

-- Count members
SELECT COUNT(*) FROM members;

-- Check specific member
SELECT * FROM members WHERE email = 'test@example.com';
```

## Debugging

### Enable Debug Output
- Check System.out.println() messages in IDE console
- Review stack traces in error output
- Use Alert dialogs to display debug information

### Common Issues

**Issue: NullPointerException on login**
```java
// Cause: getConnection() returned null
// Solution: Check database is running and credentials are correct
if (conn == null) {
    System.err.println("Connection is null");
}
```

**Issue: SQLException on INSERT**
```java
// Cause: Column mismatch or data type error
// Solution: Verify table schema and PreparedStatement parameters
```

**Issue: JavaFX window not displaying**
```java
// Cause: Missing stage.show() or scene not set
// Solution: Ensure stage.setScene() and stage.show() are called
```

## Performance Considerations

1. **Connection Pooling**: Current implementation creates new connections per operation
   - Future: Implement connection pool for better performance

2. **Query Optimization**: Use prepared statements (already implemented)
   - Prevents SQL injection
   - Better performance for repeated queries

3. **UI Responsiveness**: Long operations should run on separate threads
   - Current: Simple operations only
   - Future: Use Task/Service for database heavy operations

## Security Considerations

1. **SQL Injection**: Use PreparedStatements ✓
2. **Password Storage**: Currently using phone number, implement proper hashing for passwords
3. **Connection Security**: Add SSL/TLS for database connections
4. **Input Validation**: Add more robust validation in forms
5. **Session Management**: Implement logout and session timeout

## Future Enhancements

### Planned Features
1. Admin Dashboard for member management
2. Payment gateway integration
3. Email notifications
4. SMS notifications
5. Member reporting and analytics
6. Membership tier system
7. Automated renewal system
8. Multi-user support with roles

### Architectural Improvements
1. Implement MVC pattern more explicitly
2. Add Service layer for business logic
3. Implement proper exception handling
4. Add logging framework (Log4j)
5. Add unit tests
6. Implement dependency injection

## Useful Resources

- [JavaFX Documentation](https://openjfx.io/)
- [Apache Derby Documentation](https://db.apache.org/derby/docs/)
- [JDBC API](https://docs.oracle.com/javase/tutorial/jdbc/)
- [NetBeans IDE](https://netbeans.apache.org/)

## Contact & Support

For development-related questions or to contribute:
1. Review existing source code
2. Follow the coding guidelines in this document
3. Test thoroughly before committing changes
4. Update documentation for significant changes

---

**Version**: 1.0  
**Last Updated**: May 2026
