$(document).ready(() => {
    $(".guiThongTin").click(() => {
        var email = $("#typeEmail").val();
        var taiKhoan = $("#userName").val();
        if(taiKhoan == "" || taiKhoan == null){
            $(".thongBaoTenTaiKhoan").text("*Vui lòng không để trống");
            $(".thongBaoEmail").text("");
            return;
        }
        if (email == "" || email == null) {
            $(".thongBaoEmail").text("*Vui lòng không để trống");
            $(".thongBaoTenTaiKhoan").text("");
            return;
        }

        var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!regex.test(email)) {
            $(".thongBaoEmail").text("*Email không đúng định dạng");
            return;
        }
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/static/check-email?email=" + email+"&taiKhoan="+taiKhoan,
            success: function (response) {
                console.log(response)
                if (response.statusCode === 'NOT_FOUND') {
                    $(".thongBaoTenTaiKhoan").text("");
                    $(".thongBaoEmail").text("");
                    $(".thongBaoTong").text("*Không tìm thấy tài khoản với email này!");
                    return;
                }
                // Lấy thời gian hiện tại
                var thoiGianHienTai = new Date().getTime();
                // Đặt thời gian tồn tại là 3 phút (3 * 60 * 1000 milliseconds)
                var thoiGianHetHan = thoiGianHienTai + 3 * 60 * 1000;
                localStorage.setItem("account", JSON.stringify({
                    giaTri: response.content,
                    hetHan: thoiGianHetHan
                }));
                window.location.href = "http://localhost:8081/static/otp-form"
            },
            error: function (error) {
                console.log(error);
                $(".thongBaoEmail").text("*Không tìm thấy email này");
            }
        })
    });

});