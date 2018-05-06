package theater2018;

public class SeatBlock {
	
	int startSeatNumber;
	int numSeats;
	String status;
	long blockStartTime;
	
	// the idea is to have seats as contiguous blocks where every block is connected to previous and next block
	SeatBlock previous;
	SeatBlock next;
	
	public SeatBlock(int startSeatNumber, int numSeats, String status, long blockStartTime, SeatBlock previous,
			SeatBlock next) {
		super();
		this.startSeatNumber = startSeatNumber;
		this.numSeats = numSeats;
		this.status = status;
		this.blockStartTime = blockStartTime;
		this.previous = previous;
		this.next = next;
	}
	
	

	public int getStartSeatNumber() {
		return startSeatNumber;
	}



	public void setStartSeatNumber(int startSeatNumber) {
		this.startSeatNumber = startSeatNumber;
	}



	public int getNumSeats() {
		return numSeats;
	}



	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SeatBlock getPrevious() {
		return previous;
	}

	public void setPrevious(SeatBlock previous) {
		this.previous = previous;
	}

	public SeatBlock getNext() {
		return next;
	}

	public void setNext(SeatBlock next) {
		this.next = next;
	}

	public long getBlockStartTime() {
		return blockStartTime;
	}

	public void setBlockStartTime(long blockStartTime) {
		this.blockStartTime = blockStartTime;
	}
	
	
	


}
