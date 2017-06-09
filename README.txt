This is specifily configered for finding the optimal solution of recipes that will meet all the dislike requirements for a group of people

RUNNING
There are two ways that you can run the program 
ONE
  ./run <recipe.csv>

TWO
  java -classpath jgap.jar:. Main <recipe.csv>
  
both of these ways require you to supply a recipe csv file where the first item is the recipe name and any following are the ingrediants that are in the recipe.

COMPILING
Again there are two was to compile the project
ONE 
  ./compile <file.java>

TWO
  javac -classpath jgap.jar:. <file.java>
  
main is a good file to compile since it will then compile all the java classes and files that are imported into main.
also by compileing main you are compiling the entier project as it is
