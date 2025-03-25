using System;
using System.Collections.Generic;

#nullable disable

namespace HVK.Models
{
    public partial class PetReservationService
    {
        public int PetReservationId { get; set; }
        public int ServiceId { get; set; }

        public virtual PetReservation PetReservation { get; set; }
        public virtual Service Service { get; set; }
        //public List<object> GetData() {

        //    Service service1 = new Service(1, "Boarding");
        //    Run Run6 = new Run() { RunId = 6, Size = "L", Covered = false, Location = "F", Status = 1 } ;

        //    Customer SampleCust = new Customer() {
        //        CustomerId = 3251,
        //            FirstName=    "Kristina",
        //              LastName=  "Lahey-Ward",
        //               Street= "123 Chemin Street",
        //              City =  "Gatineau",
        //               Province = "QC",
        //               PostalCode = "A1B2C3",
        //               Phone= "1112223333",
        //                CellPhone="9998887777",
        //               Email= "kristina@outlook.com",
        //                EmergencyContactFirstName="William",
        //                EmergencyContactLastName="Brothersten",
        //               EmergencyContactPhone= "5554443333"
        //    };
                        
                      
        //    Pet SamplePet = new Pet() {
        //       PetId= 12,
        //        Name="Trooper",
        //        Gender="M",
        //        Climber = true,
        //        Barker = false,
        //        Customer = SampleCust,
        //        Breed = "German Shephard",
        //        Birthyear = 2018,
        //        DogSize = "L",
        //        SpecialNotes ="Loves ear scratches and belly rubs."

        //        };

        //    Reservation SampleReservation = new Reservation() {
        //        ReservationId= 005,
        //        StartDate= new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day),
        //        EndDate= new DateTime(2022, 03, 29),
        //        PetReservations = new PetReservation() { PetReservationId = 1, Pet = SamplePet, Reservation = this.PetReservation.Reservation, Run = Run6}
        //        };

        //    PetReservation SamplePetReservation = new PetReservation(
        //        1,
        //        SamplePet,
        //        SampleReservation,
        //        Run6
        //        );


        //    PetReservationService PetResServ = new PetReservationService(SamplePetReservation, service1);



        //    List<object> data = new List<object>();
        //    data.Add(PetResServ);
        //    data.Add(new DailyRate(3, 25, 'L', service1));

        //    return data;
        //}
    }
}
