$(document).ready(() => {
    $.ajax({
        type: "GET",
        url: "/api/v1/user/find-luat-san",
        success: function (response) {
            if (response.content == null) {
                $(".thongTinLuatSan").text("Chưa có thông tin!");
                return;
            }
            $(".thongTinLuatSan").html(response.content.thongTin);
            console.log(response);
        },
        error: function (error) {
            console.log(error);
            console.log("Không có data");
        }
    })
})