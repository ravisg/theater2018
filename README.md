# theater2018

This repo is to manage a Ticket Servicing of a high performance theater.

Edit: Line 356 of TicketServiceImplementation.java should be changed to 'return pointer' instead of 'return null'. 

Download and run the driver method

1.Clone the repo on local

2.Navigate to the folder theater2018 and type the command "mvn clean install". This creates a jar file in the path theater2018\target with the name theater2018-0.0.1-SNAPSHOT.jar

3.To run the driver program with a sample use case , type "java -cp theater2018-0.0.1-SNAPSHOT.jar theater2018.TheaterMain"
	
Assumptions and Design
 
1.The seat ids range from 1 to n where n is the total number of seats in the theater.
The seat ids start from 1 which is the leftmost seat in the first row and increase by 1 as you move to the adjacent seat. Once a row ends , the seat  behind the last seat has the next seat id . So a typical seat id would look like ( for n = 20 )

1	  2	  3	  4  	5 

10	 9	  8	  7	  6 

11	12	13	14	15 

20	19	18	17	16

Such an identification is used to assign adjacent seats for an incoming request.

The priority of the seats decreases with increase in the seat id . Hence in the above example, the order of priorty is 1>2>3>4.....>19>20
	
2.To incorporate expiry of holds on a block of seats , we use a blockStartTime variable for all seat blocks. Similar to EPOCH time, we assume that this time can be represented as long value of seconds.
	
3.At the start of the program , we assume all of the seats in the theater are in a single block. A dummy block called LinkedListHeadNode points to the start of this block. The block has its previous as the LinkedListHeadNode. The next of the block is null . In short , the seat blocks will be represented as doubly linked lists. 
	
4.Once a request for hold comes in , we break the block in two blocks and create set status of the first block to hold , the second block is set to free. The LinkedListHeadNode would point to the block on hold and vice versa. The block on hold would point to the free block and vice versa . The free block points to null.
	
5.As and when the new requests come , we check iterate through every block in the list whether it is free or on hold. 

		If it is free , we see if it is big enough to accommodate the request ( split it into two blocks if the number of seats are greater than the request) or we move to the next block.
    
		If it is on hold , we check if it is ready to expire . If its is expired , we see if it can accommodate the request . If it is smaller than request , we check the previous and next blocks to see if they are free. If either or both are free , we merge the blocks and create one block , we now check if this block can accommodate the request , if not we move to the next block.We do this until we reach the end of the list when we return a null.
		
    If a block is alloted for the request , we create a seatHold object and return it as per the specs in the interface.
		
6.Also , the hold seat block is stored on a hashmap with a key as the hold id and value of the seat block held. This is to make life easy when a request for reserving a hold comes in . We can look it up in the hashmap using the key .
			
      If it has expired , we return that " the request expired" and we merge the block of seats back into the list of blocks of available seats.
			
      If it has not expired, we delete this seat block from the list of blocks of available seats and we return user a confirmation code.
	
7.The idea of the code is to perform as less operations as possible. So we try to change the status of blocks whose hold has expired to free, only when a new request comes in or the method to calculate number of seats is called.


Understanding the classes and methods

SeatBlock.java

1. The SeatBlock class represent different blocks of seats in the theater. It holds the starting seat of the block , number of seats in the block, if the block is free or on hold and also the start time of the block. If a seat block is on hold , the start time would be the instant at which the hold was created . This is used to calculate the expiry of holds on seat blocks.

2. 	Every SeatBlock has a pointer to the next and previous seat block. The first seat block is called a LinkedListHeadNode and has a status of dummy and a start seat number of 0. This is a dummy block which was always represent the head of all the available seat blocks.

3. The last seat block in the list will always have its next as null which is used to signify that there are no more seat blocks while traversing through the list of available seat blocks.

SeatHold.java

Information of a hold like the starting seat in the block , the number of seats , email address of the request and a hold id ( which is a combination of start seat number , number of seats and email address)


TicketServiceImplementation.java

1. It has variables TotalSeatsInTheater and maxHoldtime ( which is the expiration time of a hold in seconds) . There is also the LinkedListHeadNode which has been explained in the write up above along with the hashmap onHoldSeatBlocks which contain key,value pairs of seatHoldId and SeatBlock alloted for the hold.

2. The constrcutor TicketServiceImplementation takes two arguments : total number of seats in the theater and maxHoldtime.
	
It creates a dummy SeatBlock called the LinkedListHeadNode . It also creates a SeatBlock emptyTheater which has number of seats equal to the TotalSeatsInTheater . It connects the two SeatBlocks to each other and marks the next seat block of emptyTheater as null.
	
3. public int numSeatsAvailable() - This method traverses the list of available seat blocks starting with LinkedListHeadNode and keep track of number of available seats. Whenever it reaches a SeatBlock on hold , it checks to see if the hold expired . If the hold has expired , it calls auxiliary method freeBlockAndMerge() which merges it with previous and next blocks if they are free . The status is set to free and the blockStartTime is updated to current epoch time in milliseconds. The count is incremented as per the number of seats in the free blocks and the blocks whose hold has expired.

4. void freeBlockAndMerge(SeatBlock pointer,long currTime)

This method is used to merge an expired block with the free blocks adjacent to it. 

a.If the previous and next blocks are free ( and next is not null ) , it merges the three blocks and connects them to the previous and next blocks in the linkedlist of available seat blocks

b.If only the previous is free , it merges previous and current and places inserts them in the appropriate place in linkedlist of available seat blocks

c.If only next is free and next is not null , it merges current and next and places inserts them in the appropriate place in linkedlist of available seat blocks
		
		
		
5. public SeatHold findAndHoldSeats(int numSeats, String customerEmail)

If the numSeats is less than TotalSeatsInTheater or it is less than 1 , we return a null right away.
			
************* We do not want to call numSeatsAvailable() at this time because we dont want to traverse the entire list of SeatBlocks and free all the expired holds , that is process intentsive . We would free a hold only when needed *****************************
			
Then it  starts at LinkedListHeadNode and iterates through the list 

a.if a SeatBlock is free , it calls auxiliary method placeHoldOnCurrentSeatBlock which creates a SeatBlock on needed size , puts a hold on it,updates onHoldSeatBlocks hashmap with hold info and returns a SeatHold object

b.if a SeatBlock is expired

b1.if the SeatBlock can satisfy the request by itself , it calls placeHoldOnCurrentSeatBlock which does same as above.

b2.if the SeatBlock cannot satisfy the request by itself, it calls freeBlockAndMergeWithNewBlockReturned method , which merges the SeatBlocks with adjacent free SeatBlocks and returns the new SeatBlock. If the new SeatBlock can satisfy the request , placeHoldOnCurrentSeatBlock is called which does same as above.
					
c.if the end of list is reached , a null is returned


6. public String reserveSeats(String seatHoldId, String customerEmail)
		
When a request for reserving a hold comes in ,it  looks it up in the hashmap onHoldSeatBlocks using the seatHoldId as key .

a.If the request is not found , we return "Booking expired or doesn't exist"


b.If it has expired , we return that " the request expired" and we merge the block of seats back into the list of blocks of available seats.

b.If it has not expired, we delete this seat block from the list of blocks of available seats and we return user a confirmation code.


7. public SeatHold placeHoldOnCurrentSeatBlock(SeatBlock currentNode,int numSeats,String customerEmail,long currTime )

Assigns the current seat block to a hold request and changes its status to hold . If the size of the block is larger , it splits the block into two . The first block is set two hold and used for  request , the second block is set free. A few operations are performed to maintain the order of doubly linkedlist. A SeatHold object is returned as per the specs.

8. SeatBlock freeBlockAndMergeWithNewBlockReturned(SeatBlock pointer,long currTime)
Same as freeBlockAndMerge but returns the SeatBlockobject of the newly created SeatBlock incase of a merge or if not returns the original SeatBlock
			
