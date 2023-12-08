$(document).ready(() => {
    $(".checkBoxGiaoCa").change((event) => {
        if (event.target.checked) {
            app.isReset = false;
            app.isBanGiaoCa = true;
        } else {
            app.isReset = true;
            app.isBanGiaoCa = false;
        }
    });
    //get all giao ca
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/staff/giao-ca",
        success: (response) => {
            console.log(response);
            if (response.statusCode === 'OK') {
                app.giaoCaStaff.id = response.content.id,
                    app.giaoCaStaff.thoiGianNhanCa = app.formatDate(response.content.thoiGianNhanCa),
                    app.giaoCaStaff.thoiGianHienTai = app.formatDate(response.content.thoiGianHienTai),
                    app.giaoCaStaff.accountResponse = response.content.accountResponse,
                    app.giaoCaStaff.tienBanDau = app.curenlyNumber(response.content.tienBanDau),
                    app.giaoCaStaff.tongHoaDonDaThanhToan = response.content.tongHoaDonDaThanhToan,
                    app.giaoCaStaff.tongHoaDonChuaThanhToan = response.content.tongHoaDonChuaThanhToan,
                    app.giaoCaStaff.tienPhatSinh = response.content.tienPhatSinh,
                    app.giaoCaStaff.ghiChuPhatSinh = response.content.ghiChuPhatSinh,
                    app.giaoCaStaff.accountResponseList = response.content.accountResponseList,
                    app.giaoCaStaff.tongTienMatTrongCa = response.content.tongTienMatTrongCa,
                    app.total = app.curenlyNumber(response.content.tongTienMatTrongCa),
                    app.giaoCaStaff.tongTienThuTrongCa = app.curenlyNumber(response.content.tongTienThuTrongCa),
                    app.giaoCaStaff.tongTientThanhToanBangTienMat = app.curenlyNumber(response.content.tongTientThanhToanBangTienMat),
                    app.giaoCaStaff.tongTientThanhToanBangChuyenKhoan = app.curenlyNumber(response.content.tongTientThanhToanBangChuyenKhoan),
                    app.giaoCaStaff.displayName = response.content.accountResponse.displayName,
                    app.giaoCaStaff.soDienThoai = response.content.accountResponse.soDienThoai,
                    app.giaoCaStaff.tongTienTrongCa = app.curenlyNumber(parseFloat(app.repleaPriceDouble(app.total)) + parseFloat(response.content.tongTientThanhToanBangChuyenKhoan))
            }
        },
        error: (error) => {
            console.log(error);
        }
    });

    //ket ca
    $(".confirm").click(() => {
        if ($("#tienPhatSinh").val() != 0 && $(".ghiChuPhatSinh").val() == "") {
            document.title = "Thông báo!";
            createAndShowToast("bg-warning", "Thông báo!", "Bạn có tiền phát sinh! Vui lòng nhập ghi chú phát sinh!");
        } else {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "http://localhost:8081/api/v1/staff/giao-ca/ket-ca",
                contentType: "application/json",
                data: JSON.stringify({
                    thoiGianKetCa: app.formatDateTimeStamps($("#NowTime").val()),
                    tienPhatSinh: $("#tienPhatSinh").val() == 0 ? $("#tienPhatSinh").val() : app.repleaPriceDouble($("#tienPhatSinh").val()),
                    ghiChuPhatSinh: $(".ghiChuPhatSinh").val() == "" ? null : $(".ghiChuPhatSinh").val(),
                    idNhanVienCaTiepTheo: $("#nhanVienNhanCaTiepTheo").val(),
                    tongTienMat: $("#tongTienMatTrongCa").val() == 0 ? $("#tongTienMatTrongCa").val() : app.repleaPriceDouble($("#tongTienMatTrongCa").val()),
                    tongTienTrongCa: app.repleaPriceDouble(app.giaoCaStaff.tongTienTrongCa),
                    tongTienKhac: $(".tienChuyenKhoan").val() == 0 ? $(".tienChuyenKhoan").val() : app.repleaPriceDouble($(".tienChuyenKhoan").val()),
                }),
                success: (response) => {
                    var confirm = false;
                    if (response.statusCode === 'OK') {
                        Swal.fire({
                            title: 'Bàn giao ca thành công!',
                            icon: 'success',
                            confirmButtonText: 'Xác nhận'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                confirm = true;
                                window.location.href = "/authentication/staff-login";
                            }
                        });
                        setTimeout(() => {
                            if (!confirm) {
                                window.location.href = "/authentication/staff-login";
                            }
                        }, 4000);
                        return;
                    }
                    createAndShowToast("bg-warning", "Thông báo!", "Lỗi!");
                    return;
                },
                error: (error) => {
                    console.log(error)
                }
            });
        }

    });
    //reset và kết ca
    $(".resetCa").click(() => {
        Swal.fire({
            title: "Bạn có chắc chắn thực hiện thao tác này?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
        }).then((result) => {
            if (result.isConfirmed) {
                if ($("#tienPhatSinh").val() != 0 && $(".ghiChuPhatSinh").val() == "") {
                    document.title = "Thông báo!";
                    createAndShowToast("bg-warning", "Thông báo!", "Bạn có tiền phát sinh! Vui lòng nhập ghi chú phát sinh!");
                } else {
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "http://localhost:8081/api/v1/staff/giao-ca/ket-ca",
                        contentType: "application/json",
                        data: JSON.stringify({
                            thoiGianKetCa: app.formatDateTimeStamps($("#NowTime").val()),
                            tienPhatSinh: $("#tienPhatSinh").val() == 0 ? $("#tienPhatSinh").val() : app.repleaPriceDouble($("#tienPhatSinh").val()),
                            ghiChuPhatSinh: $(".ghiChuPhatSinh").val() == "" ? null : $(".ghiChuPhatSinh").val(),
                            idNhanVienCaTiepTheo: $("#nhanVienNhanCaTiepTheo").val(),
                            tongTienMat: $("#tongTienMatTrongCa").val() == 0 ? $("#tongTienMatTrongCa").val() : app.repleaPriceDouble($("#tongTienMatTrongCa").val()),
                            tongTienTrongCa: app.repleaPriceDouble(app.giaoCaStaff.tongTienTrongCa),
                            thoiGianReset: app.formatDateTimeStamps($("#NowTime").val()),
                            tongTienMatRut: $("#tienMatRut").val() == 0 ? $("#tienMatRut").val() : app.repleaPriceDouble($("#tienMatRut").val()),
                            tongTienKhac: $(".tienChuyenKhoan").val() == 0 ? $(".tienChuyenKhoan").val() : app.repleaPriceDouble($(".tienChuyenKhoan").val()),
                        }),
                        success: (response) => {
                            var confirm = false;
                            if (response.statusCode === 'OK') {
                                sendMailThongKe();
                                Swal.fire({
                                    title: 'Rút tiền và Bàn giao ca thành công!',
                                    text: 'Hẹn gặp lại!',
                                    icon: 'success',
                                    confirmButtonText: 'OKE'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        confirm = true;
                                        window.location.href = "/authentication/staff-login";
                                    }
                                });
                                setTimeout(() => {
                                    if (!confirm) {
                                        window.location.href = "/authentication/staff-login";
                                    }
                                }, 4000);
                                return;
                            }
                            createAndShowToast("bg-warning", "Thông báo!", "Bàn giao ca thất bại!");
                            return;
                        },
                        error: (error) => {
                            console.log(error);
                            createAndShowToast("bg-warning", "Thông báo!", "Lỗi!");
                        }
                    });
                }
            }
        });


    });
});

function sendMailThongKe() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/static/send-mail-thong-ke",
    });
}

var app = new Vue({
    el: "#app",
    data: {
        giaoCaStaff: {
            id: null,
            thoiGianNhanCa: null,
            thoiGianHienTai: null,
            accountResponse: null,
            tienBanDau: 0,
            tongHoaDonDaThanhToan: 0,
            tongHoaDonChuaThanhToan: 0,
            tienPhatSinh: 0,
            ghiChuPhatSinh: null,
            accountResponseList: null,
            tongTienMatTrongCa: 0,
            tongTienThuTrongCa: 0,
            tongTientThanhToanBangTienMat: 0,
            tongTientThanhToanBangChuyenKhoan: 0,
            soDienThoai: null,
            displayName: null,
            tongTienTrongCa: 0,
        },
        isDisable: true,
        total: 0,
        isReset: true,
        isBanGiaoCa: false,
    },
    methods: {
        checkValid(event) {
            if(this.giaoCaStaff.tongTienMatTrongCa === 0){
                event.target.value = 0;
                createAndShowToast("bg-warning", "Thông báo!", "Xin lỗi, Bạn không đủ tiền!");
                return ;
            }
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                event.target.value = 0;
            }
            this.checkTrong(event);
            event.target.value = parseInt(event.target.value).toLocaleString(
                "vi-VN"
            );
            if (event.target.value === "" || event.target.value == 0) {
                this.total = this.curenlyNumber(app.giaoCaStaff.tongTienMatTrongCa);
                this.giaoCaStaff.tongTienTrongCa = this.curenlyNumber(parseFloat(this.repleaPriceDouble(this.total)));
                // this.giaoCaStaff.tongTienTrongCa = this.curenlyNumber(parseFloat(this.repleaPriceDouble(this.total)) + parseFloat(this.repleaPriceDouble(this.giaoCaStaff.tienBanDau)));
                this.isDisable = true;
            } else {
                var price = parseFloat(this.giaoCaStaff.tongTienMatTrongCa) - parseFloat(event.target.value.replace(/\./g, ""));
                if (price < 0) {
                    createAndShowToast("bg-warning", "Thông báo!", "Xin lỗi, Bạn không đủ tiền!");
                    return event.target.value = parseInt(this.repleaPriceDouble(event.target.value.slice(0, -1))).toLocaleString(
                        "vi-VN"
                    );
                }
                this.giaoCaStaff.tongTienTrongCa = this.curenlyNumber(parseFloat(this.repleaPriceDouble(this.giaoCaStaff.tongTienThuTrongCa)) + parseFloat(this.repleaPriceDouble(this.giaoCaStaff.tienBanDau)) - parseFloat(event.target.value.replace(/\./g, "")));
                this.total = this.curenlyNumber(price);
                this.isDisable = false;
            }
        },
        checkTrong(event) {
            if (event.target.value === "") {
                return (event.target.value = 0);
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                return (event.target.value = event.target.value.replace(
                    /\D/g,
                    ""
                ));
            }
        },
        curenlyNumber(number) {
            return number.toLocaleString("vi-VN");
        },
        formatDate(date) {
            return moment(date).format("HH:mm:ss DD-MM-YYYY");
        },
        formatDateTimeStamps(date) {
            return moment(date, "HH:mm:ss DD-MM-YYYY").format("YYYY-MM-DD HH:mm:ss");
        },
        repleaPriceDouble(price) {
            return price.replace(/\./g, "");
        },
        checkPriceReset(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                event.target.value = 0;
            }
            this.checkTrong(event);
            event.target.value = parseInt(event.target.value).toLocaleString(
                "vi-VN"
            );
            if(this.repleaPriceDouble(this.total) == 0){
                createAndShowToast("bg-warning", "Thông báo!", "Rất tiếc bạn chỉ có " + this.total + " VND");
                return event.target.value = 0;
            }
            if (parseInt(this.repleaPriceDouble(event.target.value)) > parseInt(this.repleaPriceDouble(this.total))) {
                createAndShowToast("bg-warning", "Thông báo!", "Rất tiếc bạn chỉ có " + this.total + " VND");
                return event.target.value = parseInt(this.repleaPriceDouble(event.target.value.slice(0, -1))).toLocaleString(
                    "vi-VN"
                );
                ;
            }
        }
    },

});