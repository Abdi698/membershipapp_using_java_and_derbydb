# Setup Instructions for Membership App

This document provides detailed setup instructions for getting the Membership App running on your system.

## Prerequisites

### System Requirements
- **Operating System**: Windows, macOS, or Linux
- **RAM**: Minimum 2 GB (4 GB recommended)
- **Disk Space**: 500 MB free space
- **Internet Connection**: Required for initial setup (to download dependencies)

### Software Requirements
- Java Development Kit (JDK) 8 or higher
- Apache Derby 10.x or higher
- NetBeans IDE 8.x or higher (optional, but recommended)

## Step-by-Step Installation

### Step 1: Install Java Development Kit (JDK)

#### On Windows:
1. Download JDK from [Oracle Java Downloads](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer and follow the installation wizard
3. Set JAVA_HOME environment variable:
   - Right-click "This PC" > Properties > Advanced system settings
   - Click "Environment Variables"
   - Click "New" and add:
     - Variable name: `JAVA_HOME`
     - Variable value: `C:\Program Files\Java\jdk-[version]`

#### Verify Installation:
```bash
java -version
javac -version
```

### Step 2: Install and Configure Apache Derby

#### Download and Install:
1. Download Apache Derby from [Apache Derby Downloads](https://db.apache.org/derby/releases/release-10.16.1.html)
2. Extract to a folder (e.g., `C:\apache-derby` on Windows)
3. Set DERBY_HOME environment variable:
   - Follow the same process as JAVA_HOME but use `DERBY_HOME`

#### Start Apache Derby Server:
1. Open Command Prompt/Terminal
2. Navigate to the Derby bin directory:
   ```bash
   cd C:\apache-derby\bin
   ```
3. Start the server:
   ```bash
   startNetworkServer.bat    # On Windows
   ./startNetworkServer.sh   # On macOS/Linux
   ```
4. You should see: `Server is ready to accept connections on port 1527`

### Step 3: Create and Configure MembershipDB Database

1. Open a new Command Prompt/Terminal window
2. Navigate to the Derby tools directory
3. Connect to the Derby server:
   ```bash
   ij.bat                    # On Windows
   ./ij                      # On macOS/Linux
   ```
4. In the ij prompt, enter:
   ```sql
   CONNECT 'jdbc:derby://localhost:1527/MembershipDB;create=true' USER 'app' PASSWORD 'app';
   ```

5. Create the members table:
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

6. Verify table creation:
   ```sql
   SHOW TABLES;
   ```

7. Exit ij:
   ```sql
   EXIT;
   ```

### Step 4: Set Up the Membership App Project

#### Option A: Using NetBeans IDE (Recommended)

1. **Download NetBeans**: Get it from [Apache NetBeans](https://netbeans.apache.org/)

2. **Open the Project**:
   - Launch NetBeans
   - Click File → Open Project
   - Navigate to the MembershipApp folder
   - Click Open Project

3. **Configure JavaFX** (if needed):
   - Right-click the project → Properties
   - Under Libraries, add JavaFX SDK
   - Apply and click OK

4. **Build the Project**:
   - Right-click the project → Clean and Build
   - Or use Shift+F11

5. **Run the Application**:
   - Right-click the project → Run
   - Or press F6

#### Option B: Using Command Line

1. Navigate to the project directory:
   ```bash
   cd path\to\MembershipApp
   ```

2. Clean and build:
   ```bash
   ant clean build
   ```

3. Run the application:
   ```bash
   ant run
   ```

### Step 5: Verify Database Connection

1. Launch the Membership App
2. At the login form, try logging in with test credentials
3. If you see database connection errors:
   - Verify Apache Derby server is running
   - Check database credentials (username: `app`, password: `app`)
   - Review the console error messages

## Testing the Application

### Test Login (If Database is Empty)
When you first run the app and the database is empty:
1. Go to the login form
2. Click "Don't have an account? Register here"
3. Complete registration with test data
4. After registration, you should see the dashboard

### Test Registration Form
1. Fill in all required fields
2. Submit the form
3. Check that data appears in the database

#### Verify Data in Database:
```sql
CONNECT 'jdbc:derby://localhost:1527/MembershipDB' USER 'app' PASSWORD 'app';
SELECT * FROM members;
EXIT;
```

## Troubleshooting

### Issue: "Cannot connect to database"
**Solution:**
- Ensure Apache Derby server is running: `netstat -an | find "1527"`
- Restart Derby server
- Check database credentials in DatabaseManager.java

### Issue: "Database not found"
**Solution:**
- Create MembershipDB following Step 3
- Verify the database name in connection string: `jdbc:derby://localhost:1527/MembershipDB`

### Issue: "JavaFX not found"
**Solution:**
- Download JavaFX SDK from [JavaFX.io](https://gluonhq.com/products/javafx/)
- In NetBeans: Tools → Libraries → New Library → Add JavaFX SDK
- Set project library dependency

### Issue: Compilation errors
**Solution:**
- Run `ant clean` to remove old build artifacts
- Ensure JDK version is 8 or higher: `javac -version`
- Check that all libraries are properly referenced

### Issue: Application runs but buttons don't work
**Solution:**
- Check the console for error messages
- Verify database connection is successful
- Review DatabaseManager.java for SQL errors

## Configuration Files

### Key Configuration Files:
- `build.xml` - Ant build configuration
- `nbproject/project.properties` - Project settings
- `nbproject/project.xml` - NetBeans project metadata

## Environment Variables Summary

After installation, you should have set:
```
JAVA_HOME = C:\Program Files\Java\jdk-[version]  (or your installation path)
DERBY_HOME = C:\apache-derby                       (or your installation path)
```

Add these to your system PATH for easy command-line access:
```
PATH = %PATH%;%JAVA_HOME%\bin;%DERBY_HOME%\bin
```

## Next Steps

1. Review the [README.md](README.md) for usage instructions
2. Explore the source code in the `src/membershipapp/` directory
3. Customize the application according to your requirements
4. Set up additional features like email verification or payment processing

## Support

If you encounter issues:
1. Check the troubleshooting section above
2. Review console error messages for specific errors
3. Verify all prerequisites are installed correctly
4. Consult the source code comments for implementation details

---

**Last Updated**: May 2026
