#ifndef _HW2_H
#define _HW2_H

/** 
* This program loads student, course, and grade data from a text file then allows
* the user to perform simple queries on that data through a command line interface. 
*
* 
* @author Sydney Cochran
* @version 9/10/22
*
*/

////////////////////////////////////////////////////////////////////////////////
// INCLUDES
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

////////////////////////////////////////////////////////////////////////////////
// MACROS: CONSTANTS
#define MAX_STUDENT_NAME_LENGTH 20
#define MAX_COURSE_NAME_LENGTH 10
#define MAX_TEACHER_NAME_LENGTH 20
#define MAX_COMMENT_LENGTH 20
#define MAX_ASSIGN_NAME_LENGTH 20
#define MAX_FILENAME_LENGTH 30

////////////////////////////////////////////////////////////////////////////////
//DATA STRUCTURES

typedef enum AcademicLevel{Freshman = 1, Sophomore, Junior, Senior} AcademicLevel;
typedef struct ScoreStruct {
        double score;
        char comment[MAX_COMMENT_LENGTH];
        } ScoreStruct;
typedef struct Assign {
        int id;
        char assignmentName[MAX_ASSIGN_NAME_LENGTH];
        
        } Assign;
typedef struct Student {
        AcademicLevel academicLevel;
        int id;
        char name[MAX_STUDENT_NAME_LENGTH];
        } Student;
typedef struct Course {
        int id;
        char name[MAX_COURSE_NAME_LENGTH];
        char instructor[MAX_TEACHER_NAME_LENGTH];
        int numAssignments;
        Assign* assignments;
        ScoreStruct** scores;
        } Course;

////////////////////////////////////////////////////////////////////////////////
//GLOBAL VARIABLES
//place to store student information
Student* students = NULL;
//place to store course information
Course* courses = NULL; 
int numCourses, numStudents;

////////////////////////////////////////////////////////////////////////////////
//FORWARD DECLARATIONS

void readFile(char* filename);
void readString(FILE* file, char** dst, int max_length);
Student* readStudents(FILE* file);
Course* readCourses(FILE* file);
Assign* readAssigns(FILE* file, int numAssigns);
ScoreStruct** readScores(FILE* file, int numAssigns);

void terminate();
void studentsDestructor();
void coursesDestructor();
void assignsDestructor(Assign** assigns, int numAssign);
void scoresDestructor(ScoreStruct*** scores, int numAssigns);

void printStudents();
void printAssigns(Assign* assigns, int numAssigns);
void printGrades(ScoreStruct** scores, int numAssigns);
void printCourse(Course course);
char* getLevel(AcademicLevel level);

void mainMenu();
void mainMenuBranch(int option);
void subMenu(Course course);
void subMenuBranch(int option, Course course);

void studentMenu(Course course);
void getStudentScores(Course course, int studentNo);
void assignmentMenu(Course course);
void getAssignmentScore(Course course, int assignmentNo);

void performInstructions(char* iFile);

void printUsage();

/////////////////////////////////////////////////////////////////////////////////
//IMPLEMENTATION

/**
* Loads data from student/course data file
* @param filename is the name of the file 
*/
void readFile(char* filename){
  FILE* file = fopen(filename, "r");
 //FOR YOU TO IMPLEMENT 
  students = readStudents(file);
  courses = readCourses(file);
  fclose(file);
}

Student* readStudents(FILE* file){
    fscanf(file, "%d", &numStudents);
    Student* temp = (Student*)malloc(numStudents*sizeof(Student));
    
    for(int i = 0; i < numStudents; i++){
      int level;
      char name[MAX_STUDENT_NAME_LENGTH];
      int id;
      fscanf(file, "%d %s %d", &level, name, &id);
      temp[i].academicLevel = level;
      temp[i].id = id;
      strcpy(temp[i].name, name);
  }
    return temp;
}
Course* readCourses(FILE* file){
    fscanf(file, "%d", &numCourses);
    Course* temp = (Course * )malloc(numCourses*sizeof(Course));
    for(int i = 0; i<numCourses; i++){
      int id;
      char instructor[MAX_TEACHER_NAME_LENGTH];
      char name[MAX_COURSE_NAME_LENGTH];
      int numAssignments;
      
      fscanf(file, "%d %s %s %d", &id, name, instructor, &numAssignments);
      temp[i].id = id;
      strcpy(temp[i].instructor, instructor);
      strcpy(temp[i].name, name);
      temp[i].numAssignments = numAssignments;
      temp[i].assignments = readAssigns(file, numAssignments);
      
      temp[i].scores = readScores(file, numAssignments);
      
      
  }
    return temp;
}

Assign* readAssigns(FILE* file, int numAssigns){
    Assign* temp = (Assign * )malloc(numAssigns*sizeof(Assign));
    for(int j = 0; j < numAssigns; j++){
          int id;
          char name[MAX_ASSIGN_NAME_LENGTH];
          fscanf(file, "%d %s", &id, name);
          temp[j].id = id;
          strcpy(temp[j].assignmentName, name); 
      }
    return temp;
}

ScoreStruct** readScores(FILE* file, int numAssigns){
    ScoreStruct** temp = (ScoreStruct**)malloc(numAssigns*sizeof(ScoreStruct*));
    for(int i = 0; i < numAssigns; i++){
        *(temp + i) = (ScoreStruct*) malloc(numStudents*sizeof(ScoreStruct));
    }
    
    for(int i = 0; i < numAssigns; i++){
      for(int j = 0; j<numStudents; j++){
        int assignId;
        int studentId;
        double score;
        char comment[MAX_COMMENT_LENGTH];
        fscanf(file, "%d\n",&assignId);
        fscanf(file, "%d\n", &studentId);
        fscanf(file, "%lf\n", &score);
        fscanf(file, "%s\n", comment);
        
        temp[assignId-1][studentId-1].score = score;
        strcpy(temp[assignId-1][studentId-1].comment, comment);  
      }
        
    }
    
    return temp;
}


/**
* Implements main menu functionality, which allows user to select a course to interact with
*/
void mainMenu(){
  int input_buffer;
  printf("Course Searcher");  
  do {
	printf("\n-----------------------------------\n");
        printf("Course Options");
	printf("\n-----------------------------------\n");
	int i;
	for(i = 0; i < numCourses; i++){
            printf("   %d %s\n", courses[i].id, courses[i].name);
        }
	printf("   0 REPEAT OPTIONS\n");
        printf("  -1 TERMINATE PROGRAM\n");
	printf("Please enter your choice ---> ");
	scanf(" %d", &input_buffer);
        mainMenuBranch(input_buffer);
  } while (1);
}

/**
* Handles menu options of main menu
* @param option is the chosen operation's option #
*/
void mainMenuBranch(int option){
  if (option < -1 || option > numCourses){
    printf("Invalid input. Please try again...\n");; 
    while(option < -1 || option > numCourses){
  	  printf("Please enter a valid option ---> ");
      scanf(" %d", &option);
    }
  }
  if(option == -1){
	printf("Terminating program...\n");
    terminate();
  } else if (option == 0) {
	printf("Repeating options...\n");
  } else {
    Course course = courses[option - 1];
        printf("Course with name %s selected.\n", course.name);
	subMenu(course);
  }
}

/**
* Implements sub menu functionality, which allows users to select how they want to interact with course
* @course is the course to be queried
*/ 
void subMenu(Course course){
  int input_buffer;
  do {
    printf("\n-----------------------------------\n");
    printf("Menu Options");
	printf("\n-----------------------------------\n");
    printf("   1 Get a student's final grades in the course\n");
	printf("   2 Get the average grade of an assignment in the course\n");
    printf("   3 Print all course data\n");
	printf("   0 REPEAT OPTIONS\n");
	printf("  -1 RETURN TO PREVIOUS MENU\n");
    printf("  -2 TERMINATE PROGRAM\n");	
	printf("Please enter your choice ---> ");
	scanf(" %d", &input_buffer);
    subMenuBranch(input_buffer, course);
  } while (input_buffer != -1);
}

/**
* Handles menu options of submenu
* @param option is the chosen operation's option #
* @param course is the course struct to be queried
*/
void subMenuBranch(int option, Course course){
  if (option < -2 || option > 3){
    printf("Invalid input. Please try again...\n");; 
    while(option < -2 || option > 3){
  	  printf("Please enter a valid option ---> ");
      scanf(" %d", &option);
    }
  }
  if(option == -2){
    printf("Terminating program...\n");
	terminate();
  } else if (option == -1){
	printf("Returning to previous menu...\n");
  } else if (option == 0){
    printf("Repeating options...\n");
  } else if (option == 1){ 
      studentMenu(course);
  } else if (option == 2){
      assignmentMenu(course);
  } else if (option == 3){
      printCourse(course);
  } 
}

void studentMenu(Course course){
    int input;
    do{
        printf("\nPlease choose from the following students:\n");
        printStudents();
        printf("   0 RETURN TO PREVIOUS MENU\n");
        printf("Enter your choice ---> ");
        scanf("%d", &input);
        getStudentScores(course, input);
    } while(input != 0);
    
    
}
void getStudentScores(Course course, int studentNo){
    if (studentNo < 0 || studentNo > numStudents){
	printf("Invalid input. Please try again...\n");; 
        while( studentNo < 0 || studentNo > numStudents){
  	  printf("Please enter a valid option ---> ");
          scanf(" %d", &studentNo);
        }
    } 
    if (studentNo == 0){
      printf("Returning to previous menu...\n");
    } else{
        printf("\n%s\'s assignment specific grades were:\n", students[studentNo-1].name);
        printf("Assign Name     Score       Comment");
        printf("\n-----------------------------------\n");
        int sum = 0;
        for(int i = 0; i < course.numAssignments; i++){
            printf("%s      %.2f     %s\n", course.assignments[i].assignmentName,
                    course.scores[i][studentNo-1].score,
                    course.scores[i][studentNo-1].comment);
            sum += course.scores[i][studentNo-1].score;
        }
        float finalScore = sum/course.numAssignments;
        printf("\n%s\'s final grade was %.2f.\n", students[studentNo-1].name, finalScore);
        
        
    }
}
void assignmentMenu(Course course){
    int input;
    do{
        printf("\nPlease choose from the following assignments:\n");
        printAssigns(course.assignments, course.numAssignments);
        printf("   0 RETURN TO PREVIOUS MENU\n");
        printf("Enter your choice ---> ");
        scanf("%d", &input);
        getAssignmentScore(course, input);
        
    } while(input != 0);   
    
}
void getAssignmentScore(Course course, int assignmentNo){
    if (assignmentNo < 0 || assignmentNo > course.numAssignments){
	printf("Invalid input. Please try again...\n");; 
        while( assignmentNo < 0 || assignmentNo > course.numAssignments){
  	  printf("Please enter a valid option ---> ");
          scanf(" %d", &assignmentNo);
        }
    } 
    if (assignmentNo == 0){
      printf("Returning to previous menu...\n");
    } 
    else{
        float sum = 0;
        for(int i = 0; i < numStudents; i++){
            sum += course.scores[assignmentNo-1][i].score;
        }
        float result = sum/numStudents;
        printf("The average grade on %s was %.2f.\n", course.assignments[assignmentNo].assignmentName, result);
    }
}


/**
* Prints basic usage instructions for the program to the command line
*/
void print_usage(){
  printf("USAGE:\n./LastNameCourseReader -d <data_file_name(char*)> -c <instruction_file_name(char*)>\n");
  printf("-d refers to the required input data file containing student & course information; this must be a valid .txt file\n");
  printf("-i refers to the optionally supported 'instruction file' that provides directions for how the program should execute without CLI input; \n\t must be a valid .txt.file\n");
}
void printStudents(){
    for(int i = 0; i < numStudents; i++){
        char* level = getLevel(students[i].academicLevel);
        printf("   %d %s (%s)\n", students[i].id, students[i].name, level); 
    }
   
}
void printAssigns(Assign* assigns, int numAssigns){
    for(int i = 0; i < numAssigns; i++){
        printf("   %d %s\n", assigns[i].id, assigns[i].assignmentName);
    }
    
}
void printGrades(ScoreStruct** scores, int numAssigns){
    for(int i = 0; i < numAssigns; i++){
        for(int j = 0; j < numStudents; j++){
            printf("   %d %d %.2f %s \n", i+1, j+1, scores[i][j].score, scores[i][j].comment);
        }
        
    }
}
void printCourse(Course course){
    printf("\nCourse ID: %d\n", course.id);
    printf("Course Name: %s\n", course.name);
    printf("Teacher: %s\n", course.instructor);
    printf("Assigns:\n");
    printAssigns(course.assignments, course.numAssignments);
    printf("Grade Data:\n");
    for(int i = 0; i < numCourses; i++){
        printGrades(course.scores, course.numAssignments);
    }
}

char* getLevel(AcademicLevel level){
  if(level == 1){
      return "Freshman";
  } else if(level == 2){
    return "Sophomore";
  } else if (level == 3){
    return "Junior";
  } else if(level == 4){
    return "Senior";
  } else{
    return "";
  }
}

void terminate(){
    studentsDestructor();
    coursesDestructor();
  exit(1);
}
void studentsDestructor(){
    free(students);
}
void coursesDestructor(){
    while(courses){
        assignsDestructor(&courses->assignments, courses->numAssignments);
        scoresDestructor(&courses->scores, courses->numAssignments);
        *courses++;
    }
    free(courses);
}

void assignsDestructor(Assign** assigns, int numAssign){
    for(int i = 0; i < numAssign; i++){    
        free(assigns[i]);
    }
    free(assigns);
}

void scoresDestructor(ScoreStruct*** scores, int numAssigns){
    for(int i = 0; i<numAssigns; i++){
      for(int j = 0; j < numStudents; j++){
        free(scores[i][j]);
      }
        free(scores[i]);
    }
    free(scores);
}

int main(int argc, char* argv[]){
  char* datafile;
  char* instructionfile;
  int opt;
  while((opt = getopt(argc, argv, ":d:i:")) != -1){
    switch(opt){
      case 'd':
        datafile = optarg;
        break;  
      case 'i':
        instructionfile = optarg;
        break;
      case ':':
		printf("option needs a value\n");
		break;
      case '?':
        printf("unknown option: %c\n", optopt);
        break;
    }
  }
  for (; optind < argc; optind++){
    printf("Given extra arguments: %s\n", argv[optind]);
  }  
 
  int dflen;
  if(datafile != NULL){
    dflen = strlen(datafile);
    if(dflen >= 5
		&& (strcmp(&datafile[dflen-4], ".txt") == 0)
        && (access(datafile, F_OK) != -1)){
      printf("Importing data from %s\n\n", datafile);
	  readFile(datafile);
    } else {
	  printf("Data file has an invalid name or does not exist.\n");
      print_usage();
	  exit(1);   
    }
  } else {
    printf("No data file name provided. This is a required field.\n");

    print_usage();
    
	exit(1);
  }

  int iflen;
  int ifval;
  if(instructionfile != NULL){
    iflen = strlen(instructionfile);
	if(iflen >= 5 
		&& (strcmp(&instructionfile[iflen-4], ".txt") == 0)
		&& (access(instructionfile, F_OK) != -1)){
	  printf("Performing instructions defined in %s:\n", instructionfile);	  
	} else {
      printf("Instruction file has an invalid name or does not exist.\n");
	  print_usage();
	  exit(1);
    }
  } else {
	printf("No instruction file provided. Using CLI:\n");
    mainMenu();
  }
  return 0;
}
#endif