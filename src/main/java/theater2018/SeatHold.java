package theater2018;

public class SeatHold {
	
	String customerEmail;
	int startSeatNumber;
	int numSeats;
	String seatHoldId;
	SeatBlock seatBlock;
	
	public SeatHold(String customerEmail, int startSeatNumber, int numSeats, String seatHoldId) {
		super();
		this.customerEmail = customerEmail;
		this.startSeatNumber = startSeatNumber;
		this.numSeats = numSeats;
		this.seatHoldId = seatHoldId;
		//this.seatBlock = seatBlock;
	}


	public String getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(String seatHoldId) {
		this.seatHoldId = seatHoldId;
	}


	public int getStartSeatNumber() {
		return startSeatNumber;
	}


	public void setStartSeatNumber(int startSeatNumber) {
		this.startSeatNumber = startSeatNumber;
	}

	
	
}
