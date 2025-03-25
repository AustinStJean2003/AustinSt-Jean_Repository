In my systems maintenance course I worked on a co-op management system that the school uses to manage their co-op processes. In this project I worked in **C#**, **RAZOR**, **JavaScript** and **CSS**. One of the things I worked on was a PDF generator. The user would fill in a form on the system and click on the submit button. This would generate a PDF document that represented what they put into the form. This was done using a library called **PDFMake**.

```cs
<link href="~/css/FormTemplate.css" rel="stylesheet" />
<div class="formlinks">
    <partial name="_FormTemplates" />
</div>
<br />
<br />
<h2>Work Agreement Form</h2>
<div class="WorkAgreementDiv">
    <form class="WorkAgreementForm">
        <div class="EmployerAndStudentDiv">
            <div class="Employer">
                <h4>Employer</h4>
                <br />

                <label class="center-label">Contact Person: </label>
                <input type="text" id="contact-person" class="form-control" />

                <label class="center-label">Employer: </label>
                <input type="text" id="employer" class="form-control" />

                <label class="center-label">Address: </label>
                <input type="text" id="address" class="form-control" />

                <label class="center-label">Telephone: </label>
                <input type="text" id="telephone" class="form-control" />

                <label class="center-label">Email: </label>
                <input type="text" id="email" class="form-control" />

            </div>
            <div class="Student">
                <h4>Student</h4>
                <br />

                <label class="center-label">Name: </label>
                <input type="text" id="student-name" class="form-control" />

                <label class="center-label">Program: </label>
                <input type="text" id="student-program" class="form-control" />

                <label class="center-label">Student Number: </label>
                <input type="text" id="student-number" class="form-control" />

                <label class="center-label">Job Title: </label>
                <input type="text" id="job-title" class="form-control" />

                <label class="center-label">Job Location: </label>
                <input type="text" id="job-location" class="form-control" />

            </div>
        </div>
        <div class="WorkTermDiv">
            <h4>Work Term</h4>
            <label class="center-label">Please list the duties of the work term :</label><br />
            <textarea id="work-term" rows="5" class="fullWidth form-control"></textarea>
        </div>
        <div class="SecondWorkTermDiv">
            <div class="StartEndDateDiv">

                <label class="center-label">Start Date: </label>
                <input type="date" id="start-date" class="form-control" />

                <label class="center-label">End Date: </label>
                <input type="date" id="end-date" class="form-control" />

                <label class="center-label">Amount per hour:</label>
                <input type="number" id="amount-per-hour" class="form-control" />

                <label class="center-label">Projected hours of work per week:</label>
                <input type="number" id="projected-hours-per-week" min="0" class="form-control" />
            </div>
            <div class="forOfficeOnly">
                <p><strong>For Office use Only</strong></p>
                <hr />
                <div class="officeOnlyDiv">
                    <label class="center-label">Total projected hours: </label>
                    <input type="number" id="total-projected-hours" min="0" class="form-control center" />
                </div>
            </div>
        </div>
        <div class="SignatureDiv">
            <div>
                <label class="floatleft center-label">Employer: </label>
                <input type="text" id="employer-signature" class="form-control" />
                <label class="floatleft center-label">Date: </label>
                <input type="date" id="employer-date" class="form-control" />
                <label class="floatleft center-label">Student: </label>
                <input type="text" id="student-signature" class="form-control" />
                <label class="floatleft center-label">Date: </label>
                <input type="date" id="student-date" class="form-control" />
                <label class="floatleft center-label">For the College: </label>
                <input type="text" id="college-signature" class="form-control" />
                <label class="floatleft center-label">Date: </label>
                <input type="date" id="college-date" class="form-control" />
            </div>
        </div>
        <div class="downloadDiv">
            <input type="button" value="Download" class="btn btn-primary" id="downloadBtn" />
        </div>
    </form>
    <br />
    <br />
    <br />
    <div id="exportDataTable">
    </div>
</div>


<script>

    function getPDF() {
        let contactPerson = document.getElementById("contact-person").value;
        let employer = document.getElementById("employer").value;
        let email = document.getElementById("email").value;
        let address = document.getElementById("address").value;
        let telephone = document.getElementById("telephone").value;
        let studentName = document.getElementById("student-name").value;
        let studentProgram = document.getElementById("student-program").value;
        let studentNumber = document.getElementById("student-number").value;
        let jobTitle = document.getElementById("job-title").value;
        let jobLocation = document.getElementById("job-location").value;
        let workTerm = document.getElementById("work-term").value;
        let startDate = document.getElementById("start-date").value;
        let endDate = document.getElementById("end-date").value;
        let projectedHoursPerWeek = document.getElementById("projected-hours-per-week").value;
        let totalProjectedHours = document.getElementById("total-projected-hours").value;
        let employerSignature = document.getElementById("employer-signature").value;
        let employerDate = document.getElementById("employer-date").value;
        let studentSigature = document.getElementById("student-signature").value;
        let studentDate = document.getElementById("student-date").value;
        let collegeSignature = document.getElementById("college-signature").value;
        let collegeDate = document.getElementById("college-date").value;
        let amountPerHour = document.getElementById("amount-per-hour").value;

        let docPdf = {
            header: {
                image: base64Imag,
                fit: [100, 100]
            },
            content: [
                { text: 'WORK AGREEMENT CONTRACT', alignment: 'center', bold: true, fontSize: 16 },
                {
                    table: {
                        headerRows: 1,
                        widths: [100, 150, 100, 150],
                        body: [
                            [{ text: 'Employer', style: 'tableHeader', colSpan: 2, alignment: 'left', bold: true, fillColor: "#F0F0F0" }, {}, { text: 'Student', bold: true, style: 'tableHeader', colSpan: 2, alignment: 'left', fillColor: "#F0F0F0" }, {}],
                            [{ text: 'Contact person:', border: [true, true, false, false] }, { text: contactPerson, border: [false, true, true, false] }, { text: 'Name:', border: [true, true, false, false] }, { text: studentName, border: [false, true, true, false] }],
                            [{ text: 'Employer:', border: [true, false, false, false] }, { text: employer, border: [false, false, true, false] }, { text: 'Program:', border: [true, false, false, false] }, { text: studentProgram, border: [false, false, true, false] }],
                            [{ text: "Address:", border: [true, false, false, false] }, { text: address, border: [false, false, true, false] }, { text: 'Student number:', border: [true, false, false, false] }, { text: studentNumber, border: [false, false, true, false] }],
                            [{ text: "Telephone:", border: [true, false, false, false] }, { text: telephone, border: [false, false, true, false] }, { text: 'Job Title:', border: [true, false, false, false] }, { text: jobTitle, border: [false, false, true, false] }],
                            [{ text: "Email:", border: [true, false, false, true] }, { text: email, border: [false, false, true, true] }, { text: 'Job Location:', border: [true, false, false, true] }, { text: jobLocation, border: [false, false, true, true] }]
                        ]
                    }
                },
                { text: " " },
                {
                    table: {
                        headerRows: 1,
                        widths: [527],
                        heights: ['*', '*', 200],
                        body: [
                            [{ text: 'Work Term', bold: true, fillColor: "#F0F0F0" }],
                            [{ text: 'Please list the duties of the work term:', border: [true, true, true, false] }],
                            [{ text: workTerm, border: [true, false, true, true] }]
                        ]
                    }
                },
                {
                    table: {
                        widths: [151, 87, 87, 87, 30, 40],
                        body: [
                            [{ text: "Start date:  " + startDate, border: [true, false, false, false], colSpan: 2 }, {}, { text: "End date:  " + endDate, border: [false, false, false, false], colSpan: 2 }, {}, { text: "" + amountPerHour, border: [false, false, false, false], alignment: 'right' }, { text: "/hour$", border: [false, false, true, false] }],
                            [{ text: "Projected hours of work per week:  " + projectedHoursPerWeek, border: [true, false, false, false], colSpan: 2 }, { text: "", border: [false, false, true, true] }, {
                                table: {
                                    headerRows: 1,
                                    body: [
                                        [{ text: "For office use only", style: 'tableHeader', colSpan: 2, alignment: 'left', bold: true, fillColor: "#D9D9D9" }, {}],
                                        [{ text: "Total projected hours", border: [true, false, false, true] }, { text: totalProjectedHours, border: [false, false, true, true] }]
                                    ]
                                }, colSpan: 4, border: [false, false, true, false]
                            }, {}, {}, {}],
                            [{ text: "", colSpan: 6, border: [true, false, true, true] }, {}, {}, {}, {}, {}]
                        ]
                    }
                },
                { text: " ", heights: 20 },
                {
                    table: {
                        headerRows: 1,
                        widths: [60, 295, 30, 115],
                        body: [
                            [{ text: "Signatures", bold: true, border: [true, true, false, true], fillColor: "#F0F0F0" }, { text: "(electronic signatures accepted)", border: [false, true, true, true], colSpan: 3, fillColor: "#F0F0F0" }, {}, {}],
                            [{ text: "", colSpan: 4, border: [true, false, true, false] }, {}, {}, {}],
                            [{ text: "Employer:", border: [true, false, false, false] }, { text: employerSignature, border: [false, false, false, false] }, { text: "Date:", border: [false, false, false, false] }, { text: employerDate, border: [false, false, true, false] }],
                            [{ text: "", colSpan: 4, border: [true, false, true, false] }, {}, {}, {}],
                            [{ text: "Student:", border: [true, false, false, false] }, { text: studentSigature, border: [false, false, false, false] }, { text: "Date:", border: [false, false, false, false] }, { text: studentDate, border: [false, false, true, false] }],
                            [{ text: "", colSpan: 4, border: [true, false, true, false] }, {}, {}, {}],
                            [{ text: "For the College: " + collegeSignature, colSpan: 2, border: [true, false, false, false] }, {}, { text: "Date:", border: [false, false, false, false] }, { text: collegeDate, border: [false, false, true, false] }],
                            [{ text: "", colSpan: 4, border: [true, false, true, true] }, {}, {}, {}]
                        ]
                    }
                }
            ]
        }
        pdfMake.createPdf(docPdf).open();
    }

    document.getElementById("downloadBtn").addEventListener("click", function () {
        getPDF();
    })

</script>
```
