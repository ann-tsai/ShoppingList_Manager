# Shopping List & Money Manager Application

## Proposal


This shopping list application allows user to create a shopping list for the items they wish to purchase in the future. 
It also allows user to keep track of the old purchases that has been checked off from their shopping list. This 
application is useful for anyone who wishes to keep track of their purchases, money usage, or shopping list. 

The **features** of this application includes:
- *Add* new items to the shopping list.
- *Add* new items to the purchased list.
- *Remove* the item from the shopping list if the user no longer wish to buy it.
- *Remove* the item from the purchased list.
- *Remove* the item from the shopping list after the purchase and add it to the purchased list.
- *Establish* the price of each item in the shopping list.
- *Establish* the price of each item in the purchased list.
- *Establish* different groups to categorize each item in the shopping/purchased list.
- *View* the items in the shopping lists.
- *View* the items in the purchased lists.
- *Keep track* of old purchases in the order that the user purchased them.
- *Keep track* of the money spent on the old purchases.
- *Keep track* of the money that is expected to be spent based on the shopping list.

I decided to create this project because I personally like to keep track of the things that I planned on buying in the 
near future so that I can manage my money before I even made the purchase. I also have a habit of keeping track of my 
old purchases and money usage to better manage my money; thus, I think that an application with these two features 
combined can be very useful for personal organizations.

## User Stories

**PHASE 1**
- As a user, I want to be able to **add an item** to my shopping list.
- As a user, I want to be able to **delete an item** from my shopping list.
- As a user, I want to be able to **mark an item as "purchased"** and move it to the old purchases list.
- As a user, I want to be able to see **the number of items** on my shopping list.
- As a user, I want to be able to see **the total expected price** of the items on my shopping list.

- As a user, I want to be able to *directly* **add an item** to my old purchases list.
- As a user, I want to be able to *directly* **delete an item** from my old purchases list. 
- As a user, I want to be able to **mark an item as "not purchased"** and move it back to the shopping list.
- As a user, I want to be able to see **the number of items** on my old purchases list.
- As a user, I want to be able to see **the total money spent** on my old purchases.

**PHASE 2**
- As a user, I want my file to be **saved** *automatically* when I quit the application.
- As a user, I want my file to be **loaded** *automatically* when I open the application.

- As a user, I want to be able to **delete all items** in my shopping list.
- As a user, I want to be able to **delete all items** in my old purchases list.
- As a user, I want to be able to **view** my shopping list.
- As a user, I want to be able to **view** my old purchases list.

**PHASE 3**
Created Graphic User Interface, including the following user stories:
- As a user, I want to be able to **view** my shopping list.
- As a user, I want to be able to **add an item** to my shopping list.
- As a user, I want to be able to **delete an item** from my shopping list.
  
- As a user, I want my file to be **saved** *automatically* when I quit the application.
- As a user, I want my file to be **loaded** *automatically* when I open the application.

**PHASE 4**

Task 2: Include a type hierarchy. 
Created an abstract class (WorkLists) in which both the ShoppingList and OldPurchasesList class extends it.

Task 3: UML Class Diagram
- extract super classes (JsonWriter and JsonReader)
- have my current ShoppingListJsonWriter/OldPurchasesListJsonWriter extends JsonWriter
- have my current ShoppingListJsonReader/OldPurchasesListJsonReader extends JsonReader
- have WorkLists class implements Writable interface so ShoppingList and OldPurchasesList don't have to

After analyzing my UML class diagram, I think it would be a good idea to extract a super class called JsonWrite and 
another one called JsonReader. I can then have my ShoppingListJsonWriter/OldPurchasesListJsonWriter and 
ShoppingListJsonReader/OldPurchasesListJsonReader extends them since they have nearly identical codes. I also think it 
would be a good idea to have my WorkLists abstract class implement the Writable interface instead of having both my 
ShoppingList class and OldPurchasesList class implements it. By doing the above refactoring, it would make my code much 
more cohesive, and it can make revising codes for future development much easier.