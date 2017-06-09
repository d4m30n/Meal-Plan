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
    if(args.length != 3){help("Missing some starting paramaters\nMain <people.csv> <recipe.csv> <number of days>");}
    try{
      int days = Integer.parseInt(args[2]);
      Menu.setMode();
      people = People.loadPeople(args[0]);
      Recipe.loadRecipes(args[1], people);
      GA ga = new GA(days,new FitFunction(Recipe.recipes),500);
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
    catch(FileNotFoundException e){help("The one of the CSV files dose not exist.");}
    catch(IOException e){e.printStackTrace();}
    catch(NumberFormatException e){help("The number of days must be an integer.");}
    catch(Exception e){e.printStackTrace();}
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
    System.out.println("<people.csv>\tCSV of people with Name and disliked ingrediants.");
    System.out.println("<number of days>\tThe number of days that you want meals for");
    System.out.println();
    System.exit(1);
  }
}
