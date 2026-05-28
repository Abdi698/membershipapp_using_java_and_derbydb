# Membership App

A JavaFX-based desktop application for managing membership registrations, logins, and member dashboards with integrated database support.

## Overview

Membership App is a Java-based desktop application built with JavaFX that provides a complete membership management system. Users can register, log in, and access a personalized dashboard where they can manage their membership details, make payments, and communicate with administrators.

## Features

- **User Authentication**: Secure login with email and phone verification
- **Member Registration**: Comprehensive registration form with personal information collection
- **Database Integration**: Apache Derby database for persistent member data storage
- **User Dashboard**: Personalized dashboard with member options including:
  - Send Messages
  - Make Payments
  - Edit Profile
- **Form Validation**: Input validation for registration fields
- **JavaFX GUI**: Modern graphical user interface with intuitive navigation

## Project Structure

```
MembershipApp/
├── src/membershipapp/
│   ├── MembershipApp.java         # Main application entry point
│   ├── LoginForm.java              # Login screen UI and logic
│   ├── RegistrationForm.java       # Registration screen UI and logic
│   ├── Dashboard.java              # User dashboard UI and operations
│   └── DatabaseManager.java        # Database operations and connectivity
├── nbproject/                      # NetBeans project configuration
├── build/                          # Compiled classes and build output
└── build.xml                       # Ant build configuration
```

## Requirements

- **Java Development Kit (JDK)**: Java 8 or higher
- **Apache Derby Database**: For data persistence
- **NetBeans IDE**: Recommended for development (or any IDE supporting JavaFX projects)
- **JavaFX SDK**: Version 11 or higher

## Prerequisites

Before running the application, ensure you have:

1. **Java JDK installed** - Check with `java -version`
2. **Apache Derby Database Server** - Running locally on port 1527
3. **MembershipDB Database** - Created with the following table structure:

```sql
CREATE TABLE members (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
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

Database Credentials:
- **Username**: `app`
- **Password**: `app`
- **Port**: 1527

## Installation & Setup

### 1. Clone or Download the Project
```bash
cd MembershipApp
```

### 2. Using NetBeans IDE
1. Open NetBeans
2. Click **File → Open Project**
3. Navigate to the `MembershipApp` folder
4. Click Open Project

### 3. Set Up Apache Derby Database
1. Start Apache Derby Network Server
2. Create the `MembershipDB` database
3. Execute the SQL schema provided above

### 4. Build the Project
```bash
ant clean build
```

### 5. Run the Application
```bash
ant run
```

Or from within NetBeans: **Run → Run Project**

## Usage

### Login
1. Launch the application
2. Enter your email and phone number on the login form
3. Click **Login** to access your dashboard
4. If you don't have an account, click **"Don't have an account? Register here"**

### Registration
1. From the login form, click the registration link
2. Fill in all required fields:
   - First Name and Last Name
   - Email and Phone Number
   - Address information
   - Country and Gender
   - Membership Type
   - Sports Interests
   - Reason for membership
3. Accept the terms and conditions
4. Click **Register** to create your account

### Dashboard
Once logged in, you can:
- View your welcome message
- **Send Message**: Contact administrators or other members
- **Make Payment**: Process membership or service payments
- **Edit Profile**: Update your personal information

## File Descriptions

| File | Purpose |
|------|---------|
| `MembershipApp.java` | Application launcher; sets up the primary stage and displays the login form |
| `LoginForm.java` | Login screen with email/phone authentication and navigation to registration |
| `RegistrationForm.java` | Registration form for new member accounts with comprehensive data collection |
| `Dashboard.java` | Post-login user interface with member options and quick actions |
| `DatabaseManager.java` | Handles all database operations including connections, authentication, and member data insertion |

## Database Connection Details

The application connects to Apache Derby using:
- **Driver**: `org.apache.derby.jdbc.ClientDriver`
- **Connection URL**: `jdbc:derby://localhost:1527/MembershipDB`
- **Username**: `app`
- **Password**: `app`

## Building from Command Line

```bash
# Clean and build
ant clean build

# Run the application
ant run

# Create JAR file
ant jar
```

## Troubleshooting

### Database Connection Error
- Ensure Apache Derby server is running on localhost:1527
- Verify the `MembershipDB` database exists
- Check username and password credentials

### JavaFX Not Found
- Ensure JavaFX SDK is installed
- Configure JavaFX library path in project settings (NetBeans: Tools → Libraries)

### Build Failures
- Run `ant clean` to remove old build artifacts
- Verify JDK version is Java 8 or higher
- Check that all dependencies are properly resolved

## Future Enhancements

- Password-based authentication
- Email verification for registration
- Payment gateway integration
- Member profile management
- Admin dashboard for member management
- Membership tier management
- Automated renewal reminders
- Member communication features

## License

This project is provided as-is for educational and business purposes.

## Support

For issues or questions, please refer to the source code comments or contact the development team.

---

**Version**: 1.0  
**Last Updated**: May 2026
