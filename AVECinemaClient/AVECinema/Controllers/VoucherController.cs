using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using AVECinema.cinema;

namespace AVECinema.Controllers
{
    public class VoucherController : Controller
    {
        // GET: Voucher
        public ActionResult Index()
        {
            return View();
        }
        
        [HttpGet]
        public ActionResult MakeVoucher()
        {

            return View(); 
        }
        [HttpPost]
        public ActionResult MakeVoucher(string code, int percentage)
        {
            voucher v = new voucher();
            v.code = code;
            v.percentage = percentage;

            CinemaWebServiceClient c = new CinemaWebServiceClient();
            c.createVoucher(v);

            return RedirectToAction("AllVouchers");
        }

        [HttpGet]
        public ActionResult AllVouchers()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();
            voucher[] v = client.getAllVouchers();

            return View(v);
        }
    }
}