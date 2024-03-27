
/**
o Name: Zhanqian Wu (52463471)
o E-Mail: zhanqian@seas.upenn.edu

o Name: Yipeng Zhang
o E-Mail: yipenggg@seas.upenn.edu

 **/


import java.util.ArrayList;
import java.util.HashSet;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 */
public class MovieTrivia {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	
	public static void main(String[] args) {
		
		//create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();
		
		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
	}
	
	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		movieDB.setUp(movieData, movieRatings);
		
		//print all actors and movies
		this.printAllActors();
		this.printAllMovies();		
	}
	
	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}
	
	
	// TODO add additional methods as specified in the instructions PDF
	public void insertActor(String actor, String[] movies, ArrayList<Actor> actorsInfo) {
	    // Clean up actor name and movie titles to ensure consistency.
	    String cleanActorName = actor.trim().toLowerCase();
	    String[] cleanMovies = new String[movies.length];
	    for (int i = 0; i < movies.length; i++) {
	        cleanMovies[i] = movies[i].trim().toLowerCase();
	    }

	    // Initialize variable for potential actor match.
	    Actor matchedActor = null;

	    // Look through the actor list to find a match.
	    for (Actor listedActor : actorsInfo) {
	        if (listedActor.getName().equalsIgnoreCase(cleanActorName)) {
	            matchedActor = listedActor;
	            break;
	        }
	    }

	    // If no matching actor is found, create and add a new actor.
	    if (matchedActor == null) {
	        matchedActor = new Actor(cleanActorName);
	        for (String movie : cleanMovies) {
	            matchedActor.getMoviesCast().add(movie);
	        }
	        actorsInfo.add(matchedActor);
	    } else {
	        // For an existing actor, add any new movies not already listed.
	        for (String newMovie : cleanMovies) {
	            if (!matchedActor.getMoviesCast().contains(newMovie)) {
	                matchedActor.getMoviesCast().add(newMovie);
	            }
	        }
	    }
	}

	
	public void insertRating(String movie, int[] ratings, ArrayList<Movie> moviesInfo) {
	    // Validate the ratings array: must be non-null, have exactly 2 elements, and both elements must be between 0 and 100
	    if (ratings == null || ratings.length != 2 || ratings[0] < 0 || ratings[0] > 100 || ratings[1] < 0 || ratings[1] > 100) {
	        return; // Return immediately if the ratings array does not meet the criteria
	    }

	    Movie foundMovie = null;
	    // Normalize the movie name by trimming whitespace and converting to lowercase for case-insensitive comparison
	    movie = movie.trim().toLowerCase();

	    // Search for the movie in the moviesInfo list
	    for (Movie m : moviesInfo) {
	        if (m.getName().equalsIgnoreCase(movie)) {
	            foundMovie = m; // If the movie is found, store it in foundMovie and exit the loop
	            break;
	        }
	    }

	    if (foundMovie == null) {
	        // If the movie does not exist in the list, create a new Movie object with the provided name and ratings, and add it to the list
	        foundMovie = new Movie(movie, ratings[0], ratings[1]);
	        moviesInfo.add(foundMovie);
	    }

	    // Update the movie's ratings
	    foundMovie.setCriticRating(ratings[0]); // Set the critic rating
	    foundMovie.setAudienceRating(ratings[1]); // Set the audience rating
	}
	
	public ArrayList<String> selectWhereActorIs(String actor, ArrayList<Actor> actorsInfo) {
        ArrayList<String> moviesList = new ArrayList<>();
        
        // Normalize the actor name to handle case sensitivity and leading/trailing whitespace.
        actor = actor.trim().toLowerCase();

        // Search for the actor in the list.
        for (Actor a : actorsInfo) {
            if (a.getName().trim().toLowerCase().equals(actor)) {
                // If the actor is found, add all their movies to the list.
                moviesList.addAll(a.getMoviesCast());
                break; // Stop searching as we found the actor.
            }
        }

        // Return the list of movies. It will be empty if the actor was not found.
        return moviesList;
    }
	
	public ArrayList<String> selectWhereMovieIs(String movie, ArrayList<Actor> actorsInfo) {
        ArrayList<String> actorNames = new ArrayList<>();
        
        // Normalize the movie name to handle case sensitivity and leading/trailing whitespaces.
        movie = movie.trim().toLowerCase();

        // Search for the movie in each actor's list of movies.
        for (Actor actor : actorsInfo) {
            for (String actorMovie : actor.getMoviesCast()) {
                // Compare each movie with the given movie name after normalization.
                if (actorMovie.trim().toLowerCase().equals(movie)) {
                    // If the movie matches, add the actor's name to the list.
                    actorNames.add(actor.getName());
                    break; // Once found, no need to check other movies for this actor.
                }
            }
        }

        // Return the list of actors. It will be empty if the movie was not found in any actor's list.
        return actorNames;
    }
	
	public ArrayList<String> selectWhereRatingIs(char comparison, int targetRating, boolean isCritic, ArrayList<Movie> moviesInfo) {
	    ArrayList<String> selectedMovies = new ArrayList<>();

	    // Validate the input parameters for rating range and comparison operators
	    if (targetRating < 0 || targetRating > 100 || !(comparison == '=' || comparison == '>' || comparison == '<')) {
	        return selectedMovies; // If validation fails, return an empty list
	    }

	    // Iterate through the list of movies to apply the comparison filter
	    for (Movie movie : moviesInfo) {
	        // Select the appropriate rating type based on isCritic flag
	        int rating = isCritic ? movie.getCriticRating() : movie.getAudienceRating();

	        // Apply the comparison operation to filter movies by rating
	        switch (comparison) {
	            case '=': // Check for equality with the target rating
	                if (rating == targetRating) {
	                    selectedMovies.add(movie.getName());
	                }
	                break;
	            case '>': // Check for ratings greater than the target rating
	                if (rating > targetRating) {
	                    selectedMovies.add(movie.getName());
	                }
	                break;
	            case '<': // Check for ratings less than the target rating
	                if (rating < targetRating) {
	                    selectedMovies.add(movie.getName());
	                }
	                break;
	        }
	    }

	    return selectedMovies; // Return the list of movies that match the criteria
	}



	public ArrayList<String> getCoActors(String actor, ArrayList<Actor> actorsInfo) {
	    // Use a HashSet to store co-actors to automatically handle duplicates
	    HashSet<String> coActorsSet = new HashSet<>();
	    
	    // Normalize the input actor's name for case-insensitive comparison
	    actor = actor.trim().toLowerCase();

	    // List to store movies that the specified actor has participated in
	    ArrayList<String> sharedMovies = new ArrayList<>();

	    // Loop through actorsInfo to find the specified actor and collect their movies
	    for (Actor a : actorsInfo) {
	        if (a.getName().trim().equalsIgnoreCase(actor)) {
	            sharedMovies.addAll(a.getMoviesCast()); // Add all movies of the found actor
	            break;  // Exit the loop once the actor is found
	        }
	    }

	    // Proceed to find co-actors if the specified actor has movies
	    if (!sharedMovies.isEmpty()) {
	        // Iterate through actorsInfo to find actors who have appeared in the same movies
	        for (Actor a : actorsInfo) {
	            // Ensure not to include the specified actor in the list of co-actors
	            if (!a.getName().trim().equalsIgnoreCase(actor)) {
	                for (String movie : a.getMoviesCast()) {
	                    if (sharedMovies.contains(movie)) {
	                        coActorsSet.add(a.getName()); // Add the actor to co-actors set
	                        break;  // Break the movie loop once a shared movie is found
	                    }
	                }
	            }
	        }
	    }

	    // Convert HashSet to ArrayList before returning
	    return new ArrayList<>(coActorsSet);
	}

    
	// Find common movies between two actors
    public ArrayList<String> getCommonMovie(String actor1, String actor2, ArrayList<Actor> actorsInfo) {
        ArrayList<String> commonMovies = new ArrayList<>();
        // Retrieve movie lists for both actors
        ArrayList<String> moviesOfActor1 = selectWhereActorIs(actor1, actorsInfo);
        ArrayList<String> moviesOfActor2 = selectWhereActorIs(actor2, actorsInfo);

        // Determine the common movies
        for (String movie : moviesOfActor1) {
            if (moviesOfActor2.contains(movie)) {
                commonMovies.add(movie);
            }
        }
        return commonMovies;
    }

    // Returns a list of movie names where both critics and audiences have rated the movie 85 or above
    public ArrayList<String> goodMovies(ArrayList<Movie> moviesInfo) {
        ArrayList<String> goodMoviesList = new ArrayList<>();

        // Iterate through the list of movie information
        for (Movie movie : moviesInfo) {
            // Check if the movie's ratings meet the criteria
            if (movie.getCriticRating() >= 85 && movie.getAudienceRating() >= 85) {
                // If both critic and audience ratings are 85 or above, add to the result list
                goodMoviesList.add(movie.getName());
            }
        }

        // Return the list of movies that meet the criteria
        return goodMoviesList;
    }

    // Retrieves common actors between two movies
    public ArrayList<String> getCommonActors(String movie1, String movie2, ArrayList<Actor> actorsInfo) {
        // Retrieve actor lists for both movies
        ArrayList<String> actorsOfMovie1 = selectWhereMovieIs(movie1, actorsInfo);
        ArrayList<String> actorsOfMovie2 = selectWhereMovieIs(movie2, actorsInfo);

        // Create a new list to store common actors
        ArrayList<String> commonActors = new ArrayList<>();

        // Find common actors
        for (String actor : actorsOfMovie1) {
            if (actorsOfMovie2.contains(actor)) {
                commonActors.add(actor);
            }
        }

        return commonActors;
    }

    // Calculates the mean critic and audience ratings for a list of movies
    public static double[] getMean(ArrayList<Movie> moviesInfo) {
        // Initialize accumulators and a counter
        double totalCriticRatings = 0;
        double totalAudienceRatings = 0;
        int numberOfMovies = moviesInfo.size(); // Get the number of movies

        // Iterate through each movie, adding up the ratings
        for (Movie movie : moviesInfo) {
            totalCriticRatings += movie.getCriticRating();
            totalAudienceRatings += movie.getAudienceRating();
        }

        // Calculate the average ratings
        double meanCriticRating = numberOfMovies > 0 ? totalCriticRatings / numberOfMovies : 0;
        double meanAudienceRating = numberOfMovies > 0 ? totalAudienceRatings / numberOfMovies : 0;

        // Store the averages in an array and return
        return new double[]{meanCriticRating, meanAudienceRating};
    }
    
	    
}


	
	
	

	
	

