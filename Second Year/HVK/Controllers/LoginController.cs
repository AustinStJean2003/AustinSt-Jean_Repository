using HVK.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;

namespace HVK.Controllers
{
    public class LoginController : Controller
    {
        private readonly HVK_Team3Context _context;
        public LoginController(HVK_Team3Context context){
            _context = context;
                }
        public IActionResult Index()
        {
            HttpContext.Session.SetInt32("_CustId", -1);
            return View();
        }
        [HttpPost]
        public IActionResult Index(Login login) {
            if (!ModelState.IsValid) {
                return View(login);
            }
          
            var DBContext = _context.Customers.Where(c => (c.Email == login.Email && c.Email != null) || (c.Phone == login.Phone && c.Phone !=null));
            var CustomerId = DBContext.First().CustomerId;
            HttpContext.Session.SetInt32("_CustId", CustomerId);
           
            return RedirectToAction("Index","Home");
        }
    }
}
