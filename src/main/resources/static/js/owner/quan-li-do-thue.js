<!-- tab 6 start -->
var urlDoThue = "http://localhost:8081/api/v1/admin/do-thue/find-all";
var tabDoThue = new Vue({
    el: ".tabDoThue",
    data: {
        listDoThue: [],
        thongBao: "Bạn có chắc chắn thực hiện thao tác?",
        success: "Thao tác thành công!",
        error: "Thao tác thất bại!",
        imageFile: null,
        isLoading: true,
        indexSearchDoThue: 0,
        pageNumberDoThue: 0,
        pageSizeDoThue: 0,
        totalPageDoThue: 0,
        tenDoThueSearch: "",
        doThue: {
            tenDoThue: "",
            soLuong: 0,
            donGia: 0,
            image: null,
        },
        detailDoThueEntity: {
            id: "",
            tenDoThue: "",
            soLuong: 0,
            donGia: 0,
            image: null,
            trangThai: 0,
        },
    },
    methods: {
        confirmSaveDoThue() {
            Swal.fire({
                title: tabDoThue.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    try {
                        $.ajax({
                            type: "POST",
                            url: "http://localhost:8081/api/v1/admin/do-thue/save",
                            contentType: "application/json",
                            data: JSON.stringify({
                                tenDoThue: tabDoThue.doThue.tenDoThue,
                                soLuong: tabDoThue.doThue.soLuong,
                                image: tabDoThue.doThue.image,
                                donGia: tabDoThue.doThue.donGia == 0 ? 0 : tabDoThue.doThue.donGia.replace(/\D/g, ''),
                            }),
                            success: function (response) {
                                if (response.statusCode == 'OK') {
                                    $('[rel="formCreate"]').trigger('reset');
                                    tabDoThue.doThue.tenDoThue = "";
                                    tabDoThue.doThue.soLuong = 0;
                                    tabDoThue.doThue.donGia = 0;
                                    createAndShowToast("bg-success", "Thông báo!", tabDoThue.success);
                                    callApiGetDoThue(urlDoThue);
                                } else {
                                    createAndShowToast("bg-danger", "Thông báo!", tabDoThue.error);
                                }
                            },
                        });
                    } catch (error) {
                        createAndShowToast("bg-danger", "Thông báo!", tabDoThue.error);
                        console.log(error);
                    }

                }
            })
        },
        updateDoThue() {
            Swal.fire({
                title: tabDoThue.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    try {
                        $.ajax({
                            type: "PUT",
                            url: "http://localhost:8081/api/v1/admin/do-thue/update",
                            contentType: "application/json",
                            data: JSON.stringify({
                                id: tabDoThue.detailDoThueEntity.id,
                                tenDoThue: tabDoThue.detailDoThueEntity.tenDoThue,
                                soLuong: tabDoThue.detailDoThueEntity.soLuong,
                                image: tabDoThue.detailDoThueEntity.image == ""?null:tabDoThue.detailDoThueEntity.image,
                                donGia: tabDoThue.detailDoThueEntity.donGia == 0 ? 0 : tabDoThue.detailDoThueEntity.donGia.replace(/\D/g, ''),
                                trangThai: tabDoThue.detailDoThueEntity.trangThai,
                            }),
                            success: function (response) {
                                if (response.statusCode === 'OK') {
                                    callApiGetDoThue(urlDoThue);
                                    createAndShowToast("bg-success", "Thông báo!", tabDoThue.success);
                                    $(".huyModalUpdate").click();
                                    $('[rel="formUpdate"]').trigger('reset');
                                } else {
                                    createAndShowToast("bg-danger", "Thông báo!", tabDoThue.error);
                                }
                            },
                        });
                    } catch (error) {
                        createAndShowToast("bg-danger", "Thông báo!", tabDoThue.error);
                        console.log(error);
                    }

                }
            })
        },
        deleteDoThue(id) {
            Swal.fire({
                title: tabDoThue.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    try {
                        $.ajax({
                            type: "DELETE",
                            url: "http://localhost:8081/api/v1/admin/do-thue/delete/" + id,
                            success: function (response) {
                                if (response.statusCode === 'OK') {
                                    callApiGetDoThue(urlDoThue);
                                    createAndShowToast("bg-success", "Thông báo!", tabDoThue.success);
                                } else {
                                    createAndShowToast("bg-danger", "Thông báo!", tabDoThue.error);
                                }
                            },
                        });
                    } catch (error) {
                        createAndShowToast("bg-danger", "Thông báo!", tabDoThue.error);
                        console.log(error);
                    }

                }
            })
        },
        handelImg(event) {

            const file = event.target.files[0]; // Sử dụng event.target.files thay vì truy cập trực tiếp biến fileInput

            if (file && file.type.startsWith("image/")) {
                // Đây là một tệp ảnh, bạn có thể thực hiện xử lý tiếp theo ở đây
                const render = new FileReader();
                render.onload = function () {
                    const base64String = render.result.split(',')[1];
                    if (event.target.id === 'idFileAnhCreate') {
                        tabDoThue.doThue.image = base64String;
                    } else {
                        tabDoThue.detailDoThueEntity.image = base64String;
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
        handelExcel(event) {
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
                    title: tabDoThue.thongBao,
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonText: 'Xác nhận',
                }).then((result) => {
                    if (result.isConfirmed) {
                        $.ajax({
                            type: "POST",
                            url: "http://localhost:8081/api/v1/admin/do-thue/import",
                            dataType: "json",
                            contentType: false,
                            processData: false,
                            data: formData,
                            success: function (reponse) {
                                if (reponse.statusCode === 'OK') {
                                    callApiGetDoThue(urlDoThue);
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
                        $(".importExcelDoThue").val(null);
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
                url: "http://localhost:8081/api/v1/admin/do-thue/upload-image",
                data: tabDoThue.imageFile,
                processData: false, // Important!
                contentType: false, // Important!
                success: function (response) {
                    console.log(response);
                },
                error: function (error) {
                    console.error("Error uploading file: ", error);
                    $(".importExcelDoThue").val(null);
                },
            });
        }
        ,
        handleSoLuong(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                if (event.target.id === 'idSoLuongCreate') {
                    tabDoThue.doThue.soLuong = 0;
                } else {
                    tabDoThue.detailDoThueEntity.soLuong = 0;
                }
                return;
            }
            if (event.target.value === "" || event.target.value === null) {
                if (event.target.id === 'idSoLuongCreate') {
                    tabDoThue.doThue.soLuong = 0;
                } else {
                    tabDoThue.detailDoThueEntity.soLuong = 0;
                }
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                if (event.target.id === 'idSoLuongCreate') {
                    tabDoThue.doThue.soLuong = event.target.value.replace(/\D/g, '');
                } else {
                    tabDoThue.detailDoThueEntity.soLuong = event.target.value.replace(/\D/g, '');
                }
                return;
            }
        }
        ,
        handleDonGia(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                if (event.target.id === 'idDonGiaCreate') {
                    tabDoThue.doThue.donGia = 0;
                } else {
                    tabDoThue.detailDoThueEntity.donGia = 0;
                }
                return;
            }
            if (event.target.value === "" || event.target.value === null) {
                if (event.target.id === 'idDonGiaCreate') {
                    tabDoThue.doThue.donGia = 0;
                } else {
                    tabDoThue.detailDoThueEntity.donGia = 0;
                }

                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                if (event.target.id === 'idDonGiaCreate') {
                    tabDoThue.doThue.donGia = event.target.value.replace(/\D/g, '');
                    tabDoThue.doThue.donGia = parseInt(tabDoThue.doThue.donGia).toLocaleString("vi-VN");
                } else {
                    tabDoThue.detailDoThueEntity.donGia = event.target.value.replace(/\D/g, '');
                    tabDoThue.detailDoThueEntity.donGia = parseInt(tabDoThue.detailDoThueEntity.donGia).toLocaleString("vi-VN");
                }

                return;
            }
            if (event.target.id === 'idDonGiaCreate') {
                tabDoThue.doThue.donGia = parseInt(event.target.value).toLocaleString("vi-VN");
                return;
            }
            tabDoThue.detailDoThueEntity.donGia = parseInt(event.target.value).toLocaleString("vi-VN");
        }
        ,
        imgSrc(value) {
            return `data:image/png;base64,${value}`;
        },
        detailDoThue(id) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/do-thue/find/" + id,
                success: function (response) {
                    tabDoThue.detailDoThueEntity.id = response.content.id;
                    tabDoThue.detailDoThueEntity.tenDoThue = response.content.tenDoThue;
                    tabDoThue.detailDoThueEntity.donGia = tabDoThue.currenlyNumBerDoThue(parseInt(response.content.donGia));
                    tabDoThue.detailDoThueEntity.soLuong = response.content.soLuong;
                    tabDoThue.detailDoThueEntity.image = response.content.image;
                    tabDoThue.detailDoThueEntity.trangThai = response.content.trangThai;
                },
                error: function (error) {
                    console.log(error);
                }
            })
        },
        currenlyNumBerDoThue(number) {
            return number.toLocaleString("vi-VN");
        },
        tiemKiemTheoTenDoThue(event) {

            var url = "http://localhost:8081/api/v1/admin/do-thue/find-by-name?tenDoThue=" + event.target.value;
            callApiGetDoThue(url);
            tabDoThue.indexSearchDoThue = 1;
            if (event.target.value === "" || event.target.value == null) {
                tabDoThue.indexSearchDoThue = 0;
                callApiGetDoThue("http://localhost:8081/api/v1/admin/do-thue/find-all");
            }
        },
        pageTionDT(value) {
            if (parseInt(tabDoThue.indexSearchDoThue) === 1) {
                urlDoThue = "http://localhost:8081/api/v1/admin/do-thue/find-by-name?tenDoThue=" +
                    tabDoThue.tenDoThueSearch +
                    "&page=" +
                    value + "&size=" + tabDoThue.pageSizeDoThue;
            } else {
                urlDoThue = "http://localhost:8081/api/v1/admin/do-thue/find-all?page=" + value + "&size=" + tabDoThue.pageSizeDoThue;

            }
            callApiGetDoThue(urlDoThue);
        },
        nextPageDT() {
            if (parseInt(tabDoThue.pageNumberDoThue) + 1 == parseInt(tabDoThue.totalPageDoThue)) {
                return;
            }
            this.pageTionDT(parseInt(tabDoThue.pageNumberDoThue) + 1);
        },
        previuosDT() {
            if (parseInt(tabDoThue.pageNumberDoThue) == 0) {
                return;
            }
            this.pageTionDT(parseInt(tabDoThue.pageNumberDoThue) - 1);
        },
        pageSizeSelectDoThue(event) {
            if (parseInt(tabDoThue.indexSearchDoThue) === 1) {
                urlDoThue = "http://localhost:8081/api/v1/admin/do-thue/find-by-name?tenDoThue=" +
                    tabDoThue.tenDoThueSearch +
                    "&size=" + event.target.value;
            } else {
                urlDoThue = "http://localhost:8081/api/v1/admin/do-thue/find-all?size=" + event.target.value;

            }
            callApiGetDoThue(urlDoThue);
        }
    },

});
$(document).ready(function () {
    callApiGetDoThue(urlDoThue);
    exportExcelDoThue();
});

function callApiGetDoThue(url) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function (response) {
            if (response.content.data <= 0) {
                tabDoThue.pageNumberDoThue = -1;
                tabDoThue.pageSizeDoThue = 0;
                tabDoThue.totalPageDoThue = 0;
                tabDoThue.listDoThue = [];
                tabDoThue.isLoading = false;
                return;
            }
            tabDoThue.listDoThue = response.content.data;
            tabDoThue.isLoading = false;
            tabDoThue.pageSizeDoThue = response.content.pageSize;
            tabDoThue.pageNumberDoThue = response.content.currentPage;
            tabDoThue.totalPageDoThue = response.content.totalPages;
        },
        error: function (error) {
            console.log(error);
            tabDoThue.isLoading = false;
        },
    });
}

function exportExcelDoThue() {
    $(".exportDoThue").click(() => {
        window.location.href = "http://localhost:8081/api/v1/admin/do-thue/export";
    });
}

<!-- tab 6 end -->