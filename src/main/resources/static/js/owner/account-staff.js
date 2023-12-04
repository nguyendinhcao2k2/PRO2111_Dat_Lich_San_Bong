var tab1 = new Vue({
    el: ".tabAccount",
    data: {
        arrayAccount: [],
        totalPage: 0,
        curentPage: 0,
        searchSDT: "",
        countIndex: 0,
        account: {
            email: "",
            taiKhoan: "",
            matKhau: "",
            soDienThoai: "",
        },
    },
    methods: {
        confirmDeleteById(id) {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.deleteById(id);
                }
            });
        },
        deleteById(id) {
            $.ajax({
                type: "DELETE",
                url: "http://localhost:8081/api/v1/admin/account/delete/" + id,
                success: function (response) {
                    createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                    callApi("http://localhost:8081/api/v1/admin/account/find-all");
                },
                error: function (error) {
                    createAndShowToast("bg-danger","Thông báo!","Thao tác thất bại!");
                },
            });
        },
        checkRegex(value) {
            var regex =
                /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/;
            return regex.test(value);
        },
        confirmSave() {
            if (!this.checkRegex(this.account.soDienThoai)) {
                createAndShowToast("bg-warning","Thông báo!","Số điện thoại không đúng định dạng!");
                return;
            }
            var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if(!regex.test(this.account.email)){
                createAndShowToast("bg-warning","Thông báo!","Email không đúng định dạng!");
                return;
            }
            if(this.account.taiKhoan.length > 20){
                createAndShowToast("bg-warning","Thông báo!","Tài khoản vui lòng không quá 20 kí tự!");
                return;
            }
            if(this.account.matKhau.length < 6){
                createAndShowToast("bg-warning","Thông báo!","Mật khẩu tối thiểu 6 kí tự!");
                return;
            }
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.save();
                }
            });
        },
        save() {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/v1/admin/account/create",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    email: this.account.email,
                    taiKhoan: this.account.taiKhoan,
                    matKhau: this.account.matKhau,
                    displayName: this.account.email.split("@")[0],
                    soDienThoai: this.account.soDienThoai,
                }),
                success: function (response) {
                    if (response.statusCode == "OK") {
                        createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                        callApi(
                            "http://localhost:8081/api/v1/admin/account/find-all"
                        );
                        return;
                    }
                    if (response.statusCode == "ACCEPTED") {
                        createAndShowToast("bg-warning","Thông báo!","Đã tồn tại userName!");
                        return;
                    }
                },
                error: function (error) {
                    createAndShowToast("bg-danger","Thông báo!","Lỗi!");
                },
            });
        },
        search() {
            $.ajax({
                type: "GET",
                url:
                    "http://localhost:8081/api/v1/admin/account/search?sdt=" +
                    this.searchSDT,
                success: function (response) {
                    if (response.content.data.length == 0) {
                        callApi(
                            "http://localhost:8081/api/v1/admin/account/find-all"
                        );
                        tab1.countIndex = 0;
                        return;
                    }
                    tab1.arrayAccount = response.content.data;
                    tab1.totalPage = response.content.totalPages;
                    tab1.curentPage = response.content.currentPage;
                    tab1.countIndex = 1;
                },
                error: function (error) {
                    createAndShowToast("bg-danger","Thông báo!","Lỗi!");
                },
            });
        },
    },
});

$(document).ready(function () {
    callApi("http://localhost:8081/api/v1/admin/account/find-all");
    $(".nextPage").click(() => {
        if (parseInt(tab1.curentPage + 1) == parseInt(tab1.totalPage)) {
            return;
        }
        pageTion(parseInt(tab1.curentPage) + 1);
    });
    $(".previousPage").click(() => {
        if (parseInt(tab1.curentPage) == 0) {
            return;
        }
        pageTion(parseInt(tab1.curentPage) - 1);
    });
    pageSize();
});

function callApi(url) {
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            tab1.arrayAccount = response.content.data;
            tab1.totalPage = response.content.totalPages;
            tab1.curentPage = response.content.currentPage;
        },
        error: function (error) {
            createAndShowToast("bg-danger","Thông báo!","Lỗi!");
        },
    });
}

function pageTion(value) {
    var url = "http://localhost:8081/api/v1/admin/account/find-all";
    if (tab1.countIndex == 0) {
        url =
            "http://localhost:8081/api/v1/admin/account/find-all?page=" + value;
    } else {
        url =
            "http://localhost:8081/api/v1/admin/account/search?page=" +
            value +
            "&sdt=" +
            tab1.searchSDT;
    }
    callApi(url);
}

function pageSize() {
    $(".pageSizeSelect").change((event) => {
        if (tab1.countIndex == 0) {
            url =
                "http://localhost:8081/api/v1/admin/account/find-all?pageSize=" +
                parseInt(event.target.value);
        } else {
            url =
                "http://localhost:8081/api/v1/admin/account/search?pageSize=" +
                parseInt(event.target.value) +
                "&sdt=" +
                tab1.searchSDT;
        }
        callApi(url);
    });
}

