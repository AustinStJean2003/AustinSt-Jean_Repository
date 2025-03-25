using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

#nullable disable

namespace HVK.Models {
    public partial class Pet {
        public Pet() {
            PetReservations = new HashSet<PetReservation>();
            PetVaccinations = new HashSet<PetVaccination>();
        }

        public int PetId { get; set; }
        [StringLength(25, ErrorMessage = "Name must not be longer than 25 characters.")]
        [MaxLength(25)] 
        [DataType(DataType.Text)]
        [Required(ErrorMessage = "Name is required.")]
        public string Name { get; set; }

        [Required(ErrorMessage ="Pet's gender is required.")]
        public string Gender { get; set; }
        [DataType(DataType.Text)]
        public string Breed { get; set; }
        [Display(Name = "Birth Year")]
        public int? Birthyear { get; set; }
        public int CustomerId { get; set; }
        [Display(Name = "Size")]
        public string DogSize { get; set; }
        public bool Climber { get; set; }
        public bool Barker { get; set; }
        [Display(Name = "Special Notes")]
        public string SpecialNotes { get; set; }
        public enum Size { Small, Medium, Large }
        public enum GenderOptions { Female, Male }

        public virtual Customer Customer { get; set; }
        public virtual ICollection<PetReservation> PetReservations { get; set; }
        public virtual ICollection<PetVaccination> PetVaccinations { get; set; }

    }
}
