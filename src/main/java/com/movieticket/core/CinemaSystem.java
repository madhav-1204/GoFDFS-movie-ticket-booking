package com.movieticket.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Central system managing all cinema operations
 * This acts as an in-memory database for the application
 */
public class CinemaSystem {
    private static CinemaSystem instance;
    
    private Map<Integer, Movie> movies;
    private Map<Integer, Theater> theaters;
    private Map<Integer, Showtime> showtimes;
    private Map<Integer, User> users;
    private Map<String, User> usersByUsername;
    private Map<Integer, Booking> bookings;
    private Map<Integer, List<Booking>> bookingsByUser;
    
    private AtomicInteger movieIdCounter;
    private AtomicInteger theaterIdCounter;
    private AtomicInteger showtimeIdCounter;
    private AtomicInteger userIdCounter;
    private AtomicInteger bookingIdCounter;
    
    private CinemaSystem() {
        movies = new ConcurrentHashMap<>();
        theaters = new ConcurrentHashMap<>();
        showtimes = new ConcurrentHashMap<>();
        users = new ConcurrentHashMap<>();
        usersByUsername = new ConcurrentHashMap<>();
        bookings = new ConcurrentHashMap<>();
        bookingsByUser = new ConcurrentHashMap<>();
        
        movieIdCounter = new AtomicInteger(1);
        theaterIdCounter = new AtomicInteger(1);
        showtimeIdCounter = new AtomicInteger(1);
        userIdCounter = new AtomicInteger(1);
        bookingIdCounter = new AtomicInteger(1);
        
        initializeSampleData();
    }
    
    public static synchronized CinemaSystem getInstance() {
        if (instance == null) {
            instance = new CinemaSystem();
        }
        return instance;
    }
    
    private void initializeSampleData() {
        // Fixed date for all showtimes
        LocalDate showDate = LocalDate.of(2023, 11, 15);
        
        // --- Theaters ---
        // Single Screens
        Theater sandhya70mm = new Theater(theaterIdCounter.getAndIncrement(), "Sandhya 70MM", 150, 15);
        Theater sudarshan35mm = new Theater(theaterIdCounter.getAndIncrement(), "Sudarshan 35MM", 96, 12);
        Theater devi70mm = new Theater(theaterIdCounter.getAndIncrement(), "Devi 70MM", 140, 14);
        Theater shanti70mm = new Theater(theaterIdCounter.getAndIncrement(), "Shanti 70MM", 117, 13);
        Theater sapna70mm = new Theater(theaterIdCounter.getAndIncrement(), "Sapna 70MM", 88, 11);
        Theater sandhya35mm = new Theater(theaterIdCounter.getAndIncrement(), "Sandhya 35MM", 70, 10);
        
        theaters.put(sandhya70mm.getId(), sandhya70mm);
        theaters.put(sudarshan35mm.getId(), sudarshan35mm);
        theaters.put(devi70mm.getId(), devi70mm);
        theaters.put(shanti70mm.getId(), shanti70mm);
        theaters.put(sapna70mm.getId(), sapna70mm);
        theaters.put(sandhya35mm.getId(), sandhya35mm);
        
        // Multiplexes
        Theater prasadsImax = new Theater(theaterIdCounter.getAndIncrement(), "Prasads IMAX", 216, 18);
        Theater pvrIcon = new Theater(theaterIdCounter.getAndIncrement(), "PVR ICON (Inorbit Mall)", 70, 10);
        Theater pvrGvkOne = new Theater(theaterIdCounter.getAndIncrement(), "PVR GVK One", 88, 11);
        Theater ambCinemas = new Theater(theaterIdCounter.getAndIncrement(), "AMB Cinemas (Gachibowli)", 108, 12);
        
        theaters.put(prasadsImax.getId(), prasadsImax);
        theaters.put(pvrIcon.getId(), pvrIcon);
        theaters.put(pvrGvkOne.getId(), pvrGvkOne);
        theaters.put(ambCinemas.getId(), ambCinemas);
        
        // --- Movies ---
        // Telugu Movies (Single Screen)
        Movie businessman = new Movie(movieIdCounter.getAndIncrement(), "Businessman", "Action", 130, "U/A",
            "A young entrepreneur takes on the mafia in his own way.", "https://via.placeholder.com/300x450?text=Businessman");
        Movie khaleja = new Movie(movieIdCounter.getAndIncrement(), "Khaleja", "Action-Comedy", 150, "U/A",
            "A taxi driver becomes an unwitting hero in a village plagued by evil.", "https://via.placeholder.com/300x450?text=Khaleja");
        Movie athadu = new Movie(movieIdCounter.getAndIncrement(), "Athadu", "Action-Thriller", 170, "U/A",
            "A professional assassin finds himself framed for a political murder.", "https://via.placeholder.com/300x450?text=Athadu");
        Movie pokiri = new Movie(movieIdCounter.getAndIncrement(), "Pokiri", "Action", 160, "U/A",
            "An undercover cop infiltrates a criminal gang.", "https://via.placeholder.com/300x450?text=Pokiri");
        Movie rrr = new Movie(movieIdCounter.getAndIncrement(), "RRR", "Epic Action", 187, "U/A",
            "A fictional tale of two revolutionary leaders and their journey away from home.", "https://via.placeholder.com/300x450?text=RRR");
        Movie baahubali2 = new Movie(movieIdCounter.getAndIncrement(), "Baahubali: The Conclusion", "Epic Action", 167, "U/A",
            "Amarendra Baahubali fights against the tyrannical rule of the king.", "https://via.placeholder.com/300x450?text=Baahubali+2");
        Movie bharatAneNenu = new Movie(movieIdCounter.getAndIncrement(), "Bharat Ane Nenu", "Political Drama", 173, "U/A",
            "A young chief minister fights against corruption in the government.", "https://via.placeholder.com/300x450?text=Bharat+Ane+Nenu");
        Movie dookudu = new Movie(movieIdCounter.getAndIncrement(), "Dookudu", "Action-Comedy", 165, "U/A",
            "A cop goes undercover to take down a dangerous criminal.", "https://via.placeholder.com/300x450?text=Dookudu");
        Movie maharshi = new Movie(movieIdCounter.getAndIncrement(), "Maharshi", "Social Drama", 176, "U/A",
            "A successful businessman returns to his roots to help farmers.", "https://via.placeholder.com/300x450?text=Maharshi");
        Movie svsc = new Movie(movieIdCounter.getAndIncrement(), "Seethamma Vakitlo Sirimalle Chettu", "Family Drama", 159, "U",
            "A heartwarming story about family bonds and relationships.", "https://via.placeholder.com/300x450?text=SVSC");
        Movie baahubali1 = new Movie(movieIdCounter.getAndIncrement(), "Baahubali: The Beginning", "Epic Action", 159, "U/A",
            "The story of a warrior's quest to discover his true identity.", "https://via.placeholder.com/300x450?text=Baahubali");
        Movie brahmotsavam = new Movie(movieIdCounter.getAndIncrement(), "Brahmotsavam", "Family Drama", 156, "U",
            "A celebration of family values and traditions.", "https://via.placeholder.com/300x450?text=Brahmotsavam");
        
        movies.put(businessman.getId(), businessman);
        movies.put(khaleja.getId(), khaleja);
        movies.put(athadu.getId(), athadu);
        movies.put(pokiri.getId(), pokiri);
        movies.put(rrr.getId(), rrr);
        movies.put(baahubali2.getId(), baahubali2);
        movies.put(bharatAneNenu.getId(), bharatAneNenu);
        movies.put(dookudu.getId(), dookudu);
        movies.put(maharshi.getId(), maharshi);
        movies.put(svsc.getId(), svsc);
        movies.put(baahubali1.getId(), baahubali1);
        movies.put(brahmotsavam.getId(), brahmotsavam);
        
        // Hollywood Movies (Multiplexes)
        Movie oppenheimer = new Movie(movieIdCounter.getAndIncrement(), "Oppenheimer", "Biographical Drama", 180, "A",
            "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.", "https://via.placeholder.com/300x450?text=Oppenheimer");
        Movie avengersEndgame = new Movie(movieIdCounter.getAndIncrement(), "Avengers: Endgame", "Superhero", 181, "U/A",
            "The Avengers assemble once more to reverse Thanos' actions and restore balance.", "https://via.placeholder.com/300x450?text=Avengers+Endgame");
        Movie interstellar = new Movie(movieIdCounter.getAndIncrement(), "Interstellar", "Sci-Fi", 169, "U/A",
            "A team of explorers travel through a wormhole in space to ensure humanity's survival.", "https://via.placeholder.com/300x450?text=Interstellar");
        Movie spidermanNwh = new Movie(movieIdCounter.getAndIncrement(), "Spider-Man: No Way Home", "Superhero", 148, "U/A",
            "Spider-Man's identity is revealed and he asks Doctor Strange for help.", "https://via.placeholder.com/300x450?text=Spider-Man");
        Movie barbie = new Movie(movieIdCounter.getAndIncrement(), "Barbie", "Comedy", 114, "U/A",
            "Barbie and Ken are having the time of their lives in Barbie Land.", "https://via.placeholder.com/300x450?text=Barbie");
        Movie topGunMaverick = new Movie(movieIdCounter.getAndIncrement(), "Top Gun: Maverick", "Action", 130, "U/A",
            "Maverick trains a new generation of Top Gun graduates.", "https://via.placeholder.com/300x450?text=Top+Gun");
        Movie theBatman = new Movie(movieIdCounter.getAndIncrement(), "The Batman", "Superhero", 176, "U/A",
            "Batman ventures into Gotham City's underworld when a sadistic killer leaves behind cryptic clues.", "https://via.placeholder.com/300x450?text=The+Batman");
        Movie joker = new Movie(movieIdCounter.getAndIncrement(), "Joker", "Thriller", 122, "A",
            "A failed comedian descends into madness and becomes the Joker.", "https://via.placeholder.com/300x450?text=Joker");
        Movie miDeadReckoning = new Movie(movieIdCounter.getAndIncrement(), "Mission Impossible: Dead Reckoning", "Action-Spy", 163, "U/A",
            "Ethan Hunt and his IMF team must track down a terrifying new weapon.", "https://via.placeholder.com/300x450?text=MI");
        Movie avengersInfinityWar = new Movie(movieIdCounter.getAndIncrement(), "Avengers: Infinity War", "Superhero", 149, "U/A",
            "The Avengers must stop Thanos from collecting all the Infinity Stones.", "https://via.placeholder.com/300x450?text=Infinity+War");
        Movie inception = new Movie(movieIdCounter.getAndIncrement(), "Inception", "Sci-Fi", 148, "U/A",
            "A thief who steals corporate secrets through dream-sharing technology.", "https://via.placeholder.com/300x450?text=Inception");
        Movie theDarkKnight = new Movie(movieIdCounter.getAndIncrement(), "The Dark Knight", "Superhero", 152, "U/A",
            "Batman faces the Joker, a criminal mastermind who wants to plunge Gotham into chaos.", "https://via.placeholder.com/300x450?text=Dark+Knight");
        
        movies.put(oppenheimer.getId(), oppenheimer);
        movies.put(avengersEndgame.getId(), avengersEndgame);
        movies.put(interstellar.getId(), interstellar);
        movies.put(spidermanNwh.getId(), spidermanNwh);
        movies.put(barbie.getId(), barbie);
        movies.put(topGunMaverick.getId(), topGunMaverick);
        movies.put(theBatman.getId(), theBatman);
        movies.put(joker.getId(), joker);
        movies.put(miDeadReckoning.getId(), miDeadReckoning);
        movies.put(avengersInfinityWar.getId(), avengersInfinityWar);
        movies.put(inception.getId(), inception);
        movies.put(theDarkKnight.getId(), theDarkKnight);
        
        // --- Showtimes ---
        // Sandhya 70MM - Businessman & Khaleja
        addShowtime(businessman, sandhya70mm, showDate, 11, 0);
        addShowtime(businessman, sandhya70mm, showDate, 14, 30);
        addShowtime(businessman, sandhya70mm, showDate, 18, 30);
        addShowtime(businessman, sandhya70mm, showDate, 22, 0);
        addShowtime(khaleja, sandhya70mm, showDate, 10, 0);
        addShowtime(khaleja, sandhya70mm, showDate, 13, 30);
        addShowtime(khaleja, sandhya70mm, showDate, 17, 30);
        addShowtime(khaleja, sandhya70mm, showDate, 21, 30);
        
        // Sudarshan 35MM - Athadu & Pokiri
        addShowtime(athadu, sudarshan35mm, showDate, 10, 0);
        addShowtime(athadu, sudarshan35mm, showDate, 13, 0);
        addShowtime(athadu, sudarshan35mm, showDate, 16, 0);
        addShowtime(athadu, sudarshan35mm, showDate, 19, 0);
        addShowtime(pokiri, sudarshan35mm, showDate, 11, 0);
        addShowtime(pokiri, sudarshan35mm, showDate, 14, 0);
        addShowtime(pokiri, sudarshan35mm, showDate, 18, 0);
        addShowtime(pokiri, sudarshan35mm, showDate, 21, 30);
        
        // Devi 70MM - RRR & Baahubali 2
        addShowtime(rrr, devi70mm, showDate, 9, 0);
        addShowtime(rrr, devi70mm, showDate, 13, 0);
        addShowtime(rrr, devi70mm, showDate, 17, 0);
        addShowtime(rrr, devi70mm, showDate, 21, 0);
        addShowtime(baahubali2, devi70mm, showDate, 10, 30);
        addShowtime(baahubali2, devi70mm, showDate, 14, 30);
        addShowtime(baahubali2, devi70mm, showDate, 18, 30);
        addShowtime(baahubali2, devi70mm, showDate, 22, 30);
        
        // Shanti 70MM - Bharat Ane Nenu & Dookudu
        addShowtime(bharatAneNenu, shanti70mm, showDate, 10, 0);
        addShowtime(bharatAneNenu, shanti70mm, showDate, 14, 0);
        addShowtime(bharatAneNenu, shanti70mm, showDate, 18, 0);
        addShowtime(bharatAneNenu, shanti70mm, showDate, 21, 30);
        addShowtime(dookudu, shanti70mm, showDate, 11, 0);
        addShowtime(dookudu, shanti70mm, showDate, 15, 0);
        addShowtime(dookudu, shanti70mm, showDate, 19, 0);
        addShowtime(dookudu, shanti70mm, showDate, 22, 30);
        
        // Sapna 70MM - Maharshi & SVSC
        addShowtime(maharshi, sapna70mm, showDate, 10, 0);
        addShowtime(maharshi, sapna70mm, showDate, 13, 30);
        addShowtime(maharshi, sapna70mm, showDate, 17, 30);
        addShowtime(maharshi, sapna70mm, showDate, 21, 30);
        addShowtime(svsc, sapna70mm, showDate, 9, 30);
        addShowtime(svsc, sapna70mm, showDate, 13, 0);
        addShowtime(svsc, sapna70mm, showDate, 16, 30);
        addShowtime(svsc, sapna70mm, showDate, 20, 0);
        
        // Sandhya 35MM - Baahubali 1 & Brahmotsavam
        addShowtime(baahubali1, sandhya35mm, showDate, 10, 0);
        addShowtime(baahubali1, sandhya35mm, showDate, 13, 30);
        addShowtime(baahubali1, sandhya35mm, showDate, 17, 30);
        addShowtime(baahubali1, sandhya35mm, showDate, 21, 0);
        addShowtime(brahmotsavam, sandhya35mm, showDate, 4, 0); // Benefit Show
        addShowtime(brahmotsavam, sandhya35mm, showDate, 11, 0);
        addShowtime(brahmotsavam, sandhya35mm, showDate, 14, 30);
        addShowtime(brahmotsavam, sandhya35mm, showDate, 18, 30);
        addShowtime(brahmotsavam, sandhya35mm, showDate, 22, 0);
        
        // Prasads IMAX - Oppenheimer, Avengers Endgame, Interstellar
        addShowtime(oppenheimer, prasadsImax, showDate, 9, 0);
        addShowtime(oppenheimer, prasadsImax, showDate, 13, 0);
        addShowtime(oppenheimer, prasadsImax, showDate, 17, 0);
        addShowtime(oppenheimer, prasadsImax, showDate, 21, 0);
        addShowtime(avengersEndgame, prasadsImax, showDate, 10, 0);
        addShowtime(avengersEndgame, prasadsImax, showDate, 14, 30);
        addShowtime(avengersEndgame, prasadsImax, showDate, 18, 30);
        addShowtime(avengersEndgame, prasadsImax, showDate, 22, 30);
        addShowtime(interstellar, prasadsImax, showDate, 11, 0);
        addShowtime(interstellar, prasadsImax, showDate, 15, 0);
        addShowtime(interstellar, prasadsImax, showDate, 19, 0);
        
        // PVR ICON - Spider-Man, Barbie, Top Gun
        addShowtime(spidermanNwh, pvrIcon, showDate, 9, 30);
        addShowtime(spidermanNwh, pvrIcon, showDate, 13, 0);
        addShowtime(spidermanNwh, pvrIcon, showDate, 16, 30);
        addShowtime(spidermanNwh, pvrIcon, showDate, 20, 0);
        addShowtime(barbie, pvrIcon, showDate, 10, 0);
        addShowtime(barbie, pvrIcon, showDate, 13, 30);
        addShowtime(barbie, pvrIcon, showDate, 17, 30);
        addShowtime(barbie, pvrIcon, showDate, 21, 30);
        addShowtime(topGunMaverick, pvrIcon, showDate, 11, 0);
        addShowtime(topGunMaverick, pvrIcon, showDate, 14, 30);
        addShowtime(topGunMaverick, pvrIcon, showDate, 18, 30);
        addShowtime(topGunMaverick, pvrIcon, showDate, 22, 30);
        
        // PVR GVK One - The Batman, Joker, Mission Impossible
        addShowtime(theBatman, pvrGvkOne, showDate, 10, 0);
        addShowtime(theBatman, pvrGvkOne, showDate, 13, 30);
        addShowtime(theBatman, pvrGvkOne, showDate, 17, 30);
        addShowtime(theBatman, pvrGvkOne, showDate, 21, 30);
        addShowtime(joker, pvrGvkOne, showDate, 11, 0);
        addShowtime(joker, pvrGvkOne, showDate, 15, 0);
        addShowtime(joker, pvrGvkOne, showDate, 19, 0);
        addShowtime(joker, pvrGvkOne, showDate, 22, 30);
        addShowtime(miDeadReckoning, pvrGvkOne, showDate, 9, 0);
        addShowtime(miDeadReckoning, pvrGvkOne, showDate, 12, 30);
        addShowtime(miDeadReckoning, pvrGvkOne, showDate, 16, 0);
        addShowtime(miDeadReckoning, pvrGvkOne, showDate, 19, 30);
        addShowtime(miDeadReckoning, pvrGvkOne, showDate, 22, 30);
        
        // AMB Cinemas - Avengers Infinity War, Inception, The Dark Knight
        addShowtime(avengersInfinityWar, ambCinemas, showDate, 9, 0);
        addShowtime(avengersInfinityWar, ambCinemas, showDate, 13, 0);
        addShowtime(avengersInfinityWar, ambCinemas, showDate, 17, 0);
        addShowtime(avengersInfinityWar, ambCinemas, showDate, 21, 0);
        addShowtime(inception, ambCinemas, showDate, 10, 30);
        addShowtime(inception, ambCinemas, showDate, 14, 30);
        addShowtime(inception, ambCinemas, showDate, 18, 30);
        addShowtime(inception, ambCinemas, showDate, 22, 30);
        addShowtime(theDarkKnight, ambCinemas, showDate, 11, 0);
        addShowtime(theDarkKnight, ambCinemas, showDate, 15, 0);
        addShowtime(theDarkKnight, ambCinemas, showDate, 19, 0);
        
        // Create a demo user
        User demoUser = new User(userIdCounter.getAndIncrement(), "demo", "demo123", 
            "demo@movieticket.com", "Demo User", "1234567890");
        users.put(demoUser.getId(), demoUser);
        usersByUsername.put(demoUser.getUsername(), demoUser);
    }
    
    // Helper method to add showtimes
    private void addShowtime(Movie movie, Theater theater, LocalDate date, int hour, int minute) {
        LocalDateTime showtime = LocalDateTime.of(date, LocalTime.of(hour, minute));
        Showtime st = new Showtime(showtimeIdCounter.getAndIncrement(), movie, theater, showtime);
        showtimes.put(st.getId(), st);
    }
    
    // Movie operations
    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }
    
    public Movie getMovieById(int id) {
        return movies.get(id);
    }
    
    // Theater operations
    public List<Theater> getAllTheaters() {
        return new ArrayList<>(theaters.values());
    }
    
    public Theater getTheaterById(int id) {
        return theaters.get(id);
    }
    
    // Showtime operations
    public List<Showtime> getAllShowtimes() {
        return new ArrayList<>(showtimes.values());
    }
    
    public List<Showtime> getShowtimesByMovie(int movieId) {
        return showtimes.values().stream()
            .filter(st -> st.getMovie().getId() == movieId)
            .collect(Collectors.toList());
    }
    
    public Showtime getShowtimeById(int id) {
        return showtimes.get(id);
    }
    
    // User operations
    public User registerUser(String username, String password, String email, String fullName, String phone) {
        if (usersByUsername.containsKey(username)) {
            return null; // Username already exists
        }
        
        User user = new User(userIdCounter.getAndIncrement(), username, password, email, fullName, phone);
        users.put(user.getId(), user);
        usersByUsername.put(username, user);
        return user;
    }
    
    public User authenticateUser(String username, String password) {
        User user = usersByUsername.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public User getUserById(int id) {
        return users.get(id);
    }
    
    // Booking operations
    public Booking createBooking(User user, Showtime showtime, List<String> seatNumbers) {
        List<Seat> selectedSeats = new ArrayList<>();
        
        // Find and validate seats
        for (String seatNumber : seatNumbers) {
            Seat seat = showtime.getSeats().stream()
                .filter(s -> s.getSeatNumber().equals(seatNumber) && !s.isBooked())
                .findFirst()
                .orElse(null);
                
            if (seat == null) {
                return null; // Seat not available
            }
            selectedSeats.add(seat);
        }
        
        // Mark seats as booked
        for (Seat seat : selectedSeats) {
            seat.setBooked(true);
        }
        
        // Create booking
        Booking booking = new Booking(bookingIdCounter.getAndIncrement(), user, showtime, selectedSeats);
        bookings.put(booking.getId(), booking);
        
        // Add to user's bookings
        bookingsByUser.computeIfAbsent(user.getId(), k -> new ArrayList<>()).add(booking);
        
        return booking;
    }
    
    public List<Booking> getUserBookings(int userId) {
        return bookingsByUser.getOrDefault(userId, new ArrayList<>());
    }
    
    public Booking getBookingById(int id) {
        return bookings.get(id);
    }
    
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }
}
