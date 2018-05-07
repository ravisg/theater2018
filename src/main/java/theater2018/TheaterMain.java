package theater2018;

public class TheaterMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TicketServiceImplementation ts = new TicketServiceImplementation (40,3);
		System.out.println("total seats "+ts.numSeatsAvailable());

		SeatHold h1= ts.findAndHoldSeats(4, "a1@gmail.com");
		
		SeatHold h2 = ts.findAndHoldSeats(3, "a2@gmail.com");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ts.printSeatHoldIds();
		
		System.out.println("total seats "+ts.numSeatsAvailable());
		
		SeatHold h3 = ts.findAndHoldSeats(7, "a3@gmail.com");
		
		SeatHold h4 = ts.findAndHoldSeats(2, "a4@gmail.com");
		
		System.out.println("total seats "+ts.numSeatsAvailable());
		
		SeatHold h5 = ts.findAndHoldSeats(8, "a5@gmail.com");
		
		ts.printSeatHoldIds();
		
		String reserve1 = ts.reserveSeats(h3.getSeatHoldId(), "a5@gmail.com");
		System.out.println("reserve1 id "+reserve1);
		//1:4:a1@gmail.com
		String reserve2 = ts.reserveSeats(h5.getSeatHoldId(), "1:4:a1@gmail.com");
		System.out.println("reserve1 id "+reserve2);
		
		System.out.println("total seats "+ts.numSeatsAvailable());
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("total seats "+ts.numSeatsAvailable());
		
		SeatHold h6= ts.findAndHoldSeats(400, "a1@gmail.com");
		
		SeatHold h7 = ts.findAndHoldSeats(-3, "a2@gmail.com");
		
		ts.printSeatHoldIds();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("total seats "+ts.numSeatsAvailable());
		
		SeatHold h8 = ts.findAndHoldSeats(6, "a8@gmail.com");
		
		SeatHold h9 = ts.findAndHoldSeats(6, "a9@gmail.com");
		
		SeatHold h10 = ts.findAndHoldSeats(1, "a10@gmail.com");
		
		SeatHold h11 = ts.findAndHoldSeats(10, "a11@gmail.com");
		
		String reserveh9 = ts.reserveSeats(h9.getSeatHoldId(), h9.customerEmail);
		System.out.println("reserveh9 id "+reserveh9);
		String reserveh10 = ts.reserveSeats(h10.getSeatHoldId(), h10.customerEmail);
		System.out.println("reserveh10 id "+reserveh10);
		ts.printSeatHoldIds();
		
		System.out.println("total seats "+ts.numSeatsAvailable());
		
		SeatHold h12 = ts.findAndHoldSeats(6, "a12@gmail.com");
		//String reserveh12 = ts.reserveSeats(h12.getSeatHoldId(), h12.customerEmail, 0);
		if(h12==null)
		System.out.println("h12 booking was unsuccessful ");
		
		SeatHold h13 = ts.findAndHoldSeats(2, "a13@gmail.com");
		String reserveh13 = ts.reserveSeats(h13.getSeatHoldId(), h13.customerEmail);
		System.out.println("reserveh13 id "+reserveh13);
		
		System.out.println("Main ends ");
	}
	
		
		

}
