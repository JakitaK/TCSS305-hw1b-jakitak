# TCSS305

Assignment 1b

Jakita Kaur

Autumn 2024

## Assignment Overview
My understanding of the purpose and the scope of the assignment was
to basically help me imporve my Java programming skills by working with 
classes. The primary goal was to develop classes (StoreItem, StoreItem, StoreItemOrder).
I am manily suppose to build the backend part of the program which means dealing 
with how items are added to the cart, how the bulk pricing works, and also making
sure the total cost is calculated correctly using BigDecimal for precision. 
A big part of this assignment was writing tests before coding. Unit testing is emphasized
to ensure full code coverage and bug prevention That was new to me so I had to realy make 
sure that I covered up all possible scenariors with my tests. It was important to write 
my code in a way that is clean and also handles errors properly such as throwing 
exceptions when prices or quantities aren't valid.


## Technical Impression:
For this assignment I had to use Test-Driven Development in order for me to complete this assignment.
I read the provided API very carefully and the instructions that were given to me to know what each class
needed to do. I started by writing my test cases first, so I had to think about all the possible ways the
method could be used and most importantly also misused before even writing the actual code. That was
something I’m not used to doing, so it took me a bit to figure out. It was helpful to look at the ItemTest
That was provided because I could see how the test cases should be written. It took me a lot of time setting
up the tests for StoreCart and making sure that it was fully covered. For me testing bulk pricing and the
calculateTotal() method was tricky especially when combining bulk items and regular items. Had to also
double check the math for bulk pricing scenarios to make sure the calculations for items like buy 6 for
$10 worked as it is expected. Getting the BigDecimal calculations right also took me some time to figure
out because I had to keep remembering to not convert to double or a float. I already did some research
for BigDecimal for the quiz, so it made it a bit easier for me so know about setScale() and the rounding
modes. Another thing that frustrated me was figuring out how to manage the items in the cart and making
sure that when an item was readied, the new quantity replaced the old one instead of just adding on top.
I had a lot of check style errors so fixing those took some time as well, but I’m getting used to fixing my
code to make it look more presentable. Overall this was a very interesting assignment, but it was challenging 
though and I had to think a lot.


## Unresolved problems in my submission:
None

## Citations and Collaborations:
I touched base with Balkirat Singh, a student in the class, for some help with methods and to work out a couple of 
issues to further improve our coding assignments. For Java Doc I referenced my past assignment as well as your test class
for JavaDoc guidance, also took quick look at Java API.
## Questions:
None