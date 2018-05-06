package theater2018;

public interface TicketService {
	
	int numSeatsAvailable(int timeOfAction);

	SeatHold findAndHoldSeats(int numSeats, String customerEmail, int timeOfAction); 

	String reserveSeats(int seatHoldId, String customerEmail, int timeOfAction); 


}
