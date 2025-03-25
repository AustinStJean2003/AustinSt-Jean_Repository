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
    public class PetsController : Controller
    {
        private readonly HVK_Team3Context _context;

        public PetsController(HVK_Team3Context context)
        {
            _context = context;
        }

        // GET: Pets
        [HttpGet]
        public async Task<IActionResult> Index()
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            return View();
        }

        [HttpPost]
        public IActionResult Index(Pet pet)
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            if (!ModelState.IsValid)
                return View(pet);
            else
            {
                try
                {
                    return RedirectToAction("Index", "Home");
                }
                catch
                {
                    return View(pet);
                }
            }
        }


       
        // GET: Pets/Edit/5
        [HttpGet]
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

            var pet = await _context.Pets.FindAsync(id);
            if (CustId != pet.CustomerId)
            {
                return RedirectToAction("Index", "Login");
            }
            if (pet == null)
            {
                return NotFound();
            }
            //ViewData["CustomerId"] = new SelectList(_context.Customers, "CustomerId", "FirstName", pet.CustomerId);
            return View(pet);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Pet pet)
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
                    //_context.Update(pet);
                    //await _context.SaveChangesAsync();
                    return RedirectToAction("Index", "Home");

                }
                catch
                {
                    return View(pet);
                }
            }
            //ViewData["CustomerId"] = new SelectList(_context.Customers, "CustomerId", "FirstName", pet.CustomerId);
            return View(pet);
        }
        // POST: Pets/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        //[HttpPost]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> Edit(int id, [Bind("PetId,Name,Gender,Breed,Birthyear,CustomerId,DogSize,Climber,Barker,SpecialNotes")] Pet pet)
        //{
        //    if (id != pet.PetId)
        //    {
        //        return NotFound();
        //    }

        //    if (ModelState.IsValid)
        //    {
        //        try
        //        {
        //            //_context.Update(pet);
        //            //await _context.SaveChangesAsync();
        //           return RedirectToAction("Index", "Home");

        //        }
        //        catch
        //        {
        //            return View(pet);
        //        }
        //    }
        //    ViewData["CustomerId"] = new SelectList(_context.Customers, "CustomerId", "FirstName", pet.CustomerId);
        //    return View(pet);
        //}

        // GET: Pets/Delete/5
        //public async Task<IActionResult> Delete(int? id)
        //{
        //    if (id == null)
        //    {
        //        return NotFound();
        //    }

        //    var pet = await _context.Pets
        //        .Include(p => p.Customer)
        //        .FirstOrDefaultAsync(m => m.PetId == id);
        //    if (pet == null)
        //    {
        //        return NotFound();
        //    }

        //    return View(pet);
        //}

        // POST: Pets/Delete/5
        //[HttpPost, ActionName("Delete")]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> DeleteConfirmed(int id)
        //{
        //    var pet = await _context.Pets.FindAsync(id);
        //    _context.Pets.Remove(pet);
        //    await _context.SaveChangesAsync();
        //    return RedirectToAction(nameof(Index));
        //}

        //private bool PetExists(int id)
        //{
        //    return _context.Pets.Any(e => e.PetId == id);
        //}
    }
}
