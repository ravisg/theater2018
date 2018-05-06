package theater2018;

import java.util.HashMap;

public class TicketServiceImplementation implements TicketService {
	
	int TotalSeatsInTheater;
	HashMap<String, SeatHold> onHoldSeatBlocks = new HashMap<String, SeatHold>();
	SeatBlock LinkedListHeadNode; // Pointer to first seat block
	

	int maxHoldTime;
	
	
	TicketServiceImplementation (int totalSeats,int maxHoldTime) {
		TotalSeatsInTheater = totalSeats;
		this.maxHoldTime = maxHoldTime;
		/*Seat numbers start with number 1, but dummy node will be the head of linkedlist and 
		 * will have a dummy seat number 0 with 0 number of seats.*/
		long currentEpochTime = System.currentTimeMillis();
		LinkedListHeadNode= new SeatBlock(0,0,"dummy",currentEpochTime,null, null);

		SeatBlock emptyTheater = new SeatBlock(1, TotalSeatsInTheater, "free", currentEpochTime, LinkedListHeadNode, null);
		LinkedListHeadNode.next = emptyTheater;
	}
	

	public int numSeatsAvailable(int timeOfAction) {
		// TODO Auto-generated method stub
		/*Iterate from the linkedlist start node, keep checking if any holds expire as you iterate and keep adding all the free
		 *  seats and return the number.*/
		int count=0;
		long currTime = System.currentTimeMillis();
		
		SeatBlock pointer = this.LinkedListHeadNode;
		while(pointer!=null) {
			
			long startTime = pointer.getBlockStartTime();
			long diff = (currTime - startTime)/1000;
			
			// remove hold and make seats free
			if(diff>maxHoldTime && pointer.getStatus().equals("hold"))
			{
				// set status to free and update block time
				pointer.setStatus("free");
				pointer.setBlockStartTime(currTime);
				
				freeBlockAndMerge(pointer, currTime);				
				count=count+pointer.getNumSeats();
				
			}else if(pointer.getStatus().equals("free")) {
				count=count+pointer.getNumSeats();
			}
			
			pointer = pointer.getNext();
			
		}		

		return count;
	}

	private void freeBlockAndMerge(SeatBlock pointer,long currTime) {
		// TODO Auto-generated method stub
		// if the previous and next blocks are free, merge the three blocks and prev node is not LinkedListHeadNode
		if(pointer.getPrevious().getStatus().equals("free") && pointer.getNext().getStatus().equals("free") && !pointer.getPrevious().getStatus().equals("dummy"))
		{
			SeatBlock prev = pointer.getPrevious();
			SeatBlock newPrev = prev.getPrevious();
			
			SeatBlock next = pointer.getNext();
			SeatBlock newNext = next.getNext();
			
			SeatBlock newSeatBlock = new SeatBlock(prev.getStartSeatNumber(),prev.getNumSeats()+pointer.getNumSeats()+next.getNumSeats(),
					"free",currTime,newPrev,newNext);
			
			newPrev.setNext(newSeatBlock);
			newNext.setPrevious(newSeatBlock);
			
			
		}
		//only if previous block is free, merge previous and current
		else if(pointer.getPrevious().getStatus().equals("free")  && !pointer.getPrevious().getStatus().equals("dummy") ) {
			
			SeatBlock prev = pointer.getPrevious();
			SeatBlock newPrev = prev.getPrevious();
			
			SeatBlock next = pointer.getNext();
			
			SeatBlock newSeatBlock = new SeatBlock(prev.getStartSeatNumber(),prev.getNumSeats()+pointer.getNumSeats(),
					"free",currTime,newPrev,next);
			newPrev.setNext(newSeatBlock);
			next.setPrevious(newSeatBlock);
			
			
			
		}
		//only if next block is free, merge next and current
		else if(pointer.getNext().getStatus().equals("free")){
			
			SeatBlock prev = pointer.getPrevious();
			
			SeatBlock next = pointer.getNext();
			SeatBlock newNext = next.getNext();
			
			SeatBlock newSeatBlock = new SeatBlock(prev.getStartSeatNumber(),pointer.getNumSeats()+next.getNumSeats(),
					"free",currTime,prev,newNext);
			
			prev.setNext(newSeatBlock);
			newNext.setPrevious(newSeatBlock);
			
		}
	}



	public SeatHold findAndHoldSeats(int numSeats, String customerEmail, int timeOfAction) {
		// TODO Auto-generated method stub
		/*Keep iterating from the beginning to find 1) If any existing blocks are expiring, 2) Can the current request be 
		 * satisfied by an existing free block directly or by breaking the existing free block. Once found, first create a 
		 * seatBlock object of type hold and put it in the hashmap with the String seatHoldID as key and the seatBlock as value.
		 *  Then create a seatHold object for the customer and return it. The second step is needed as per the spec given to you.
		 */
		long currTime = System.currentTimeMillis();
		SeatBlock head = this.LinkedListHeadNode; 
		
		SeatBlock currentNode = head.getNext();
		
		while(currentNode!=null) {
			
			long startTime = currentNode.getBlockStartTime();
			long diff = (currTime - startTime)/1000;
			
			// remove hold and make seats free
			if(diff>maxHoldTime && currentNode.getStatus().equals("hold"))
			{
				// set status to free and update block time
				currentNode.setStatus("free");
				currentNode.setBlockStartTime(currTime);
				
				freeBlockAndMerge(currentNode, currTime);				
				
			}
			
			
			
		}
		
		
		
		return null;
	}

	public String reserveSeats(int seatHoldId, String customerEmail, int timeOfAction) {
		// TODO Auto-generated method stub
		/*  When this request comes, you don’t need to iterate through the entire linkedlist to find the seatBlock node for the 
		 * corresponding seats. Go use the seatHoldID as the hashmap key to get directly to the node in the linkedlist, then check
		 *  if it expired or not, if expired, free them and return null, else remove the node from linkedList and return a c
		 *  onfirmationCode. You can create a confirmationCode based on the concatenation of email and seatHoldID or some 
		 *  such assumed string and return it.
		 */
		
		
		return null;
	}

}
