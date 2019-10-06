using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using AVECinema.cinema;

namespace AVECinema.Controllers
{
    public class HomeController : Controller
    {
        
        public ActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public ActionResult Programme()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();

            movie[] m = client.getAllMoviesForProgramme();
            return View(m);
        }

        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(string email, string password)
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();

            user user = client.logIn(email, password);
            if (user != null)
            {
                Session["email"] = user.email;
                Session["role"] = user.role;

                return RedirectToAction("Programme");
            }
            else
            {
                 ViewData["Message"]= "User Login Failed";
                return View();
            }

        }

        [HttpGet]
        public ActionResult Logout()
        {
            Session["email"] = null;
            Session["role"] = null;
            return RedirectToAction("Programme");
        }

       
    }
}