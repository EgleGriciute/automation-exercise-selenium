# Automation Exercise Selenium Project 🧪

## Overview
This project is an automated testing framework using Selenium WebDriver for practicing and demonstrating web automation testing techniques. It is designed to test various scenarios on the Automation Exercise website.

## Prerequisites

### Technologies Used
- Java
- Selenium WebDriver
- TestNG
- Maven

### Requirements
- Java Development Kit (JDK) 11 or higher
- Maven
- Web browser (Chrome/Firefox recommended)

## Project Structure
```
automation-exercise-selenium/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/           # Page Object Model classes
│   │       └── utils/           # Utility classes
│   │
│   └── test/
│       └── java/
│           └── tests/           # Test case implementations
│
├── pom.xml                      # Maven project configuration
└── README.md                    # Project documentation
```

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/EgleGriciute/automation-exercise-selenium.git
   ```

2. Navigate to the project directory:
   ```bash
   cd automation-exercise-selenium
   ```

3. Install dependencies:
   ```bash
   mvn clean install
   ```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=TestClassName
```

## Development Branches
- `main`: Stable, production-ready code
- `dev`: Active development branch with latest features and improvements

### Contributing
1. Create a new branch from `dev`
2. Make your changes
3. Submit a pull request to the `dev` branch

## Test Coverage
The project includes automated tests for various scenarios such as:
- User registration
- Login functionality
- Product browsing
- Checkout process

## Reporting Issues
Please report any issues or bugs by creating a GitHub issue in the repository.

## Acknowledgments
- Selenium WebDriver
- TestNG
- Automation Exercise Website
