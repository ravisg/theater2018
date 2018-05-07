package theater2018;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplementationTest {

	@InjectMocks 
	TicketServiceImplementation tsImpl= new TicketServiceImplementation(30,3);

	@Test
	public void testNumSeatsAvailable() {
		
		assertEquals(tsImpl.numSeatsAvailable(),30);

	}

	@Test
	public void testNumSeatsAvailableWithHoldsAndReserves() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"free",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"hold",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"free",System.currentTimeMillis()+10000,blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(tsImpl.numSeatsAvailable(),23);
	}


	@Test
	public void testFindAndHoldSeats() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"free",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"free",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"hold",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"free",System.currentTimeMillis()+10000,blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SeatHold sHold=tsImpl.findAndHoldSeats(7, "test1@gmail.com");
		assertEquals(sHold.getStartSeatNumber(),1);
	}

	@Test
	public void testFindAndHoldSeatsWithHoldExpiry() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis()+10000,HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"hold",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SeatHold sHold=tsImpl.findAndHoldSeats(3, "test1@gmail.com");
		assertEquals(sHold.getStartSeatNumber(),23);
	}

	@Test
	public void testFindAndHoldSeatsWithHoldExpiryAndMerge() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis()+10000,HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SeatHold sHold=tsImpl.findAndHoldSeats(13, "test1@gmail.com");
		assertEquals(sHold.getStartSeatNumber(),16);
	}

	@Test
	public void testFindAndHoldSeatsWithLessSeatsAvailable() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis()+10000,HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;



		SeatHold sHold=tsImpl.findAndHoldSeats(13, "test1@gmail.com");
		assertEquals(sHold,null);
	}


	@Test
	public void testReserveSeats() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis()+10000,HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		SeatHold blockOneHold=new SeatHold("abc@gmail.com",1,7,"1:7:abc@gmail.com");

		tsImpl.onHoldSeatBlocks.put("1:7:abc@gmail.com", blockOne);

		assertEquals(tsImpl.reserveSeats("1:7:abc@gmail.com", "abc@gmail.com"),"1:7:abc@gmail.com"+"_"+blockOne.getBlockStartTime());

	}

	@Test
	public void testReserveSeatsHoldExpired() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		SeatHold blockOneHold=new SeatHold("abc@gmail.com",1,7,"1:7:abc@gmail.com");

		tsImpl.onHoldSeatBlocks.put("1:7:abc@gmail.com", blockOne);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(tsImpl.reserveSeats("1:7:abc@gmail.com", "abc@gmail.com"),"Booking expired or doesn't exist");

	}

	@Test
	public void testReserveSeatsIncorrectHoldId() {

		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis()+10000,HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		SeatHold blockOneHold=new SeatHold("abc@gmail.com",1,7,"1:7:abc@gmail.com");

		tsImpl.onHoldSeatBlocks.put("1:7:abc@gmail.com", blockOne);

		assertEquals(tsImpl.reserveSeats("wrongHoldId", "abc@gmail.com"),"Booking expired or doesn't exist");

	}

	@Test
	public void testPlaceHoldOnCurrentSeatBlock() {
		
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis()+10000,HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		SeatHold blockThreeHold=new SeatHold("abc@gmail.com",16,7,"16:7:abc@gmail.com");

		//tsImpl.onHoldSeatBlocks.put("1:7:abc@gmail.com", blockOne);
		
		SeatHold actualSeatHold = tsImpl.placeHoldOnCurrentSeatBlock(blockThree, 7, "abc@gmail.com", 5);

		assertEquals(actualSeatHold.getSeatHoldId(),blockThreeHold.getSeatHoldId());
		assertEquals(actualSeatHold.getStartSeatNumber(),blockThreeHold.getStartSeatNumber());

	}
	
	@Test
	public void testPlaceHoldOnCurrentSeatBlockWithBlockSplit() {
		
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis()+10000,HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis()+10000,blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis()+10000,blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;

		SeatHold blockThreeHold=new SeatHold("abc@gmail.com",16,3,"16:3:abc@gmail.com");

		//tsImpl.onHoldSeatBlocks.put("1:7:abc@gmail.com", blockOne);
		
		SeatHold actualSeatHold = tsImpl.placeHoldOnCurrentSeatBlock(blockThree, 3, "abc@gmail.com", 5);

		assertEquals(actualSeatHold.getSeatHoldId(),blockThreeHold.getSeatHoldId());
		assertEquals(actualSeatHold.getStartSeatNumber(),blockThreeHold.getStartSeatNumber());

	}
	
	@Test
	public void freeBlockAndMergeTestMergePrevNext() {
		
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"free",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis(),blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis(),blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;
		
		tsImpl.freeBlockAndMerge(blockTwo,5);
		
		assertEquals(22,HeadNode.getNext().getNumSeats());
		
	}
	
	@Test
	public void freeBlockAndMergeTestMergePrev() {
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"free",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis(),blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"hold",System.currentTimeMillis(),blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;
		
		tsImpl.freeBlockAndMerge(blockTwo,5);
		
		assertEquals(15,HeadNode.getNext().getNumSeats());
		
	}
	
	@Test
	public void freeBlockAndMergeTestMergeNext() {
		
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis(),blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis(),blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;
		
		tsImpl.freeBlockAndMerge(blockTwo,5);
		
		assertEquals(15,HeadNode.getNext().getNext().getNumSeats());
		
		
	}

	@Test
	public void freeBlockAndMergeWithNewBlockReturnedTestMergePrevNext() {
		
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"free",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis(),blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis(),blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;
		
		SeatBlock sb = tsImpl.freeBlockAndMergeWithNewBlockReturned(blockTwo,5);
		
		assertEquals(22,sb.getNumSeats());
		assertEquals(1,sb.getStartSeatNumber());
		
	}
	
	@Test
	public void freeBlockAndMergeWithNewBlockReturnedTestMergePrev() {
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"free",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis(),blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"hold",System.currentTimeMillis(),blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;
		
		SeatBlock sb = tsImpl.freeBlockAndMergeWithNewBlockReturned(blockTwo,5);
		
		assertEquals(15,sb.getNumSeats());
		assertEquals(1,sb.getStartSeatNumber());
		
	}
	
	@Test
	public void freeBlockAndMergeWithNewBlockReturnedTestMergeNext() {
		
		SeatBlock HeadNode = tsImpl.LinkedListHeadNode;

		SeatBlock blockOne = new SeatBlock(1,7,"hold",System.currentTimeMillis(),HeadNode,null);

		SeatBlock blockTwo = new SeatBlock(8,8,"hold",System.currentTimeMillis(),blockOne,null);

		SeatBlock blockThree = new SeatBlock(16,7,"free",System.currentTimeMillis(),blockTwo,null);

		SeatBlock blockFour = new SeatBlock(23,8,"hold",System.currentTimeMillis(),blockThree,null);

		HeadNode.setNext(blockOne);
		blockOne.setNext(blockTwo);
		blockTwo.setNext(blockThree);
		blockThree.setNext(blockFour);
		blockFour.setNext(null);

		tsImpl.LinkedListHeadNode=HeadNode;
		
		SeatBlock sb = tsImpl.freeBlockAndMergeWithNewBlockReturned(blockTwo,5);
		
		assertEquals(15,sb.getNumSeats());
		assertEquals(8,sb.getStartSeatNumber());
		
		
	}
	
	@Test
	public void testPrintSeatHoldIds() {
		
		SeatHold h1= tsImpl.findAndHoldSeats(4, "a1@gmail.com");
		
		SeatHold h2 = tsImpl.findAndHoldSeats(3, "a2@gmail.com");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tsImpl.printSeatHoldIds();

	}

}
