/* =========================================================
   SARASWATI COLLEGE OF ENGINEERING
   COMMON WEBSITE JAVASCRIPT
   ========================================================= */


/* =========================================================
   PAGE LOAD
   ========================================================= */

document.addEventListener("DOMContentLoaded", function () {

    console.log(
        "Saraswati College of Engineering website loaded successfully."
    );

    initializeNavigation();
    updateCurrentYear();

});


/* =========================================================
   NAVIGATION
   ========================================================= */

function initializeNavigation() {

    const navigationLinks =
        document.querySelectorAll(".nav-link");


    navigationLinks.forEach(function (link) {

        link.addEventListener("click", function () {

            navigationLinks.forEach(function (item) {

                item.classList.remove("active");

            });


            this.classList.add("active");

        });

    });

}


/* =========================================================
   CURRENT YEAR
   ========================================================= */

function updateCurrentYear() {

    const yearElement =
        document.getElementById("currentYear");


    if (yearElement) {

        yearElement.textContent =
            new Date().getFullYear();

    }

}


/* =========================================================
   ANNOUNCEMENT MESSAGE
   ========================================================= */

function updateAnnouncement(message) {

    const announcement =
        document.querySelector(".announcement-message");


    if (announcement) {

        announcement.innerHTML =
            message + ' <span>→</span>';

    }

}