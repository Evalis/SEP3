using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using AVECinema.cinema;

namespace AVECinema.Controllers
{
    public class ManagerController : Controller
    {
        // GET: Manager
        public ActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public ActionResult PersonalInfo()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();
            manager m = client.getManager((string)Session["email"]);

            return View(m);
        }

        [HttpPost]
        public ActionResult PersonalInfo(string password)
        {

            manager m = new manager();
            m.email = (string)Session["email"];
            m.password = password;
            CinemaWebServiceClient client = new CinemaWebServiceClient();

            client.updateManagerInfo(m);
            return View(m);


        }
    }






}