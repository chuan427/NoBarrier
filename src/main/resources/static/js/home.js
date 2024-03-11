document.addEventListener("DOMContentLoaded", function() {
    window.addEventListener("scroll", function() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            document.getElementById("back-to-top-btn").style.display = "block";
        } else {
            document.getElementById("back-to-top-btn").style.display = "none";
        }
    });
});
