# Task
Create an application that identifies the pair of employees who have worked
together on common projects for the longest period of time.

Input data `A CSV file with data in the following format`
```csv
EmpID, ProjectID, DateFrom, DateTo
143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2011-04-27
```
Simple output:
```csv
143, 218, 8
```
## Specific requirements
1) DateTo can be NULL, equivalent to today
2) The input data must be loaded to the program from a CSV file
3) The task solution needs to be uploaded in github.com, repository name must be in format:
   {FirstName}-{LastName}-employees

## Bonus points
1) Create an UI:
   The user picks up a file from the file system and, after selecting it, all common projects of the
   pair are displayed in datagrid with the following columns:
   Employee ID #1, Employee ID #2, Project ID, Days worked
2) More than one date format to be supported, extra points will be given if all date formats are
   supported

## Solution
1. REST: endpoint returning the simple output:
```dtd
curl --location 'http://localhost:8085/api/v1/files/upload' \
--form 'file=@"c:\\Users\\bansk\\Downloads\\task2.txt"' \
--form 'delimiter=";"'
```
2. UI: 

Can be checked at
```
http://localhost:8085
```

> Note: Keep in mind there are no validation.


Solution is no good:
* Base logic is concentrated in single class, the architecture is not clearly constructed, the controllers and layers are mixed.
* Missing abstractions - chain-of-responsibility is made of public classes without interfaces
* Validation is hard to follow with to many constructors.
* Exception handling is vague/missing - without checks on new row.
* missing code comments.
* code duplication. 
* ~~Too much of `var` usage makes it unreadable.~~ 
* ~~Algorithm doesn't cover important requirements. For example: A-B = B-A, misses cases where start and end date are same.~~
