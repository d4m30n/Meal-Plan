import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import recipes.Recipe;

import people.People;

import menu.Menu;

import ga.GA;
import ga.FitFunction;

public class Main{

  //Holds a list of all the people in the list.
  private static ArrayList<People> people;

  //Public Methords
  
  public static void main(String[] args){
    if(args.length != 2){help("Missing recipe file");return;}
    try{
      Menu.setMode();
      people = People.loadPeople(args[0]);
      Recipe.loadRecipes(args[1], people);
      GA ga = new GA(12,new FitFunction(Recipe.recipes),500);
      Recipe[] best = ga.start();

      if(Menu.getMode() < 2){
        System.out.println();
        int i = 1;
        int dislike = 0;
        //Output the recipes.
        for(Recipe b : best){
          System.out.println("Day "+i+": "+b.getTitle());
          dislike += b.getClashes();
          i++;
        }
        System.out.println("Number of Dislikes:"+dislike);
        System.out.println();
      }
    }
    catch(FileNotFoundException e){e.printStackTrace();return;}
    catch(IOException e){e.printStackTrace();return;}
    catch(Exception e){e.printStackTrace();return;}
  }
  
  //Private Methords
  /**
   *  This is a simple reminder for what the program expects
   *  @param error prints of a specific message if needed null if not
   **/
  private static void help(String error){
    System.out.println();
    if(error != null){System.out.println(error);}
    System.out.println("<recipe.csv>\tCSV of recipes with Title and ingrediants list.");
    System.out.println();
  }
}
