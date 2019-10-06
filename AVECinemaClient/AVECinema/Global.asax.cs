using AVECinema.cinema;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;

namespace AVECinema
{
    public class MvcApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            CinemaWebServiceClient client = new CinemaWebServiceClient();

            GlobalVariables.VoucherList = new List<voucher>(client.getAllVouchers());
                                 

            AreaRegistration.RegisterAllAreas();
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
        }
    }

    public static class GlobalVariables
    {
        // read-write variable
        public static List<voucher> VoucherList
        {
            get
            {
                return HttpContext.Current.Application["voucherList"] as List<voucher>;
            }
            set
            {
                HttpContext.Current.Application["voucherList"] = value;
            }
        }
    }
}
