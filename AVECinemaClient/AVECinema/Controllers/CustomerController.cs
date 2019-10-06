using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using AVECinema.cinema;


namespace AVECinema.Controllers
{
    public class CustomerController : Controller
    {
        // GET: Customer
        public ActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public ActionResult Register()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Register(string firstName, string lastName, string phoneNo, string email, string password)
        {
         
                customer c = new customer();
                c.firstName = firstName;
                c.lastName = lastName;
                c.phoneNo = phoneNo;
                c.email = email;
                c.password = password;

                CinemaWebServiceClient client = new CinemaWebServiceClient();
            
                try
                {
                    client.registerNewCustomer(c);
                    return RedirectToAction("Login", "Home");
                }
                catch (System.ServiceModel.FaultException e)
                {
                    ViewData["Message"] = "Email already in use";

                    return View();
                }
            
        }

        [HttpGet]
        public ActionResult PersonalInfo()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();

            customer c = client.getCustomer((string)Session["email"]);

            return View(c);
        }

        [HttpPost]
        public ActionResult PersonalInfo(string firstName, string lastName, string phoneNo,  string password)
        {
            customer c = new customer();
            c.firstName = firstName;
            c.lastName = lastName;
            c.phoneNo = phoneNo;
            c.email = (string)Session["email"];
            c.password = password;
            CinemaWebServiceClient client = new CinemaWebServiceClient();
            client.updateCustomerInfo(c);

            return View(c);
        }

        [HttpGet]
        public ActionResult AllCustomers()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();
            customer[] customer = client.getAllCustomers();

            return View(customer);
        }


    }
}