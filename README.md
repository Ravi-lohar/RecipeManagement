<div align = "center">
  <h1> Recipes Management System</h1>
 </div>

## Framework and Language
* Java 
* SpringBoot

## Dependencies
  * SpringWeb
  * Lombok
  * Swagger
  * JPA
  * My SQL Server
  * Validation
    
## Database Used 
  * Sql

    
## Data flow
  **The project has four main packages which are following** 
* Controller - Controller package has all the api and Crud operations.
* Services - Service class has all the business logic and it will return the result of that method which is called by controller class . 
* Repository - Respository has all the datasource in it i uses SQL Database
#### Model - Model class has model User , Comment , Recipe 
* User - user has email with validtion , contactNumber with validation of 12 digit( 2 digit country code and 10 digit number ) , email , apssword and gender with validation .
* Recipe : Recipe has recipeId , recipeName , Ingrediends , Instruction , cusine , total time ( 5 to 120 minutes ) , serving (1 to 10) and Craetion timw all with validation.and it is mapper with user via @ManytoOne as many recipe can be craeted by one user.
* Comment : Comment has commentId , comment body and mapped with recipe by @Many to one as many comments can be created on sigle recipe and with user also by @Manytoone has a singal user can comment many times. 
> The flow of data is from controller has all the api and logics of that api has in service class and
service class is using the repository class which has all the data it keeps data source.The api called by user call the controller method
return the logic which is written in service class so service class object is called and service class use data source of repo class.

## Datastructure 
* ArrayList

## Project Summary 
This is a Recipe Management System which has functionality of sign in , sign up . Following operation can be performed
* User SignUp
* User SignIn
* User SignOut
* User can create recipe when he was signIn we dont to give id of the user as it will be setted by mail id.
* Authenticated User who has craeted recipe can update and delte Recipe no need to give userId it will be taken via email provided at the time authentication.
* Anyone can see all recipes
* Comment can be created by SignedIn user any can comment ( you have to give cooment body and only recipe id all will be seted automatically via code
* Comment can be deleted by only that user who has commnted or the owner of the recipe.
