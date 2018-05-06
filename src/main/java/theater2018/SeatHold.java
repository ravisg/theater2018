package theater2018;

public class SeatHold {
	
	String customerEmail;
	String startSeatNumber;
	int numSeats;
	String seatHoldId;
	//SeatBlock seatBlock;
	
	public SeatHold(String customerEmail, String startSeatNumber, int numSeats, String seatHoldId,SeatBlock seatBlock) {
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


	public String getStartSeatNumber() {
		return startSeatNumber;
	}


	public void setStartSeatNumber(String startSeatNumber) {
		this.startSeatNumber = startSeatNumber;
	}

	
	
}
