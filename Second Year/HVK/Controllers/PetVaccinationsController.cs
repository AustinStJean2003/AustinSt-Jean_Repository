using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using HVK.Models;
using Microsoft.AspNetCore.Http;

namespace HVK.Controllers
{
    public class PetVaccinationsController : Controller
    {
        private readonly HVK_Team3Context _context;

        public PetVaccinationsController(HVK_Team3Context context)
        {
            _context = context;
        }



        // GET: PetVaccinations/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {

            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            if (id == null)
            {
                return NotFound();
            }

            Pet p =  _context.Pets.Find(id);
            if (CustId != p.CustomerId)
            {
                return RedirectToAction("Index", "Login");
            }

            ViewBag.PetName = p.Name;
            var petVaccinationList = await _context.PetVaccinations.Where(p => p.PetId == id).Include(p=>p.Vaccination).ToListAsync();
            if (petVaccinationList == null)
            {
                return NotFound();
            }

            return View(petVaccinationList);
        }

        [HttpPost]
        public async Task<IActionResult> Edit(Pet pet)
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            return RedirectToAction("Index", "Home");
        }




    }
}
