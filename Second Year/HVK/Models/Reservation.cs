using System;
using System.Collections.Generic;

#nullable disable

namespace HVK.Models
{
    public partial class Reservation
    {
        public Reservation()
        {
            PetReservations = new HashSet<PetReservation>();
            ReservationDiscounts = new HashSet<ReservationDiscount>();
        }

        public int ReservationId { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public decimal Status { get; set; }

        public virtual ICollection<PetReservation> PetReservations { get; set; }
        public virtual ICollection<ReservationDiscount> ReservationDiscounts { get; set; }
    }
}
