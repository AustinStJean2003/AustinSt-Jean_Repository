using HVK.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;

namespace HVK.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly HVK_Team3Context _db;
        const string SessionCustId = "_CustId";

        public HomeController(ILogger<HomeController> logger, HVK_Team3Context db)
        {
            _logger = logger;
            _db = db;
        }

        public IActionResult Index()
        {

            var CustId = HttpContext.Session.GetInt32(SessionCustId);
            if (CustId == null || CustId < 0) {
                return RedirectToAction("Index", "Login");
                

            } else {
                var data = _db.Customers.Where(c => c.CustomerId == CustId).Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).First();
                return View(data);
            }
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
