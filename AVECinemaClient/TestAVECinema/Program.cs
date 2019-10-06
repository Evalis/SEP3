
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestAVECinema.cinema;
using TMDbLib;
using TMDbLib.Client;
using TMDbLib.Objects.General;
using TMDbLib.Objects.Search;
using Email;

namespace TestAVECinema
{
    class Program
    {
        static void Main(string[] args)
        {
            //TestDateTimeOfMovieSchedule("550");
            TestEmail("liskova.evica@gmail.com");

            Console.ReadKey();
        }

        public static void TestTMDBLib()
        {
            TMDbClient client = new TMDbClient("71e4cebd739f9f30aec016154250620f");
            SearchContainer<SearchMovie> results = client.SearchMovieAsync("007").Result;
            Console.WriteLine($"Got {results.Results.Count:N0} of {results.TotalResults:N0} results");
            foreach (SearchMovie result in results.Results)
            {
                Console.WriteLine(result.Title);
                Console.WriteLine(result.Adult);

            }
        }

        public static void TestDateTimeOfMovieSchedule(string movieID)
        {
            CinemaWebServerClient client = new CinemaWebServerClient();
            schedule[] schedules = client.getSchedules(movieID);
            foreach (var s in schedules)
            {
                Console.WriteLine(s.roomNo);
                Console.WriteLine(s.date.ToString());
                Console.WriteLine(s.startTime);
                Console.WriteLine(s.endTime);
            }

        }
        public static void TestEmail(string email)
        {
            MailService m = new MailService("cinema.ave@gmail.com");
            m.SendEmailToCustomer(email);

        
        }
    }
}
