<!-- tab 6 start -->
var urlNuocUong = "http://localhost:8081/api/v1/admin/nuoc-uong/find-all";
var tabNuocUong = new Vue({
    el: ".tabNuocUong",
    data: {
        listNuocUong: [],
        thongBao: "Bạn có chắc chắn thực hiện thao tác?",
        success: "Thao tác thành công!",
        error: "Thao tác thất bại!",
        imageFile: null,
        isLoadingNuocUong: true,
        indexSearchNuocUong: 0,
        pageNumberNuocUong: 0,
        pageSizeNuocUong: 0,
        totalPageNuocUong: 0,
        tenNuocUongSearch: "",
        NuocUong: {
            tenNuocUong: "",
            soLuong: 0,
            donGia: 0,
            image: null,
        },
        detailNuocUongEntity: {
            id: "",
            tenNuocUong: "",
            soLuong: 0,
            donGia: 0,
            image: null,
            trangThai: 0,
        },
    },
    methods: {
        confirmSaveNuocUong() {
            Swal.fire({
                title: tabNuocUong.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    try {
                        $.ajax({
                            type: "POST",
                            url: "http://localhost:8081/api/v1/admin/nuoc-uong/save",
                            contentType: "application/json",
                            data: JSON.stringify({
                                tenNuocUong: tabNuocUong.NuocUong.tenNuocUong,
                                soLuong: tabNuocUong.NuocUong.soLuong,
                                image: tabNuocUong.NuocUong.image,
                                donGia: tabNuocUong.NuocUong.donGia == 0 ? 0 : tabNuocUong.NuocUong.donGia.replace(/\D/g, ''),
                            }),
                            success: function (response) {
                                if (response.statusCode == 'OK') {
                                    $('[rel="formCreateNuocUong"]').trigger('reset');
                                    tabNuocUong.NuocUong.tenNuocUong = "";
                                    tabNuocUong.NuocUong.soLuong = 0;
                                    tabNuocUong.NuocUong.donGia = 0;
                                    createAndShowToast("bg-success", "Thông báo!", tabNuocUong.success);
                                    callApiGetNuocUong(urlNuocUong);
                                } else {
                                    createAndShowToast("bg-danger", "Thông báo!", tabNuocUong.error);
                                }
                            },
                        });
                    } catch (error) {
                        createAndShowToast("bg-danger", "Thông báo!", tabNuocUong.error);
                        console.log(error);
                    }

                }
            })
        },
        updateNuocUong() {
            Swal.fire({
                title: tabNuocUong.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    try {
                        $.ajax({
                            type: "PUT",
                            url: "http://localhost:8081/api/v1/admin/nuoc-uong/update",
                            contentType: "application/json",
                            data: JSON.stringify({
                                id: tabNuocUong.detailNuocUongEntity.id,
                                tenNuocUong: tabNuocUong.detailNuocUongEntity.tenNuocUong,
                                soLuong: tabNuocUong.detailNuocUongEntity.soLuong,
                                image: tabNuocUong.detailNuocUongEntity.image == "" ? null : tabNuocUong.detailNuocUongEntity.image,
                                donGia: tabNuocUong.detailNuocUongEntity.donGia == 0 ? 0 : tabNuocUong.detailNuocUongEntity.donGia.replace(/\D/g, ''),
                                trangThai: tabNuocUong.detailNuocUongEntity.trangThai,
                            }),
                            success: function (response) {
                                if (response.statusCode === 'OK') {
                                    callApiGetNuocUong(urlNuocUong);
                                    createAndShowToast("bg-success", "Thông báo!", tabNuocUong.success);
                                    $(".huyModalUpdate").click();
                                    $('[rel="formUpdateNuocUong"]').trigger('reset');
                                } else {
                                    createAndShowToast("bg-danger", "Thông báo!", tabNuocUong.error);
                                }
                            },
                        });
                    } catch (error) {
                        createAndShowToast("bg-danger", "Thông báo!", tabNuocUong.error);
                        console.log(error);
                    }

                }
            })
        },
        deleteNuocUong(id) {
            Swal.fire({
                title: tabNuocUong.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    try {
                        $.ajax({
                            type: "DELETE",
                            url: "http://localhost:8081/api/v1/admin/nuoc-uong/delete/" + id,
                            success: function (response) {
                                if (response.statusCode === 'OK') {
                                    callApiGetNuocUong(urlNuocUong);
                                    createAndShowToast("bg-success", "Thông báo!", tabNuocUong.success);
                                } else {
                                    createAndShowToast("bg-danger", "Thông báo!", tabNuocUong.error);
                                }
                            },
                        });
                    } catch (error) {
                        createAndShowToast("bg-danger", "Thông báo!", tabNuocUong.error);
                        console.log(error);
                    }

                }
            })
        },
        handelImgNuocUong(event) {

            const file = event.target.files[0]; // Sử dụng event.target.files thay vì truy cập trực tiếp biến fileInput

            if (file && file.type.startsWith("image/")) {
                // Đây là một tệp ảnh, bạn có thể thực hiện xử lý tiếp theo ở đây
                const render = new FileReader();
                render.onload = function () {
                    const base64String = render.result.split(',')[1];
                    if (event.target.id === 'idFileAnhCreateNuocUong') {
                        tabNuocUong.NuocUong.image = base64String;
                    } else {
                        tabNuocUong.detailNuocUongEntity.image = base64String;
                    }

                }
                render.readAsDataURL(file);
            } else {
                // Đây không phải là một tệp ảnh, thông báo hoặc xử lý tương ứng
                createAndShowToast("bg-warning", "Thông báo!", "Đây không phải là ảnh xin chọn lại!");
                event.target.value = ""; // Xóa giá trị tệp đã chọn (nếu muốn)
            }
            //image check
        }
        ,
        handelExcelNuocUong(event) {
            // Thay đổi cách bạn lấy selectedFile
            this.selectedFile = event.target.files[0];
            const formData = new FormData();
            formData.append("file", this.selectedFile);

            // Kiểm tra loại tệp
            const fileInput = event.target; // Thay đổi cách bạn lấy fileInput
            const file = event.target.files[0];

            // Lấy phần mở rộng của tệp (ví dụ: ".xlsx", ".xls")
            const fileExtension = file.name.split('.').pop().toLowerCase();

            if (file && (fileExtension === 'xlsx' || fileExtension === 'xls')) {
                // Đây là một tệp Excel, bạn có thể thực hiện xử lý tiếp theo ở đây
                Swal.fire({
                    title: tabNuocUong.thongBao,
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonText: 'Xác nhận',
                }).then((result) => {
                    if (result.isConfirmed) {
                        $.ajax({
                            type: "POST",
                            url: "http://localhost:8081/api/v1/admin/nuoc-uong/import",
                            dataType: "json",
                            contentType: false,
                            processData: false,
                            data: formData,
                            success: function (reponse) {
                                if (reponse.statusCode === 'OK') {
                                    callApiGetNuocUong(urlNuocUong);
                                    createAndShowToast("bg-success", "Thông báo!", "Import file thành công!");
                                    event.target.value = "";
                                } else {
                                    createAndShowToast("bg-warning", "Thông báo!", "Vui lòng để đúng thứ tự các trường và kiểu dữ liệu(Với đơn giá có thể tùy biến) như file export excel! Không bạn sẽ không import được đâu");
                                    event.target.value = "";
                                }

                            },
                            error: function (error) {
                                console.log(error);
                                event.target.value = "";
                                createAndShowToast("bg-warning", "Thông báo!", "Vui lòng để đúng thứ tự các trường như file export excel! Không cần cột STT nhưng phải đúng thứ tự các trường!");
                            }
                        })
                    } else {
                        $(".importExcelNuocUong").val(null);
                    }
                });

            } else {
                // Đây không phải là một tệp Excel, thông báo hoặc xử lý tương ứng
                createAndShowToast("bg-warning", "Thông báo!", "Đây không phải là một tệp Excel, vui lòng chọn lại!");
                event.target.value = ""; // Xóa giá trị tệp đã chọn (nếu muốn)
            }
        }
        ,
        uploadImage() {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/v1/admin/nuoc-uong/upload-image",
                data: tabNuocUong.imageFile,
                processData: false, // Important!
                contentType: false, // Important!
                success: function (response) {
                    console.log(response);
                },
                error: function (error) {
                    console.error("Error uploading file: ", error);
                    $(".importExcelNuocUong").val(null);
                },
            });
        }
        ,
        handleSoLuongNuocUong(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                if (event.target.id === 'idSoLuongCreateNuocUong') {
                    tabNuocUong.NuocUong.soLuong = 0;
                } else {
                    tabNuocUong.detailNuocUongEntity.soLuong = 0;
                }
            }

            if (event.target.value === "" || event.target.value === null) {
                if (event.target.id === 'idSoLuongCreateNuocUong') {
                    tabNuocUong.NuocUong.soLuong = 0;
                } else {
                    tabNuocUong.detailNuocUongEntity.soLuong = 0;
                }
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                if (event.target.id === 'idSoLuongCreateNuocUong') {
                    tabNuocUong.NuocUong.soLuong = event.target.value.replace(/\D/g, '');
                } else {
                    tabNuocUong.detailNuocUongEntity.soLuong = event.target.value.replace(/\D/g, '');
                }
                return;
            }
        }
        ,
        handleDonGiaNuocUong(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                if (event.target.id === 'idDonGiaCreate') {
                    tabNuocUong.NuocUong.donGia = 0;
                } else {
                    tabNuocUong.detailNuocUongEntity.donGia = 0;
                }
            }

            if (event.target.value === "" || event.target.value === null) {
                if (event.target.id === 'idDonGiaCreate') {
                    tabNuocUong.NuocUong.donGia = 0;
                } else {
                    tabNuocUong.detailNuocUongEntity.donGia = 0;
                }

                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                if (event.target.id === 'idDonGiaCreate') {
                    tabNuocUong.NuocUong.donGia = event.target.value.replace(/\D/g, '');
                    tabNuocUong.NuocUong.donGia = parseInt(tabNuocUong.NuocUong.donGia).toLocaleString("vi-VN");
                } else {
                    tabNuocUong.detailNuocUongEntity.donGia = event.target.value.replace(/\D/g, '');
                    tabNuocUong.detailNuocUongEntity.donGia = parseInt(tabNuocUong.detailNuocUongEntity.donGia).toLocaleString("vi-VN");
                }

                return;
            }
            if (event.target.id === 'idDonGiaCreate') {
                tabNuocUong.NuocUong.donGia = parseInt(event.target.value).toLocaleString("vi-VN");
                return;
            }
            tabNuocUong.detailNuocUongEntity.donGia = parseInt(event.target.value).toLocaleString("vi-VN");
        }
        ,
        imgSrcNuocUong(value) {
            return `data:image/png;base64,${value}`;
        },
        detailNuocUong(id) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/nuoc-uong/find/" + id,
                success: function (response) {
                    tabNuocUong.detailNuocUongEntity.id = response.content.id;
                    tabNuocUong.detailNuocUongEntity.tenNuocUong = response.content.tenNuocUong;
                    tabNuocUong.detailNuocUongEntity.donGia = tabNuocUong.currenlyNumBerNuocUong(parseInt(response.content.donGia));
                    tabNuocUong.detailNuocUongEntity.soLuong = response.content.soLuong;
                    tabNuocUong.detailNuocUongEntity.image = response.content.image;
                    tabNuocUong.detailNuocUongEntity.trangThai = response.content.trangThai;
                },
                error: function (error) {
                    console.log(error);
                }
            })
        },
        currenlyNumBerNuocUong(number) {
            return number.toLocaleString("vi-VN");
        },
        tiemKiemTheoTenNuocUong(event) {

            var url = "http://localhost:8081/api/v1/admin/nuoc-uong/find-by-name?tenNuocUong=" + event.target.value;
            callApiGetNuocUong(url);
            tabNuocUong.indexSearchNuocUong = 1;

            if (event.target.value === "" || event.target.value == null) {
                tabNuocUong.indexSearchNuocUong = 0;
                callApiGetNuocUong("http://localhost:8081/api/v1/admin/nuoc-uong/find-all");
            }
        },
        pageTionNU(value) {
            if (parseInt(tabNuocUong.indexSearchNuocUong) === 1) {
                urlNuocUong = "http://localhost:8081/api/v1/admin/nuoc-uong/find-by-name?tenNuocUong=" +
                    tabNuocUong.tenNuocUongSearch +
                    "&page=" +
                    value + "&size=" + tabNuocUong.pageSizeNuocUong;
            } else {
                urlNuocUong = "http://localhost:8081/api/v1/admin/nuoc-uong/find-all?page=" + value + "&size=" + tabNuocUong.pageSizeNuocUong;

            }
            callApiGetNuocUong(urlNuocUong);
        },
        nextPageNU() {
            if (parseInt(tabNuocUong.pageNumberNuocUong) + 1 == parseInt(tabNuocUong.totalPageNuocUong)) {
                return;
            }
            this.pageTionNU(parseInt(tabNuocUong.pageNumberNuocUong) + 1);
        },
        previuosNU() {
            if (parseInt(tabNuocUong.pageNumberNuocUong) == 0) {
                return;
            }
            this.pageTionNU(parseInt(tabNuocUong.pageNumberNuocUong) - 1);
        },
        pageSizeSelectNuocUong(event) {
            if (parseInt(tabNuocUong.indexSearchNuocUong) === 1) {
                urlNuocUong = "http://localhost:8081/api/v1/admin/nuoc-uong/find-by-name?tenNuocUong=" +
                    tabNuocUong.tenNuocUongSearch +
                    "&size=" + event.target.value;
            } else {
                urlNuocUong = "http://localhost:8081/api/v1/admin/nuoc-uong/find-all?size=" + event.target.value;

            }
            callApiGetNuocUong(urlNuocUong);
        }
    },

});
$(document).ready(function () {
    callApiGetNuocUong(urlNuocUong);
    exportExcelNuocUong();
});

function callApiGetNuocUong(url) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function (response) {
            if (response.content.data <= 0) {
                tabNuocUong.pageNumberNuocUong = -1;
                tabNuocUong.pageSizeNuocUong = 0;
                tabNuocUong.totalPageNuocUong = 0;
                tabNuocUong.listNuocUong = [];
                tabNuocUong.isLoadingNuocUong = false;
                return;
            }
            tabNuocUong.listNuocUong = response.content.data;
            tabNuocUong.isLoadingNuocUong = false;
            tabNuocUong.pageSizeNuocUong = response.content.pageSize;
            tabNuocUong.pageNumberNuocUong = response.content.currentPage;
            tabNuocUong.totalPageNuocUong = response.content.totalPages;
        },
        error: function (error) {
            console.log(error);
            tabNuocUong.isLoadingNuocUong = false;
        },
    });
}

function exportExcelNuocUong() {
    $(".exportNuocUong").click(() => {
        window.location.href = "http://localhost:8081/api/v1/admin/nuoc-uong/export";
    });
}

<!-- tab 6 end -->