var tabParam = new Vue({
    el: ".tabParam",
    data: {
        listCodeParam: [],
        listParam: [],
        thongBao: "Bạn có chắc chắn thực hiện thao tác?",
        success: "Thao tác thành công!",
        error: "Thao tác thất bại!",
        param: {
            code: "",
            value: "",
            type: "TYPE01",
            chucNang: "",
            note: "OKE",
            trangThai: 0,
        },
        paramUpdate: {
            code: "",
            chucNang: "",
            value: 0,
            id: "",
            trangThai: 0,
            note: "",
            type: "",
            name: "",
        },
    },
    methods: {
        checkValue(event) {
            if (this.param.code == "" || this.param.code == null) {
                this.param.value = "";
                return;
            }
            // check la so
            if (this.param.code != "" || this.param.code != null) {
                this.checkLaSo(event, this.param.value);
            }
        },
        checkLaSo(event, valueInput) {
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                return (valueInput = event.target.value.replace(/\D/g, ""));
            }
        },
        confirmCreate() {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.createSysParam();
                }
            });
        },
        createSysParam() {
            $.ajax({
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                url: "http://localhost:8081/api/v1/admin/sys-param/create",
                data: JSON.stringify({
                    code: tabParam.param.code,
                    value: tabParam.param.value,
                    type: tabParam.param.type,
                    name: tabParam.param.chucNang,
                    note: tabParam.param.note,
                    trangThai: parseInt(tabParam.param.trangThai),
                }),
                success: function (response) {
                    if (response.statusCode == "OK") {
                        createAndShowToast("bg-success","Thông báo",tabParam.success);
                        callApiListParam();
                        return;
                    }
                    if (response.statusCode == "ACCEPTED") {
                        createAndShowToast("bg-warning","Thông báo","Bạn chỉ có thể tạo 1 lần với loại tham số này!");
                        return;
                    }
                },
                error: function (error) {
                    createAndShowToast("bg-warning","Thông báo","Không thể thực hiện hành động! Vui lòng thử lại");
                    console.log(error);
                },
            });
        },
        detailParam(id) {
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "http://localhost:8081/api/v1/admin/sys-param/by-id/" + id,
                success: function (response) {
                    tabParam.paramUpdate.id = response.content.id;
                    tabParam.paramUpdate.chucNang = response.content.chucNang;
                    tabParam.paramUpdate.code = response.content.code;
                    tabParam.paramUpdate.type = response.content.type;
                    tabParam.paramUpdate.value = response.content.value;
                    tabParam.paramUpdate.note = response.content.note;
                    tabParam.paramUpdate.name = response.content.name;
                    tabParam.paramUpdate.trangThai = parseInt(
                        response.content.trangThai
                    );
                    if (
                        tabParam.paramUpdate.code == "PHAN_TRAM_GIA_TIEN_COC" ||
                        tabParam.paramUpdate.code == "SO_COMBO_DAT"
                    ) {
                        $(".valueParamUpdate").attr("type", "number");
                    }
                },
                error: function (error) {
                    console.log(error);
                },
            });
        },
        checkValidaValueUpdate(event) {
            this.checkLaSo(event, this.paramUpdate.value);
        },
        checkValidValueSysParam(event){
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                tabParam.paramUpdate.value = 0;
                return;
            }
            if (event.target.value === "" || event.target.value === null) {
                tabParam.paramUpdate.value = 0;
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                tabParam.paramUpdate.value = event.target.value.replace(/\D/g, '');
                return;
            }
        }
        ,
        confirmUpdate() {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.updateParam();
                }
            });
        },
        updateParam() {
            $.ajax({
                type: "PUT",
                dataType: "json",
                contentType: "application/json",
                url: "http://localhost:8081/api/v1/admin/sys-param/update",
                data: JSON.stringify({
                    id: tabParam.paramUpdate.id,
                    value: tabParam.paramUpdate.value,
                    note: tabParam.paramUpdate.note,
                    chucNang: tabParam.paramUpdate.chucNang,
                    code: tabParam.paramUpdate.code,
                    name: tabParam.paramUpdate.name,
                    type: tabParam.paramUpdate.type,
                    trangThai: 0,
                }),
                success: function (response) {
                    if (response.statusCode == "OK") {
                        createAndShowToast("bg-success","Thông báo",tabParam.success);
                        $("#detailParam").modal("toggle");
                        $(".modal-backdrop").remove();
                        callApiListParam();
                        return;
                    }
                },
                error: function (error) {
                    createAndShowToast("bg-warning","Thông báo","Không thể thực hiện hành động! Vui lòng thử lại");
                    console.log(error);
                },
            });
        },
        confirmDelete(id) {
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện thao tác này?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        type: "DELETE",
                        url:
                            "http://localhost:8081/api/v1/admin/sys-param/delete/" + id,
                        success: function (response) {
                            if (response.statusCode == "OK") {
                                createAndShowToast("bg-success","Thông báo",tabParam.success);
                                callApiListParam();
                            }
                        },
                        error: function (error) {
                            createAndShowToast("bg-warning","Thông báo","Không thể thực hiện hành động! Vui lòng thử lại");
                            console.log(error);
                        },
                    });
                }
            });
        },
    },
});
$(document).ready(() => {
    callApiListCodePaRam();
    checkLoaiThamSo();
    callApiListParam();
});

function callApiListParam() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8081/api/v1/admin/sys-param/find-all",
        success: function (response) {
            tabParam.listParam = response.content;
        },
        error: function (error) {
            console.log(error);
        },
    });
}

function callApiListCodePaRam() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8081/api/v1/admin/sys-param/list-code",
        success: function (response) {
            tabParam.listCodeParam = response.content;
        },
        error: function (error) {
            console.log(error);
        },
    });
}

function checkLoaiThamSo() {
    $(".loaiThamSo").change((event) => {
        if (
            event.target.value == "PHAN_TRAM_GIA_TIEN_COC" ||
            event.target.value == "SO_COMBO_DAT"
        ) {
            $(".valueParam").attr("type", "number");
            $(".valueParam").attr("min", 0);
            $(".valueParam").attr("max", 100);
            return;
        }
        $(".valueParam").attr("type", "text");
        $(".valueParam").removeAttr("min");
        $(".valueParam").removeAttr("max");
    });
}
