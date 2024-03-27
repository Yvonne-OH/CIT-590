
/**
o Name: Zhanqian Wu (52463471)
o E-Mail: zhanqian@seas.upenn.edu

o Name: Yipeng Zhang
o E-Mail: yipenggg@seas.upenn.edu

 **/


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;

class MovieTriviaTest {

	// instance of movie trivia object to test
	MovieTrivia mt;
	// instance of movieDB object
	MovieDB movieDB;

	@BeforeEach
	void setUp() throws Exception {
		// initialize movie trivia object
		mt = new MovieTrivia();

		// set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");

		// get instance of movieDB object from movie trivia object
		movieDB = mt.movieDB;
	}

	@Test
	void testSetUp() {
		assertEquals(6, movieDB.getActorsInfo().size(),
				"actorsInfo should contain 6 actors after reading moviedata.txt.");
		assertEquals(7, movieDB.getMoviesInfo().size(),
				"moviesInfo should contain 7 movies after reading movieratings.csv.");

		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName(),
				"\"meryl streep\" should be the name of the first actor in actorsInfo.");
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size(),
				"The first actor listed in actorsInfo should have 3 movies in their moviesCasted list.");
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0),
				"\"doubt\" should be the name of the first movie in the moviesCasted list of the first actor listed in actorsInfo.");

		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName(),
				"\"doubt\" should be the name of the first movie in moviesInfo.");
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating(),
				"The critics rating for the first movie in moviesInfo is incorrect.");
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating(),
				"The audience rating for the first movie in moviesInfo is incorrect.");
	}

	@Test
	void testInsertActor() {

		// try to insert new actor with new movies
		mt.insertActor("test1", new String[] { "testmovie1", "testmovie2" }, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size(),
				"After inserting an actor, the size of actorsInfo should have increased by 1.");
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName(),
				"After inserting actor \"test1\", the name of the last actor in actorsInfo should be \"test1\".");
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size(),
				"Actor \"test1\" should have 2 movies in their moviesCasted list.");
		assertEquals("testmovie1",
				movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0),
				"\"testmovie1\" should be the first movie in test1's moviesCasted list.");

		// try to insert existing actor with new movies
		mt.insertActor("   Meryl STReep      ", new String[] { "   DOUBT      ", "     Something New     " },
				movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size(),
				"Since \"meryl streep\" is already in actorsInfo, inserting \"   Meryl STReep      \" again should not increase the size of actorsInfo.");

		// look up and inspect movies for existing actor
		// note, this requires the use of properly implemented selectWhereActorIs method
		// you can comment out these two lines until you have a selectWhereActorIs
		// method
		assertEquals(4, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size(),
			"After inserting Meryl Streep again with 2 movies, only one of which is not on the list yet, the number of movies \"meryl streep\" appeared in should be 4.");
		assertTrue(mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).contains("something new"),
			"After inserting Meryl Streep again with a new Movie \"     Something New     \", \"somenthing new\" should appear as one of the movies she has appeared in.");

		// TODO add additional test case scenarios
		
		// Try to insert an existing actor with existing movies
	    mt.insertActor("test1", new String[]{"testmovie1"}, movieDB.getActorsInfo());
	    assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size(),
	        "Inserting an existing movie for \"test1\" should not increase their movie list size.");
	    
	    // Try inserting an actor with a mixed case name and existing movies to check for case-insensitive comparison
	    mt.insertActor("   TeSt1      ", new String[]{"   testmovie3      "}, movieDB.getActorsInfo());
	    assertEquals(3, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size(),
	        "\"TeSt1\" should now have 3 movies, including the newly added \"testmovie3\" after case-insensitive comparison and trimming.");
	    
	}

	@Test
	void testInsertRating() {
		//Checked!!

		// try to insert new ratings for new movie
		mt.insertRating("testmovie", new int[] { 79, 80 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"After inserting ratings for a movie that is not in moviesInfo yet, the size of moviesInfo should increase by 1.");
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName(),
				"After inserting a rating for \"testmovie\", the name of the last movie in moviessInfo should be \"testmovie\".");
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating(),
				"The critics rating for \"testmovie\" is incorrect.");
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating(),
				"The audience rating for \"testmovie\" is incorrect.");

		// try to insert new ratings for existing movie
		mt.insertRating("doubt", new int[] { 100, 100 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"Since \"doubt\" is already in moviesInfo, inserting ratings for it should not increase the size of moviesInfo.");

		// look up and inspect movies based on newly inserted ratings
		// note, this requires the use of properly implemented selectWhereRatingIs
		// method
		// you can comment out these two lines until you have a selectWhereRatingIs
		// method
		assertEquals(1, mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).size(),
			"After inserting a critic rating of 100 for \"doubt\", there should be 1 movie in moviesInfo with a critic rating greater than 99.");
		assertTrue(mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).contains("doubt"),
			"After inserting the rating for \"doubt\", \"doubt\" should appear as a movie with critic rating greater than 99.");

		// TODO add additional test case scenarios
		// invalid rating value 
		mt.insertRating("testmovie", new int[] { -1, 1000 }, movieDB.getMoviesInfo());
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating(),
				"Invalid rating! The critics rating for \"testmovie\" should not be updated.");
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating(),
				"Invalid rating! The audience rating for \"testmovie\" should not be updated.");
		
		// invalid input
		mt.insertRating("testmovie2", new int[] { 1, 1, 1 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"Check input!");
		mt.insertRating("testmovie3", new int[] { 1 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"Check input!");
	}

	@Test
	void testSelectWhereActorIs() {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size(),
				"The number of movies \"meryl streep\" has appeared in should be 3.");
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0),
				"\"doubt\" should show up as first in the list of movies \"meryl streep\" has appeared in.");

		// TODO add additional test case scenarios
		// Invalid actor
		assertEquals(0, mt.selectWhereActorIs("  Invalid Actor  ", movieDB.getActorsInfo()).size(),
				"The number of movies \"Invalid Actor\" has appeared in should be 0.");
		//case-insensitive comparison
		assertEquals(3, mt.selectWhereActorIs("  Meryl streep", movieDB.getActorsInfo()).size(),
				"The number of movies \"meryl streep\" has appeared in should be 3.");
	}

	@Test
	void testSelectWhereMovieIs() {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size(),
				"There should be 2 actors in \"doubt\".");
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"),
				"\"meryl streep\" should be an actor who appeared in \"doubt\".");
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"),
				"\"amy adams\" should be an actor who appeared in \"doubt\".");

		// TODO add additional test case scenarios
		// case-insensitive && with white space and upper class
		assertEquals(true, mt.selectWhereMovieIs("DoUbt       ", movieDB.getActorsInfo()).contains("meryl streep"),
				"\"meryl streep\" should be an actor who appeared in \"doubt\".");
		// Invalid Movie
		assertEquals(false, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("Invalid"),
				"\"amy adams\" should be an actor who appeared in \"doubt\".");
	}

	@Test
	void testSelectWhereRatingIs() {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size(),
				"There should be 6 movies where critics rating is greater than 0.");
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size(),
				"There should be no movie where audience rating is equal to 65.");
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size(),
				"There should be 2 movies where critics rating is less than 30.");

		// TODO add additional test case scenarios
		//invalid comparison
		assertEquals(0, mt.selectWhereRatingIs('a', 65, false, movieDB.getMoviesInfo()).size(),
				"There should be no movie since invalid comparison.");
		
		//invalid score
		assertEquals(0, mt.selectWhereRatingIs('=', -65, false, movieDB.getMoviesInfo()).size(),
				"There should be no movie since invalid score.");
	}

	@Test
	void testGetCoActors() {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size(),
				"\"meryl streep\" should have 2 co-actors.");
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"),
				"\"tom hanks\" was a co-actor of \"meryl streep\".");
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"),
				"\"amy adams\" was a co-actor of \"meryl streep\".");

		// TODO add additional test case scenarios
		
		// with white space and upper class
		assertEquals(2, mt.getCoActors("    Meryl streep     ", movieDB.getActorsInfo()).size(),
				"\"meryl streep\" should have 2 co-actors.");
		// Invalid actor
		assertEquals(0, mt.getCoActors("Invaild", movieDB.getActorsInfo()).size(),
				"\"meryl streep\" should have no co-actors.");
	}

	@Test
	void testGetCommonMovie() {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size(),
				"\"tom hanks\" and \"meryl streep\" should have 1 movie in common.");
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"),
				"\"the post\" should be a common movie between \"tom hanks\" and \"meryl streep\".");

		// TODO add additional test case scenarios
		// Invalid movie
		assertEquals(0, mt.getCommonActors("Invalid movie 1", "Invalid movie 2", movieDB.getActorsInfo()).size(),
				"There should be no actor since no such Invalid movie.");
		
		assertEquals(1, mt.getCommonMovie("   Meryl strEep ", "  tom haNks  ", movieDB.getActorsInfo()).size(),
				"\"tom hanks\" and \"meryl streep\" should have 1 movie in common.");
	}

	@Test
	void testGoodMovies() {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size(),
				"There should be 3 movies that are considered good movies, movies with both critics and audience rating that are greater than or equal to 85.");
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"),
				"\"jaws\" should be considered a good movie, since it's critics and audience ratings are both greater than or equal to 85.");

		// TODO add additional test case scenarios
		//Invalid Movie
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("Invalid"),
				"Invalid Movie.");
	}

	@Test
	void testGetCommonActors() {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size(),
				"There should be one actor that appeared in both \"doubt\" and \"the post\".");
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"),
				"The actor that appeared in both \"doubt\" and \"the post\" should be \"meryl streep\".");

		// TODO add additional test case scenarios
		// Invalid actors
		assertEquals(0, mt.getCommonMovie("Invalid1", "Invalid2", movieDB.getActorsInfo()).size(),
				"Invalid actors. There should be 0 actor that appeared");
		
		// with white space and upper class
		assertEquals(1, mt.getCommonActors("  Doubt", "  the Post   ", movieDB.getActorsInfo()).size(),
				"There should be one actor that appeared in both \"doubt\" and \"the post\".");
		
		// same actor
		assertEquals(3, mt.getCommonMovie("tom hanks", "tom hanks", movieDB.getActorsInfo()).size(),
				"\"tom hanks\" should have 3 movie in common.");
	}

	@Test
	void testGetMean() {

		// TODO: Add all possible test case scenarios for comprehensive testing.
		assertEquals(67.85, mt.getMean(movieDB.getMoviesInfo())[0], 0.01); // Asserting the mean score of the first metric is as expected before adding new data
		assertEquals(65.71, mt.getMean(movieDB.getMoviesInfo())[1], 0.01); // Asserting the mean score of the second metric is as expected before adding new data

		// Adding a new movie with scores to test how it affects the mean values
		mt.insertRating("testmovie", new int[] { 1, 5 }, movieDB.getMoviesInfo()); // Inserting a new movie with scores 1 and 5 into the database
		assertEquals(59.50, mt.getMean(movieDB.getMoviesInfo())[0], 0.01); // Verifying the new mean score of the first metric after adding the new movie
		assertEquals(58.125, mt.getMean(movieDB.getMoviesInfo())[1], 0.01); // Verifying the new mean score of the second metric after adding the new movie
	}
}
