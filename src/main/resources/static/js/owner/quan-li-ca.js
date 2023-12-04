var urlCa = "http://localhost:8081/api/v1/admin/ca/find-all";
$(document).ready(()=>{
    callApiCa(urlCa);
    exportExcelCa();
});

function exportExcelCa(){
    $(".exportExcelCa").click(()=>{
       window.location.href = "http://localhost:8081/api/v1/admin/ca/export";
    });
}


function callApiCa(url){
    $.ajax({
       type:"GET",
       url: url,
       success: function (response){
           tabCa.listCaAdminResponse = response.content.data;
           tabCa.pageNumberCa = response.content.currentPage;
           tabCa.totalPageCa = response.content.totalPages;
           tabCa.pageSizeCa = response.content.pageSize;
       } ,
        error: function(error){
            console.log(error);
        }
    });
}

var  tabCa = new Vue({
    el:".tabCa",
    data:{
        listCaAdminResponse:[],
        isReadOnly: true,
        thongBao:"Bạn có chắc chắn thực hiện hành động này không?",
        success:"Thao tác thành công",
        error: "Thao tác thất bại",
        nameSearchCa: "",
        indexSeachCa:0,
        pageNumberCa:0,
        totalPageCa:0,
        pageSizeCa:0,
        caCreate:{
            tenCa:"",
            giaCa:0,
            thoiGianBatDau:"",
            thoiGianKetThuc:"",
            trangThai:0,
        },
        caDetail:{
            id:"",
            tenCa:"",
            giaCa:0,
            thoiGianBatDau:"",
            thoiGianKetThuc:"",
            trangThai:0,
        }
    },
    methods:{
        createCaConfirm(event){
            Swal.fire({
                title: this.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    const thoiGianBatDau = moment(this.caCreate.thoiGianBatDau,"HH:mm").format("HH:mm:ss");
                    const thoiGianKetThuc = moment(this.caCreate.thoiGianKetThuc,"HH:mm").format("HH:mm:ss");
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json",
                        url:"http://localhost:8081/api/v1/admin/ca/create",
                        data:JSON.stringify({
                            tenCa: this.caCreate.tenCa,
                            thoiGianBatDau: thoiGianBatDau,
                            thoiGianKetThuc: thoiGianKetThuc,
                            giaCa: this.caCreate.giaCa==0?0:this.caCreate.giaCa.replace(/\D/g, ''),
                            trangThai: this.caCreate.trangThai
                        }),
                        success: function(response){
                            console.log(response);
                            if(response.statusCode ==='OK'){
                                createAndShowToast("bg-success","Thông báo!",tabCa.success);
                                callApiCa(urlCa);
                            }else if(response.statusCode ==='ALREADY_REPORTED'){
                                createAndShowToast("bg-warning","Thông báo!","Ca bạn thêm đã tồn tại trước đó hoặc có thể là nó nằm trong khoảng thời gian nghỉ giữa ca! Vui lòng thay đổi!");
                            }else{
                                createAndShowToast("bg-danger","Thông báo!",tabCa.error);
                            }
                        },
                        error: function(error){
                            console.log(error);
                        }
                    })
                }
            });
        },
       updateCaConfirm(event){
            Swal.fire({
                title: this.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    const thoiGianBatDau = moment(this.caDetail.thoiGianBatDau,"HH:mm").format("HH:mm:ss");
                    const thoiGianKetThuc = moment(this.caDetail.thoiGianKetThuc,"HH:mm").format("HH:mm:ss");
                    $.ajax({
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json",
                        url:"http://localhost:8081/api/v1/admin/ca/update",
                        data:JSON.stringify({
                            id: this.caDetail.id,
                            tenCa: this.caDetail.tenCa,
                            thoiGianBatDau: thoiGianBatDau,
                            thoiGianKetThuc: thoiGianKetThuc,
                            giaCa: this.caDetail.giaCa==0?0:this.caDetail.giaCa.replace(/\D/g, ''),
                            trangThai: this.caDetail.trangThai
                        }),
                        success: function(response){
                            console.log(response);
                            if(response.statusCode ==='OK'){
                                createAndShowToast("bg-success","Thông báo!",tabCa.success);
                                callApiCa(urlCa);
                                $(".btnModalCloseDetailCa").click();
                            }else if(response.statusCode ==='ALREADY_REPORTED'){
                                createAndShowToast("bg-warning","Thông báo!","Ca bạn thêm đã tồn tại trước đó ! Vui lòng thay đổi!");
                            }else{
                                createAndShowToast("bg-danger","Thông báo!",tabCa.error);
                            }
                        },
                        error: function(error){
                            console.log(error);
                        }
                    })
                }
            });
        },
        detailCaAdmin(id){
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/v1/admin/ca/find/"+id,
                success: function(response){
                    tabCa.caDetail.id = response.content.id;
                    tabCa.caDetail.tenCa = response.content.tenCa;
                    tabCa.caDetail.giaCa = response.content.giaCa.toLocaleString("vi-VN");
                    tabCa.caDetail.thoiGianBatDau = response.content.thoiGianBatDau;
                    tabCa.caDetail.thoiGianKetThuc = response.content.thoiGianKetThuc;
                    tabCa.caDetail.trangThai = response.content.trangThai;
                },
                error: function(error){
                    console.log(error);
                    createAndShowToast("bg-danger","Thông báo!",tabCa.error);
                }
            })
        },
        deleteCaConfirm(id){
            Swal.fire({
                title: this.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        type: "Delete",
                        url:"http://localhost:8081/api/v1/admin/ca/delete/"+id,
                        success: function(response){
                            console.log(response);
                            if(response.statusCode ==='OK'){
                                createAndShowToast("bg-success","Thông báo!",tabCa.success);
                                callApiCa(urlCa);
                            }else{
                                createAndShowToast("bg-danger","Thông báo!",tabCa.error);
                            }
                        },
                        error: function(error){
                            console.log(error);
                        }
                    })
                }
            });
        },
        handleImportExcelCa(event){
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
                            url: "http://localhost:8081/api/v1/admin/ca/import",
                            dataType: "json",
                            contentType: false,
                            processData: false,
                            data: formData,
                            success: function (reponse) {
                                var arrTBCa = [];
                                if (reponse.statusCode === 'OK') {
                                    callApiCa(urlCa);
                                    createAndShowToast("bg-success", "Thông báo!", "Import file thành công!");
                                    event.target.value = "";
                                } else if (reponse.statusCode === 'ALREADY_REPORTED'){
                                    console.log(reponse.statusCode)
                                    console.log(reponse)
                                    reponse.content.forEach((items)=>{
                                        var warningCa = ["bg-warning", "Thông báo", "Ca bạn thêm đã tồn tại trước đó có thời gian: "+items.timeStart +"-"+items.timeEnd+" ! Vui lòng thay đổi!"];
                                        arrTBCa.push(warningCa);
                                    });
                                    createMultipleToasts(arrTBCa);
                                    event.target.value = "";
                                }
                                else {
                                    console.log(reponse.statusCode)
                                    console.log(reponse)
                                    createAndShowToast("bg-warning", "Thông báo!", "Vui lòng để đúng thứ tự các trường và kiểu dữ liệu không để trống hay khác kiểu! Không bạn sẽ không import được đâu");
                                    event.target.value = "";
                                }

                            },
                            error: function (error) {
                                console.log(error);
                                event.target.value = "";
                                createAndShowToast("bg-warning", "Thông báo!", "Vui lòng để đúng thứ tự các trường như file export excel! Không cần cột STT nhưng phải đúng thứ tự các trường!");
                            }
                        })
                    }else {
                        $(".caImportExcel").val("");
                    }
                });

            } else {
                // Đây không phải là một tệp Excel, thông báo hoặc xử lý tương ứng
                createAndShowToast("bg-warning", "Thông báo!", "Đây không phải là một tệp Excel, vui lòng chọn lại!");
                event.target.value = ""; // Xóa giá trị tệp đã chọn (nếu muốn)
            }
        },
        handleDonGiaCa(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                this.caCreate.giaCa = 0;
                return;
            }
            if (event.target.value === "" || event.target.value === null) {
                this.caCreate.giaCa = 0;
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
               var priceCaFormat = event.target.value.replace(/\D/g, '');
                this.caCreate.giaCa = parseInt(priceCaFormat).toLocaleString("vi-VN");
                return;
            }
            this.caCreate.giaCa = parseInt(event.target.value).toLocaleString("vi-VN");
        },
        handleGiaCaDetail(event) {
            if(event.target.value.replace(/\D/g, "") == null || event.target.value.replace(/\D/g, "") == ''){
                this.caDetail.giaCa = 0;
                return;
            }
            if (event.target.value === "" || event.target.value === null) {
                this.caDetail.giaCa = 0;
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                var priceCaFormat = event.target.value.replace(/\D/g, '');
                this.caDetail.giaCa = parseInt(priceCaFormat).toLocaleString("vi-VN");
                return;
            }
            this.caDetail.giaCa = parseInt(event.target.value).toLocaleString("vi-VN");
        },
        tinhPhutNgay(value) {
            return new Date(`2000-01-01T${value}`);
        },
        inputThoiGianBatDau(event) {
            if(event.target.value != null || event.target.value != ''){
                this.isReadOnly = false;
            }
        },
        inputDetailThoiGianBatDau(event) {
            const dateBD = this.tinhPhutNgay(this.caDetail.thoiGianBatDau);
            const dateKT = this.tinhPhutNgay(this.caDetail.thoiGianKetThuc);
            if(dateKT <= dateBD){
                createAndShowToast("bg-warning","Thông báo!","Giờ kết thúc vui lòng phải lớn hơn Giờ bắt đầu");
                this.caDetail.thoiGianBatDau = "";
                return;
            }
        },
        inputThoiGianKetThucCa(event){
            const dateBD = this.tinhPhutNgay(this.caCreate.thoiGianBatDau);
            const dateKT = this.tinhPhutNgay(this.caCreate.thoiGianKetThuc);
            if(dateKT <= dateBD){
                createAndShowToast("bg-warning","Thông báo!","Giờ kết thúc vui lòng phải lớn hơn Giờ bắt đầu");
                this.caCreate.thoiGianKetThuc = "";
                return;
            }

            // const phutBatDau = new Date(`2000-01-01T${this.caCreate.thoiGianBatDau}`).getTime();
            // const phutKetThuc = new Date(`2000-01-01T${this.caCreate.thoiGianKetThuc}`).getTime();
            // const  phutGiuaThoiGianBatDauVaThoiGianKetThuc = Math.abs((phutKetThuc - phutBatDau)/60000);
            // console.log(phutGiuaThoiGianBatDauVaThoiGianKetThuc);
            // if(phutGiuaThoiGianBatDauVaThoiGianKetThuc < 90){
            //     createAndShowToast("bg-warning","Thông báo!","Khoảng cách giữa 2 giờ phải là 1 tiếng 30p");
            //     this.caCreate.thoiGianKetThuc = "";
            //     return;
            // }

        },
        inputDetailThoiGianKetThuc(event) {
            const dateBD = this.tinhPhutNgay(this.caDetail.thoiGianBatDau);
            const dateKT = this.tinhPhutNgay(this.caDetail.thoiGianKetThuc);
            if(dateKT <= dateBD){
                createAndShowToast("bg-warning","Thông báo!","Giờ kết thúc vui lòng phải lớn hơn Giờ bắt đầu");
                this.caDetail.thoiGianKetThuc = "";
                return;
            }
        },
        searchByNameCa(event){
            $.ajax({
                url:"http://localhost:8081/api/v1/admin/ca/search?name="+tabCa.nameSearchCa,
                type:"GET",
                success: function(response){
                    if(response.statusCode === 'OK'){
                        tabCa.listCaAdminResponse = response.content.data;
                        tabCa.pageNumberCa = response.content.currentPage;
                        tabCa.totalPageCa = response.content.totalPages;
                        tabCa.pageSizeCa = response.content.pageSize;
                        tabCa.indexSeachCa = 1;
                        return;
                    }
                    tabCa.indexSeachCa = 0;
                },
                error:function (error){
                    console.log(error);
                }
            })
        },
        pageTionCa(value) {
            if (parseInt(tabCa.indexSeachCa) === 1) {
                urlCa = "http://localhost:8081/api/v1/admin/ca/search?name=" +
                    tabCa.nameSearchCa +
                    "&page=" +
                    value + "&size=" + tabCa.pageSizeCa;
            } else {
                urlCa = "http://localhost:8081/api/v1/admin/ca/find-all?page=" + value + "&size=" + tabCa.pageSizeCa;

            }
            callApiCa(urlCa);
        },
        nextPageCa() {
            if (parseInt(tabCa.pageNumberCa) + 1 == parseInt(tabCa.totalPageCa)) {
                return;
            }
            this.pageTionCa(parseInt(tabCa.pageNumberCa) + 1);
        },
        previuosCa() {
            if (parseInt(tabCa.pageNumberCa) == 0) {
                return;
            }
            this.pageTionCa(parseInt(tabCa.pageNumberCa) - 1);
        },
        pageSizeSelectCa(event) {
            if (parseInt(tabCa.indexSeachCa) === 1) {
                urlCa = "http://localhost:8081/api/v1/admin/ca/search?name=" +
                    tabCa.nameSearchCa +
                    "&size=" + event.target.value;
            } else {
                urlCa = "http://localhost:8081/api/v1/admin/ca/find-all?size=" + event.target.value;

            }
            callApiCa(urlCa);
        },
        currencyVnCa(number){
            return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
        }
    }
})





//custom time
// document.addEventListener('DOMContentLoaded', function() {
//     flatpickr('#customTimeInput', {
//         enableTime: true,
//         noCalendar: true,
//         dateFormat: 'H:i',
//         time_24hr: true,
//         minuteIncrement: 15,
//         onOpen: function(selectedDates, dateStr, instance) {
//             // Lắng nghe sự kiện mở dropdown và ngăn chặn sự kiện nhập bằng tay
//             instance.calendarContainer.addEventListener('keydown', function(e) {
//                 e.preventDefault();
//             });
//         }
//     });
// });
// //custom time