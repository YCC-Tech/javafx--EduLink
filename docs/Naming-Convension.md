# Naming Convention

## What are the naming conventions?

Naming or coding conventions are style guidelines for programming. They typically cover the following:

- Naming and declaration rules for properties and methods.
- Rules for the use of white space, indentation, and comments.
- Programming practices and principles.

## Benefits of following naming conventions

Naming conventions secure quality:

- imporves code readability
- make code maintenance easier
- beautiful and consistent styles of code

## EduLink

In this docs, we are going to state the naming convention we used in this project. There are multiple convention formats that we can use in naming our variables and methods, such as `camelCase`, `PascalCase`, `underscore` and so on. We are going to apply them in where they are ment to be used.

#### Java

| Use case                          | Convention | Example           |
| --------------------------------- | ---------- | ----------------- |
| variable name                     | camelCase  | totalCount        |
| function or method name           | camelCase  | findAll           |
| class name                        | PascalCase | StudentController |
| model name                        | PascalCase | StudentModel      |
| global and constant variable name | UPPERCASE  | PI                |

#### MySQL

| Use case      | Convention          | Example          |
| ------------- | ------------------- | ---------------- |
| database name | underscore          | library_db        |
| table name    | plural underscore   | attendance_years |
| column name   | singular underscore | father_name      |

#### Folder and File names

| Folder      | Convention         | Example                        |
| ----------- | ------------------ | ------------------------------ |
| controllers | singular camelCase | StudentController.java         |
| models      | singular camelCase | Student.java                   |

### Code Indentation

We use a `tab` for code indentation.

```java
public double toCelsius(double fahrenheit) {
    return (5 / 9) * (fahrenheit - 32);
}
```

### Spaces around operators

We always put spaces around operators ( = + - \* / ), and after commas:

```java
double x = y + z;
String[] fruits = {"Apple", "Banana", "Cherry"};
```

### IDE

In eclipse, you can format code by pressing `Ctrl + Shift + F`. 
But be careful if the file has custom formatted code like SQL queries.
