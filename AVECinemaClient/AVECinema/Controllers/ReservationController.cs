using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using AVECinema.cinema;
using Newtonsoft.Json;

namespace AVECinema.Controllers
{
    public class ReservationController : Controller
    {
        // GET: Reservation
        public ActionResult Index()
        {

            return View();
        }

        [HttpGet]
        public ActionResult MakeReservation(int scheduleID, double moviePrice)
        {
            string email = (string)Session["email"];
            if (email == null)
            {
                return RedirectToAction("Login", "Home");
            }
            CinemaWebServiceClient c = new CinemaWebServiceClient();
            schedule s = c.getScheduleByID(scheduleID);
            ViewBag.scheduleForReservation = s;
            ViewBag.reservedSeats = reservedSeatsArray(s.seats);
            ViewBag.moviePrice = moviePrice;

            return View("");
        }

        private List<int> reservedSeatsArray(scheduleEntry[] scheduleEntries)
        {
            List<int> seats = new List<int>();
            foreach (var scheduleEntry in scheduleEntries)
            {
                seats.Add(scheduleEntry.key);
            }
            return seats;
        }

        [HttpPost]
        public ActionResult MakeReservation(int scheduleID, double totalPrice, int numberOfTicket, string seats)
        {
            int?[] seatsArray = JsonConvert.DeserializeObject<int?[]>(seats);
            reservation r = new reservation();
            r.scheduleID = scheduleID;
            r.totalPrice = totalPrice;
            r.numberOfTicket = numberOfTicket;
            r.seats = seatsArray;

            CinemaWebServiceClient c = new CinemaWebServiceClient();
            c.makeReservation(r, Session["email"].ToString());


            return RedirectToAction("MyReservation");
        }

        [HttpGet]
        public ActionResult MyReservation()
        {
            if (Session["email"] == null)
            {
                return RedirectToAction("Index", "Home");
            }
            CinemaWebServiceClient client = new CinemaWebServiceClient();
            reservation[] r = client.getAllReservationsByCustomer(Session["email"].ToString());

            return View(r);
        }

        [HttpPost]
        public ActionResult CancelReservation(int reservationID)
        {

            CinemaWebServiceClient c = new CinemaWebServiceClient();
            c.updateReservationStatus(reservationID, "Cancelled");

            return RedirectToAction("MyReservation");
        }

        [HttpGet]
        public ActionResult AllReservations()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();
            reservation[] reservation = client.getAllReservations();

            return View(reservation);
        }

        [HttpPost]
        public ActionResult ProccessPayment(reservation reservation)
        {
            if (Session["email"] == null)
            {
                return RedirectToAction("Index", "Home");
            }
            return View(reservation);
        }

        [HttpPost]
        public ActionResult RedeemVoucher(reservation reservation)
        {
            if (Session["email"] == null)
            {
                return RedirectToAction("Index", "Home");
            }
            string voucherCode = reservation.voucherCode;
            List<voucher> vs = GlobalVariables.VoucherList;

            // lookup the voucher in voucher list
            foreach (var v in vs)
            {
                if(v.code == voucherCode)
                {
                    reservation.voucherCode = voucherCode;
                    reservation.totalPrice -= ((reservation.totalPrice * v.percentage) / 100);
                    CinemaWebServiceClient c = new CinemaWebServiceClient();
                    c.updateReservation(reservation);
                    return View("ProccessPayment", reservation);
                }
            }

            reservation.voucherCode = "";
            return View("ProccessPayment", reservation);
        }

        [HttpPost]
        public ActionResult Pay(reservation reservation)
        {
            if (Session["email"] == null)
            {
                return RedirectToAction("Index", "Home");
            }
            CinemaWebServiceClient c = new CinemaWebServiceClient();
            c.updateReservationStatus(reservation.reservationID, "Paid");
            return RedirectToAction("MyReservation");
        }
    }
}