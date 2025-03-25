using System;
using System.Collections.Generic;

#nullable disable

namespace HVK.Models
{
    public partial class PetVaccination
    {
        public DateTime ExpiryDate { get; set; }
        public int VaccinationId { get; set; }
        public int PetId { get; set; }
        public bool VaccinationChecked { get; set; }
        public enum AllVaccinations { Bordatella, Distemper , Hepatitis , Parainfluenza , Parovirus , Rabies }

        public virtual Pet Pet { get; set; }
        public virtual Vaccination Vaccination { get; set; }
    }
}
