package theater2018;

public interface TicketService {
	
	int numSeatsAvailable();

	SeatHold findAndHoldSeats(int numSeats, String customerEmail); 

	String reserveSeats(String seatHoldId, String customerEmail); 


}
