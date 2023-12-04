$(document).ready(() => {
    $(".xacThuc").click(() => {
        var opt1 = $(".otp1").val();
        var opt2 = $(".otp2").val();
        var opt3 = $(".otp3").val();
        var opt4 = $(".otp4").val();
        var opt5 = $(".otp5").val();
        var opt6 = $(".otp6").val();
        var optForm = opt1 + opt2 + opt3 + opt4 + opt5 + opt6;
        var sessionAcountLocalStorage = localStorage.getItem("account");
        // Lấy thời gian hiện tại
        var thoiGianHienTai = new Date().getTime();
        if (sessionAcountLocalStorage) {
            var sessionOb = JSON.parse(sessionAcountLocalStorage);
            if (sessionOb.hetHan < thoiGianHienTai) {
                localStorage.removeItem('account');
                alert("Quá thời gian 3 phút")
                window.location.href = "http://localhost:8081/static/forgot-pass";
                return;
            }
            if (sessionOb.giaTri.idHangKhachHang === optForm) {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8081/static/send-mail-forgot-pass",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify(sessionOb.giaTri),
                    success: function (response) {
                        if (response.statusCode === 'OK') {
                            alert("Mật khẩu mới đã được gửi về mail của bạn!");
                            window.location.href = "http://localhost:8081/authentication/home-login";
                            return;
                        }
                    },
                })
                return;
            }
            alert("Code xác thực mail không đúng!")
        } else {
            alert("Đã hết hạn code hoặc tìm không thấy!")
            window.location.href = "http://localhost:8081/static/forgot-pass";
        }
    });
});