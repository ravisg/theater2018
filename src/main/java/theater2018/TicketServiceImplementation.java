package theater2018;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class TicketServiceImplementation implements TicketService {

	int TotalSeatsInTheater;
	HashMap<String, SeatBlock> onHoldSeatBlocks = new HashMap<String, SeatBlock>();
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


	public int numSeatsAvailable() {
		// TODO Auto-generated method stub
		/*Iterate from the linkedlist start node, keep checking if any holds expire as you iterate and keep adding all the free
		 *  seats and return the number.*/
		int count=0;
		long currTime = System.currentTimeMillis();

		SeatBlock pointer = this.LinkedListHeadNode;
		while(pointer!=null) {

			long startTime = pointer.getBlockStartTime();
			long diff = (currTime - startTime)/1000;
			
			//System.out.println("line 41 diff "+diff);

			// remove hold and make seats free
			if(diff>maxHoldTime && pointer.getStatus().equals("hold"))
			{
				// set status to free and update block time
				////System.out.println("line 47 attempt to remove hold ");
				pointer.setStatus("free");
				pointer.setBlockStartTime(currTime);

				freeBlockAndMerge(pointer, currTime);				
				count=count+pointer.getNumSeats();
				////System.out.println("line 53 count "+count);

			}else if(pointer.getStatus().equals("free")) {
				count=count+pointer.getNumSeats();
				////System.out.println("line 57 count "+count);
			}

			pointer = pointer.getNext();

		}		

		return count;
	}

	void freeBlockAndMerge(SeatBlock pointer,long currTime) {
		// TODO Auto-generated method stub
		////System.out.println("free block and merge starts");
		
		// if the previous and next blocks are free, merge the three blocks and prev node is not LinkedListHeadNode
		if(pointer.getNext()!=null && pointer.getPrevious().getStatus().equals("free") && pointer.getNext().getStatus().equals("free") )
		{
			//&& !pointer.getPrevious().getStatus().equals("dummy") - check not needed
			SeatBlock prev = pointer.getPrevious();
			SeatBlock newPrev = prev.getPrevious();

			SeatBlock next = pointer.getNext();
			SeatBlock newNext = next.getNext();

			SeatBlock newSeatBlock = new SeatBlock(prev.getStartSeatNumber(),prev.getNumSeats()+pointer.getNumSeats()+next.getNumSeats(),
					"free",currTime,newPrev,newNext);

			newPrev.setNext(newSeatBlock);
			if(newNext!=null)
				newNext.setPrevious(newSeatBlock);


		}
		//only if previous block is free, merge previous and current
		else if(pointer.getPrevious().getStatus().equals("free")   ) {
			//&& !pointer.getPrevious().getStatus().equals("dummy") check not needed

			SeatBlock prev = pointer.getPrevious();
			SeatBlock newPrev = prev.getPrevious();

			SeatBlock next = pointer.getNext();

			SeatBlock newSeatBlock = new SeatBlock(prev.getStartSeatNumber(),prev.getNumSeats()+pointer.getNumSeats(),
					"free",currTime,newPrev,next);
			newPrev.setNext(newSeatBlock);
			if(next!=null)
				next.setPrevious(newSeatBlock);



		}
		//only if next block is free, merge next and current
		else if(pointer.getNext()!=null && pointer.getNext().getStatus().equals("free")){

			SeatBlock prev = pointer.getPrevious();

			SeatBlock next = pointer.getNext();
			SeatBlock newNext = next.getNext();

			SeatBlock newSeatBlock = new SeatBlock(pointer.getStartSeatNumber(),pointer.getNumSeats()+next.getNumSeats(),
					"free",currTime,prev,newNext);

			prev.setNext(newSeatBlock);
			if(newNext!=null)
				newNext.setPrevious(newSeatBlock);

		}
	}



	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		// TODO Auto-generated method stub
		/*Keep iterating from the beginning to find 1) If any existing blocks are expiring, 2) Can the current request be 
		 * satisfied by an existing free block directly or by breaking the existing free block. Once found, first create a 
		 * seatBlock object of type hold and put it in the hashmap with the String seatHoldID as key and the seatBlock as value.
		 *  Then create a seatHold object for the customer and return it. 
		 */
		
		////System.out.println("find and hold seats starts");
		long currTime = System.currentTimeMillis();
		if(numSeats>TotalSeatsInTheater || numSeats<1)
			return null;
		
		SeatBlock head = this.LinkedListHeadNode; 

		SeatBlock currentNode = head.getNext();
		////System.out.println("line 135 current node start seat "+currentNode.getStartSeatNumber());
		////System.out.println("line 136 num seats "+currentNode.getNumSeats());
		////System.out.println("status "+currentNode.getStatus());

		while(currentNode!=null) {

			long startTime = currentNode.getBlockStartTime();
			long diff = (currTime - startTime)/1000;


			if(currentNode.getStatus().equals("free") && numSeats<=currentNode.getNumSeats())
			{
				////System.out.println("147 current node "+currentNode.getStartSeatNumber());
				//System.out.println("status "+currentNode.getStatus());
				////System.out.println("steatss "+currentNode.getNumSeats());
				SeatHold hold= placeHoldOnCurrentSeatBlock(currentNode,numSeats,customerEmail,currTime);
				
				return hold;

			}

			// remove hold and make seats free
			else if(diff>maxHoldTime && currentNode.getStatus().equals("hold"))
			{
				// set status to free and update block time
				
				// set status to free and update block time
				////System.out.println("line 163 in find and hold seats attempt to remove hold ");
				
				currentNode.setStatus("free");
				currentNode.setBlockStartTime(currTime);

				if(numSeats<=currentNode.getNumSeats())
				{
					SeatHold hold= placeHoldOnCurrentSeatBlock(currentNode,numSeats,customerEmail,currTime);
					return hold;

				}else {

					SeatBlock newSeatBlock=freeBlockAndMergeWithNewBlockReturned(currentNode, currTime);				
					if(numSeats<=newSeatBlock.getNumSeats())
					{
						SeatHold hold= placeHoldOnCurrentSeatBlock(newSeatBlock,numSeats,customerEmail,currTime);
						return hold;
					}
				}

			}		

			currentNode = currentNode.getNext();

		}

		return null;
	}

	public String reserveSeats(String seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		/*  When this request comes, you don’t need to iterate through the entire linkedlist to find the seatBlock node for the 
		 * corresponding seats. Go use the seatHoldID as the hashmap key to get directly to the node in the linkedlist, then check
		 *  if it expired or not, if expired, free them and return null, else remove the node from linkedList and return a c
		 *  onfirmationCode. Create a confirmationCode based on the concatenation of email and seatHoldID or some 
		 *  such assumed string and return it.
		 */
		if(!onHoldSeatBlocks.containsKey(seatHoldId))
			return "Booking expired or doesn't exist";
		long currTime = System.currentTimeMillis();
		SeatBlock seatBlock = onHoldSeatBlocks.get(seatHoldId);	
		
		onHoldSeatBlocks.remove(seatHoldId);

		long startTime =seatBlock.getBlockStartTime();
		long diff = (currTime - startTime)/1000;

		// remove hold and make seats free
		if(diff>maxHoldTime)
		{
			seatBlock.setStatus("free");
			return "Booking expired or doesn't exist";
		}

		else {

			String confirmationCode = seatHoldId+"_"+seatBlock.getBlockStartTime();

			SeatBlock prev=seatBlock.getPrevious();
			SeatBlock next=seatBlock.getNext();

			prev.setNext(next);
			if(next!=null)
				next.setPrevious(prev);

			return confirmationCode;
		}
	}

	public SeatHold placeHoldOnCurrentSeatBlock(SeatBlock currentNode,int numSeats,String customerEmail,long currTime ) {
		
		////System.out.println("230 entering place and hold seats");

		if(currentNode.getNumSeats()==numSeats) {

			// create a SeatHold for the entire block

			currentNode.setStatus("hold");
			currentNode.setBlockStartTime(currTime);

			String holdId=currentNode.getStartSeatNumber()+":"+numSeats+":"+customerEmail;

			SeatHold hold = new SeatHold(customerEmail,currentNode.getStartSeatNumber(),numSeats
					,holdId);
			onHoldSeatBlocks.put(holdId, currentNode);

			return hold;
		}else {

			SeatBlock prev = currentNode.getPrevious();					
			SeatBlock next = currentNode.getNext();

			////System.out.println("254 prev node "+prev.getStartSeatNumber());
			//System.out.println("status "+prev.getStatus());
			//System.out.println("steatss "+prev.getNumSeats());

			if(next!=null) {
				//System.out.println("258 next node "+next.getStartSeatNumber());
				//System.out.println("status "+next.getStatus());
				//System.out.println("steatss "+next.getNumSeats());
			}

			SeatBlock blockToBeHeld = new SeatBlock(currentNode.getStartSeatNumber(),numSeats,"hold",currTime,prev,null);
			// have to set next once remainingBlock is initialized

			SeatBlock remainingBlock = new SeatBlock(currentNode.getStartSeatNumber()+numSeats,currentNode.getNumSeats()-numSeats,"free",currTime,null,next);

			blockToBeHeld.setNext(remainingBlock);
			remainingBlock.setPrevious(blockToBeHeld);

			prev.setNext(blockToBeHeld);
			if(next!=null) // the list ends with a null , you dont have to set a previous for a null object
				next.setPrevious(remainingBlock);

			String holdId=currentNode.getStartSeatNumber()+":"+numSeats+":"+customerEmail;
			SeatHold hold = new SeatHold(customerEmail,currentNode.getStartSeatNumber(),numSeats
					,holdId);
			onHoldSeatBlocks.put(holdId, blockToBeHeld);
			return hold;

		}



	}

	 SeatBlock freeBlockAndMergeWithNewBlockReturned(SeatBlock pointer,long currTime) {
		// TODO Auto-generated method stub
		//System.out.println("free block and merge with new block returned starts");
		
		// if the previous and next blocks are free, merge the three blocks and prev node is not LinkedListHeadNode
		if(pointer.getNext()!=null && pointer.getPrevious().getStatus().equals("free") && pointer.getNext().getStatus().equals("free") )
		{
			// && !pointer.getPrevious().getStatus().equals("dummy") - check redundant
			SeatBlock prev = pointer.getPrevious();
			SeatBlock newPrev = prev.getPrevious();

			SeatBlock next = pointer.getNext();
			SeatBlock newNext = next.getNext();

			SeatBlock newSeatBlock = new SeatBlock(prev.getStartSeatNumber(),prev.getNumSeats()+pointer.getNumSeats()+next.getNumSeats(),
					"free",currTime,newPrev,newNext);

			newPrev.setNext(newSeatBlock);
			if(newNext!=null)
				newNext.setPrevious(newSeatBlock);
			return newSeatBlock;

		}
		//only if previous block is free, merge previous and current
		else if(pointer.getPrevious().getStatus().equals("free")  ) {
			// && !pointer.getPrevious().getStatus().equals("dummy") - check redundant
			SeatBlock prev = pointer.getPrevious();
			SeatBlock newPrev = prev.getPrevious();

			SeatBlock next = pointer.getNext();

			SeatBlock newSeatBlock = new SeatBlock(prev.getStartSeatNumber(),prev.getNumSeats()+pointer.getNumSeats(),
					"free",currTime,newPrev,next);
			newPrev.setNext(newSeatBlock);
			if(next!=null)
				next.setPrevious(newSeatBlock);
			return newSeatBlock;


		}
		//only if next block is free, merge next and current
		else if(pointer.getNext()!=null &&    pointer.getNext().getStatus().equals("free")){

			SeatBlock prev = pointer.getPrevious();

			SeatBlock next = pointer.getNext();
			SeatBlock newNext = next.getNext();

			SeatBlock newSeatBlock = new SeatBlock(pointer.getStartSeatNumber(),pointer.getNumSeats()+next.getNumSeats(),
					"free",currTime,prev,newNext);

			prev.setNext(newSeatBlock);
			if(newNext!=null)
				newNext.setPrevious(newSeatBlock);
			return newSeatBlock;
		}
		return null;
	}
	
	public void printSeatHoldIds() {
		
		System.out.println("**************************************************************");
		for (Entry<String, SeatBlock> entry : onHoldSeatBlocks.entrySet()) {			
			System.out.println("hold id is " +entry.getKey()+"     status is :  "+entry.getValue().getStatus());		 
		}
		System.out.println("**************************************************************");
				
	}

}
