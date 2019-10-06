using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using AVECinema.cinema;
using CinemaEmailService;

namespace AVECinema.Controllers
{
    public class EmailController : Controller
    {
        // GET: Email
        public ActionResult CinemaEmail()
        {
            return View();
        }

        [HttpPost]
        public ActionResult CinemaEmail(string receivers, string subject, string body)
        {
          
            MailService mail = new MailService("cinema.ave@gmail.comm");
            string[] rs = receivers.Split(' ');

            foreach (string receiver in rs)
            {
                mail.sendEmail(receiver, subject, body);
            }

            return View();
        }
    }

    
  

}