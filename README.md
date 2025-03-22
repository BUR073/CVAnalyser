# Smart CV Analyzer

### SID: 2408078

## Table of Contents

- [Smart CV Analyzer](#smart-cv-analyzer)
    * [Introduction](#introduction)
    * [Background](#background)
    * [Summary](#summary)
    * [Deliverables](#deliverables)
    * [Requirements](#requirements)
    * [Suggestions](#suggestions)
    * [Challenges](#challenges)
    * [Package and Class Overview](#package-and-class-overview)
    * [Project Adaptations and Modifications](#project-adaptations-and-modifications)
        + [Class Diagram Adaptations and Modifications](#class-diagram-adaptations-and-modifications)
            - [Interfaces and Action classes](#interfaces-and-action-classes)
            - [User Class](#user-class)
            - [Separation of UI and logic](#separation-of-ui-and-logic)
    * [Installation](#installation)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

## Introduction

The project outlined below is part of my first year Software Principles module assessment at Anglia Ruskin University.
This is my first project in Java as well as my first introduction to the Object-Oriented Programming style.

Note: Everything from Background through to Challenges is a direct copy from the assignment specification outline. 

## Background

Recruitment teams face the challenge of manually screening hundreds of resumes, a process that is both time-consuming
and prone to errors. This project aims to develop a **Smart CV Analyzer** that automates this process by leveraging
Natural Language Processing (NLP) to jobDescriptionNLP resumes, extract relevant skills and experience, and match them
to job requirements.

**NLP Basics:**

* Begin by understanding how NLP identifies entities, such as recognizing "Python" as a skill.
* Utilize beginner-friendly NLP tools like spaCy.

**Problem Scope:**

* Initially, focus on structured resumes (e.g., PDFs with clear headings) to simplify parsing.

## Summary

This project involves researching and designing a Smart Resume Analyzer system to automate resume screening. You will
explore tools and techniques for extracting relevant information from resumes and matching them to job requirements.
You'll need to clearly define the requirements, assumptions, and decisions involved in this process.

**Key Objectives:**

* Design a system to automate resume screening.
* Extract key details (skills, education, experience) from resumes.
* Rank candidates based on their match to job descriptions.

**MVP Approach:**

* Start with a Minimal Viable Product (MVP) using basic keyword matching.
* Progress to more advanced NLP models.
* The system does not require a Graphical User Interface (GUI).

**Objective:**

This project focuses on designing an automated system to solve real-world challenges in recruitment. It emphasizes
critical thinking, data processing, and optimization to improve hiring workflows.

## Deliverables

**Artifact:**

* A prototype application (or wireframes) demonstrating the following features:
    * Resume upload (PDF/DOCX/TXT).
    * Job description input (text field).
    * Ranked candidate list.

**Prototype:**

* A simple application with:
    * User interaction for uploading resumes and entering job descriptions.
    * Key features:
        * Extracting resume details.
        * Matching candidates to requirements.
        * Ranking results.

## Requirements

**Data Input:**

* Allow users to upload resumes in formats like PDF, DOCX, or TXT.
* Provide a text field or upload option for job descriptions.

**Resume Parsing:**

* Extract structured information from resumes, including:
    * Name and contact details.
    * Skills and technical proficiencies.
    * Work experience and job titles.
    * Education qualifications.
* Tip: Use regex for basic phone/email extraction.

**Matching & Ranking:**

* Use NLP techniques to match extracted resume details to job requirements.
* Assign scores or ranks to resume based on relevance.

**Result Display:**

* Display a ranked list of resumes with key highlights, such as matched skills or experience.
* Include filtering options based on criteria like skills, experience, or education.

## Suggestions

**Tools Selection:**

* Use spaCy or OpenNLP for named entity recognition and key phrase extraction.
* Libraries like PyPDF2 or Apache Tika can handle document parsing for PDFs and DOCX files.

**Dataset:**

* Use publicly available resume datasets or generate synthetic resumes for testing.
* Collect example job descriptions for matching experiments.

**Scalability:**

* Design the system to handle bulk resume uploads and job descriptions efficiently.

## Challenges

* Handling variations in resume formatting.
* Ensuring accurate matching of skills and experience to job descriptions.

**Strategies:**

* **Formatting Variations:** Start with resumes using standard headings (e.g., "Work Experience").
* **Ambiguous Terms:** Map synonyms (e.g., "ML" = "Machine Learning") using a glossary.

## Package and Class Overview
| Package                 | Class Name                   | Description                                                                                                      |
|-------------------------|------------------------------|------------------------------------------------------------------------------------------------------------------|
| `main`                  | `Main`                       | The entry point for the program                                                                                  |
| `main`                  | `User`                       | Handles the user login and register as well as storing the username                                              | 
| `UI`                    | `JobDescription`             | The UI for uploading the job description                                                                         |
| `UI`                    | `Login`                      | The UI for the login                                                                                             |
| `UI`                    | `Menu`                       | The menu UI                                                                                                      |
| `UI`                    | `Register`                   | The UI for register                                                                                              |
| `UI`                    | `UploadCV`                   | The UI for uploading CVs                                                                                         |
| `UI`                    | `ViewRankedCV`               | Allow the user to view the CV in ranked order from best to worst along with contact details of the applicant     |
| `NLP`                   | `JobDescriptionNLP`          | Uses NLP to parse the job description                                                                            |
| `NLP`                   | `CVsNLP`                     | Uses NLP to parse the CVs                                                                                        |
| `NLP`                   | `FindInText`                 | Extracts skills, phone numbers or email address from a String.                                                   |
| `records`               | `CVRecord`                   | Stores the data from the CV                                                                                      |
| `records`               | `CVScore`                    | Used to return the details of a CVs score, including filename, applicant name, email, phone number and the score |
| `records`               | `JobDescriptionRecord`       | Stores the data from the Job description, parsed using `JobDescriptionNLP`                                       |
| `records`               | `RecordRepository`           | Class to store Record objects                                                                                    |
| `util`                  | `KeyboardReader`             | Utility function that takes user inputs                                                                          |
| `util`                  | `Hashing`                    | Hashes text for security                                                                                         |
| `util`                  | `GetProperties`              | Retrieves values from properties files                                                                           |
| `util`                  | `NLP`                        | Small methods that are used commonly in the NLP parts of the project                                             |
| `util`                  | `FileChooser`                | Class that launches a GUI to choose files                                                                        |
| `util`                  | `FileReaderUtility`          | Class that reads a file into a string                                                                            |
| `auth`                  | `LoginAuth`                  | Handles the logic for the login                                                                                  |
| `auth`                  | `RegisterAuth`               | Handles the logic for register                                                                                   |
| `menuActions`           | `UserAction`                 | An interface that can return any data type and has an empty execute() method                                     |
| `menuActions.loggedIn`  | `JobDescriptionUploadAction` | Implements `UserAction`. Calls `upload()` from `JobDescription`                                                  | 
| `menuActions.loggedIn`  | `ShowJobDescriptionAction`   | Implements `UserAction`. Calls `showJobDescription` from `JobDescription`                                        |
| `menuActions.loggedIn`  | `UserLogoutAction`           | Implements `UserAction`. Calls `logout()` from `User`                                                            |
| `menuActions.loggedIn`  | `UploadCVAction`             | Implements `UserAction`. Calls `Upload()` from `UploadCV`                                                        |
| `menuActions.loggedIn`  | `ViewRankedCVsAction`        | Implements `UserAction`. Calls `view()` from `ViewRankedCVs`                                                     |
| `menuActions.loggedOut` | `UserLoginAction`            | Implements `UserAction`. Calls `login()` from `User`                                                             |
| `menuActions.loggedOut` | `UserRegisterAction`         | Implements `UserAction`. Calls `register()` from `User`                                                          |


## Project Adaptations and Modifications

### Class Diagram Adaptations and Modifications

![Class diagram created in the planning stage of this project](src/main/resources/READMEResources/ClassDiagram.png)
Above is the class diagram created in the planning stage of the project. I have not followed it and I will
detail why below. 

#### Interfaces and Action classes

So one of the most obvious changes is the addition of an interface class ``UserAction`` along with a group of action classes
split into a few packages (```actions.loggedin```, ```actions.loggedOut```, ```action.jobDescription```).
These are used in the various menus within the project. ```UserAction``` defines a method ```execute()```. The action functions
implement ```UserAction``` to call functions in other classes in the project, for example ```login()``` in the ```User``` class.
This in OOP concept called polymorphism. There are a few benefits to doing it this way instead of using switch statements. One benfit is 
it is modular and easy to extend. All you need to do is create a new Action class write what you would like to do within the ```execute()```
function and then add that to the hash map. 

While this may sound overkill for short menu's, which to an extent it is, it makes it easier to extend. If in the future, the project 
required a menu with 10 options, the switch statement would be very long and thus hard to read and maintain. I have even used ```UserAction``` 
for a menu that only has two options, the reason I have done this is because this project if for Track Genesis. In the future they may want to 
add more functionality to the program which could mean adding more menu options, this avoids the aforementioned long switch statements.

#### User Class

In the plan the User class had three variables: ```String: name```, ```String: email``` and ```String: password```, along with one method ```logout()```.
I have gone with a different approach. I only have two variables: ```String: username``` and ```Boolean: isLoggedIn``` and various functions including
```login()```, ```register()```, ```logout()``` and ```isLoggedIn()```. The reasons I have changed this are below:
1. I see no reason to store the password in the class, this is very private, and it would be best for security to have it stored in a file 
2. For simplicity, I have not required an email to register so there is no need to store one
3. I have added a boolean variable ```isLoggedIn()``` along with a getter function. This is used when accessing the menu to make sure you see the correct one (Logged in menu or logged out menu).
4. ```register()``` and ```login()``` have no actual logic in the class, they simply call functions in the `Login` and `Register` classes respectively. I have done this for readability, `user.login()` is far more clear compared to `Login.login()`. It also makes more sense that only `User` can access the `Login` and `Register` compared to `Menu`.
5. `logout()` is a setter function that sets `isLoggedIn()` to `false`. This means when the menu is next viewed it shows the correct menu, which in this case would be the logged out menu. 

#### Separation of UI and logic

While the implementation of `Login` and `Register` is already a step away from the initial plan, I have also created two other classes
`LoginAuth` and `RegisterAuth`. This separates the UI logging in and registering from the actual logic that makes it work. I have done this to make it easier to read and easier to maintain.
By separating the UI and logic, you make files smaller. This makes it easier to read as you have to scroll around less, it also makes it simpler to find bugs.
Also, it allows for reuse of logic, which I haven't done in this project, but in the future there may be a scenario where that is useful. I have also done this to `JobDescripton`. 

## Limitations

This program does have various limitations.
The two most notable limitations are in the scoring and the natural Language Processing. 

### Natural Language Processing

The current implementation of Natural Language Processing (NLP) solely uses keyword matching (KM).
I went with the approach to reduce the complexity of the program due to time limitations on the project,
especially as this is my first time using Java.
The KM allows for the program to pull out lists of dates, time, companies and skills; however, it can't put them together.
For example, if the CV said "Google - 2020 to 2024", it would be able to retrieve "Google", "2020" and "2024"
separately but would not see that the dates were related to the candidate's time at Google.
If I were to imporve it,
it would take considerable time and increase the complexity alot, 
but I would use a generative AI model that could understand the text not just match keywords. 

### Scoring

The Scoring system I have implemented is very simply,
it just gives the candidate a point for every keyword found in their CV that is also found in the Job Description.
This does work,
it will give the employer a general overview of what the candidate is like in relation to the job,
but it is very easily fooled.
A candidate could simply paste the job description to the bottom of their CV
(In white text so not visible on the screen), and they would appear to be the best candidate scoring 100%.
Importing this would be similar to improving the NLP, use a generative AI model that actually understands the text.
This would allow for experience to be weighted
(2 years at a company > 1 year at a company) and far improve the detail in which the score is based upon. 
## Installation

To install and run CVAnalyser, follow these steps:

1.  **Prerequisites:**
    * Java Development Kit (JDK) 8 or later.
    * Maven.
    * Git (optional, but recommended for cloning the repository).

2.  **Clone the Repository (if you haven't already):**

    ```bash
    git clone [https://github.com/BUR073/CVAnalyser.git](https://www.google.com/search?q=https://github.com/BUR073/CVAnalyser.git)
    cd CVAnalyser
    ```

3.  **Build the Project using Maven:**

    ```bash
    mvn clean install
    ```

    This command will download the necessary dependencies and compile the project. The executable JAR file will be created in the `target` directory.

4.  **Run the Application:**

    Navigate to the `target` directory:

    ```bash
    cd target
    ```

    Run the JAR file:

    ```bash
    java -jar CVAnalyser-1.0-SNAPSHOT.jar 
    ```
    Note: The actual jar name will depend on your project version, check the target folder for the correct name.
    If you have issues with the snapshot jar name, you can also run the application using maven.
    ```bash
    mvn exec:java -Dexec.mainClass="com.trackgenesis.main.Main"
    ```

**Troubleshooting:**

* If you encounter dependency issues during the build process, ensure that your Maven installation is configured correctly.
* If you get a java version error, ensure you are using java 8 or higher.
* If you have issues with the main class not being found, double check the package and main class name.