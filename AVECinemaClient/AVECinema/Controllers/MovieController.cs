using AVECinema.cinema;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using TMDbLib.Client;
using TMDbLib.Objects.General;
using TMDbLib.Objects.Movies;
using TMDbLib.Objects.Search;

namespace AVECinema.Controllers
{
    public class MovieController : Controller
    {
        // GET: Movie
        public ActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public ActionResult AddMovie()
        {

            return View(new List<SearchMovie>());
        }

        [HttpPost]
        public ActionResult AddMovie(string query)
        {
            TMDbClient client = new TMDbClient("71e4cebd739f9f30aec016154250620f");
            SearchContainer<SearchMovie> search = client.SearchMovieAsync(query).Result;

            return View(search.Results);
        }

        [HttpPost]
        public ActionResult SelectMovieToAdd(string movieId)
        {
            TMDbClient client = new TMDbClient("71e4cebd739f9f30aec016154250620f");
            Movie mOnline = client.GetMovieAsync(int.Parse(movieId), MovieMethods.Credits).Result;
            movie m = convertTMDBMovieToAVEMovie(mOnline);

            return RedirectToAction("EditMovie", m);
        }

        [HttpPost]
        public ActionResult EditMovie(string movieID, string title, string genre, string director,
        string actor, string overview, string language, int lenght, double price, string posterUrl)
        {
            movie m = new movie();
            m.movieID = movieID;
            m.title = title;
            m.genre = genre;
            m.director = director;
            m.actor = actor;
            m.overview = overview;
            m.language = language;
            m.lenght = lenght;
            m.price = price;
            m.posterUrl = posterUrl;

            CinemaWebServiceClient client = new CinemaWebServiceClient();
            client.saveMovie(m);

            return RedirectToAction("MovieList");
        }



        [HttpGet]
        public ActionResult EditMovie(movie m)
        {
            return View(m);
        }

        [HttpGet]
        public ActionResult MovieList()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();

            movie[] m = client.getAllMovies();
            return View(m);
        }

        [HttpPost]
        public ActionResult SelectMovieToEdit(movie m)
        {
            return RedirectToAction("EditMovie", m);
        }

        [HttpPost]
        public ActionResult SelectScheduleToEdit(movie movie)
        {
            return RedirectToAction("EditSchedule", "Movie", movie);
        }

        [HttpGet]
        public ActionResult AddSchedule(string movieID)
        {
            ViewBag.movieID = movieID;
            return View();
        }

        [HttpPost]
        public ActionResult AddSchedule(string roomNo, string date, string startTime, string endTime, string movieID)
        {

            schedule schedule = new schedule();
            schedule.roomNo = roomNo;
            schedule.date = date;
            schedule.startTime = startTime;
            schedule.endTime = endTime;

            CinemaWebServiceClient c = new CinemaWebServiceClient();
            int result = c.createSchedule(schedule, movieID);

            if (result == -2)
            {
                ViewBag.scheduleOverlap = "Schedule overlap other schedules";
                return RedirectToAction("AddSchedule", "Movie", new { movieID = movieID });
            }
            return RedirectToAction("EditSchedule", "Movie", new { movieID = movieID });

        }


        [HttpGet]
        public ActionResult EditSchedule(string movieID)
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();
            schedule[] schedules = client.getSchedules(movieID);
            ViewBag.movieID = movieID;
            return View(schedules);
        }

        [HttpPost]
        public ActionResult EditScheduleSaveChanges(int scheduleID, string roomNo, string date, string startTime, string endTime, string movieID)
        {

            schedule schedule = new schedule();
            schedule.roomNo = roomNo;
            schedule.date = date;
            schedule.startTime = startTime;
            schedule.endTime = endTime;
            schedule.scheduleID = scheduleID;

            CinemaWebServiceClient c = new CinemaWebServiceClient();
            c.updateScheduleInfo(schedule);
            return RedirectToAction("EditSchedule", "Movie", new { movieID = movieID });

        }

        private movie convertTMDBMovieToAVEMovie(Movie mOnline)
        {
            movie m = new movie();
            m.movieID = mOnline.Id + "";
            m.title = mOnline.Title;
            m.genre = "";
            foreach (Genre genre in mOnline.Genres)
            {
                m.genre += " " + genre.Name + " /";
            }
            if (mOnline.Genres.Count != 0)
            {
                m.genre = m.genre.Substring(0, m.genre.Length - 1);
            }
            List<Crew> c = mOnline.Credits.Crew;
            List<Cast> casts = mOnline.Credits.Cast;
            string directors = "";
            foreach (Crew crew in c)
            {
                if (crew.Job.Equals("Director"))
                {
                    directors += $"{crew.Name}, ";
                }
            }
            if (c.Count != 0)
            {
                directors = directors.Substring(0, directors.Length - 2);
            }
            string actors = "";
            foreach (Cast cast in casts)
            {
                if (cast.Order == 2)
                {
                    actors += $"{cast.Name}"; break;
                }
                actors += $"{cast.Name}, ";
            }
            int duration = (int)mOnline.Runtime;

            m.director = directors;
            m.actor = actors;
            m.overview = mOnline.Overview;
            m.language = mOnline.OriginalLanguage;
            m.lenght = duration;
            m.posterUrl = "https://image.tmdb.org/t/p/w300" + mOnline.PosterPath;
            return m;
        }

    }
}