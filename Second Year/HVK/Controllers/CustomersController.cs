using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Http;
using HVK.Models;
using Microsoft.Extensions.Logging;

namespace HVK.Controllers
{
    public class CustomersController : Controller
    {
        private readonly HVK_Team3Context _context;
        private readonly ILogger<HomeController> _logger;
        const string SessionCustId = "_CustId";

        public CustomersController(HVK_Team3Context context)
        {
            _context = context;
        }

        // GET: Customers
        public async Task<IActionResult> Index()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Index(Customer customer)
        {

            if (!ModelState.IsValid)
                return View(customer);
            else
            {
                try
                {
                    return RedirectToAction("Index", "Home");
                }
                catch
                {
                    return View(customer);
                }
            }
        }




        // GET: Customers/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {

            var CustId = HttpContext.Session.GetInt32(SessionCustId);
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            var customer = _context.Customers.Where(c => c.CustomerId == CustId).First();
            return View(customer);
        }

        // POST: Customers/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Customer customer)
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }

            if (ModelState.IsValid)
            {
                try
                {
                    return RedirectToAction("Index", "Home");

                }
                catch
                {
                    return View(customer);
                }
            }
            return View(customer);
        }

        // GET: Customers/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var customer = await _context.Customers
                .FirstOrDefaultAsync(m => m.CustomerId == id);
            if (customer == null)
            {
                return NotFound();
            }

            return View(customer);
        }

        // POST: Customers/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var customer = await _context.Customers.FindAsync(id);
            _context.Customers.Remove(customer);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CustomerExists(int id)
        {
            return _context.Customers.Any(e => e.CustomerId == id);
        }
    }
}
