$(document).ready(() => {
    // get all nhan vien
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/staff/account/find-by-roles",
        success: function (response) {
            if(response.statusCode ==="OK"){
                app.danhSachNhanVien = response.content
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
    //get nhan vien ca hien tai
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/staff/giao-ca/by-status-account",
        success: function (response) {
            if(response.statusCode ==="OK"){
                app.nhanVienCaHienTai.id = response.content.id,
                    app.nhanVienCaHienTai.thoiGianNhanCa = moment(response.content.thoiGianNhanCa).format("HH:mm:ss DD-MM-YYYY"),
                    app.nhanVienCaHienTai.idNhanVienTrongCa = response.content.idNhanVienTrongCa
                app.nhanVienCaHienTai.tienBanDau = (response.content.tienBanDau).toLocaleString("vi-VN")
                console.log(response)
                // get account by id
                $.ajax({
                    type: "GET",
                    url: "http://localhost:8081/api/v1/staff/account/by-id/" + app.nhanVienCaHienTai.idNhanVienTrongCa,
                    success: function (response) {
                        if(response.statusCode ==="OK"){
                            app.nhanVienCaHienTai.nameNhanVienTrongCa = response.content.displayName
                        }
                    },
                    error: function (error) {
                        console.log(error);
                    }
                });
            }

        },
        error: function (error) {
            console.log(error);
        }
    });

});
var app = new Vue({
    el: "#app",
    data: {
        danhSachNhanVien: null,
        nhanVienCaHienTai: {
            id: null,
            thoiGianNhanCa: null,
            // thoiGianKetCa: moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
            thoiGianKetCa: moment(new Date()).format("HH:mm:ss DD-MM-YYYY"),
            idNhanVienTrongCa: null,
            nameNhanVienTrongCa: null,
            tienBanDau: null,
            tongTienMat: null,
            tongTienKhac: null,
            tongTienTrongCa: null,
            tienPhatSinh: null,

        }
    }
});