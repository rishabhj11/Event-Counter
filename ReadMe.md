# Files:
* bbst.java : Contains the main function.
* rbTree.java : Implementation of the Red Black Tree with all the required functions
* rbNode.java : contains the definition of the node
* Makefile : Used to compile the code

# To Compile:
The program has been coded in Java and JVM complier has been used to compile the same.
Commands to use: -
* $make – To compile the code
* $ make clean – To clean the compiled classes.

# Steps to Run:
To run the program, use the following:
* $ java bbst <filename.txt>
To run the file with 10^8 records, we need to increase the heap size to 8GB. Use the following
command for the same:
* $ java –Xmx8000m bbst <filename.txt>
The application enters a user interaction mode where user can use following commands:
* $increase <theID> <m>
* $reduce < theID > <m>
* $count < theID >
* $inrange < theID1 > < theID2 >
* $next < theID >
* $previous < theID >
* $quit

# Program Structure:
Building the initial Tree:
* Step1: Input is read from command line.
* Step2: Bufferreader is used to input the text from the input file and the IDs and the count are saved in
separate arrays.
* Step3: Calculated the max height of the tree using the total number of input IDs.
* Step4: buildInitialRBTree function is called with the two arrays, the start and the end pointer and the
max height as parameters. This function recursively builds the right and left subtree around the middle
node as the root.
* Step5: At each step the height of the present node is compared with the max height and if is colored red
at the max height.
* Step6: Root is colored black after the routine execution to take care of scenario where only one node is
present in input.
* Increase(int theID,int count)
findNode(theID) is called to find if node is present in the tree.
If the Node is present, its count is increased by argument.
If the Node is not present rbInsert(theID, m) function is called which inserts node in the tree.
This insert is followed by call to void rbInsertFixUp to fix violations of the RBTree properties.
* Reduce(int theID, int count)
findNode(theID) is called to find if node is present in the tree.
If the Node is present its count is reduced by argument.
If the count becomes 0 or less rbDelete(int theID) function is called which deletes the node from
the tree.
This insert is followed by call to rbDeleteFixUp function to fix violations of the RBTree
properties.
* Count(int TheID)
findNode(theID) is called to find if node is present in the tree.
Count is returned on the screen. If node is not present 0 is returned.
* inRange(int key1, int key2)
It checks if the arguments are valid and calls the function inRange which recursively traverse
the left and right subtree if the temp node is in the range of [k1,k2] and keeps adding the
sum of the count in the global variable inRangeCount which is returned.
* next(int theID)
The next function checks if ID is beyond extremities and returns a value accordingly. If in the
middle it calls the findNext function if the node exists and find the in order successor. If node
does not exist it calls the searchNextClosest function which returns the next existing node after
the ID.
* Previous(int theID)
The previous function checks if ID is beyond extremities and returns a value accordingly. If in the
middle it calls the findLast function if the node exists and find the in order predecessor. If node
does not exist it calls the searchLastClosest function which returns the previous existing node
before the ID.

# References:
The pseudo code for the functions insert, delete, insertFixUp, transplant was referred from the
‘Introduction to Algorithms’ by Thomas Cormen et al.