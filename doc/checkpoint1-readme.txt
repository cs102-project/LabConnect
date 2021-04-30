LabConnect

Group Members:
Borga Haktan Bilen 22002733
Vedat Eren Arıcan 22002643
Berkan Şahin 22003211
Berk Çakar 22003021
Alp Ertan 22003912

Description:
LabConnect facilitates communication between students, TA's, tutors,
and instructors. In the background, it is mainly a web application
(If sensible/necessary, it may possibly be ported to Android) that aims
to assist CS introductory courses in terms of organization and communication.
Proposed ideas for features include priority queuing for TA zoom rooms among many other
enhancements to TA/instructor productivity. For example, those who have completed their labs
can be tested using pre-defined (by TA or instructor) unit tests, if students pass the
tests successfully then they will be ordered by the number of visits to TA
in the same session, in order to decrease waiting times for the students
who are waiting from the beginning, and to optimize the process in general.
TA's can also use the system to see previous versions of each student's code
in a more practical way, similar to real version control managers in spirit.
The style guidelines put forth by the instructors can be enforced automatically by parsing
the student's sent code files. Much of the repetitive work that course
staff need to do can be reduced substantially by automated actions,
allowing TA's to allocate time for more hands-on help towards students.
The student experience can be improved further by adding helpful
features such as personal notes for students and so on.

Current Status:
Testing logic (Unit test and style checker) was implemented, as well as the general 
I/O infrastructure for files needing persistent storage. Assignment, submission, 
and user type model classes were also implemented.

Further Development:
The only "business logic" needing implementation is the Live Session logic. 
Once unit classes are complete, Repository classes for DB queries must be written 
(most of the heavy lifting is already done by Spring Data) and a REST API should be 
provided for manipulating the model classes. After this, frontend code will be written based 
on the UI report and the REST API provided by the backend.

Contributions:
Berkan Şahin -> Model Classes (Unit & Style Testing Logic) + Docker Composition
Berk Çakar -> Style Checker (Regex Logic and Checker Classes)
Borga Haktan Bilen -> Model Classes + Style Checker Classes
Alp Ertan -> Unit Test related classes (Exceptions) + Style Checker Classes
Vedat Eren Arıcan -> TestResult + Style Checker (Regex Logic) + Code clean-up/refactoring