package com.example.cinemaapp.repository;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.cinemaapp.R;
import com.example.cinemaapp.model.Film;
import com.example.cinemaapp.model.Reservation;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Repository {
    static List<Film> filmList = new ArrayList<>();
    static List<Reservation> reservationList = new ArrayList<>();
    public static List<Film> favoriteList = new ArrayList<>();
    private static HashMap<String, HashMap<Time, List<Boolean>>> program;
    static List<Boolean> cinemaPlaces;

    public static List<Film> getHardcodedList() {
        List<Film> filmList = Arrays.asList(
                new Film("The Donkey King", "Animation", "Khan the Lion King decides to retire and his son stands to inherit the throne, but the animals of Azad want a democracy. Khan reluctantly agrees to hold an election.", 7.7, R.drawable.the_donkey_king),
                new Film("Jinnah", "Drama", "Jinnah waits as the celestial bureaucrats are deciding his final judgment. Meanwhile, his heavenly guide questions him about his decisions on Earth, including creating a new country for the Muslims.", 8.0, R.drawable.jinnah),
                new Film("Parvaz hai Junoon", "Adventure", "Sania decides to join the Pakistan Air Force so that she can become a fighter pilot and serve her country. However, she faces a tough time with her training after her lover dies during a mission.", 8.0, R.drawable.parvaaz),
                new Film("The Legend of Maula Jatt", "Action Drama", "The Legend of Maula Jatt is a 2022 Pakistani Punjabi-language action drama film directed and written by Bilal Lashari. The film is an adaptation and a soft reboot of the 1979 Lollywood film Maula Jatt. It is produced by Ammara Hikmat and Asad Jamil Khan under the production banner of Lashari Films and Encyclomedia.", 8.3, R.drawable.thelegendofmaulajatt),
                new Film("Maalik", "Political Thriller", "After experiencing personal tragedy, a special forces commando is hired to protect a corrupt feudal lord turned chief minister.", 7.9, R.drawable.maalik),
                new Film("Teefa in Trouble", "Romantic Action Comedy", "A gangster hires Teefa to kidnap a girl from Poland so that his son can marry her. However, Teefa starts to fall in love with the girl he is supposed to kidnap.", 6.9, R.drawable.teefaintrouble),
                new Film("Parchi", "Crime Comedy", "Parchi is a 2018 Pakistani Urdu-language crime comedy film directed by Azfar Jafri and produced by Imran Kazmi and Arif Lakhani under the banner IRK Films. The film stars Hareem Farooq, Ali Rehman Khan and Shafqat Cheema, with Ahmed Ali Akbar, Faiza Saleem, Shafqat Khan and Usman Mukhtar in supporting roles.", 5.3, R.drawable.parchi),
                new Film("Karachi se Lahore", "Road Comedy Adventure", "Zaheem takes a road trip with his friends to explore themselves and to stop at his longtime girlfriend's wedding to ask her out as his future wife.", 6.3, R.drawable.karachiselahore),
                new Film("Wrong No.", "Romantic Comedy", "Fed up with his father's insistence to get a job, Sallu switches places with Shehreyar, his lookalike and the grandson of a rich businessman. However, this puts Sallu in grave danger.", 6.4, R.drawable.wrongno),
                new Film("Yeh Jawani Phir Nahi Ani", "Adventure Comedy", "A divorce lawyer returns to his hometown and discovers that his childhood friends are leading miserable married lives. He decides to fix this by taking them on an exotic trip to Bangkok.", 7.3, R.drawable.jawaniphirnahiani),
                new Film("Superstar", "Romantic Musical Drama", "Noori, a struggling theatre artist, finds love in Sameer, an aspiring actor. However, when stardom comes knocking at their door, the differences between them puts their relationship to the test.", 5.7, R.drawable.superstar)
        );
        return filmList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void addReservation(Reservation reservation) {

        if (!searchReservation(reservation)) {

            reservationList.add(reservation);
            markReservedPlaces(reservation.getPlaces());
        }
    }

    private static void markReservedPlaces(List<Integer> places) {
        for (int i : places) {
            cinemaPlaces.set(i, false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean searchReservation(Reservation reservation) {

        for (Reservation r : reservationList) {

            if (r.equals(reservation))
                return true;
        }
        return false;
    }

    public static void addToFavorites(Film film) {

        if (!searchInFavorites(film)) {

            favoriteList.add(film);
        }

    }

    public static boolean searchInFavorites(Film film) {

        for (Film f : favoriteList) {

            if (f.equals(film))
                return true;
        }
        return false;
    }

    public static void deleteFromFavorites(Film film) {

        for (Film f : favoriteList) {

            if (f.equals(film)) {
                favoriteList.remove(f);
                return;
            }
        }
    }

    public static HashMap<String, HashMap<Time, List<Boolean>>> getHardcodedProgram() {
        if (program == null) {
            program = new HashMap<>();
            List<Film> films = getHardcodedList();
            List<List<Time>> posibilities = Arrays.asList(
                    Arrays.asList(new Time(12, 0, 0), new Time(17, 0, 0), new Time(20, 30, 0)),
                    Arrays.asList(new Time(10, 0, 0), new Time(13, 30, 0), new Time(17, 0, 0)),
                    Arrays.asList(new Time(11, 0, 0), new Time(14, 30, 0), new Time(18, 0, 0)),
                    Arrays.asList(new Time(12, 30, 0), new Time(16, 0, 0), new Time(19, 30, 0)),
                    Arrays.asList(new Time(10, 0, 0), new Time(13, 30, 0), new Time(17, 0, 0))

            );
            int i = 0;
            for (Film f : films) {
                List<Time> thisFilmTimes = posibilities.get(i % 5);
                i++;
                HashMap<Time, List<Boolean>> thisFilmProgram = new HashMap<>();
                for (Time t : thisFilmTimes) {
                    thisFilmProgram.put(t, Arrays.asList(true, true, true, true, true, true, true, true, true));
                }
                program.put(f.getTitle(), thisFilmProgram);
            }
        }
        return program;
    }

    /**
     * Get cinemaPlaces for selected film at selected startTime
     * @param filmTitle
     * @param time
     * @return
     */
    public static List<Boolean> getCinemaPlaces(String filmTitle, String time) {
        //transform time from string to Time obj
        Time timeObj = null;
        DateFormat formatter = new SimpleDateFormat("kk:mm");
        try {
            timeObj = new Time(formatter.parse(time).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cinemaPlaces = getHardcodedProgram().get(filmTitle).get(timeObj);
        return cinemaPlaces;
    }

    public static List<Reservation> getReservationList() {
        return reservationList;
    }
}

