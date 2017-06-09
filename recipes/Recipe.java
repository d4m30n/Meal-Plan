package recipes;

import java.util.ArrayList;
import java.util.HashMap;

import menu.Menu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import people.People;

public class Recipe{
  //public static veriables
  //Holds all the recipes and there byte codes.
  public static HashMap<Integer,Recipe> recipes;
  
  //private static veriables
  private static int NUM_RECIPES = 0;
  
  //private local veriables
  private String title;
  private String ingre[];
  private int code;
  private int numIngre = 0;
  private int numClashes;
  
  //public methrods
  
  /**
   *  Constructor takes a string array and splits it up into title and
   *  ingrediants.
   *  @param recipe string array of ingrediants
   *  @param code the assigned byte code
   **/
  public Recipe(String[] recipe,int code, int numClashes) throws Exception{
    ingre = new String[recipe.length-1];
    this.code = code;
    this.numClashes = numClashes;
    if(recipe == null){throw new Exception("recipe String array is null");}
    this.title = recipe[0];
    for(int i = 1; i < recipe.length; i++){
      ingre[i-1] = recipe[i];
      numIngre++;
    }
    if(ingre.length == 0){throw new Exception("No ingreadents for recipe");}
  }
  
  /**
   *  Gets the assigned byte code
   **/
  public int getCode(){return this.code;}
  public int getClashes(){return this.numClashes;}
  public int getNumIngre(){return this.numIngre;}
  
  /**
   *  Gets the title of the recipe
   *  @return the Title of the recipe
   **/
  public String getTitle(){return this.title;}
  /**
   *  returns the number of recipes that were loaded in.
   *  @return Number of recipes.
   **/
  public static int getNumRecipes(){return NUM_RECIPES;}
  
  /**
   *  Prints out the list of ingreadents.
   *  @return List out all the ingreadents in a recipe.
   **/
  public void printIngrediants(){
    System.out.println();
    System.out.println("Recipe: "+title+"\tClashes: "+numClashes);
    for(String item : ingre){
      System.out.println(item);
    }
  }
  
  /**
   *  A methord used to load in a list of recipes and there
   *  ingrediants from a CSV file given in the arguments.
   *  @param csvFile The location of the csv file.
   **/
  public static void loadRecipes(String csvFile, ArrayList<People> peopleList)
  throws IOException, FileNotFoundException{
    //create a new hashmap for the new recipes.
    recipes = new HashMap<>();
    //load in the csv file and read line by line
    BufferedReader br = new BufferedReader(new FileReader(csvFile));
    String line = "";
    //the byte codes start at 0. 255 is the max recipes it can hold
    int start = 0;
    while((line = br.readLine()) != null){
      //if there are no ingrediants the constructor will not create
      //class
      try{
        String[] ingredants = line.toLowerCase().split(",");
        int clashes = 0;
        for(People person : peopleList){
          String[] dislike = person.getDislikes();
          for(String b : dislike){
            for(String a : ingredants){
              if(a.compareTo(b) == 0){clashes++;}
            }
          }
        }
        Recipe tmp = new Recipe(line.split(","),start,clashes);
        if(Menu.getMode() == 2){
          tmp.printIngrediants();
          Menu.pause();
          System.err.println();
        }
        //pass the csv file the processing is handled in the 
        //constructor of the class.
        recipes.put(start,tmp);
        start++;
      }
      catch(Exception e){System.err.println(e);}
    }
    br.close();
    NUM_RECIPES = start;
  }
}
