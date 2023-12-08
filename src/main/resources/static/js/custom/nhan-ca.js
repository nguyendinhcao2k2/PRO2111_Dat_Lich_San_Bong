
$(document).ready(function () {

    //Get account by login
    $.ajax({
        url: "http://localhost:8081/api/v1/staff/account",
        type: "GET",
        success: function (response) {
            if (response.statusCode === "OK") {
                app.account = response.content.displayName;
            }
        }
    });


    //nhan ca lam viec
    $(".xacNhanCaLam").click((event) => {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/api/v1/staff/giao-ca/khoi-tao-ca-lam",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                ghiChuPhatSinh: $(".ghiChuPhatSinh").val(),
                tienBanDau: parseInt(
                    $(".tienBanDau").val().replace(/\./g, "")
                ),
                thoiGianNhanCa: app.currentDateTime,
                idNhanVienTrongCa: "237c8426-54ff-44b4-b6c6-411fc92322de",
                trangThai: 0,
            }),
            success: function (response) {
                if (response.statusCode === "OK") {
                    $.ajax({
                        type: "GET",
                        url: "http://localhost:8081/api/v1/staff/giao-ca/view",
                        success: function (data) {
                            //đọc file html
                            // document.documentElement.innerHTML = data;
                            var confirm = false;
                            Swal.fire({
                                title: 'Nhận ca thành công!',
                                icon: 'success',
                                confirmButtonText: 'Xác nhận'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    confirm = true;
                                    window.location.href = "http://localhost:8081/api/v1/staff/view-dat-lich";
                                }
                            });
                            setTimeout(() => {
                                if (!confirm) {
                                    window.location.href = "http://localhost:8081/api/v1/staff/view-dat-lich";
                                }
                            }, 3000);
                            return;
                        }
                    });
                } else {
                    createAndShowToast("bg-danger","Thông báo!","Xin lỗi nhận ca thất bại!");
                }

            },
            error: function (e) {
                console.log(e);
            },
        });
    });
});
// vuejs
var app = new Vue({
    el: "#app",
    data: {
        amount500k: 0,
        amount200k: 0,
        amount100k: 0,
        amount50k: 0,
        amount20k: 0,
        amount10k: 0,
        amount5k: 0,
        amount2k: 0,
        amount1k: 0,
        tongTien: 0,
        currentDateTime: moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
        account: 'NULL',
    },
    methods: {
        validatePrice(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                event.target.value = 0;
            }
            try {
                this.checkTrong(event);
                event.target.value = parseInt(event.target.value).toLocaleString(
                    "vi-VN"
                );
            } catch (error) {
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
        checkSo(event) {
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                event.target.value = event.target.value.slice(0, 1);
            }
        },
        tinhTien(event, number) {
            this.checkSo(event);
            this.checkTrong(event);
            let formatNumber = parseFloat(number);
            let price = parseFloat(event.target.value) * formatNumber;
            if (formatNumber === 500000) {
                return (this.amount500k = price);
            }
            if (formatNumber === 200000) {
                return (this.amount200k = price);
            }
            if (formatNumber === 100000) {
                return (this.amount100k = price);
            }
            if (formatNumber === 50000) {
                return (this.amount50k = price);
            }
            if (formatNumber === 20000) {
                return (this.amount20k = price);
            }
            if (formatNumber === 10000) {
                return (this.amount10k = price);
            }
            if (formatNumber === 5000) {
                return (this.amount5k = price);
            }
            if (formatNumber === 2000) {
                return (this.amount2k = price);
            }
            if (formatNumber === 1000) {
                return (this.amount1k = price);
            }
        },
    },
    computed: {
        tongTienTrongCa(event) {
            let totalPrice =
                this.amount500k +
                this.amount200k +
                this.amount100k +
                this.amount50k +
                this.amount20k +
                this.amount10k +
                this.amount5k +
                this.amount2k +
                this.amount1k;
            this.tongTien = totalPrice;
            return this.tongTien.toLocaleString("vi-VN");
        },
    },
});

