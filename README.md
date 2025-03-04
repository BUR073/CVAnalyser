# Smart CV Analyzer 

## SID: 2408078

## Introduction

The project outlined below is part of my 1st year Software Principles module assessment at Anglia Ruskin University. This is my first project in Java as well as my first introduction to the Object-Oriented Programming style. 
## Background

Recruitment teams face the challenge of manually screening hundreds of resumes, a process that is both time-consuming and prone to errors. This project aims to develop a **Smart CV Analyzer** that automates this process by leveraging Natural Language Processing (NLP) to jobDescriptionNLP resumes, extract relevant skills and experience, and match them to job requirements.

**NLP Basics:**

* Begin by understanding how NLP identifies entities, such as recognizing "Python" as a skill.
* Utilize beginner-friendly NLP tools like spaCy.

**Problem Scope:**

* Initially, focus on structured resumes (e.g., PDFs with clear headings) to simplify parsing.

## Summary

This project involves researching and designing a Smart Resume Analyzer system to automate resume screening. You will explore tools and techniques for extracting relevant information from resumes and matching them to job requirements. You'll need to clearly define the requirements, assumptions, and decisions involved in this process.

**Key Objectives:**

* Design a system to automate resume screening.
* Extract key details (skills, education, experience) from resumes.
* Rank candidates based on their match to job descriptions.

**MVP Approach:**

* Start with a Minimal Viable Product (MVP) using basic keyword matching.
* Progress to more advanced NLP models.
* The system does not require a Graphical User Interface (GUI).

**Objective:**

This project focuses on designing an automated system to solve real-world challenges in recruitment. It emphasizes critical thinking, data processing, and optimization to improve hiring workflows.

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
* Assign scores or ranks to resumes based on relevance.

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


## Classes

| Package   | Class Name             | Description                                                                |
|-----------|------------------------|----------------------------------------------------------------------------|
| `auth`    | `LoginAuth`            | Handles the logic for the login                                            |
| `auth`    | `RegisterAuth`         | Handles the logic for register                                             |
| `NLP`     | `JobDescriptionNLP`    | Uses NLP to parse the job description                                      |
| `records` | `CVRecord`             | Stores the data from the CV                                                |
| `records` | `JobDescriptionRecord` | Stores the data from the Job description, parsed using `JobDescriptionNLP` |
| `UI`      | `JobDescription`       | The UI for uploading the job description                                   |
| `UI`      | `Login`                | The UI for the login                                                       |
| `UI`      | `Menu`                 | The menu UI                                                                |
| `UI`      | `Register`             | The UI for register                                                        |
| `util`    | `FileExtractor`        | Extracts data from files and saves to `.txt` file                          |
| `util`    | `FileSaver`            | Saves file to chosen dir                                                   |
| `util`    | `KeyboardReader`       | Utility function that takes user inputs                                    |
| `main`    | `Main`                 | The entry point for the program                                            |
| `main`    | `User`                 | Handles the user login and register as well as storing the username        | 

## How this project differs from the plan

### Class Diagram

```mermaid
classDiagram
    class User {
        -name: String
        -email: String
        -password: String
        +logout(): void
    }
    class CVAnalyser {
        -u: User
        -m: Menu
        -userFilePath: String
        +login(): void
        +register(): void
        +usernameExist(): boolean
        +createUser(): void
        +runMenu(): void
    }
    class Menu {
        -userChoice: int
        -a: Applicant[]
        -jd: JobDescription[]
        -ar: ApplicantRanking
        -u: User
        -jobFilePath: String
        -applicantFilePath: String
        -applicantRankingFilePath: String
        +addApplicant(): Applicant
        +addJobDescription(): JobDescription
        +rankCVs(): ApplicantRanking
        +save(): void
        +uploadJobDescription(): void
        +inputJobDescription(): void
    }
    class ApplicantRanking {
        -ajs: ApplicantJobScore[]
        -jd: JobDescription
        -numberApplicants: int
        +selectJobID(): int
        +sortByDetailType(detailType: DetailType): void
        +filterByDetail(detail: String): void
        +filterNumberApplicants(numberApplicants: int): void
        +scoreApplicant(): ApplicantJobScore
    }
    class FileProcessor {
        -pathToOpenFrom: String
        -pathToSaveTo: String
        +chooseFile(): String
        +getFileType(): String
        +convertToTxt(): String
        +addExtractionError(): boolean
        +extractSection(detailType: DetailType): String
        +extractKeywords(): String
    }
    class JobProcessor {
        +extractTitle(): String
        +extractID(): int
        +editTitle(): String
        +editID(): int
        +editJobDetails(): String
    }
    class ApplicantProcessor {
        +extractName(): String
        +extractEmail(): String
        +extractAddress(): String
        +extractPhoneNumber(): String
        +addExtraConsideration(): boolean
    }
    class JobDescription {
        -title: String
        -titleExtractionError: boolean
        -ID: int
        -iDExtractionError: boolean
        -jobSection: JobSection[]
        +deleteJobDescription(): void
        +addExtractionError(): void
    }
    class JobDetail {
        -detail: String
        -detailWeighting: int
        -detailType: DetailType
        +editDetail(): String
        +editWeighting(): int
    }
    class JobSection {
        -section: String
        -sectionType: DetailType
        -extractionError: boolean
        -jobDetail: JobDetail[]
        -sectionWeighting: int
        +editWeighting(): int
        +addExtractionError(): boolean
    }
    class ApplicantJobScore {
        -a: Applicant
        -jd: JobDescription
        -jobID: int
        -totalScore: int
        -sectionScore: int[]
        -detailMatches: boolean[]
        -extraConsideration: boolean
        +detailMatch(): boolean
        +calculateScore(detailCategory: String): int
    }
    class ApplicantSection {
        -sectionFullText: String
        -sectionKeywords: String
        -sectionType: DetailType
    }
    class Applicant {
        -cvText: String
        -name: String
        -email: String
        -address: String
        -phoneNumber: String
        -extraConsideration: boolean
        -extractionError: boolean
        -keywords: String[]
        -applicantSection: ApplicantSection[]
        +deleteApplicant(): void
    }
    enum DetailType {
        RequiredSkills,
        DesiredSkills,
        Education,
        Experience
    }

    User "1" -- "0..*" CVAnalyser : uses
    CVAnalyser "1" -- "1" Menu : uses
    CVAnalyser "1" -- "0..*" Applicant : manages
    CVAnalyser "1" -- "0..*" JobDescription : manages
    CVAnalyser "1" -- "1" ApplicantRanking : creates
    Menu "1" -- "0..*" Applicant : manages
    Menu "1" -- "0..*" JobDescription : manages
    Menu "1" -- "1" ApplicantRanking : creates
    ApplicantRanking "1" -- "*" ApplicantJobScore : contains
    ApplicantRanking "1" -- "1" JobDescription : refers
    FileProcessor <|-- JobProcessor : extends
    FileProcessor <|-- ApplicantProcessor : extends
    JobDescription "1" -- "*" JobSection : contains
    JobSection "1" -- "*" JobDetail : contains
    ApplicantJobScore "1" -- "1" Applicant : refers
    ApplicantJobScore "1" -- "1" JobDescription : refers
    Applicant "1" -- "*" ApplicantSection : contains
    ApplicantSection "1" -- "1" DetailType : refers
    JobDetail "1" -- "1" DetailType : refers