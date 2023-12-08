var tab2 = new Vue({
    el: ".tabLoaiSan",
    data: {
        sanBong: {
            tenSanBong: "",
            loaiSan: "",
            trangThai: 0,
        },
        sanBongUpdate: {
            id: "",
            tenSanBong: "",
            loaiSan: "",
            trangThai: 0,
        },
        url: "http://localhost:8081/api/v1/admin/san-bong/find-all",
        indexSearch: 0,
        searchModelTenSB: "",
        arrSanBong: [],
        arrLoaiSan: [],
        currentPageSB: 0,
        totalPageSB: 0,
        pageSize: 0,
        loaiSan: {
            tenLoaiSan: "",
            giaSan: 0,
            id: null,
        },
    },
    methods: {
        //chung
        currenlyNumberSB(number){
           return  new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
        },
        //chung
        // loai san start
        confirmCreateLS() {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    if (tab2.loaiSan.id != null) {
                        this.updateLoaiSan();
                    } else {
                        this.saveLoaiSan();
                    }
                }
            });
        },
        saveLoaiSan() {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/v1/admin/loai-san/create",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    tenLoaiSan: tab2.loaiSan.tenLoaiSan,
                    giaSan:parseInt(tab2.loaiSan.giaSan.replace(/\D/g, "")),
                }),
                success: function (response) {
                    createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                    callApiLoaiSan();
                    callApiSanBong("http://localhost:8081/api/v1/admin/san-bong/find-all");
                },
            });
        },
        validationPriceLS(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                event.target.value = 0;
            }

            if (event.target.value == "" || event.target.value == null) {
                this.loaiSan.giaSan = 0;
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                event.target.value = event.target.value.replace(/\D/g, "");
            }
            this.loaiSan.giaSan = this.currenNumber(event.target.value);
        },
        currenNumber(number) {
            return parseInt(number).toLocaleString("vi-VN");
        },
        confirmDeleteLoaiSan(id) {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.deleteLoaiSan(id);
                }
            });
        },
        deleteLoaiSan(id) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/loai-san/delete/" + id,
                success: function (response) {
                    createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                    callApiLoaiSan();
                    callApiSanBong("http://localhost:8081/api/v1/admin/san-bong/find-all");
                },
            });
        },
        detailLoaiSan(id) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/loai-san/find-by/" + id,
                success: function (response) {
                    tab2.loaiSan.tenLoaiSan = response.content.tenLoaiSan;
                    tab2.loaiSan.id = response.content.id;
                    tab2.loaiSan.giaSan =
                        response.content.giaSan.toLocaleString("vi-VN");
                },
            });
        },
        updateLoaiSan() {
            $.ajax({
                type: "PUT",
                url: "http://localhost:8081/api/v1/admin/loai-san/update",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    tenLoaiSan: tab2.loaiSan.tenLoaiSan,
                    giaSan: parseInt(tab2.loaiSan.giaSan.replace(/\D/g, "")),
                    id: tab2.loaiSan.id,
                }),
                success: function (response) {
                    createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                    callApiLoaiSan();
                    callApiSanBong("http://localhost:8081/api/v1/admin/san-bong/find-all");
                },
            });
        },
        themNhanh() {
            tab2.loaiSan.tenLoaiSan = "";
            tab2.loaiSan.id = null;
            tab2.loaiSan.giaSan = 0;
        },
        // loai san end
        //  san bong start
        findByIdLoaiSan(id) {
            var result = this.arrLoaiSan.find((el) => el.id == id);
            return result ? result : "Không tồn tại";
        },
        confirmSanBongSave() {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.createSanBong();
                }
            });
        },
        createSanBong() {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/v1/admin/san-bong/create",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    tenSanBong: this.sanBong.tenSanBong,
                    idLoaiSan: this.sanBong.loaiSan,
                    trangThai: parseInt(this.sanBong.trangThai),
                    giaSan: this.findByIdLoaiSan(this.sanBong.loaiSan).giaSan,
                }),
                success: function (response) {
                    if (response.statusCode === "ACCEPTED") {
                        createAndShowToast("bg-warning","Thông báo!","Tên sân bóng đã tồn tại");
                        return;
                    }
                    createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                    callApiSanBong(tab2.url);
                },
            });
        },
        confirmDeleteSanBong(id) {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.deleteSanBong(id);
                }
            });

        },
        deleteSanBong(id) {
            $.ajax({
                type: "DELETE",
                url: "http://localhost:8081/api/v1/admin/san-bong/delete/" + id,
                success: function (response) {
                    createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                    callApiSanBong(tab2.url);
                },error: function (error) {
                    console.log(error);
                    createAndShowToast("bg-danger","Thông báo!","Lỗi!");
                }
            });
        },
        detailSanBong(id) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/san-bong/find-by/" + id,
                success: function (response) {
                    tab2.sanBongUpdate.tenSanBong = response.content.tenSanBong;
                    tab2.sanBongUpdate.loaiSan = response.content.idLoaiSan;
                    tab2.sanBongUpdate.trangThai = response.content.trangThai;
                    tab2.sanBongUpdate.id = response.content.id;
                },
                error: function (error) {
                    console.log(error);
                    createAndShowToast("bg-danger","Thông báo!","Lỗi!");
                }
            });
        },
        confirmUpdate() {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.updateSanBong();
                }
            });
        },
        updateSanBong() {
            $.ajax({
                type: "PUT",
                url: "http://localhost:8081/api/v1/admin/san-bong/update",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    id: this.sanBongUpdate.id,
                    tenSanBong: this.sanBongUpdate.tenSanBong,
                    idLoaiSan: this.sanBongUpdate.loaiSan,
                    trangThai: parseInt(this.sanBongUpdate.trangThai),
                    giaSan: this.findByIdLoaiSan(this.sanBongUpdate.loaiSan).giaSan,
                }),
                success: function (response) {
                    if (response.statusCode === "ACCEPTED") {
                        createAndShowToast("bg-warning","Thông báo!","Tên sân bóng đã tồn tại");
                        return;
                    }
                    createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                    $("#detailSanBong").modal("toggle");
                    $(".modal-backdrop").remove();
                    callApiSanBong(tab2.url);
                },
                error: function (error) {
                    console.log(error);
                    createAndShowToast("bg-danger","Thông báo!","Lỗi!");
                }
            });
        },
        searchByTenSanBong() {
            $.ajax({
                type: "GET",
                url:
                    "http://localhost:8081/api/v1/admin/san-bong/search?name=" +
                    this.searchModelTenSB,
                success: function (response) {
                    if (parseInt(response.content.data.length) == 0) {
                        this.indexSearch = 0;
                        callApiSanBong(tab2.url);
                        return;
                    }
                    tab2.arrSanBong = response.content.data;
                    tab2.totalPageSB = response.content.totalPages;
                    tab2.currentPageSB = response.content.currentPage;
                    tab2.pageSize = response.content.pageSize;
                    tab2.indexSearch = 1;
                },
                error: function (error) {
                    console.log(error);
                    createAndShowToast("bg-danger","Thông báo!","Lỗi!");
                }
            });
        },
        //  san bong end
    },
});
$(document).ready(function () {
    var url = "http://localhost:8081/api/v1/admin/san-bong/find-all";
    callApiLoaiSan();
    callApiSanBong(url);
    pageDirection();
    pageSizeTion();
});

function callApiSanBong(url) {
    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            tab2.arrSanBong = response.content.data;
            tab2.totalPageSB = response.content.totalPages;
            tab2.currentPageSB = response.content.currentPage;
            tab2.pageSize = response.content.pageSize;
        },
    });
}

function callApiLoaiSan() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/admin/loai-san/find-all",
        success: function (response) {
            tab2.arrLoaiSan = response.content;
        },

    });
}

function pageDirection() {
    $(".nextPageSB").click(() => {
        if (parseInt(tab2.currentPageSB) + 1 == parseInt(tab2.totalPageSB)) {
            return;
        }
        pageTion(parseInt(tab2.currentPageSB) + 1);
    });
    $(".previousPageSB").click(() => {
        if (parseInt(tab2.currentPageSB) == 0) {
            return;
        }
        pageTion(parseInt(tab2.currentPageSB) - 1);
    });
}

function pageTion(value) {
    var url = "";
    if (parseInt(tab2.indexSearch) == 0) {
        url =
            "http://localhost:8081/api/v1/admin/san-bong/find-all?page=" +
            value;
    } else {
        url =
            "http://localhost:8081/api/v1/admin/san-bong/search?name=" +
            tab2.searchModelTenSB +
            "&page=" +
            value;
    }
    callApiSanBong(url);
}

function pageSizeTion() {
    $(".pageSizeSelect").change((event) => {
        var url = "";
        if (parseInt(tab2.indexSearch) == 0) {
            url =
                "http://localhost:8081/api/v1/admin/san-bong/find-all?pageSize=" +
                parseInt(event.target.value);
        } else {
            url =
                "http://localhost:8081/api/v1/admin/san-bong/search?name=" +
                tab2.searchModelTenSB +
                "&pageSize=" +
                parseInt(event.target.value);
        }
        callApiSanBong(url);
    });
}
