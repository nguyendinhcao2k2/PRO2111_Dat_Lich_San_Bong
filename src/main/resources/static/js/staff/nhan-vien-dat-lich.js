// LAMNP 22/10
// Lấy tham chiếu đến button và checkbox container
const showCheckboxButton = document.getElementById("showCheckboxButton");
// const checkboxContainer = document.getElementById("checkboxContainer");

// Lập trình sự kiện click cho nút "Hiển thị checkbox"
showCheckboxButton.addEventListener("click", function () {
    // Lấy tất cả các thẻ card
    const cards = document.querySelectorAll(".card-san");
    // Lặp qua tất cả các thẻ card
    // debugger;
    cards.forEach(function (card) {
        // Lấy trạng thái từ mỗi card
        const status = card.querySelector(".badge-status").textContent;

        // Kiểm tra nếu trạng thái là "Đang trống"
        if (status === "Đang trống") {
            // Tìm container của ô checkbox trong card
            const checkboxContainer = card.querySelector("#checkboxContainer");
            // Thêm ô checkbox vào container và hiển thị/ẩn container
            if (checkboxContainer.style.display === "none") {
                checkboxContainer.style.display = "block";
            } else {
                checkboxContainer.style.display = "none";
            }
        }
    });
});

// LamNP 20/10 NAV Tab
$(document).ready(function () {
    $(".tab-content-item").hide();
    $(".tab-content-item:first-child").fadeIn();

    $(".nav-tabs li").click(function () {
        // add active
        $(".nav-tabs li").removeClass("active");
        $(this).addClass("active");
        // show tab content
        let id_tab_content = $(this).children("a").attr("href");
        $(".tab-content-item").hide();
        $(id_tab_content).fadeIn();

        return false;
    });
});
